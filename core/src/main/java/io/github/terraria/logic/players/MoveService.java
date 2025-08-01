package io.github.terraria.logic.players;

public interface MoveService {
    public enum Direction {
        right, left
    }
    // Trzeba będzie pewnie pamiętać czas od opuszczenia ziemi, żeby móc jakieś bardziej skomplikowane skoki robić np.
    public abstract void movePlayer(PhysicalPlayer player, Direction direction);
    public abstract void jumpPlayer(PhysicalPlayer player);
}
