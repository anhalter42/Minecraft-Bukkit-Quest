/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author andre
 */
public class CommandQuestStop implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aStrings.length > 0) {
            QuestTask lTask = QuestPlugin.plugin.getQuestTask(aStrings[0]);
            if (lTask != null) {
                QuestPlugin.plugin.stopQuest(lTask);
                aCommandSender.sendMessage("Quest " + lTask.quest.name + " stopped!");
            } else {
                aCommandSender.sendMessage("Quest " +  aStrings[0] + " not found!");
            }
        }
        return true;
    }
}
