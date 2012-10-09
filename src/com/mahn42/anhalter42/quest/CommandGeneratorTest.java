/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.anhalter42.quest.action.GenerateBlocks;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.BookAndQuill;
import com.mahn42.framework.SyncBlockList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
                HashMap<String,Object> lMap = new HashMap<String, Object>();
                ItemStack lItemInHand = lPlayer.getItemInHand();
                if (lItemInHand != null && lItemInHand.getType().equals(Material.BOOK_AND_QUILL)) {
                    BookAndQuill lBook = new BookAndQuill(lItemInHand);
                    String[] lPages = lBook.getPages();
                    String lContent = "";
                    for(String lPage : lPages) {
                        lContent += lPage.replaceAll("_", " ");
                    }
                    YamlConfiguration lYaml = new YamlConfiguration();
                    try {
                        lYaml.loadFromString(lContent);
                    } catch (InvalidConfigurationException ex) {
                        Logger.getLogger(CommandGeneratorTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Object lObj = lYaml.get("generator");
                    if (lObj instanceof HashMap) {
                        lMap = (HashMap<String, Object>) lYaml.get("generator");
                    } else if (lObj instanceof MemorySection) {
                        Map<String, Object> lValues = ((MemorySection)lObj).getValues(false);
                        lMap.putAll(lValues);
                    }
                }
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
