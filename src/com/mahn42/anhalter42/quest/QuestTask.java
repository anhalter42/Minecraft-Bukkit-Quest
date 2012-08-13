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
    
    protected boolean fInitialized = false;
    
    @Override
    public void run() {
        if (!fInitialized) {
            quest.initialze();
        }
        SyncBlockList lList = new SyncBlockList(quest.world);
        quest.syncList = lList;
        quest.run();
        quest.syncList.execute();
        quest.syncList = null;
    }
    
}
