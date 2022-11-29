package he4rt.open.tibia.world.game.map.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@SuperBuilder
public class ItemVO {

    private int itemId;
    private int count;
    private Map<ItemAtributesServerEnum, Object> atributes;
    private ItemType itemBase;

}
