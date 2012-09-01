/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.generator;

import com.mahn42.anhalter42.quest.GeneratorBase;
import com.mahn42.anhalter42.quest.QuestObject;
import com.mahn42.framework.BlockArea;
import com.mahn42.framework.BlockArea.BlockAreaItem;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.SyncBlockList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author andre
 */
public class Lobster extends GeneratorBase{

    public class Mat extends QuestObject {
        protected Material mat;
        public byte data = (byte)0;
        public int chanceToUse = 100;
        public int mazeLevel = -1; // allways
        public void setMaterialFromSectionValue(Object aValue) {
            String lStr = aValue.toString().toUpperCase();
            mat = Material.getMaterial(lStr);
            if (mat == null) {
                mat = Material.getMaterial(Integer.parseInt(lStr));
            }
        }
    }
    
    public class MatList extends ArrayList<Mat> {
        protected Random fRnd = new Random();

        public Mat getNext(int aMazeLevel) {
            Mat lResult = null;
            Mat lFound = new Mat();
            lFound.mat = fMat;
            lFound.data = baseMaterialData;
            for(Mat lMat : this) {
                if (lMat.mazeLevel < 0 || lMat.mazeLevel == aMazeLevel) {
                    lFound = lMat;
                    if (fRnd.nextInt(100) < lMat.chanceToUse) {
                        lResult = lMat;
                    }
                }
            }
            return lResult == null ? lFound : lResult;
        }
        public void fromSectionValue(Object aValue) {
            if (aValue instanceof ArrayList) {
                for(Object lItem : ((ArrayList)aValue)) {
                    Mat lMat = new Mat();
                    if (lItem instanceof HashMap) {
                        lMat.fromSectionValue(lItem);
                    } else if (lItem instanceof String) {
                        String lStr = lItem.toString().toUpperCase();
                        lMat.mat = Material.getMaterial(lStr);
                        if (lMat.mat == null) {
                            lMat.mat = Material.getMaterial(Integer.parseInt(lStr));
                        }
                    }
                    add(lMat);
                }
            }
        }
    }
    
    public class ChestItem extends QuestObject { 
        public Material material;
        public byte data = (byte)0;
        public int amount = 1;
        public int maxAmount = 1;
        public int chanceToUse = 100;
        public int mazeLevel = -1; // allways
        
        public void setMaterialFromSectionValue(Object aValue) {
            String lStr = aValue.toString().toUpperCase();
            material = Material.getMaterial(lStr);
            if (material == null) {
                material = Material.getMaterial(Integer.parseInt(lStr));
            }
        }
    }
    
    public class ChestItems extends ArrayList<ChestItem> {
        protected Random fRnd = new Random();

        public ItemStack[] getNext(int aMazeLevel) {
            ItemStack[] lResult = null;
            ArrayList<ItemStack> lStacks = new ArrayList<ItemStack>();
            for(ChestItem lItem : this) {
                if (lItem.mazeLevel < 0 || lItem.mazeLevel == aMazeLevel) {
                    if (fRnd.nextInt(100) < lItem.chanceToUse) {
                        int lMore = lItem.maxAmount - lItem.amount;
                        if (lMore > 0) {
                            lMore = fRnd.nextInt(1 + lMore);
                        }
                        lStacks.add(new ItemStack(lItem.material, lItem.amount + lMore, (short)0, lItem.data));
                    }
                }
            }
            if (!lStacks.isEmpty()) {
                lResult = new ItemStack[lStacks.size()];
                for(int i=0;i<lStacks.size();i++) {
                    lResult[i] = lStacks.get(i);
                }
            }
            return lResult;
        }
        public void fromSectionValue(Object aValue) {
            if (aValue instanceof ArrayList) {
                for(Object lItem : ((ArrayList)aValue)) {
                    ChestItem lMat = new ChestItem();
                    if (lItem instanceof HashMap) {
                        lMat.fromSectionValue(lItem);
                    } else if (lItem instanceof String) {
                        String lStr = lItem.toString().toUpperCase();
                        lMat.material = Material.getMaterial(lStr);
                        if (lMat.material == null) {
                            lMat.material = Material.getMaterial(Integer.parseInt(lStr));
                        }
                    }
                    add(lMat);
                }
            }
        }
    }
    
    // RUNTIME
    protected Maze fMaze;
    protected Material fMat;
    
    // META
    public String type;
    public int corridorWidth = 1;
    public int corridorHeight = 2;
    public int borderThickness = 1;
    public int wallThickness = 1;
    public int ceilingThickness = 1;
    public boolean UpDownUseCorridorWidth = false;
    public String baseMaterial = "SMOOTH_BRICK";
    public byte baseMaterialData = (byte)3;
    public boolean placeTorches = true;
    public boolean placeLadders = true;
    public boolean placeChests = true;
    //TODO public boolean breakMoreWalls = true;
    //TODO public int chanceForBreakWalls = 10;
    //TODO public boolean placeWoodenDoors = true;
    public int chanceForUpDown = 50;
    public int chanceForTorches = 50;
    public int chanceForChests = 50;
    //TODO public int chanceForWoodenDoors = 10;
    
    public MatList wallMaterials = new MatList();
    public MatList floorMaterials = new MatList();
    public MatList ceilingMaterials = new MatList();
    
    public ChestItems chestItems = new ChestItems();
    
    public void setWallMaterialsFromSectionValue(Object aValue) {
        wallMaterials.fromSectionValue(aValue);
    }
    
    public void setFloorMaterialsFromSectionValue(Object aValue) {
        floorMaterials.fromSectionValue(aValue);
    }
    
    public void setCeilingMaterialsFromSectionValue(Object aValue) {
        ceilingMaterials.fromSectionValue(aValue);
    }
    
    public void setChestItemsFromSectionValue(Object aValue) {
        chestItems.fromSectionValue(aValue);
    }
    
    @Override
    public void initialize(BlockPosition aFrom, BlockPosition aTo) {
        super.initialize(aFrom, aTo);
        int lMazeWidth = (width - (borderThickness*2+wallThickness)) / (corridorWidth+wallThickness); // rand + wände + gang
        int lMazeDepth = (depth - (borderThickness*2+wallThickness)) / (corridorWidth+wallThickness); // rand + wände + gang
        int lMazeHeight = ((height - (borderThickness*2+ceilingThickness)) / (corridorHeight+ceilingThickness)); // rand + wände + gänge hoch
        fMat = Material.getMaterial(baseMaterial.toUpperCase());
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
        return borderThickness + ceilingThickness + aMazeY * (corridorHeight+ceilingThickness);
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
            if (UpDownUseCorridorWidth) {
                for(int wz=0; wz<corridorWidth;wz++) {
                    for(int wx=0; wx<corridorWidth;wx++) {
                        for(int w=1; w<=ceilingThickness;w++) {
                            area.get(getX(aMazeX) + wx, getY(aMazeY) + lDy*w, getZ(aMazeZ) + wz).id = Material.AIR.getId();
                        }
                    }
                }
            } else {
                for(int w=1; w<=ceilingThickness;w++) {
                    area.get(getX(aMazeX), getY(aMazeY) + lDy*w, getZ(aMazeZ)).id = Material.AIR.getId();
                }
            }
        }
    }
    
    protected int fMazeToLadderDirs[] = new int[6];
    {
        fMazeToLadderDirs[0] = 0; // y+ up down not working
        fMazeToLadderDirs[1] = 0; // y- up down not working
        fMazeToLadderDirs[2] = 4; // x+ 
        fMazeToLadderDirs[3] = 5; // x-
        fMazeToLadderDirs[4] = 2; // z+
        fMazeToLadderDirs[5] = 3; // z-
    }
    
    protected void initializeArea() {
        if (wallMaterials.isEmpty() && floorMaterials.isEmpty()) {
            area.clear(fMat, baseMaterialData);
        } else {
            area.clear(fMat, baseMaterialData);
            for(int x=0; x<fMaze.width; x++) {
                for(int y=0; y<fMaze.height; y++) {
                    for(int z=0; z<fMaze.depth; z++) {
                        int xx = getX(x);
                        int yy = getY(y);
                        int zz = getZ(z);
                        for(int lx=0;lx<corridorWidth;lx++) {
                            for(int lz=0;lz<corridorWidth;lz++) {
                                BlockAreaItem lItem;
                                Mat lMat;
                                lItem = area.get(xx+lx, yy-1, zz+lz);
                                lMat = floorMaterials.getNext(y);
                                lItem.id = lMat.mat.getId();
                                lItem.data = lMat.data;
                                lItem = area.get(xx+lx, yy+corridorHeight, zz+lz);
                                lMat = ceilingMaterials.getNext(y);
                                lItem.id = lMat.mat.getId();
                                lItem.data = lMat.data;
                            }
                        }
                        for(int ly=0;ly<corridorHeight;ly++) {
                            for(int lx=0;lx<corridorWidth;lx++) {
                                BlockAreaItem lItem;
                                Mat lMat;
                                lItem = area.get(xx+lx, yy+ly, zz-1);
                                lMat = wallMaterials.getNext(y);
                                lItem.id = lMat.mat.getId();
                                lItem.data = lMat.data;
                                lItem = area.get(xx+lx, yy+ly, zz+corridorWidth);
                                lMat = wallMaterials.getNext(y);
                                lItem.id = lMat.mat.getId();
                                lItem.data = lMat.data;
                            }
                            for(int lz=0;lz<corridorWidth;lz++) {
                                BlockAreaItem lItem;
                                Mat lMat;
                                lItem = area.get(xx-1, yy+ly, zz+lz);
                                lMat = wallMaterials.getNext(y);
                                lItem.id = lMat.mat.getId();
                                lItem.data = lMat.data;
                                lItem = area.get(xx+corridorWidth, yy+ly, zz+lz);
                                lMat = wallMaterials.getNext(y);
                                lItem.id = lMat.mat.getId();
                                lItem.data = lMat.data;
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void execute(SyncBlockList aSyncList) {
        fMaze.build();
        initializeArea();
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
                        Maze.Cell lCell = fMaze.get(x, y, z);
                        if (!lCell.links[Maze.DirectionBottom].broken) {
                            if (lRnd.nextInt(100) < chanceForTorches) {
                                BlockAreaItem lItem = area.get(getX(x), getY(y), getZ(z));
                                lItem.id = Material.TORCH.getId();
                                lItem.data = (byte)5;
                            }
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
                                    for(int ldy=0;ldy<(corridorHeight*2 + ceilingThickness);ldy++) {
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
        if (placeChests) {
            Random lRnd = new Random();
            for(int x=0; x<fMaze.width; x++) {
                for(int y=0; y<fMaze.height; y++) {
                    for(int z=0; z<fMaze.depth; z++) {
                        Maze.Cell lCell = fMaze.get(x, y, z);
                        if (!lCell.links[Maze.DirectionTop].broken
                                && !lCell.links[Maze.DirectionBottom].broken) {
                            int lBCount = 0;
                            int lFDir = 0;
                            for (int d=2;d<6;d++) {
                                if (lCell.links[d].broken) {
                                    lBCount++;
                                } else {
                                    lFDir = d;
                                }
                            }
                            if (lBCount < 2 && lRnd.nextInt(100) < chanceForChests) {
                                BlockAreaItem lItem = area.get(getX(x), getY(y), getZ(z));
                                lItem.id = Material.CHEST.getId();
                                lItem.data = (byte)fMazeToLadderDirs[lFDir];
                                lItem.itemStacks = chestItems.getNext(y);
                                /*lItem.itemStacks = new ItemStack[1];
                                lItem.itemStacks[0] = new ItemStack(Material.GOLD_BLOCK, 1, (short)0, (byte)0);*/
                            }
                        }
                    }
                }
            }
        }
        /* TODO
        if (placeWoodenDoors) {
            Random lRnd = new Random();
            for(int x=0; x<fMaze.width; x++) {
                for(int y=0; y<fMaze.height; y++) {
                    for(int z=0; z<fMaze.depth; z++) {
                        Maze.Cell lCell = fMaze.get(x, y, z);
                        if (!lCell.links[Maze.DirectionTop].broken
                                && !lCell.links[Maze.DirectionBottom].broken) {
                            if (lRnd.nextInt(100) < chanceForWoodenDoors) {
                                BlockAreaItem lItem = area.get(getX(x), getY(y), getZ(z));
                                lItem.id = Material.CHEST.getId();
                                lItem.data = (byte)fMazeToLadderDirs[lFDir];
                                lItem.itemStacks = new ItemStack[1];
                                lItem.itemStacks[0] = new ItemStack(Material.GOLD_BLOCK, 1, (short)0, (byte)0);
                            }
                        }
                    }
                }
            }
        }
        */
        /* Statistics
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
        */
        area.toList(aSyncList, from, BlockArea.BlockAreaPlaceMode.full);
    }
    
}
