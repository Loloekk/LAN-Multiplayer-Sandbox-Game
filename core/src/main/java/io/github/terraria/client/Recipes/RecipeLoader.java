package io.github.terraria.client.Recipes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.terraria.client.StationBank;
import io.github.terraria.client.state.ClientMainPlayerState;
import io.github.terraria.client.view.textures.texture.TextureBank;
import io.github.terraria.common.StationType;
import io.github.terraria.controler.network.PacketClientToServer.PacketCraftItems;
import io.github.terraria.loading.RecordLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.esotericsoftware.kryonet.Connection;


public class RecipeLoader {
    private Stage inventoryStage;
    private Connection conn;
    Map<StationType, ScrollPane> craftings = new HashMap<>();
    Map<StationType,List<Button>> buttons = new HashMap<>();
    Map<StationType,List<Recipe>> recipes;
    ScrollPane emptyPane;
    TextureBank itemsTextures;
    ClientMainPlayerState playerState;

    public RecipeLoader(String jsonName,  TextureBank itemsTextures,Connection conn, Stage inventoryStage, ClientMainPlayerState playerState)
    {
        this.conn = conn;
        this.inventoryStage = inventoryStage;
        this.itemsTextures = itemsTextures;
        this.playerState = playerState;
        var list = RecordLoader.loadList(jsonName, Recipe.class);
        recipes = list.stream()
            .filter(r -> r.station() != null)
            .collect(Collectors.groupingBy(Recipe::station, HashMap::new, Collectors.toList()));
//        recipes = list.stream()
//            .filter(r -> r.station() != null)
//            .collect(Collectors.groupingBy(Recipe::station));
        for(Map.Entry<StationType,List<Recipe>> recipeList : recipes.entrySet())
        {
            StationType type = recipeList.getKey();
            List<Recipe> stationList = recipeList.getValue();
            buttons.put(type,new ArrayList<>());
//            System.out.println(type);
            for(Recipe recipe: stationList)
            {
                buttons.get(type).add(createButton(recipe));
            }
//                System.out.println(recipe);
        }
        createCraftingsPlanes();

    }
    public RecipeLoader(TextureBank textures,Connection conn, Stage inventoryStage, ClientMainPlayerState playerState)
    {
        this("recipes.json",textures,conn,inventoryStage,playerState);
    }

    private Button createButton(Recipe recipe)
    {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("recipeButton.png")));
        buttonStyle.font = new BitmapFont();

        Button recipeButton = new Button(buttonStyle);

        playerState.getEquipment().addRecipeAvailListener((r, avail) -> {
            if (r.recipeId() == recipe.id()) {
                recipeButton.setColor(avail ? Color.WHITE : Color.GRAY);
            }
        });

        Table table = new Table();
        table.pad(5).defaults().pad(5);

        int i = 0;
        for(Product product : recipe.ingredients())
        {
            if(i>0)
            {
                table.add(new Label("+", new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
            }
            Image itemImage = new Image(new TextureRegionDrawable(new TextureRegion(itemsTextures.getTexture(product.name()))));
            Label itemCount = new Label("x" + product.amount(), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
            VerticalGroup itemGroup = new VerticalGroup();
            itemGroup.addActor(itemImage);
            itemGroup.addActor(itemCount);
            table.add(itemGroup);
        }
        table.add(new Label("=", new Label.LabelStyle(new BitmapFont(), Color.GRAY)));
        Image itemImage = new Image(new TextureRegionDrawable(new TextureRegion(itemsTextures.getTexture(recipe.output().name()))));
        Label itemCount = new Label("x" + recipe.output().amount(), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        VerticalGroup itemGroup = new VerticalGroup();
        itemGroup.addActor(itemImage);
        itemGroup.addActor(itemCount);
        table.add(itemGroup);
        recipeButton.add(table).expand().fill();
        recipeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Kliknięto recepturę: " + recipe.id());
                PacketCraftItems craft = new PacketCraftItems();
                craft.playerId = playerState.getPlayerId();
                craft.craftingId = recipe.id();
                conn.sendTCP(craft);
            }
        });
        return recipeButton;
    }
    private void createCraftingsPlanes()
    {
        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();
        for(Map.Entry<StationType, List<Button>> entry : buttons.entrySet())
        {
            Table craftingsTable = new Table();
            craftingsTable.top().pad(20);
            StationType station = entry.getKey();
            List<Button> buttons = entry.getValue();
            for(Button button : buttons) {
                craftingsTable.row();
                craftingsTable.add(button).width(500).height(120).pad(10);
//                System.out.println(station+" "+button);
            }
            ScrollPane scrollPane = new ScrollPane(craftingsTable, scrollStyle);
            scrollPane.setFadeScrollBars(false);
            scrollPane.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    inventoryStage.setScrollFocus(scrollPane);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (inventoryStage.getScrollFocus() == scrollPane) {
                        inventoryStage.setScrollFocus(null);
                    }
                }
            });

            craftings.put(station,scrollPane);
        }
        Table emptyTable = new Table();
        ScrollPane scrollPane = new ScrollPane(emptyTable, scrollStyle);
        scrollPane.setFadeScrollBars(false);
    }
    public ScrollPane getStationCraftings(StationType station)
    {
        if(craftings.containsKey(station))
            return craftings.get(station);
        return emptyPane;
    }
}


