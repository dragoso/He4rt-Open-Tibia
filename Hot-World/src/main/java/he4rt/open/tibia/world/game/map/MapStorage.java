package he4rt.open.tibia.world.game.map;

import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import he4rt.open.tibia.base.core.io.vo.OutputBaosVO;
import he4rt.open.tibia.base.core.tibia.Position;
import he4rt.open.tibia.world.game.io.BinaryNode;
import he4rt.open.tibia.world.game.map.house.HouseTileVO;
import he4rt.open.tibia.world.game.map.house.HouseVO;
import he4rt.open.tibia.world.game.map.item.ItemAtributesServerEnum;
import he4rt.open.tibia.world.game.map.item.ItemComponent;
import he4rt.open.tibia.world.game.map.item.ItemType;
import he4rt.open.tibia.world.game.map.item.ItemVO;
import he4rt.open.tibia.world.game.map.tile.TileEnum;
import he4rt.open.tibia.world.game.map.tile.TileVO;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Log4j2
@Getter
@Component
public class MapStorage {

    private final ItemComponent itemComponent;

    private final long version;
    private final Integer width;
    private final Integer height;

    private final long minVersion;
    private final long majVersion;

    private String title;
    private String description;

    private String monsterName;
    private String npcName;
    private String houseName;

    private Map<Integer, MapTownVO> towns = new HashMap();
    private Map<Long, HouseVO> houses = new HashMap();
    private Map<Position, TileVO> tiles = new HashMap();

    public MapStorage(ItemComponent itemComponent, Properties configProperties) throws IOException {
        this.itemComponent = itemComponent;

        BinaryNode root = BinaryNode.load(String.format("%s%s%s%s", configProperties.getProperty("directoryData"), "world/", configProperties.getProperty("mapName"), ".otbm"));

        log.info("Start desserialize map...");
        InputBaosVO inputBaosVO = root.generateInputBaos();

        version = inputBaosVO.readInt();
        width = (int) inputBaosVO.readShort();
        height = (int) inputBaosVO.readShort();
        minVersion = inputBaosVO.readInt();
        majVersion = inputBaosVO.readInt();

        BinaryNode map = root.getFirstChild();
        if (!Objects.equals(MapNodesEnum.getByInt(map.getType()), MapNodesEnum.OTBM_MAP_DATA)) {
            throw new IOException("Unexcpected node type.");
        }

        inputBaosVO = map.generateInputBaos();

        generateHeader(inputBaosVO);

        for (BinaryNode mapData : map.getChildren()) {
            inputBaosVO = mapData.generateInputBaos();

            if (Objects.equals(MapNodesEnum.getByInt(mapData.getType()), MapNodesEnum.OTBM_TILE_AREA)) {

                generateTiles(inputBaosVO, mapData);

            } else if (Objects.equals(MapNodesEnum.getByInt(mapData.getType()), MapNodesEnum.OTBM_TOWNS)) {

                generateTowns(mapData);
            }
        }

        log.info("End desserialize map. Map size: <{}>-<{}>...", getWidth(), getHeight());
    }

    private void generateTowns(BinaryNode mapData) throws IOException {
        for (BinaryNode townNode : mapData.getChildren()) {
            if (Objects.equals(MapNodesEnum.getByInt(townNode.getType()), MapNodesEnum.OTBM_TOWN)) {
                InputBaosVO inputBaosVO = townNode.generateInputBaos();
                MapTownVO town = MapTownVO
                        .builder()
                        .id((int) inputBaosVO.readInt())
                        .name(inputBaosVO.readString())
                        .temple(Position.builder().x(inputBaosVO.readShort()).y(inputBaosVO.readShort()).z(inputBaosVO.readByte()).build())
                        .build();

                getTowns().put(town.getId(), town);
            } else {
                throw new IOException("Unknown node type " + townNode.getType() + ".");
            }
        }
    }

    private void generateTiles(InputBaosVO in, BinaryNode mapData) {
        int baseX = in.readShort();
        int baseY = in.readShort();
        int baseZ = in.readByte();

        for (BinaryNode tileNode : mapData.getChildren()) {
            in = tileNode.generateInputBaos();
            if (Objects.equals(MapNodesEnum.getByInt(tileNode.getType()), MapNodesEnum.OTBM_TILE) || Objects.equals(tileNode.getType(), MapNodesEnum.OTBM_HOUSETILE)) {

                TileVO tileVO = TileVO
                        .builder()
                        .position(Position.builder().x(baseX + in.readByte()).y(baseY + in.readByte()).z(baseZ).build())
                        .flag(TileEnum.TILESTATE_NONE.getValue())
                        .topItems(new LinkedList())
                        .downItems(new LinkedList())
                        .creatures(new LinkedHashSet())
                        .build();

                if (Objects.equals(MapNodesEnum.getByInt(tileNode.getType()), MapNodesEnum.OTBM_HOUSETILE)) {

                    long houseId = in.readInt();
                    HouseVO house = getHouses().containsKey(houseId) ? getHouses().get(houseId) : new HouseVO(houseId);

                    tileVO = HouseTileVO
                            .builder()
                            .houseVO(house)
                            .position(tileVO.getPosition())
                            .flag(TileEnum.TILESTATE_NONE.getValue())
                            .topItems(new LinkedList())
                            .downItems(new LinkedList())
                            .creatures(new LinkedHashSet())
                            .build();

                    house.getTiles().add((HouseTileVO) tileVO);
                }

                //read tile attributes
                while (in.available() > 0) {

                    MapEnum attribute = MapEnum.getByInt(in.readByte());

                    if (Objects.equals(MapEnum.OTBM_ATTR_TILE_FLAGS, attribute)) {

                        long flags = in.readInt();

                        if (TileEnum.TILESTATE_PROTECTIONZONE.hasFlag(flags)) {
                            tileVO.addFlag(TileEnum.TILESTATE_PROTECTIONZONE);
                        } else if (TileEnum.TILESTATE_NOPVPZONE.hasFlag(flags)) {
                            tileVO.addFlag(TileEnum.TILESTATE_NOPVPZONE);
                        } else if (TileEnum.TILESTATE_PVPZONE.hasFlag(flags)) {
                            tileVO.addFlag(TileEnum.TILESTATE_PVPZONE);
                        }

                        if (TileEnum.TILESTATE_NOLOGOUT.hasFlag(flags)) {
                            tileVO.addFlag(TileEnum.TILESTATE_NOLOGOUT);
                        }

                    } else if (Objects.equals(attribute, MapEnum.OTBM_ATTR_ITEM)) {

                        int id = in.readShort();
                        ItemType itemType = itemComponent.getItens().get(id);

                        if (Objects.isNull(itemType)) {
                            log.info("Item id <{}> not founded in store.", id);
                            continue;
                        }

                        //TODO ADD FLOOR DIRECTION
                        if (Objects.equals(itemType.getGroup(), ItemType.Group.GROUND)) {

                            tileVO.setGround(ItemVO
                                    .builder()
                                    .itemId((int) id)
                                    .count(0)
                                    .itemBase(itemType)
                                    .atributes(new HashMap())
                                    .build()
                            );

                            continue;
                        }
                        log.info("Novo tile?");
                    }
                }

                for (BinaryNode itemNode : tileNode.getChildren()) {
                    InputBaosVO iin = itemNode.generateInputBaos();
                    if (Objects.equals(MapNodesEnum.getByInt(itemNode.getType()), MapNodesEnum.OTBM_ITEM)) {

                        int id = iin.readShort();
                        ItemType itemType = itemComponent.getItens().get(id);

                        switch (id) {
                            case 1487:
                                id = 1492;
                                break;

                            case 1488:
                                id = 1493;
                                break;

                            case 1489:
                                id = 1494;
                                break;

                            case 1491:
                                id = 1495;
                                break;

                            case 1490:
                                id = 1496;
                                break;

                            case 1497:
                                id = 1498;
                                break;

                            case 1499:
                                id = 2721;
                                break;
                        }

                        Map<ItemAtributesServerEnum, Object> atributes = new HashMap();

                        while (iin.getBytes().length > iin.getPosition()) {

                            ItemAtributesServerEnum itemAtributesServerEnum = ItemAtributesServerEnum.getByInt(iin.readByte());

                            switch (itemAtributesServerEnum) {

                                case ATTR_COUNT:
                                case ATTR_RUNE_CHARGES:
                                case ATTR_DECAYING_STATE:
                                case ATTR_HOUSEDOORID:
                                    atributes.put(itemAtributesServerEnum, iin.readByte());
                                    break;

                                case ATTR_ACTION_ID:
                                case ATTR_UNIQUE_ID:
                                case ATTR_CHARGES:
                                case ATTR_EXTRADEFENSE:
                                case ATTR_DEPOT_ID:
                                    atributes.put(itemAtributesServerEnum, iin.readShort());
                                    break;

                                case ATTR_TEXT:
                                case ATTR_WRITTENBY:
                                case ATTR_DESCRIPTION:
                                case ATTR_NAME:
                                case ATTR_ARTICLE:
                                case ATTR_PLURALNAME:
                                    atributes.put(itemAtributesServerEnum, iin.readString());
                                    break;

                                case ATTR_WRITTENDATE:
                                case ATTR_WEIGHT:
                                case ATTR_ATTACK:
                                case ATTR_DEFENSE:
                                case ATTR_ARMOR:
                                case ATTR_HITCHANCE:
                                case ATTR_SHOOTRANGE:
                                case ATTR_DECAYTO:
                                case ATTR_SLEEPERGUID:
                                case ATTR_SLEEPSTART:
                                case ATTR_CONTAINER_ITEMS:
                                    atributes.put(itemAtributesServerEnum, iin.readInt());
                                    break;

                                case ATTR_DURATION:
                                    atributes.put(itemAtributesServerEnum, Math.max(0, iin.readInt()));
                                    break;

                                case ATTR_TELE_DEST:
                                    atributes.put(itemAtributesServerEnum, iin.readPosition());
                                    break;

                                case ATTR_CUSTOM_ATTRIBUTES:
                                    log.info("asdasd");
                                    break;

                            }

                        }

                        if(Objects.nonNull(itemType)) {
                            if (itemType.getAlwaysOnTopOrder() > 0) {

                                tileVO.getTopItems().add(ItemVO
                                        .builder()
                                        .itemId(id)
                                        .count(1)
                                        .itemBase(itemType)
                                        .atributes(atributes)
                                        .build()
                                );

                            } else {

                                tileVO.getDownItems().add(ItemVO
                                        .builder()
                                        .itemId(id)
                                        .count(1)
                                        .itemBase(itemType)
                                        .atributes(atributes)
                                        .build()
                                );
                            }
                        }

                    }
                }

                tileVO.getTopItems().sort(Collections.reverseOrder());
                tileVO.getDownItems().sort(Collections.reverseOrder());

                tiles.put(tileVO.getPosition(), tileVO);
            }
        }
    }

    private void generateHeader(InputBaosVO in) {
        while (in.available() > 0) {
            MapEnum mapEnum = MapEnum.getByInt(in.readByte());
            switch (mapEnum) {
                case OTBM_ATTR_DESCRIPTION -> {
                    if (Objects.isNull(title))
                        title = in.readString();
                    else
                        description = in.readString();
                }
                case OTBM_ATTR_EXT_SPAWN_MONSTER_FILE -> monsterName = in.readString();
                case OTBM_ATTR_EXT_SPAWN_NPC_FILE -> npcName = in.readString();
                case OTBM_ATTR_EXT_HOUSE_FILE -> houseName = in.readString();
            }
        }
    }

    public void getMapArea(int x, int y, int z, OutputBaosVO outputBaosVO) {

        int skip = -1;

        int crawlTo = z > 7 ? Math.min(15, z + 2) : 0;
        int crawlFrom = z > 7 ? z - 2 : 7;
        int crawlDelta = z > 7 ? 1 : -1;


        for (int nz = crawlFrom; nz != crawlTo + crawlDelta; nz += crawlDelta) {
            skip = getObjectsInRange(x, y, nz, z - nz, skip, outputBaosVO);
        }


        if (skip >= 0) {
            outputBaosVO.write(skip);
            outputBaosVO.write(0xFF);
        }

    }

    public int getObjectsInRange(int x, int y, int z, int verticalOffset, int skip, OutputBaosVO outputBaosVO) {

        int windowSizeX = 18;
        int windowSizeY = 14;

        int start = 0xFE;
        int end = 0xFF;

        for (int nx = 0; nx < windowSizeX; nx++)
            for (int ny = 0; ny < windowSizeY; ny++) {

                Position position = Position
                        .builder()
                        .x(x + nx + verticalOffset)
                        .y(y + ny + verticalOffset)
                        .z(z)
                        .build();

                TileVO tileVO = tiles.get(position);

                if (Objects.nonNull(tileVO)) {

                    if (skip >= 0) {
                        outputBaosVO.write(skip);
                        outputBaosVO.write(end);
                    }

                    skip = 0;

                    tileVO.encodeToClient(outputBaosVO);

                } else if (skip == start) {
                    outputBaosVO.write(end);
                    outputBaosVO.write(end);
                    skip = -1;
                } else {
                    ++skip;
                }

            }

        return skip;
    }
}
