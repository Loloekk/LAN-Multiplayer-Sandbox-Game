package io.github.terraria.client.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.terraria.client.state.ClientPlayerState;
import io.github.terraria.client.view.ganarators.EquipmentGenerator;

import java.sql.Connection;

public class EquipmentRenderer {

    private Stage inventoryStage;
    private ClientPlayerState playerState;
    private Connection conn;
    public EquipmentRenderer(ClientPlayerState playerState)
    {
        this.playerState = playerState;
    }
}
