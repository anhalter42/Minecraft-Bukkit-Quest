/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.Framework;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class TellPlayer extends Action {
    // META
    public int player = 1;
    public boolean allPlayer = false;
    public String text = "hello";
    public boolean useMessenger = false;

    @Override
    public void initialize() {
        super.initialize();
    }
    
    @Override
    public void execute() {
        if (allPlayer) {
            for(String aName : quest.players) {
                Player lPlayer = quest.getPlayer(aName);
                if (lPlayer != null) {
                    if (useMessenger) {
                        Framework.plugin.getMessenger().sendPlayerMessage("Quest:" + quest.name, aName, text);
                    } else {
                        lPlayer.sendMessage(text);
                    }
                }
            }
        } else {
            Player lPlayer = quest.getPlayer(player - 1);
            if (lPlayer != null) {
                if (useMessenger) {
                    Framework.plugin.getMessenger().sendPlayerMessage("Quest:" + quest.name, lPlayer.getName(), text);
                } else {
                    lPlayer.sendMessage(text);
                }
            }
        }
    }
    
}
