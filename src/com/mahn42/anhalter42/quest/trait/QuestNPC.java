/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.trait;

import com.mahn42.anhalter42.quest.QuestObject;
import com.mahn42.anhalter42.quest.QuestObjectArray;

/**
 *
 * @author andre
 */
public class QuestNPC extends QuestObject {
    // META
    public String id;
    public QuestObjectArray<QuestNPCPathItem> path = new QuestObjectArray<QuestNPCPathItem>(quest, QuestNPCPathItem.class);;
    public QuestNPCTraitList traits = new QuestNPCTraitList(quest);;

    // Runtime
    public long entityId;
    
    @Override
    public void init() {
        path = new QuestObjectArray<QuestNPCPathItem>(quest, QuestNPCPathItem.class);
        traits = new QuestNPCTraitList(quest);
    }
    
    @Override
    public boolean equals(Object aObject) {
        if (aObject instanceof Integer) {
            return entityId == (Integer)aObject;
        } else if (aObject instanceof QuestNPC) {
            return id == ((QuestNPC)aObject).id && quest == ((QuestNPC)aObject).quest;
        } else {
            return false;
        }
    }
}
