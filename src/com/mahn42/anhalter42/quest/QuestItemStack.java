/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author andre
 */
public class QuestItemStack extends QuestObject {
    public Material material;
    public byte data = (byte)0;
    public int amount = 1;
    public short damage = 0;

    public ItemStack toItemStack() {
        return new ItemStack(material, amount, damage, data);
    }
    
    public void fromItemStack(ItemStack aStack) {
        material = aStack.getType();
        data = aStack.getData().getData();
        amount = aStack.getAmount();
        damage = aStack.getDurability();
    }
}
