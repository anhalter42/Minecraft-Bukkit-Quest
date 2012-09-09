/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.trigger;

import com.mahn42.anhalter42.quest.QuestVariable;

/**
 *
 * @author andre
 */
public class VariableReachedValue extends Trigger {
    
    public enum Comparator {
        equal,
        lower,
        lowerOrEqual,
        higher,
        higherOrEqual,
    }
    
    // RUNTIME
    protected boolean fIsReached = false;
    protected QuestVariable fVariable;
    
    // META
    public String name;
    public String value;
    public Comparator comparator = Comparator.equal;
    
    @Override
    public void initialize() {
        super.initialize();
        fVariable = quest.getVariable(name);
    }
    
    @Override
    public boolean check() {
        boolean lResult = false;
        switch (comparator) {
            case equal:
                lResult = fVariable.compare(value) == 0;
                break;
            case lower:
                lResult = fVariable.compare(value) < 0;
                break;
            case lowerOrEqual:
                lResult = fVariable.compare(value) <= 0;
                break;
            case higher:
                lResult = fVariable.compare(value) > 0;
                break;
            case higherOrEqual:
                lResult = fVariable.compare(value) >= 0;
                break;
        }
        if (fIsReached && lResult) {
            lResult = false;
        } else if (fIsReached && !lResult) {
            fIsReached = false;
        } else if (!fIsReached && lResult) {
            fIsReached = true;
        }
        return lResult;
    }
}
