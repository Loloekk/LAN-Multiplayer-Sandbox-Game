package io.github.terraria.logic.building;

class BlockBuilder {
    int id = 1;
    String name = "Stone";
    boolean isPhysical = true;
    int layer = 0;
    BlockBuilder isPhysical(boolean isPhysical) {
        this.isPhysical = isPhysical;
        // this.layer = 0; No longer necessary thanks to post-constructor.
        return this;
    }
    BlockBuilder layer(int layer) {
        this.layer = layer;
        if(layer > 0) this.isPhysical = false;
        return this;
    }
    Block build() { return new Block(new BlockType(id, name, isPhysical, layer)); }
}
