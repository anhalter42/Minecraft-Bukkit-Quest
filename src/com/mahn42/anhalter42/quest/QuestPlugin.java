/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.anhalter42.quest.action.Action;
import com.mahn42.anhalter42.quest.action.GenerateBlocks;
import com.mahn42.anhalter42.quest.trigger.Trigger;
import java.io.File;
import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author andre
 */
public class QuestPlugin extends JavaPlugin {

    public static QuestPlugin plugin;
    
    public ArrayList<QuestTask> tasks = new ArrayList<QuestTask>();
    
    public static void main(String[] args) {
        Trigger.register();
        Action.register();
        GenerateBlocks.register();
        Quest lQuest = new Quest();
        lQuest.load(new File("/Users/andre/craftbukkit/test.quest.yml"));
        //lQuest.run();
    }
    
    
    @Override
    public void onEnable() { 
        plugin = this;
        Trigger.register();
        Action.register();
        GenerateBlocks.register();
        getCommand("q_start").setExecutor(new CommandQuestStart());
        getCommand("q_stop").setExecutor(new CommandQuestStop());
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }
    
    public void startQuest(Quest aQuest) {
        QuestTask lTask = new QuestTask();
        lTask.quest = aQuest;
        lTask.taskId = getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, lTask, 10, 10);
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
