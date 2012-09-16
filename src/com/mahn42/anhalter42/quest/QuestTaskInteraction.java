/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.BlockPosition;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author andre
 */
public class QuestTaskInteraction {
    public enum Kind {
        interaction,
        placement
    }
    public Kind kind = Kind.interaction;
    public BlockPosition position;
    public Action action;
    public Material material;
    public byte data;
    public ItemStack item;
    public Player player;
}
