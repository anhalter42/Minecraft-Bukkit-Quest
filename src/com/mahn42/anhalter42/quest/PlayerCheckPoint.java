/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author andre
 */
public class PlayerCheckPoint {
    public String playerName;
    public Location position;
    public ItemStack[] invenory;
    
    public PlayerCheckPoint(Player aPlayer) {
        playerName = aPlayer.getName();
        fromPlayer(aPlayer);
    }
    
    public void updatePlayer() {
        Player lPlayer = QuestPlugin.plugin.getServer().getPlayer(playerName);
        lPlayer.getInventory().setContents(invenory);
        lPlayer.teleport(position, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    public void fromPlayer(Player aPlayer) {
        position = aPlayer.getLocation();
        PlayerInventory lInv = aPlayer.getInventory();
        invenory = lInv.getContents();
    }
}
