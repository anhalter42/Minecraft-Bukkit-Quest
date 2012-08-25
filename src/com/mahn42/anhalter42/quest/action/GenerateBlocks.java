/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest.action;

import com.mahn42.anhalter42.quest.IGenerator;
import com.mahn42.anhalter42.quest.Quest;
import com.mahn42.anhalter42.quest.QuestObject;
import com.mahn42.anhalter42.quest.generator.Lobster;
import com.mahn42.anhalter42.quest.generator.Randomizer;
import com.mahn42.framework.BlockPosition;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class GenerateBlocks extends Action {

    public static void register() {
        Quest.generatorTypes.put("Randomizer", Randomizer.class);
        Quest.generatorTypes.put("Lobster", Lobster.class);
    }
    
    // RUNTIME
    protected IGenerator fGenerator;
    // META
    public String generator;
    public BlockPosition from = new BlockPosition();
    public BlockPosition to = new BlockPosition();
    
    //TODO parameters for generator via name value pairs
    
    public void setGeneratorFromSectionValue(Object aObject) {
        if (aObject instanceof String) {
            generator = aObject.toString();
        } else {
            if (aObject instanceof HashMap) {
                HashMap lMap = (HashMap)aObject;
                generator = (String)lMap.get("type");
                createGenerator();
                if (fGenerator instanceof QuestObject) {
                    ((QuestObject)fGenerator).fromSectionValue(aObject);
                }
            } else {
                quest.log("unkown generator description!");
            }
        }
        quest.log("generator " + generator);
    }

    protected void createGenerator() {
        Class<IGenerator> lClass = Quest.generatorTypes.get(generator);
        if (lClass != null) {
            try {
                fGenerator = lClass.newInstance();
            } catch (Exception ex) {
                Logger.getLogger(GenerateBlocks.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (fGenerator instanceof QuestObject) {
                ((QuestObject)fGenerator).quest = quest;
            }
        } else {
            quest.log("generator " + generator + " not found!");
        }
    }
    
    @Override
    public void initialize() {
        BlockPosition lFrom = from.clone();
        BlockPosition lTo = to.clone();
        from = lFrom.getMinPos(lTo);
        to = lFrom.getMaxPos(lTo);
        from.add(quest.edge1);
        to.add(quest.edge1);
        if (fGenerator == null) {
            createGenerator();
        }
        if (fGenerator != null) {
            fGenerator.initialize(from, to);
        }
    }

    @Override
    public void execute() {
        if (fGenerator != null) {
            fGenerator.execute(quest.syncList);
        }
    }

}
