/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.generator;

import com.mahn42.anhalter42.quest.GeneratorBase;
import com.mahn42.framework.BlockArea;
import com.mahn42.framework.BlockArea.BlockAreaItem;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.SyncBlockList;
import java.util.Random;
import org.bukkit.Material;

/**
 *
 * @author andre
 */
public class Lobster extends GeneratorBase{

    // RUNTIME
    protected Maze fMaze;
    protected Material fMat;
    
    // META
    public String type;
    public int corridorWidth = 1;
    public int corridorHeight = 2;
    public int borderThickness = 1;
    public int wallThickness = 1;
    public String baseMaterial = "SMOOTH_BRICK";
    public byte baseMaterialData = (byte)3;
    public boolean placeTorches = true;
    public boolean placeLadders = true;
    public int chanceForUpDown = 50;
    
    @Override
    public void initialize(BlockPosition aFrom, BlockPosition aTo) {
        super.initialize(aFrom, aTo);
        int lMazeWidth = (width - (borderThickness*2)) / (corridorWidth+wallThickness); // rand + wände + gang
        int lMazeDepth = (depth - (borderThickness*2)) / (corridorWidth+wallThickness); // rand + wände + gang
        int lMazeHeight = ((height - (borderThickness*2)) / (corridorHeight+wallThickness)); // rand + wände + gänge hoch
        fMat = Material.getMaterial(baseMaterial);
        if (fMat == null) {
            fMat = Material.getMaterial(Integer.parseInt(baseMaterial));
        }
        fMaze = new Maze(lMazeWidth, lMazeHeight, lMazeDepth);
        fMaze.chanceForUpDown = chanceForUpDown;
        quest.log("Lobster: cw=" + corridorWidth + " ch=" + corridorHeight + " wt=" + wallThickness + " bt=" + borderThickness);
        quest.log("Area: w=" + width + " h=" + height + " d=" + depth);
        quest.log("Maze: w=" + lMazeWidth + " h=" + lMazeHeight + " d=" + lMazeDepth);
    }
    
    public int getX(int aMazeX) {
        return borderThickness + wallThickness + aMazeX * (corridorWidth+wallThickness);
    }
    
    public int getY(int aMazeY) {
        return borderThickness + wallThickness + aMazeY * (corridorHeight+wallThickness);
    }
    
    public int getZ(int aMazeZ) {
        return borderThickness + wallThickness + aMazeZ * (corridorWidth+wallThickness);
    }
    
    protected void setCellEmpty(int aMazeX, int aMazeY, int aMazeZ) {
        for(int wx=0; wx<corridorWidth; wx++) {
            for(int wz=0; wz<corridorWidth; wz++) {
                for(int y=0; y<corridorHeight; y++) {
                    area.get(getX(aMazeX) + wx, getY(aMazeY) + y, getZ(aMazeZ) + wz).id = Material.AIR.getId();
                }
            }
        }
    }
    
    protected void breakWall(int aMazeX, int aMazeY, int aMazeZ, int aMazeDirection) {
        int lDx = fMaze.getDeltaX(aMazeDirection);
        int lDy = fMaze.getDeltaY(aMazeDirection);
        int lDz = fMaze.getDeltaZ(aMazeDirection);
        // normaler gang?
        if (lDy == 0) {
            if (lDz == 0) {
                for(int wd=0; wd<corridorWidth;wd++) {
                    for(int w=1; w<=wallThickness;w++) {
                        for(int y=0; y<corridorHeight; y++) {
                            area.get(getX(aMazeX) + w*lDx, getY(aMazeY) + y, getZ(aMazeZ) + wd).id = Material.AIR.getId();
                        }
                    }
                }
            } else {
                for(int wd=0; wd<corridorWidth;wd++) {
                    for(int w=1; w<=wallThickness;w++) {
                        for(int y=0; y<corridorHeight; y++) {
                            area.get(getX(aMazeX) + wd, getY(aMazeY) + y, getZ(aMazeZ) + w*lDz).id = Material.AIR.getId();
                        }
                    }
                }
            }
        } else {
            for(int wz=0; wz<corridorWidth;wz++) {
                for(int wx=0; wx<corridorWidth;wx++) {
                    for(int w=1; w<=wallThickness;w++) {
                        area.get(getX(aMazeX) + wx, getY(aMazeY) + lDy*w, getZ(aMazeZ) + wz).id = Material.AIR.getId();
                    }
                }
            }
        }
    }
    
    protected int fMazeToLadderDirs[] = new int[6];
    {
        fMazeToLadderDirs[0] = 0; // y+ up down not working
        fMazeToLadderDirs[1] = 0; // y- up down not working
        /*
        fMazeToLadderDirs[2] = 5; // x+ 
        fMazeToLadderDirs[3] = 4; // x-
        fMazeToLadderDirs[4] = 3; // z+
        fMazeToLadderDirs[5] = 2; // z-
        */
        fMazeToLadderDirs[2] = 4; // x+ 
        fMazeToLadderDirs[3] = 5; // x-
        fMazeToLadderDirs[4] = 2; // z+
        fMazeToLadderDirs[5] = 3; // z-
    }
    
    @Override
    public void execute(SyncBlockList aSyncList) {
        fMaze.build();
        area.clear(fMat, baseMaterialData);
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
        if (placeTorches) {
            Random lRnd = new Random();
            for(int x=0; x<fMaze.width; x++) {
                for(int y=0; y<fMaze.height; y++) {
                    for(int z=0; z<fMaze.depth; z++) {
                        //Maze.Cell lCell = fMaze.get(x, y, z);
                        if (lRnd.nextBoolean()) {
                            BlockAreaItem lItem = area.get(getX(x), getY(y), getZ(z));
                            lItem.id = Material.TORCH.getId();
                            lItem.data = (byte)5;
                        }
                    }
                }
            }
        }
        if (placeLadders && fMaze.height > 1) {
            for(int x=0; x<fMaze.width; x++) {
                for(int y=0; y<(fMaze.height-1); y++) {
                    for(int z=0; z<fMaze.depth; z++) {
                        Maze.Cell lCell = fMaze.get(x, y, z);
                        if (lCell.links[Maze.DirectionTop].broken) {
                            Maze.Cell lCellTop = fMaze.get(x, y + 1, z);
                            for(int d=2;d<6;d++) {
                                if (!lCell.links[d].broken && !lCellTop.links[d].broken) {
                                    int lcx = 0;
                                    int lcz = 0;
                                    if (fMaze.getDeltaX(d)>0) {
                                        lcx = corridorWidth - 1;
                                    }
                                    if (fMaze.getDeltaZ(d)>0) {
                                        lcz = corridorWidth - 1;
                                    }
                                    for(int ldy=0;ldy<(corridorHeight*2 + wallThickness);ldy++) {
                                        BlockAreaItem lItem = area.get(getX(x) + lcx, getY(y) + ldy, getZ(z) + lcz);
                                        lItem.id = Material.LADDER.getId();
                                        lItem.data = (byte)fMazeToLadderDirs[d];
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        int fStats[] = new int[6]; fStats[0]=fStats[1]=fStats[2]=fStats[3]=fStats[4]=fStats[5]=0;
        for(int x=0; x<fMaze.width; x++) {
            for(int y=0; y<fMaze.height; y++) {
                for(int z=0; z<fMaze.depth; z++) {
                    Maze.Cell lCell = fMaze.get(x, y, z);
                    for(int d=0; d<6;d++) {
                        if (lCell.links[d].broken) {
                            fStats[d]++;
                        }
                    }
                }
            }
        }
        quest.log("0=" + fStats[0] + " 1=" + fStats[1] + " 2=" + fStats[2] + " 3=" + fStats[3] + " 4=" + fStats[4] + " 5=" + fStats[5]);
        area.toList(aSyncList, from, BlockArea.BlockAreaPlaceMode.full);
    }
    
}
