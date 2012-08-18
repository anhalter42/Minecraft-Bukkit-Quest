/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.BlockPosition;
import java.io.File;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class CommandQuestStart  implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player) aCommandSender;
            if (aStrings.length > 0) {
                Quest lQuest = new Quest();
                lQuest.world = lPlayer.getWorld();
                lQuest.load(new File(aStrings[0]));
                lQuest.edge1 = new BlockPosition(lPlayer.getLocation());
                lQuest.edge1.subtract(lQuest.startPos);
                QuestPlugin.plugin.startQuest(lQuest);
                aCommandSender.sendMessage("Quest " + lQuest.name + " started.");
            }
        }
        return true;
    }
    
}
