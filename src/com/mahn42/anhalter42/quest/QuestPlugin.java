/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.anhalter42.quest.action.Action;
import com.mahn42.anhalter42.quest.trigger.Trigger;
import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author andre
 */
public class QuestPlugin extends JavaPlugin {

    public static QuestPlugin plugin;
    
    public static void main(String[] args) {
        Trigger.register();
        Action.register();
        Quest lQuest = new Quest();
        lQuest.load(new File("/Users/andre/craftbukkit/test.quest.yml"));
    }
    
    
    @Override
    public void onEnable() { 
        plugin = this;
        Trigger.register();
        Action.register();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }
}
