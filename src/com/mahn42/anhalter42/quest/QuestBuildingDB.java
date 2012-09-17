/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.BuildingDB;
import java.io.File;
import org.bukkit.World;

/**
 *
 * @author andre
 */
public class QuestBuildingDB extends BuildingDB<QuestBuilding> {

    public QuestBuildingDB() {
        super(QuestBuilding.class);
    }

    public QuestBuildingDB(World aWorld, File aFile) {
        super(QuestBuilding.class, aWorld, aFile);
    }
}
