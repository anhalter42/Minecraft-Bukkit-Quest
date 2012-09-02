/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.BlockPosition;
import org.bukkit.Effect;
import org.bukkit.Location;

/**
 *
 * @author andre
 */
public class PlayEffect extends Action {
    
    public int player = 0;
    public Effect effect = Effect.CLICK1;
    public BlockPosition to = new BlockPosition();
    public int data = 0;
    public int radius = 0;
    
    @Override
    public void initialize() {
        super.initialize();
        to.add(quest.edge1);
    }
    
    @Override
    public void execute() {
        Location lLoc = to.getLocation(quest.world);
        if (player == 0) {
            if (radius > 0) {
                quest.world.playEffect(lLoc, Effect.CLICK2, data, radius);
            } else {
                quest.world.playEffect(lLoc, Effect.CLICK2, data);
            }
        } else {
            if (player < 0) {
                for(String lPlayerName: quest.players) {
                    quest.getPlayer(lPlayerName).playEffect(lLoc, effect, data);
                }
            } else {
                quest.getPlayer(player).playEffect(lLoc, effect, data);
            }
        }
    }
}
