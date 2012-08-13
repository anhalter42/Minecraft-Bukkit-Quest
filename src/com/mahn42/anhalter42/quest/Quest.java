/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.BlockAreaList;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.SyncBlockList;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author andre
 */
public class Quest extends QuestObject {
    
    /* Runtime */
    public World world = null;
    public ArrayList<Player> players = new ArrayList<Player>();
    public Scene currentScene = null;
    public SyncBlockList syncList = null;
    public BlockPosition edge1 = new BlockPosition();
    public BlockPosition edge2 = new BlockPosition();
    public BlockAreaList frames = new BlockAreaList();
    
    /* Meta */
    public ArrayList<Scene> scenes = new ArrayList<Scene>();
    public HashMap<String, BlockPosition> markers = new HashMap<String, BlockPosition>();
    public String name;
    public String startScene;
    public BlockPosition startPos = new BlockPosition(0,0,0); // relative from player
    
    /* Static */
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
            frames.load(new File(aFile.toString().replaceAll(".yml", ".frm")));
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
        if (aValue instanceof ArrayList) {
            for(Object lItem : ((ArrayList)aValue)) {
                HashMap<String, String> lMap = (HashMap)lItem;
                BlockPosition lPos = new BlockPosition();
                lPos.fromCSV(lMap.get("pos"), ",");
                markers.put(lMap.get("name"), lPos);
            }
        }
    }

    public Scene getScene(String aName) {
        for(Scene lScene : scenes) {
            if (lScene.name.equals(aName)) {
                return lScene;
            }
        }
        return null;
    }
    
    public void initialze() {
        for(Scene lScene : scenes) {
            lScene.initilize();
        }
        if (startScene != null) {
            currentScene = getScene(startScene);
        }
    }
    
    public void run() {
        if (currentScene != null) {
            currentScene.run();
        }
    }
}
