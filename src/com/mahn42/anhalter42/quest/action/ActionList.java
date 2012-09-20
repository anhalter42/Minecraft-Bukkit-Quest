/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.anhalter42.quest.Quest;
import com.mahn42.anhalter42.quest.QuestObjectArray;
import java.util.HashMap;
import org.bukkit.ChatColor;

/**
 *
 * @author andre
 */
public class ActionList extends QuestObjectArray<Action> {
    public ActionList(Quest aQuest) {
        super(aQuest, Action.class);
    }
    
    @Override
    public Class getItemClass(Object aValue) {
        if (aValue instanceof HashMap) {
            HashMap<String, Object> lMap = (HashMap)aValue;
            String lType = (String) lMap.get("type");
            Class lActionClass = Quest.actionTypes.get(lType);
            if (lActionClass == null) {
                quest.log(ChatColor.RED + "unkown action type " + lType);
            }
            return lActionClass;
        } else {
            return super.getItemClass(aValue);
        }
    }
}
