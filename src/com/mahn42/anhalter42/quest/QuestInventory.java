/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import java.util.ArrayList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author andre
 */
public class QuestInventory extends QuestObject {
    
    // META
    public String name;
    public ArrayList<QuestItemStack> items = new ArrayList<QuestItemStack>();
    
    public void setItemsFromSectionValue(Object aObject) {
        if (aObject instanceof ArrayList) {
            for(Object lItem : (ArrayList)aObject) {
                QuestItemStack lStack = new QuestItemStack();
                lStack.quest = quest;
                lStack.fromSectionValue(lItem);
                items.add(lStack);
            }
        }
    }
    
    public ItemStack[] getItemStack() {
        compress();
        ItemStack[] lResult = new ItemStack[items.size()];
        int i = 0;
        for(QuestItemStack lStack : items) {
            lResult[i] = lStack.toItemStack();
            i++;
        }
        return lResult;
    }

    public void add(QuestInventory aInv) {
        items.addAll(aInv.items);
        compress();
    }

    public void add(Inventory aInv) {
        for(ItemStack lStack : aInv.getContents()) {
            if (lStack != null) {
                QuestItemStack lItem = new QuestItemStack();
                lItem.fromItemStack(lStack);
                items.add(lItem);
            }
        }
        compress();
    }

    private void compress() {
        ArrayList<QuestItemStack> lItems = new ArrayList<QuestItemStack>();
        for(QuestItemStack lStack : items) {
            boolean lFound = false;
            for(QuestItemStack lFree : lItems) {
                if (lFree.amount < lFree.material.getMaxStackSize() && lFree.material.equals(lStack.material) && lFree.data == lStack.data) {
                    lFree.amount += lStack.amount;
                    if (lFree.amount > lFree.material.getMaxStackSize()) {
                        lStack.amount = lFree.amount - lFree.material.getMaxStackSize();
                        lFree.amount = lFree.material.getMaxStackSize();
                    } else {
                        lFound = true;
                        break;
                    }
                }
            }
            if (!lFound) {
                lItems.add(lStack);
            }
        }
        items.clear();
        for(QuestItemStack lStack : lItems) {
            if (lStack.amount > 0) {
                items.add(lStack);
            }
        }
    }

    public void remove(QuestInventory aInv) {
        for(QuestItemStack lStack : items) {
//            boolean lFound = false;
            for(QuestItemStack lRemove : aInv.items) {
                if (lStack.amount > 0 && lRemove.material.equals(lStack.material) && lRemove.data == lStack.data) {
                    lStack.amount -= lRemove.amount;
                    if (lStack.amount < 0) {
                        lRemove.amount = -lStack.amount;
                        lStack.amount = 0;
                    } else {
//                        lFound = true;
                        break;
                    }
                }
            }
        }
        compress();
    }

    public void toInventory(Inventory aInv) {
        compress();
        for(QuestItemStack lStack : items) {
            aInv.addItem(lStack.toItemStack());
        }
    }
}
