/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.anhalter42.quest.Timer;

/**
 *
 * @author andre
 */
public class StopTimer extends Action {
    // META
    public String name;
    public boolean reset = true;
    
    @Override
    public void initialize() {
        Timer lTimer = (Timer)quest.objects.get(name);
        if (lTimer == null) {
            lTimer = new Timer();
            quest.objects.put(name, lTimer);
        }
        super.initialize();
    }
    
    @Override
    public void execute() {
        Timer lTimer = (Timer)quest.objects.get(name);
        if (lTimer != null) {
            if (reset) {
                lTimer.currentTicks = 0;
            }
            lTimer.disable();
        }
    }
    
}
