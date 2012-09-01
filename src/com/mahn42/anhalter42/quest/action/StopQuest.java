/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

/**
 *
 * @author andre
 */
public class StopQuest extends Action {
    @Override
    public void execute() {
        quest.stop();
    }
}
