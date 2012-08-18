/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

/**
 *
 * @author andre
 */
public class Timer extends QuestObject implements IQuestTick {

    public boolean enabled = false;
    public boolean raised = false;
    public int maxTicks = 0;
    public int currentTicks = 0;
    
    @Override
    public void tick() {
        if (enabled) {
            currentTicks++;
            quest.log("timer " + currentTicks + " max " + maxTicks);
            if (currentTicks >= maxTicks) {
                raised = true;
            } else {
                raised = false;
            }
        } else {
            raised = false;
        }
    }

    public void disable() {
        enabled = false;
        raised = false;
    }

    public void enable() {
        enabled = true;
        raised = false;
    }
    
}
