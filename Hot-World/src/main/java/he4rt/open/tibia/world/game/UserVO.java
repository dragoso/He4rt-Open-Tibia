package he4rt.open.tibia.world.game;

import he4rt.open.tibia.base.data.entity.UserEntity;
import he4rt.open.tibia.base.game.UserAB;
import he4rt.open.tibia.base.utils.ScheduleUtils;
import he4rt.open.tibia.world.config.IntegrationConfig;
import he4rt.open.tibia.world.game.player.PlayerVO;
import he4rt.open.tibia.world.handler.pong.PongJob;
import he4rt.open.tibia.world.handler.pong.PongPacket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Getter
@Setter
@SuperBuilder
public class UserVO extends UserAB {

    private PlayerVO playerVO;
    private UserEntity userEntity;
    private IntegrationConfig integrationConfig;

    private static int PING_INTERVAL = 5000;
    private long lastPing;
    private boolean recvFirstPacket;

    private Map<Object, ScheduledFuture<?>> schedules;

    public void disconnect() {
        close();
    }

    public void pinValidator() {

        //send(PongPacket.encodePing(getUserEntity().getLangue()));

        //integrationConfig.getTaskScheduler().schedule(new PongJob(this), Instant.now().plusSeconds(15));
    }

    public void makerPlayerSchedule(Runnable runnable, Object validator, int time) {

        ScheduledFuture<?> storageSchedule = getSchedules().get(validator);
        ScheduleUtils.cancel(storageSchedule, true);

        storageSchedule = integrationConfig.getTaskScheduler().schedule(runnable, Instant.now().plusMillis(time));
        getSchedules().put(validator, storageSchedule);

    }

}
