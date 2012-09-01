/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.framework.BlockArea;
import com.mahn42.framework.BlockPosition;
import java.util.HashMap;

/**
 *
 * @author andre
 */
public class StoreArea extends Action {
    public class Item {
        public BlockArea area;
        BlockPosition edge;
        public Item(BlockPosition aEdge, BlockArea aArea) {
            area = aArea;
            edge = aEdge;
        }
    }
    
    // RUNTIME
    public static HashMap<String, Item> areas = new HashMap<String, Item>();
    // META
    public String name;
    public BlockPosition from = new BlockPosition();
    public BlockPosition to = new BlockPosition();
    
    @Override
    public void initialize() {
        super.initialize();
        BlockPosition lFrom = from.clone();
        BlockPosition lTo = to.clone();
        from = lFrom.getMinPos(lTo);
        to = lFrom.getMaxPos(lTo);
        from.add(quest.edge1);
        to.add(quest.edge1);
    }
    
    @Override
    public void execute() {
        BlockArea lArea = new BlockArea(quest.world, from, to);
        areas.put(name, new Item(from, lArea));
    }
}
