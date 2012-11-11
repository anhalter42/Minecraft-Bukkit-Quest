/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.BlockPosition;

/**
 *
 * @author andre
 */
public class DeactivateProjectionArea extends Action {
    // Meta
    public BlockPosition destination = new BlockPosition();
    
    @Override
    public void execute() {
        quest.removeProjectionArea(destination);
    }    
}
