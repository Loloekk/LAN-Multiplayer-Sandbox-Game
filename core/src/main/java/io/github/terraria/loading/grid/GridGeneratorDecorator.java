package io.github.sandboxGame.loading.grid;

public abstract class GridGeneratorDecorator implements GridGenerator {
    protected final GridGenerator generator;
    public GridGeneratorDecorator(GridGenerator generator) {
        this.generator = generator;
    }
}
