package io.github.terraria.logic;

import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import io.github.terraria.logic.building.BlockType;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.building.falling.FallingService;
import io.github.terraria.logic.building.falling.VolatileBlocksContainer;

public abstract class GameState {
    private final PlaneContainer grid;
    private final FallingService fallingService;
    private final PlayersContainer players;
    private final World world;
    public GameState(PlaneContainer container, PlayersContainer players, World world) {
        Box2D.init();
        this.grid = container;
        this.fallingService = new FallingService(container, new VolatileBlocksContainer());
        this.players = players;
        this.world = world;
    }
    // TODO: Rozdzielić te metody po kilku klasach.
    // Zrobiłbym service na rejestrację, service na lokalne widoki,
    // klasę na ruchy graczy i klasę na interakcje zewnętrzne (na tę chwilę tylko z blokami).
    // Warto pamiętać jeszcze o tym, że trzeba informacje o samych graczach wysyłać poza lokalnymi widokami.
    public abstract void step();
    public abstract void registerPlayer(int playersId); // Imię trzymane na wyższym poziomie?
    public abstract void loginPlayer(int playersId);
    public abstract void logoutPlayer(int playersId);
    public enum Direction {
        right, left
    }
    // Trzeba będzie pewnie pamiętać czas od opuszczenia ziemi, żeby móc jakieś bardziej skomplikowane skoki robić np.
    public abstract void movePlayer(int playersId, Direction direction);
    public abstract void jumpPlayer(int playersId);
    // W którym miejscu konwersja koordynatów? Na pewno nie tu.
    public abstract void hitAt(int playersId, int x, int y);
    public abstract void placeAt(int playersId, BlockType block);
    // Poniższa funkcjonalność (i inne jak np. dane o ekwipunku gracza) jest potrzebna,
    // ale niekoniecznie jako metoda tutaj.
    // public abstract LocalMapView getLocalMapView(int x, int y, int width, int height);
}
