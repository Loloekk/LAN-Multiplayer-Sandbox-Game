package io.github.terraria.logic;

class PlayerImpl implements Player {
    private final int id;
    public PlayerImpl(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
