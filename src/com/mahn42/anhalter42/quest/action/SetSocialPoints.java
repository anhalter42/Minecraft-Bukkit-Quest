/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

/**
 *
 * @author andre
 */
public class SetSocialPoints extends Action {
    public enum Mode {
        set,
        add,
        remove
    }
    
    public Mode mode = Mode.set;
    public int amount = 1;
    
    @Override
    public void execute() {
        switch (mode) {
            case set: quest.socialPoints = amount; break;
            case add: quest.socialPoints += amount; break;
            case remove: quest.socialPoints -= amount; break;
        }
    }
}
