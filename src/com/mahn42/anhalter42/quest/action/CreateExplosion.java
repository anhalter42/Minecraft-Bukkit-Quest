/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.BlockPosition;
import org.bukkit.Location;

/**
 *
 * @author andre
 */
public class CreateExplosion extends Action {
    public BlockPosition to = new BlockPosition();
    public float power = 4;
    public boolean setFire = false;

    @Override
    public void initialize() {
        super.initialize();
        to.add(quest.edge1);
    }
    
    @Override
    public void execute() {
        Location lLoc = to.getLocation(quest.world);
        quest.world.createExplosion(lLoc, power, setFire);              
    }
}
