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
    
    public enum Comperator {
        equal,
        lower,
        lowerOrEqual,
        higher,
        higherOrEqual,
    }
    
    // RUNTIME
    protected QuestVariable fVariable;
    
    // META
    public String name;
    public String value;
    public Comperator comperator = Comperator.equal;
    
    @Override
    public void initialize() {
        super.initialize();
        fVariable = quest.getVariable(name);
    }
    
    @Override
    public boolean check() {
        boolean lResult = false;
        switch (comperator) {
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
        return lResult;
    }
}
