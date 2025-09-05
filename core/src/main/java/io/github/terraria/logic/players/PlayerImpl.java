package io.github.terraria.logic.players;

class PlayerImpl implements Player {
    private final int id;
    private final String name;
    public PlayerImpl(int id) {
        this.id = id;this.name = "ala";
    }
    public int getId() {
        return id;
    }
    public String getName() {return name;}
}
