/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.anhalter42.quest.QuestInventory;
import com.mahn42.anhalter42.quest.QuestItemStack;
import com.mahn42.framework.BlockPosition;
import java.util.ArrayList;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author andre
 */
public class ChangeInventory extends Action {
    public enum Mode {
        add,
        remove
    }
    public BlockPosition to = new BlockPosition(-1,-1,-1);
    public BlockPosition from = new BlockPosition(-1,-1,-1);
    public String name = null;
    public int player = -1; // -1 kein player, 0 alle player, >0 player nummer
    public String loadFrom = null;
    public Mode mode = Mode.add;
    public boolean clear = false;
    public int fromPlayer = -1; // -1 kein player, >0 player nummer
    public ArrayList<QuestItemStack> items = new ArrayList<QuestItemStack>();

    @Override
    public void initialize() {
        super.initialize();
        if (to.x >= 0) {
            to.add(quest.edge1);
        }
        if (from.x >= 0) {
            from.add(quest.edge1);
        }
    }
    
    protected Inventory getInventoryFromPos(BlockPosition aPos) {
        BlockState lState = to.getBlock(quest.world).getState();
        Inventory lInv = null;
        if (lState instanceof Chest) {
            lInv = ((Chest)lState).getBlockInventory();
        } else if (lState instanceof Dispenser) {
            lInv = ((Dispenser)lState).getInventory();
        } else if (lState instanceof Furnace) {
            lInv = ((Furnace)lState).getInventory();
        }
        return lInv;
    }
    
    @Override
    public void execute() {
        QuestInventory lInv = new QuestInventory();
        lInv.items.addAll(items);
        if (loadFrom != null && !loadFrom.isEmpty()) {
            QuestInventory lLF = quest.inventories.get(loadFrom);
            lInv.add(lLF);
        }
        if (fromPlayer > 0) {
            Player lPlayer = quest.getPlayer(player - 1);
            PlayerInventory lPI = lPlayer.getInventory();
            lInv.add(lPI);
        }
        if (from.x >= 0) {
            Inventory lFromInv = getInventoryFromPos(from);
            if (lFromInv != null) {
                lInv.add(lFromInv);
            }
        }
        if (name != null && !name.isEmpty()) {
            QuestInventory lTo = quest.getInventory(name);
            switch(mode) {
                case add:
                    lTo.add(lInv);
                    break;
                case remove:
                    lTo.remove(lInv);
                    break;
            }
        } else if (player >= 0) {
            if (player > 0) {
                Player lPlayer = quest.getPlayer(player - 1);
                PlayerInventory lPI = lPlayer.getInventory();
                QuestInventory lTo = new QuestInventory();
                lTo.add(lPI);
                switch(mode) {
                    case add:
                        lTo.add(lInv);
                        break;
                    case remove:
                        lTo.remove(lInv);
                        break;
                }
                lPI.clear();
                lTo.toInventory(lPI);
            } else {
                for(String lPlayerName : quest.players) {
                    Player lPlayer = quest.getPlayer(lPlayerName);
                    PlayerInventory lPI = lPlayer.getInventory();
                    QuestInventory lTo = new QuestInventory();
                    lTo.add(lPI);
                    switch(mode) {
                        case add:
                            lTo.add(lInv);
                            break;
                        case remove:
                            lTo.remove(lInv);
                            break;
                    }
                    lPI.clear();
                    lTo.toInventory(lPI);
                }
            }
        } else {
            Inventory lToInv = getInventoryFromPos(to);
            if (lToInv != null) {
                QuestInventory lTo = new QuestInventory();
                lTo.add(lToInv);
                switch(mode) {
                    case add:
                        lTo.add(lInv);
                        break;
                    case remove:
                        lTo.remove(lInv);
                        break;
                }
                lToInv.clear();
                lTo.toInventory(lToInv);
            }
        }
    }
}
