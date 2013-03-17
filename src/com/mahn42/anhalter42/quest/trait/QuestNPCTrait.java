/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.trait;

import com.mahn42.anhalter42.quest.Quest;
import com.mahn42.anhalter42.quest.QuestObject;

/**
 *
 * @author andre
 */
public class QuestNPCTrait extends QuestObject {
    public static void register() {
        Quest.traitTypes.put("", QuestNPCTrait.class);
        Quest.traitTypes.put("none", QuestNPCTrait.class);
    }
    
}
