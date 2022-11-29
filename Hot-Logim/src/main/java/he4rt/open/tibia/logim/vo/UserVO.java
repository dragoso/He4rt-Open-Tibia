package he4rt.open.tibia.logim.vo;

import he4rt.open.tibia.base.game.UserAB;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UserVO extends UserAB {

    @Override
    public void disconnect() {

        close();

    }
}
