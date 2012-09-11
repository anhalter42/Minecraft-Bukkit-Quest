/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.trigger;

/**
 *
 * @author andre
 */
public class PlayerCountReached extends Trigger {
    
    // RUNTIME
    protected boolean fIsReached = false;
    
    // META
    public int count = 1;
    
    @Override
    public boolean check() {
        boolean lResult = quest.players.size() >= count;
        if (fIsReached && !lResult) {
            fIsReached = false;
        } else if (fIsReached && lResult) {
            lResult = false;
        } else if (!fIsReached && lResult) {
            fIsReached = true;
        }
        return lResult;
    }
}
