/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.trigger;

import com.mahn42.anhalter42.quest.Timer;

/**
 *
 * @author andre
 */
public class TimerLapsed extends Trigger {
    // META
    public String name;
    public boolean restart = false;
    
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
    public boolean check() {
        boolean lResult = false;
        Timer lTimer = (Timer)quest.objects.get(name);
        if (lTimer != null ) {
            //quest.log("timer " + name + " " + lTimer.currentTicks);
            lResult = lTimer.raised;
            if (lResult) {
                lTimer.disable();
                if (restart) {
                    lTimer.currentTicks = 0;
                    lTimer.enable();
                }
            }
        }
        return lResult;
    }
}
