package he4rt.open.tibia.world.game.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MapTileEnum {

    OTBM_TILEFLAG_PROTECTIONZONE(1),
    OTBM_TILEFLAG_NOPVPZONE(4),
    OTBM_TILEFLAG_NOLOGOUT(8),
    OTBM_TILEFLAG_PVPZONE(16);

    private final int value;

}
