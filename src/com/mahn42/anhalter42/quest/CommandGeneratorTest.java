/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.anhalter42.quest.action.GenerateBlocks;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.SyncBlockList;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class CommandGeneratorTest implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender aCommandSender, Command aCommand, String aString, String[] aStrings) {
        if (aCommandSender instanceof Player) {
            Player lPlayer = (Player) aCommandSender;
            if (aStrings.length > 0) {
                BlockPosition lfrom = new BlockPosition(lPlayer.getLocation());
                BlockPosition lto;
                Quest quest = new Quest();
                String generator = aStrings[0];
                IGenerator fGenerator = null;
                lto = lfrom.clone();
                lto.add(Integer.parseInt(aStrings[1]) - 1,Integer.parseInt(aStrings[2]) - 1,Integer.parseInt(aStrings[3]) - 1);
                HashMap<String,String> lMap = new HashMap<String, String>();
                for(int i=4;i<aStrings.length;i+=2) {
                    lMap.put(aStrings[i], aStrings[i+1]);
                }
                Class<IGenerator> lClass = Quest.generatorTypes.get(generator);
                if (lClass != null) {
                    try {
                        fGenerator = lClass.newInstance();
                    } catch (Exception ex) {
                        Logger.getLogger(GenerateBlocks.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fGenerator != null) {
                        if (fGenerator instanceof QuestObject) {
                            ((QuestObject)fGenerator).quest = quest;
                            ((QuestObject)fGenerator).fromSectionValue(lMap);
                        }
                        fGenerator.initialize(lfrom, lto);
                        SyncBlockList lList = new SyncBlockList(lPlayer.getWorld());
                        fGenerator.execute(lList);
                        lList.execute();
                    }
                } else {
                    aCommandSender.sendMessage("generator " + generator + " not found!");
                }
            }
        }
        return true;
    }
}
