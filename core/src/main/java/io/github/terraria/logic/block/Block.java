package io.github.terraria.logic.block;

/**
 * Represents a block in the game.
 * Each block has an ID, a name, a texture path, and a behavior.
 */
public class Block {
    private final int id;
    private final String name;
    public final String texture; // path
    public final BlockBehavior behavior;

    Block(int id, String name, String texture, String behavior) {
        this.id = id;
        this.name = name;
        this.texture = texture;
        this.behavior = BehaviorFactory.create(behavior);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTexture() {
        return texture;
    }

    public BlockBehavior getBehavior() {
        return behavior;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass())
            return false;
        Block bl = (Block) o;
        return name.equals(bl.name);
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
