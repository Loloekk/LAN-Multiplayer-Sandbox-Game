package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class PlayerActivator {
    private final PlayerRegistry registry;
    protected final World world;
    private final ActivePlayers activePlayers;
    public PlayerActivator(PlayerRegistry registry, World world, ActivePlayers activePlayers) {
        this.registry = registry;
        this.world = world;
        this.activePlayers = activePlayers;
    }

    protected final Vector2 spawn = new Vector2(0f, 0f); // Czy to nie będzie zależało od init PlainContainer? Trzeba tak zrobić właśnie, żeby nie zależało. (0, 0) to dobra wartość na spawn.

    protected abstract Body getNewPlayerBody();

    // Sprawdzanie haseł poza modelem.
    public void loginPlayer(int playersId) {
        activePlayers.add(new PhysicalPlayer(registry.getPlayer(playersId), getNewPlayerBody()));
    }
    public void logoutPlayer(int playersId) {
        // Destroy body.
        Body body = activePlayers.remove(playersId).body();
        world.destroyBody(body);

        // Save some player info to registry?
    }
}
