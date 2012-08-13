/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.trigger;

import com.mahn42.anhalter42.quest.action.Action;
import com.mahn42.anhalter42.quest.Quest;
import com.mahn42.anhalter42.quest.QuestObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class Trigger extends QuestObject {

    public static void register() {
        Quest.triggerTypes.put("Trigger", Trigger.class);
        Quest.triggerTypes.put("SceneInitialized", SceneInitialized.class);
        Quest.triggerTypes.put("PlayerEnteredRegion", PlayerEnteredRegion.class);
    }

    public String type;
    public ArrayList<Action> actions = new ArrayList<Action>();
    
    public void setActionsFromSectionValue(Object aValue) {
        if (aValue instanceof ArrayList) {
            for(Object lItem : ((ArrayList)aValue)) {
                HashMap<String, Object> lMap = (HashMap)lItem;
                String lType = (String) lMap.get("type");
                Class lActionClass = Quest.actionTypes.get(lType);
                if (lActionClass != null) {
                    try {
                        Action lAction = (Action) lActionClass.getConstructor().newInstance();
                        lAction.quest = quest;
                        lAction.fromSectionValue(lItem);
                        actions.add(lAction);
                    } catch (Exception ex) {
                        Logger.getLogger("xxx").log(Level.SEVERE, null, ex);
                    }
                } else {
                    Logger.getLogger("xxx").info("unkown action type " + lType);
                }
            }
        }
    }

    public void initialize() {
        for(Action lAction : actions) {
            lAction.initialize();
        }
    }
    
    public boolean check() {
        return false;
    }
}
