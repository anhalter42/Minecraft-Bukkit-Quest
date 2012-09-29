/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.Building;
import com.mahn42.framework.BuildingDB;
import com.mahn42.framework.BuildingHandlerBase;
import java.io.File;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
    public JavaPlugin getPlugin() {
        return QuestPlugin.plugin;
    }
    
    @Override
    public Building insert(Building aBuilding) {
        if (aBuilding.description.name.startsWith("Quest.Starter")) {
            Sign lSign = (Sign)aBuilding.getBlock("sign").position.getBlock(aBuilding.world).getState();
            String lQuestName = lSign.getLine(0);
            File lFolder = QuestPlugin.plugin.getBuildingQuestFolder();
            File lFile = new File(lFolder.getPath() + File.separatorChar + lQuestName);
            if (!lFile.exists()) {
                aBuilding.sendToPlayer("Quest '%s' does not exists!", lQuestName);
                return null;
            }
        }
        QuestBuildingDB lDB = QuestPlugin.plugin.DBs.getDB(aBuilding.world);
        QuestBuilding lQuestBuilding = new QuestBuilding();
        lQuestBuilding.cloneFrom(aBuilding);
        lDB.addRecord(lQuestBuilding);
        return lQuestBuilding;
    }
    
    @Override
    public boolean redstoneChanged(BlockRedstoneEvent aEvent, Building aBuilding) {
        QuestBuilding lQB = (QuestBuilding)aBuilding;
        switch (lQB.kind) {
            case building:
                if (aEvent.getNewCurrent() > 0) {
                    QuestPlugin.plugin.startBuildingQuest(lQB);
                }
        }
        return true;
    }
}
