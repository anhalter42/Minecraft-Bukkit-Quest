/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.BlockRect;
import com.mahn42.framework.ProjectionArea;

/**
 *
 * @author andre
 */
public class ActivateProjectionArea extends Action {
    // Meta
    public BlockRect area = new BlockRect();
    public BlockPosition destination = new BlockPosition();
    public BlockPosition vector = new BlockPosition();
    public BlockPosition scale = new BlockPosition();
    
    @Override
    public void execute() {
        ProjectionArea lArea = new ProjectionArea(area, destination);
        lArea.vector = vector.getVector();
        lArea.scale = destination.getVector();
        quest.addProjectionArea(lArea);
    }
}
