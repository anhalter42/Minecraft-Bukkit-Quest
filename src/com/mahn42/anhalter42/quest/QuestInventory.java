/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author andre
 */
public class QuestInventory extends QuestObject {
    
    public class Stack extends QuestObject {
        public Material material;
        public byte data = (byte)0;
        public int amount = 1;
        public short damage = 0;
        
        public ItemStack toItemStack() {
            return new ItemStack(material, amount, damage, data);
        }
    }
    
    // META
    public String name;
    public ArrayList<Stack> items = new ArrayList<Stack>();
    
    public void setItemsFromSectionValue(Object aObject) {
        if (aObject instanceof ArrayList) {
            for(Object lItem : (ArrayList)aObject) {
                Stack lStack = new Stack();
                lStack.fromSectionValue(lItem);
                items.add(lStack);
            }
        }
    }
    
    public ItemStack[] getItemStack() {
        ItemStack[] lResult = new ItemStack[items.size()];
        int i = 0;
        for(Stack lStack : items) {
            lResult[i] = lStack.toItemStack();
            i++;
        }
        return lResult;
    }
}
