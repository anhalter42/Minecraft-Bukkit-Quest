/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.trigger;

import com.mahn42.anhalter42.quest.QuestTaskInteraction;
import com.mahn42.framework.BlockPosition;
import org.bukkit.Material;

/**
 *
 * @author andre
 */
public class PlayerInteractBlock extends Trigger {
    public enum PlayerAction {
        use_or_fight,
        fight,
        use
    }
    // RUNTIME
    protected boolean fIsReached = false;
    
    // META
    public BlockPosition from = new BlockPosition(-1,-1,1);
    public BlockPosition to = new BlockPosition(-1,-1,1);
    public PlayerAction playerAction = PlayerAction.use_or_fight;
    public QuestTaskInteraction.Kind kind = QuestTaskInteraction.Kind.interaction;
    public int player = -1;
    public Material material = null;
    public byte data = (byte)0;
    public Material itemInHand = null;
    public boolean addToPlayers = true;
    
    @Override
    public void initialize() {
        if (from.x == -1 && from.y == -1 && from.z == -1) {
            from.cloneFrom(to);
        }
        if (to.x == -1 && to.y == -1 && to.z == -1) {
            to.cloneFrom(from);
        }
        from.add(quest.edge1);
        to.add(quest.edge1);
    }
    
    @Override
    public boolean check() {
        boolean lResult = false;
        for(QuestTaskInteraction lI : quest.interactions) {
            if (kind == lI.kind
                    && (from.x == -1 || lI.position.isBetween(from, to))
                    && (material == null || lI.material.equals(material))
                    && (data == 0 || data == lI.data)
                    && (itemInHand == null || (lI.item != null && lI.item.getType() == itemInHand))
                    && (playerAction == PlayerAction.use_or_fight
                     || playerAction == PlayerAction.use && (lI.action == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK || lI.action == org.bukkit.event.block.Action.RIGHT_CLICK_AIR)
                     || playerAction == PlayerAction.fight && (lI.action == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK || lI.action == org.bukkit.event.block.Action.LEFT_CLICK_AIR))) {
                if (player >= 0) {
                    if (player == 0) {
                        if (quest.players.contains(lI.player.getName())) {
                            lResult = true;
                            break;
                        }
                    } else if (lI.player.equals(quest.getPlayer(player - 1))) {
                        lResult = true;
                        break;
                    }
                } else {
                    if (addToPlayers && !quest.players.contains(lI.player.getName())) {
                        quest.players.add(lI.player.getName());
                    }
                    lResult = true;
                }
            }
        }
        /*if (fIsReached && lResult) {
            lResult = false;
        } else if (fIsReached && !lResult) {
            fIsReached = false;
        } else if (!fIsReached && lResult) {
            fIsReached = true;
        }*/
        return lResult;
    }
}
