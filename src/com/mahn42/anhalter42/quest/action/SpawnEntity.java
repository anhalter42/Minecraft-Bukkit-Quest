/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.BlockPosition;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author andre
 */
public class SpawnEntity extends Action {
    public EntityType entityType = EntityType.PIG;
    public BlockPosition to = new BlockPosition();
    public BlockPosition vector = new BlockPosition();
    public int amount = 1;
    public float speed = (float) 0.6; // for ARROW
    public float spread = 12;         // for ARROW
    public Material material = Material.APPLE; // for Dropped Item
    public byte data = 0;                      // for Dropped Item
    public short damage = 0;                   // for Dropped Item
    public boolean naturally = true;           // for Dropped Item
    
    @Override
    public void initialize() {
        super.initialize();
        to.add(quest.edge1);
    }
    
    @Override
    public void execute() {
        for(int i=0;i<amount;i++) {
            if (entityType == EntityType.ARROW) {
                quest.world.spawnArrow(to.getLocation(quest.world), vector.getVector(), speed, spread);
            } else if (entityType == EntityType.DROPPED_ITEM) {
                ItemStack lStack = new ItemStack(material, amount, damage, data);
                if (naturally) {
                    quest.world.dropItemNaturally(to.getLocation(quest.world), lStack);
                } else {
                    quest.world.dropItem(to.getLocation(quest.world), lStack);
                }
            } else if (entityType == EntityType.FALLING_BLOCK) {
                quest.world.spawnFallingBlock(to.getLocation(quest.world), material, data);
            } else {
                quest.syncList.add(to, entityType);
                //quest.world.spawnEntity(to.getLocation(quest.world), entityType);
            }
        }
    }
}
