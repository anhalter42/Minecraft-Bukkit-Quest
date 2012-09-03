/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.anhalter42.quest.action.Action;
import com.mahn42.anhalter42.quest.action.GenerateBlocks;
import com.mahn42.anhalter42.quest.generator.Maze;
import com.mahn42.anhalter42.quest.generator.Maze.Cell;
import com.mahn42.anhalter42.quest.trigger.Trigger;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author andre
 */
public class QuestPlugin extends JavaPlugin {

    public int configQuestTaskTicks = 1;
    
    public static QuestPlugin plugin;
    
    public ArrayList<QuestTask> tasks = new ArrayList<QuestTask>();
    
    public static void main(String[] args) {
        Trigger.register();
        Action.register();
        GenerateBlocks.register();
        Maze lMaze = new Maze(10, 3, 10);
        lMaze.build();
        for(int y=0; y<3; y++) {
            String lDump = "";
            for(int z=0;z<10;z++) {
                String lStr1 = "";
                String lStr2 = "";
                String lStr3 = "";
                for(int x=0;x<10;x++) {
                    Cell lCell = lMaze.get(x, y, z);
                    if (lCell.links[2].broken) {
                        lStr2 += " ";
                    } else {
                        lStr2 += "|";
                    }
                    lStr2 += " ";
                    if (lCell.links[3].broken) {
                        lStr2 += " ";
                    } else {
                        lStr2 += "|";
                    }
                    if (lCell.links[4].broken) {
                        lStr1 += "   ";
                    } else {
                        lStr1 += "+-+";
                    }
                    if (lCell.links[5].broken) {
                        lStr3 += "   ";
                    } else {
                        lStr3 += "+-+";
                    }
                }
                lDump += "\n" + lStr1 + "\n" + lStr2 + "\n" + lStr3;
                //Logger.getLogger("x").info(lStr1);
                //Logger.getLogger("x").info(lStr2);
                //Logger.getLogger("x").info(lStr3);
            }
            Logger.getLogger("x").info(lDump);
        }
        //Quest lQuest = new Quest();
        //lQuest.load(new File("/Users/andre/craftbukkit/test.quest.yml"));
        //lQuest.run();
    }
    
    
    @Override
    public void onEnable() { 
        plugin = this;
        readQuestConfig();
        Trigger.register();
        Action.register();
        GenerateBlocks.register();
        getCommand("q_start").setExecutor(new CommandQuestStart());
        getCommand("q_stop").setExecutor(new CommandQuestStop());
        getCommand("q_gentest").setExecutor(new CommandGeneratorTest());
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }
    
    private void readQuestConfig() {
        FileConfiguration lConfig = getConfig();
        configQuestTaskTicks = lConfig.getInt("QuestTask.Ticks");
    }
    

    
    public void startQuest(Quest aQuest) {
        QuestTask lTask = new QuestTask();
        lTask.quest = aQuest;
        lTask.taskId = getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, lTask, 10, configQuestTaskTicks);
        tasks.add(lTask);
    }
    
    public QuestTask getQuestTask(Quest aQuest) {
        for(QuestTask lTask : tasks) {
            if (lTask.quest == aQuest) {
                return lTask;
            }
        }
        return null;
    }
    
    public QuestTask getQuestTask(String aQuestName) {
        for(QuestTask lTask : tasks) {
            if (lTask.quest.name.equalsIgnoreCase(aQuestName)) {
                return lTask;
            }
        }
        return null;
    }
    
    public void stopQuest(Quest aQuest) {
        QuestTask lTask = getQuestTask(aQuest);
        if (lTask != null) {
            stopQuest(lTask);
        } 
    }

    public void stopQuest(QuestTask aTask) {
        aTask.finish();
        tasks.remove(aTask);
        getServer().getScheduler().cancelTask(aTask.taskId);
    }
}
