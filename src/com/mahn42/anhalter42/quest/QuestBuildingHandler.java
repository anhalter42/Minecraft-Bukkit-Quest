/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.Building;
import com.mahn42.framework.BuildingDB;
import com.mahn42.framework.BuildingHandlerBase;
import org.bukkit.World;

/**
 *
 * @author andre
 */
public class QuestBuildingHandler extends BuildingHandlerBase {
    @Override
    public BuildingDB getDB(World aWorld) {
        return QuestPlugin.plugin.DBs.getDB(aWorld);
    }
    
    @Override
    public Building insert(Building aBuilding) {
        QuestBuildingDB lDB = QuestPlugin.plugin.DBs.getDB(aBuilding.world);
        QuestBuilding lQuestBuilding = new QuestBuilding();
        lQuestBuilding.cloneFrom(aBuilding);
        lDB.addRecord(lQuestBuilding);
        return lQuestBuilding;
    }

}
