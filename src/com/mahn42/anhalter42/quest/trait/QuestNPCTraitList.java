/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.trait;

import com.mahn42.anhalter42.quest.Quest;
import com.mahn42.anhalter42.quest.QuestObjectArray;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author andre
 */
public class QuestNPCTraitList<T extends QuestNPCTrait> extends QuestObjectArray<T> {
    
    public QuestNPCTraitList(Quest aQuest) {
        super(aQuest, QuestNPCTrait.class);
    }

    public Class getItemClass(Object aValue) {
        if (aValue instanceof HashMap) {
            Map<String, Object> lValues = (HashMap)aValue;
            String lType = lValues.get("type").toString();
            
        }
        return super.getItemClass(aValue);
    }
}
