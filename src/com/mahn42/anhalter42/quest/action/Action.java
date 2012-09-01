/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.anhalter42.quest.Quest;
import com.mahn42.anhalter42.quest.QuestObject;

/**
 *
 * @author andre
 */
public class Action extends QuestObject{

    public static void register() {
        Quest.actionTypes.put("action", Action.class);
        Quest.actionTypes.put("loadFrame", LoadFrame.class);
        Quest.actionTypes.put("setLever", SetLever.class);
        Quest.actionTypes.put("startScene", StartScene.class);
        Quest.actionTypes.put("startTimer", StartTimer.class);
        Quest.actionTypes.put("stopTimer", StopTimer.class);
        Quest.actionTypes.put("fillBlocks", FillBlocks.class);
        Quest.actionTypes.put("generateBlocks", GenerateBlocks.class);
        Quest.actionTypes.put("storeArea", StoreArea.class);
        Quest.actionTypes.put("restoreArea", RestoreArea.class);
        Quest.actionTypes.put("stopQuest", StopQuest.class);
    }

    public String type;
    
    public void initialize() {
    }

    public void execute() {
    }
}
