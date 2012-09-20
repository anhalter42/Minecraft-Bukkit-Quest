/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class QuestObjectHashMap<T extends QuestObject> extends HashMap<String, T>{
    protected Class fItemClass;
    public Quest quest;

    public QuestObjectHashMap(Quest aQuest, Class aItemClass) {
        super();
        quest = aQuest;
        fItemClass = aItemClass;
    }

    public Class getItemClass(Object aValue) {
        return fItemClass;
    }
    
    public String getNameFromItem(T lItem) {
        try {
            try {
                return lItem.getClass().getField("name").get(lItem).toString();
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(QuestObjectHashMap.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(QuestObjectHashMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(QuestObjectHashMap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(QuestObjectHashMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void fromSectionValue(Object aValue) {
        if (aValue instanceof ArrayList) {
            for(Object lItem : ((ArrayList)aValue)) {
                try {
                    T lNew = (T)getItemClass(lItem).newInstance();
                    lNew.quest = quest;
                    lNew.fromSectionValue(lItem);
                    put(getNameFromItem(lNew), lNew);
                } catch (InstantiationException ex) {
                    Logger.getLogger(QuestObjectArray.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(QuestObjectArray.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
