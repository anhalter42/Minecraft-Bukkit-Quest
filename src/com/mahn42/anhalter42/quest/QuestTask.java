/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.SyncBlockList;

/**
 *
 * @author andre
 */
public class QuestTask implements Runnable {

    public Quest quest;
    public int taskId = 0;
    
    protected boolean fInitialized = false;
    protected boolean fRun = false;
    
    @Override
    public void run() {
        if (!fRun) {
            fRun = true;
            try {
                if (!fInitialized) {
                    quest.initialze();
                    fInitialized = true;
                }
                quest.log("Task begin");
                SyncBlockList lList = new SyncBlockList(quest.world);
                quest.syncList = lList;
                quest.run();
                quest.syncList.execute();
                quest.syncList = null;
                quest.log("Task end");
                if (quest.stopped) {
                    stop();
                }
            } finally {
                fRun = false;
            }
        }
    }

    public void stop() {
        QuestPlugin.plugin.stopQuest(this);
    }
    
    public void finish() {
        quest.finish();
    }
    
}
