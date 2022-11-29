package he4rt.open.tibia.world.game.map.tile;

import he4rt.open.tibia.base.core.io.vo.OutputBaosVO;
import he4rt.open.tibia.base.core.tibia.Position;
import he4rt.open.tibia.world.game.creature.ICreature;
import he4rt.open.tibia.world.game.map.item.ItemVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Synchronized;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Objects;

@Setter
@Getter
@RequiredArgsConstructor
@SuperBuilder
public class TileVO {

    private final Position position;

    private ItemVO ground;

    @Getter(onMethod_ = {@Synchronized})
    private LinkedList<ItemVO> downItems;
    @Getter(onMethod_ = {@Synchronized})
    private LinkedList<ItemVO> topItems;
    @Getter(onMethod_ = {@Synchronized})
    private LinkedHashSet<ICreature> creatures;

    private long flag;

    public void addFlag(TileEnum flag) {
        this.flag |= flag.getValue();
    }

    public void encodeToClient(OutputBaosVO outputBaosVO) {

        int count = 0;

        if (Objects.nonNull(ground)) {
            outputBaosVO.writeShort(ground.getItemBase().getClientId());
            count++;
        }

        for (ItemVO topItem : topItems) {
            outputBaosVO.writeShort(topItem.getItemBase().getClientId());
            count ++;
        }

        for (ICreature iCreature : creatures) {
            outputBaosVO.writeShort(97);// tipo para player visivel 98 = não visível
            outputBaosVO.writeInt(0);//??
            outputBaosVO.writeInt(iCreature.getId());//??
            outputBaosVO.writeString(iCreature.getName());
            outputBaosVO.write((int) Math.ceil((double) iCreature.getHealth() / Math.max(iCreature.getMaxHealth(), 1)) * 100);
            outputBaosVO.write(iCreature.getDirection());
            outputBaosVO.writeShort(iCreature.getLookType());
            outputBaosVO.write(iCreature.getLookHead());//outfit head
            outputBaosVO.write(iCreature.getLookBody());//outfit torso
            outputBaosVO.write(iCreature.getLookLegs());//outfit legs
            outputBaosVO.write(iCreature.getLookFeet());//outfit detail
            outputBaosVO.write(iCreature.getLookAddons());//outfit addons
            outputBaosVO.write(0);// brilho??????
            outputBaosVO.write(0);// light color
            outputBaosVO.writeShort(iCreature.getSeed());
            outputBaosVO.write(0);// PK FLAG
            outputBaosVO.write(0);// PARTY FLAG
            outputBaosVO.write(0);// GUILD FLAG
            outputBaosVO.write(1);// Unpassable FLAG
            count ++;
        }

        for (ItemVO downItem : downItems) {
            outputBaosVO.writeShort(downItem.getItemId());
            count ++;

            if (++count == 10)
            {
                return;
            }
        }
    }
}
