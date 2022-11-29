package he4rt.open.tibia.world.handler.pong;

import he4rt.open.tibia.world.game.UserVO;

public record PongJob(UserVO userVO) implements Runnable {

    @Override
    public void run() {
        long now = System.currentTimeMillis();
        if (userVO.getLastPing() - now > 16000) {
            userVO.disconnect();
        }
    }
}
