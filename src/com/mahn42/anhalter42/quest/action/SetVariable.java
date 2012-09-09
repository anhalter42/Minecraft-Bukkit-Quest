/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.anhalter42.quest.QuestVariable;

/**
 *
 * @author andre
 */
public class SetVariable extends Action {
    public enum Operation {
        set,
        add,
        sub,
        mul,
        div,
        random
    }
    
    // RUNTIME
    protected QuestVariable fVariable;
    
    // META
    public String name;
    public String value;
    public Operation operation = Operation.set;
    
    @Override
    public void initialize() {
        fVariable = quest.getVariable(name);
    }
    
    @Override
    public void execute() {
        switch(operation) {
            case set:
                fVariable.setValue(value);
                break;
            case add:
                fVariable.add(value);
                break;
            case sub:
                fVariable.sub(value);
                break;
            case mul:
                fVariable.mul(value);
                break;
            case div:
                fVariable.div(value);
                break;
            case random:
                fVariable.random(value);
                break;
        }
        quest.log("variable '" + fVariable.name + "' = '" + fVariable.value + "'");
    }
}
