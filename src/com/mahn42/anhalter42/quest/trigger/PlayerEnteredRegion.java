/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.trigger;

import com.mahn42.anhalter42.quest.QuestPlugin;
import com.mahn42.framework.BlockPosition;
import java.util.ArrayList;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class PlayerEnteredRegion extends Trigger {
    
    public enum PlayerState {
        unknown, normal, sneaking, blocking, flying, sleeping, sprinting, insideVehicle
    }
    
    // RUNTIME
    protected ArrayList<String> fPlayers = new ArrayList<String>();
    // META
    public int player = 0;
    public BlockPosition from = new BlockPosition();
    public BlockPosition to = new BlockPosition();
    public boolean addToPlayers = true;
    public PlayerState playerState = PlayerState.unknown;
    
    protected boolean checkPlayer(Player aPlayer) {
        boolean lResult;
        PlayerState lState = PlayerState.normal;
        if (aPlayer.isBlocking()) lState = PlayerState.blocking;
        if (aPlayer.isFlying()) lState = PlayerState.flying;
        if (aPlayer.isSleeping()) lState = PlayerState.sleeping;
        if (aPlayer.isSneaking()) lState = PlayerState.sneaking;
        if (aPlayer.isSprinting()) lState = PlayerState.sprinting;
        lResult = playerState == PlayerState.unknown || lState == playerState;
        if (lResult) {
            BlockPosition lPos = new BlockPosition(aPlayer.getLocation());
            lPos.subtract(quest.edge1);
            lResult = lPos.isBetween(from, to);
            if (lResult) {
                quest.log(aPlayer.getName() + " pos " + lPos + " is " + lResult);
            }
        }
        return lResult;
    }
    
    @Override
    public boolean check() {
        boolean lResult = false;
        if (fPlayers.size() > 0) {
            ArrayList<String> fRemove = new ArrayList<String>();
            for(String lPName : fPlayers) {
                Player lPlayer = quest.getPlayer(lPName);
                if (lPlayer != null) {
                    if (checkPlayer(lPlayer)) {
                        fRemove.add(lPName);
                    }
                } 
            }
            for(String lPName : fRemove) {
                fPlayers.remove(lPName);
            }
        }
        if (player == 0) {
            Player[] lPlayers = QuestPlugin.plugin.getServer().getOnlinePlayers();
            for(Player lPlayer : lPlayers) {
                if (!fPlayers.contains(lPlayer.getName())) {
                    if (checkPlayer(lPlayer)) {
                        if (addToPlayers && !quest.players.contains(lPlayer.getName())) {
                            quest.players.add(lPlayer.getName());
                        }
                        lResult = true;
                    }
                }
            }
        } else {
            if (player < quest.players.size()) {
                Player lPlayer = quest.getPlayer(player - 1);
                if (lPlayer != null && !fPlayers.contains(lPlayer.getName())) {
                    lResult = checkPlayer(lPlayer);
                    fPlayers.add(lPlayer.getName());
                }
            }
        }
        return lResult;
    }
}
