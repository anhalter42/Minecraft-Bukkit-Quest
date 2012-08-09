/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class Trigger {

    public ArrayList<Action> actions = new ArrayList<Action>();
    
    public void initialize() {
    }
    
    public boolean check(Quest aQuest) {
        return false;
    }
}
