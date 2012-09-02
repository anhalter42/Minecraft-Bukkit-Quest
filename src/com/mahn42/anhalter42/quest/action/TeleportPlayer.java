/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.BlockPosition;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 *
 * @author andre
 */
public class TeleportPlayer extends Action {
    // META
    public int player = 1;
    public BlockPosition to = new BlockPosition();
    public boolean allPlayer = false;

    @Override
    public void initialize() {
        super.initialize();
        to.add(quest.edge1);
    }
    
    @Override
    public void execute() {
        if (allPlayer) {
            for(String aName : quest.players) {
                Player lPlayer = quest.getPlayer(aName);
                if (lPlayer != null) {
                    lPlayer.teleport(to.getLocation(quest.world), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
            }
        } else {
            Player lPlayer = quest.getPlayer(player - 1);
            if (lPlayer != null) {
                lPlayer.teleport(to.getLocation(quest.world), PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        }
    }
}
