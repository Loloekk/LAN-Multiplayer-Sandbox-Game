package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.physics.Body;

public class MoveService {
    public enum Direction {
        right, left
    }
    private static final float MOVE_IMPULSE = 0.8f;
    private static float getMoveImpulse(Direction direction) {
        return direction == Direction.right ? MOVE_IMPULSE : -MOVE_IMPULSE;
    }
    public static void movePlayer(PhysicalPlayer player, Direction direction) {
        Body body = player.body();
        Vector2 center = body.getWorldCenter();
        body.applyLinearImpulse(MoveService.getMoveImpulse(direction), 0, center.x, center.y, true);
    }
    private static final float JUMP_IMPULSE = 5f;
    // Trzeba będzie pewnie pamiętać czas od opuszczenia ziemi, żeby móc jakieś bardziej skomplikowane skoki robić np.
    // Jakby to było potrzebne, to raczej na zewnątrz co world step trzeba by zbierać dane i tutaj już podawać jako argument metody, więc static ok.
    public void jumpPlayer(PhysicalPlayer player) {
        Body body = player.body();
        Vector2 center = body.getWorldCenter();
        if (body.getLinearVelocity().y == 0) { // Only jump if on the ground
            body.applyLinearImpulse(0, JUMP_IMPULSE, center.x, center.y, true);
        }
    }
}
