package io.github.sandboxGame.logic.creatures.bots;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BotRegistry {
    private HashSet<Bot> bots = new HashSet<>();
    public void addBot(Bot bot){
        bots.add(bot);
        bot.getCreature().addDeathEvent(() -> removeBot(bot));
    }
    public void removeBot(Bot bot){
        bots.remove(bot);
    }
    public void update(){
        for(var bot : bots){
            bot.think();
        }
    }
}
