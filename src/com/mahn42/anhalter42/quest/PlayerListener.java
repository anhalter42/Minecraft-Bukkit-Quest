/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.BlockPosition;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author andre
 */
public class PlayerListener implements Listener {
    
    @EventHandler
    public void playerInteract(PlayerInteractEvent aEvent) {
        ArrayList<QuestTask> lQuestTasks = QuestPlugin.plugin.getQuestTasks(aEvent.getPlayer().getLocation());
        if (!lQuestTasks.isEmpty()) {
            QuestTaskInteraction lInteraction = new QuestTaskInteraction();
            lInteraction.kind = QuestTaskInteraction.Kind.interaction;
            lInteraction.action = aEvent.getAction();
            lInteraction.player = aEvent.getPlayer();
            if (aEvent.hasBlock()) {
                lInteraction.position = new BlockPosition(aEvent.getClickedBlock().getLocation());
                lInteraction.material = aEvent.getClickedBlock().getType();
                lInteraction.data = aEvent.getClickedBlock().getData();
            } else {
                lInteraction.position = new BlockPosition(lInteraction.player.getLocation());
                lInteraction.material = Material.AIR;
                lInteraction.data = (byte)0;
            }
            if (aEvent.hasItem()) {
                lInteraction.item = aEvent.getItem().clone();
            }
            for(QuestTask lTask : lQuestTasks) {
                lTask.addInteraction(lInteraction);
            }
        }
    }

    @EventHandler
    public void playerPlace(BlockPlaceEvent aEvent) {
        ArrayList<QuestTask> lQuestTasks = QuestPlugin.plugin.getQuestTasks(aEvent.getPlayer().getLocation());
        if (!lQuestTasks.isEmpty()) {
            QuestTaskInteraction lInteraction = new QuestTaskInteraction();
            lInteraction.kind = QuestTaskInteraction.Kind.placement;
            lInteraction.player = aEvent.getPlayer();
            lInteraction.material = aEvent.getBlockPlaced().getType();
            lInteraction.data = aEvent.getBlockPlaced().getData();
            lInteraction.item = aEvent.getItemInHand().clone();
            for(QuestTask lTask : lQuestTasks) {
                lTask.addInteraction(lInteraction);
            }
        }
    }
}
