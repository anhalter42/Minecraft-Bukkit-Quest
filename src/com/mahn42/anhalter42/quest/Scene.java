/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.anhalter42.quest.trigger.Trigger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 *
 * @author andre
 */
public class Scene extends QuestObject {

    // RUNTIME
    public int ticks = 0;
    // META
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
                        QuestPlugin.plugin.getLogger().log(Level.SEVERE, null, ex);
                    }
                } else {
                    quest.log("unkown trigger type " + lType);
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
        ticks++;
        for(Trigger lTrigger : triggers) {
            if (lTrigger.check()) {
                quest.log("trigger " + lTrigger.type + " activated!");
                lTrigger.executeActions();
            }
        }
    }
}
