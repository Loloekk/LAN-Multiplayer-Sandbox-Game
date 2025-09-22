//package io.github.terraria.logic.actions;
//
//import com.badlogic.gdx.math.Vector2;
//import io.github.terraria.common.Config;
//import io.github.terraria.logic.creatures.CreatureBody;
//import io.github.terraria.logic.physics.Body;
//import io.github.terraria.logic.players.PhysicalPlayer;
//
//public class MoveService {
//    public enum Direction {
//        right, left
//    }
//    // TODO: Consider loading constants from config file and making MoveService instance dependent.
//    private static final float MOVE_IMPULSE_X = Config.MOVE_IMPULSE_X;
//    private static final float MAX_VELOCITY_X = Config.MAX_PLAYER_VELOCITY_X;
//    private static Vector2 getMoveImpulse(Direction direction) {
//        return new Vector2(direction == Direction.right ? MOVE_IMPULSE_X : -MOVE_IMPULSE_X, 0f);
//    }
//    public static void movePlayer(PhysicalPlayer player, Direction direction) {
//        CreatureBody body = player.body();
//        if ((direction == Direction.right && body.getLinearVelocity().x < MAX_VELOCITY_X) || (direction == Direction.left && body.getLinearVelocity().x > -MAX_VELOCITY_X))
//            body.applyLinearImpulse(MoveService.getMoveImpulse(direction));
//    }
//    private static final Vector2 JUMP_IMPULSE = new Vector2(0f, Config.PLAYER_JUMP_STRENGTH);
//    // Trzeba będzie pewnie pamiętać czas od opuszczenia ziemi, żeby móc jakieś bardziej skomplikowane skoki robić np.
//    // Jakby to było potrzebne, to raczej na zewnątrz co world step trzeba by zbierać dane i tutaj już podawać jako argument metody, więc static ok.
//    public static void jumpPlayer(PhysicalPlayer player) {
//        CreatureBody body = player.body();
////        if (Math.abs(body.getLinearVelocity().y) <= 0.01) { // Only jump if on the ground
////            body.applyLinearImpulse(JUMP_IMPULSE);
////        }
//        if(body.isGrounded())body.applyLinearImpulse(JUMP_IMPULSE);
//    }
//}
