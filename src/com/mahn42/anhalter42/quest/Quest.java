/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.BlockPosition;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author andre
 */
public class Quest extends QuestObject {
    public ArrayList<Scene> scenes = new ArrayList<Scene>();
    public HashMap<String, BlockPosition> markers = new HashMap<String, BlockPosition>();
    public String name;
    public String startScene;
    
    public static HashMap<String, Class> actionTypes = new HashMap<String, Class>();
    public static HashMap<String, Class> triggerTypes = new HashMap<String, Class>();
    
    public Quest() {
        quest = this;
    }
    
    public void load(File aFile) {
        YamlConfiguration lConf = new YamlConfiguration();
        try {
            lConf.load(aFile);
            fromSectionValue(lConf.get("quest"));
        } catch (Exception ex) {
            Logger.getLogger(Quest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setScenesFromSectionValue(Object aValue) {
        if (aValue instanceof ArrayList) {
            for(Object lItem : ((ArrayList)aValue)) {
                Scene lScene = new Scene();
                lScene.quest = this;
                lScene.fromSectionValue(lItem);
                scenes.add(lScene);
            }
        }
    }

    public void setMarkersFromSectionValue(Object aValue) {
        //Logger.getLogger("xxx").info(aValue.getClass().getName());
        if (aValue instanceof ArrayList) {
            for(Object lItem : ((ArrayList)aValue)) {
                HashMap<String, String> lMap = (HashMap)lItem;
                BlockPosition lPos = new BlockPosition();
                lPos.fromCSV(lMap.get("pos"), ",");
                markers.put(lMap.get("name"), lPos);
            }
            //Logger.getLogger("xxx").info(markers.toString());
        }
    }
}
