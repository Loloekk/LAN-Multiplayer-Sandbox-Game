package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.List;

public abstract class ActivePlayers {
    // Stąd zaciągane dane przy logowaniu.
    protected final PlayerRegistry registry;
    protected final World world;
    public ActivePlayers(PlayerRegistry registry, World world) {
        this.registry = registry;
        this.world = world;
    }
    protected Vector2 spawn = new Vector2(0f, 0f); // Czy to nie będzie zależało od init PlainContainer? Trzeba tak zrobić właśnie, żeby nie zależało. (0, 0) to dobra wartość na spawn.

    private static final float width = 10f, height = 10f;
    private static final float density = 0.5f;
    private static final float friction = 0.4f;
    private static final float restitution = 0.6f;
    protected final Body getNewPlayerBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawn);

        Body body = world.createBody(bodyDef);

        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        rectangle.dispose();
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);
        return body;
    }

    // Sprawdzanie haseł poza modelem.
    public abstract void loginPlayer(int playersId);
    public abstract void logoutPlayer(int playersId);
    public abstract PhysicalPlayer getPlayer(int id);
    public abstract List<PhysicalPlayer> getList();
    // Potrzebna też jakaś osobno możliwość, żeby dostać gracza/moba w danym kordynacie.
}
