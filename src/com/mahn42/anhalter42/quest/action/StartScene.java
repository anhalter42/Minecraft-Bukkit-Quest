/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

/**
 *
 * @author andre
 */
public class StartScene extends Action {
    
    public String name = "";
    
    @Override
    public void execute() {
        quest.currentScene = quest.getScene(name);
        quest.currentScene.ticks = 0;
    }
}
