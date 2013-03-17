/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class QuestObjectArray<T extends QuestObject> extends ArrayList<T> {
    protected Class fItemClass;
    public Quest quest;
    
    public QuestObjectArray(Quest aQuest, Class aItemClass) {
        quest = aQuest;
        fItemClass = aItemClass;
    }
    
    public Class getItemClass(Object aValue) {
        return fItemClass;
    }
    
    public void fromSectionValue(Object aValue) {
        if (aValue instanceof ArrayList) {
            for(Object lItem : ((ArrayList)aValue)) {
                try {
                    T lNew = (T)getItemClass(lItem).newInstance();
                    lNew.quest = quest;
                    lNew.init();
                    lNew.fromSectionValue(lItem);
                    add(lNew);
                } catch (InstantiationException ex) {
                    Logger.getLogger(QuestObjectArray.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(QuestObjectArray.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
