/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.generator;

import com.mahn42.anhalter42.quest.GeneratorBase;
import com.mahn42.framework.BlockArea;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.SyncBlockList;
import org.bukkit.Material;

/**
 *
 * @author andre
 */
public class Lobster extends GeneratorBase{

    protected Maze fMaze;
    
    @Override
    public void initialize(BlockPosition aFrom, BlockPosition aTo) {
        super.initialize(aFrom, aTo);
        int lMazeWidth = (width - 2) / 2; // rand + wände
        int lMazeDepth = (depth - 2) / 2; // rand + wände
        int lMazeHeight = ((height - 2) / (2+1)); // rand + wände + gänge 2 hoch
        fMaze = new Maze(lMazeWidth, lMazeHeight, lMazeDepth);
    }
    
    protected void setCellEmpty(int aMazeX, int aMazeY, int aMazeZ) {
        area.get(1 + aMazeX * 2, 1 + aMazeY * 3,     1 + aMazeZ * 2).id = Material.AIR.getId();
        area.get(1 + aMazeX * 2, 1 + aMazeY * 3 + 1, 1 + aMazeZ * 2).id = Material.AIR.getId();
    }
    
    protected void breakWall(int aMazeX, int aMazeY, int aMazeZ, int aMazeDirection) {
        int lDx = fMaze.getDeltaX(aMazeDirection);
        int lDy = fMaze.getDeltaY(aMazeDirection);
        int lDz = fMaze.getDeltaZ(aMazeDirection);
        area.get(1 + aMazeX * 2 + lDx, 1 + aMazeY * 3     + lDy, 1 + aMazeZ * 2 + lDz).id = Material.AIR.getId();
        if (lDy == 0) {
            area.get(1 + aMazeX * 2 + lDx, 1 + aMazeY * 3 + 1 + lDy, 1 + aMazeZ * 2 + lDz).id = Material.AIR.getId();
        }
    }
    
    @Override
    public void execute(SyncBlockList aSyncList) {
        fMaze.build();
        area.clear(Material.SMOOTH_BRICK, (byte)3);
        for(int x=0; x<fMaze.width; x++) {
            for(int y=0; y<fMaze.height; y++) {
                for(int z=0; z<fMaze.depth; z++) {
                    Maze.Cell lCell = fMaze.get(x, y, z);
                    setCellEmpty(x, y, z);
                    for(int d=0; d<6; d++) {
                        if (lCell.links[d].broken) {
                            breakWall(x, y, z, d);
                        }
                    }
                }
            }
        }
        area.toList(aSyncList, from, BlockArea.BlockAreaPlaceMode.full);
    }
    
}
