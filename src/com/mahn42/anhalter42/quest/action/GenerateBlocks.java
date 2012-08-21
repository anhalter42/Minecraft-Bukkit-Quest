/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.anhalter42.quest.IGenerator;
import com.mahn42.anhalter42.quest.Quest;
import com.mahn42.anhalter42.quest.generator.Randomizer;
import com.mahn42.framework.BlockPosition;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class GenerateBlocks extends Action {

    public static void register() {
        Quest.generatorTypes.put("Randomizer", Randomizer.class);
    }
    
    // RUNTIME
    protected IGenerator fGenerator;
    // META
    public String generator;
    public BlockPosition from = new BlockPosition();
    public BlockPosition to = new BlockPosition();
    
    //TODO parameters for generator via name value pairs
    
    @Override
    public void initialize() {
        BlockPosition lFrom = from.clone();
        BlockPosition lTo = to.clone();
        from = lFrom.getMinPos(lTo);
        to = lFrom.getMaxPos(lTo);
        from.add(quest.edge1);
        to.add(quest.edge1);
        Class<IGenerator> lClass = Quest.generatorTypes.get(generator);
        if (lClass != null) {
            try {
                fGenerator = lClass.newInstance();
            } catch (Exception ex) {
                Logger.getLogger(GenerateBlocks.class.getName()).log(Level.SEVERE, null, ex);
            }
            fGenerator.initialize(from, to);
        } else {
            quest.log("generator " + generator + " not found!");
        }
    }

    @Override
    public void execute() {
        if (fGenerator != null) {
            fGenerator.execute(quest.syncList);
        }
    }

}
