/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author andre
 */
public class QuestPlugin extends JavaPlugin {

    public static QuestPlugin plugin;
    
    public static void main(String[] args) {
    }
    
    
    @Override
    public void onEnable() { 
        plugin = this;
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }
}
