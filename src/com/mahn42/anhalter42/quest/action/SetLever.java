/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.BlockPosition;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 *
 * @author andre
 */
public class SetLever extends Action {
    public enum Mode {
        toggle,
        on,
        off
    }
    // META
    public BlockPosition pos = new BlockPosition();
    public Mode mode = Mode.toggle;
        
    @Override
    public void execute() {
        Block lBlock = pos.getBlock(quest.world);
        Material lMat = lBlock.getType();
        if (lMat.equals(Material.LEVER) || lMat.equals(Material.STONE_BUTTON)) {
            byte lData = lBlock.getData();
            switch (mode) {
                case toggle: lData ^= 0x08; break;
                case on:     lData |= 0x08; break;
                case off:    lData &= 0xF7; break;
            }
            quest.syncList.add(pos, lMat, lData);
        }
    }
}
