package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.physics.Body;

public class MoveService {
    public enum Direction {
        right, left
    }
    // TODO: Consider loading constants from config file and making MoveService instance dependent.
    private static final float MOVE_IMPULSE_X = 1f;
    private static final float MAX_VELOCITY_X = 5f;
    private static Vector2 getMoveImpulse(Direction direction) {
        return new Vector2(direction == Direction.right ? MOVE_IMPULSE_X : -MOVE_IMPULSE_X, 0f);
    }
    public static void movePlayer(PhysicalPlayer player, Direction direction) {
        Body body = player.body();
        if ((direction == Direction.right && body.getLinearVelocity().x < MAX_VELOCITY_X) || (direction == Direction.left && body.getLinearVelocity().x > -MAX_VELOCITY_X))
            body.applyLinearImpulse(MoveService.getMoveImpulse(direction));
    }
    private static final Vector2 JUMP_IMPULSE = new Vector2(0f, 20f);
    // Trzeba będzie pewnie pamiętać czas od opuszczenia ziemi, żeby móc jakieś bardziej skomplikowane skoki robić np.
    // Jakby to było potrzebne, to raczej na zewnątrz co world step trzeba by zbierać dane i tutaj już podawać jako argument metody, więc static ok.
    public static void jumpPlayer(PhysicalPlayer player) {
        Body body = player.body();
        if (Math.abs(body.getLinearVelocity().y) <= 0.01) { // Only jump if on the ground
            body.applyLinearImpulse(JUMP_IMPULSE);
        }
    }
}
