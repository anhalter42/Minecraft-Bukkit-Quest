/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.anhalter42.quest.trigger.Trigger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class Scene extends QuestObject {
    
    public String name = "";
    public ArrayList<Trigger> triggers = new ArrayList<Trigger>();

    public void setTriggersFromSectionValue(Object aValue) {
        if (aValue instanceof ArrayList) {
            for(Object lItem : ((ArrayList)aValue)) {
                HashMap<String, Object> lMap = (HashMap)lItem;
                String lType = (String) lMap.get("type");
                Class lTriggerClass = Quest.triggerTypes.get(lType);
                if (lTriggerClass != null) {
                    try {
                        Trigger lTrigger = (Trigger) lTriggerClass.getConstructor().newInstance();
                        lTrigger.quest = quest;
                        lTrigger.fromSectionValue(lItem);
                        triggers.add(lTrigger);
                    } catch (Exception ex) {
                        Logger.getLogger("xxx").log(Level.SEVERE, null, ex);
                    }
                } else {
                    Logger.getLogger("xxx").info("unkown trigger type " + lType);
                }
            }
        }
    }

    public void initilize() {
        for(Trigger lTrigger : triggers) {
            lTrigger.initialize();
        }
    }
    
    public void run() {
    }
}
