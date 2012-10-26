/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.anhalter42.quest.QuestCheckPoint;
import com.mahn42.framework.BlockRect;

/**
 *
 * @author andre
 */
public class DefineCheckPoint extends Action {
    
    // META
    public BlockRect region = new BlockRect();
    public String text = null;
    
    @Override
    public void execute() {
        QuestCheckPoint lCP = new QuestCheckPoint();
        lCP.region = region.clone();
        lCP.quest = quest;
        if (text != null) {
            lCP.text = text;
        }
        quest.addCheckpoint(lCP);
    }
}
