/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.BlockAreaList;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.Framework;
import com.mahn42.framework.RestrictedRegion;
import com.mahn42.framework.SyncBlockList;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 *
 * @author andre
 */
public class Quest extends QuestObject {
    
    /* Runtime */
    public World world = null;
    public ArrayList<String> players = new ArrayList<String>();
    public Scene currentScene = null;
    public SyncBlockList syncList = null;
    public BlockPosition edge1 = new BlockPosition();
    public BlockPosition edge2 = new BlockPosition();
    public BlockAreaList frames = new BlockAreaList();
    public boolean stopped = false;
    public HashMap<String, Object> objects = new HashMap<String, Object>();
    public RestrictedRegion restrictedRegion = new RestrictedRegion();
    
    /* Meta */
    public ArrayList<Scene> scenes = new ArrayList<Scene>();
    public HashMap<String, BlockPosition> markers = new HashMap<String, BlockPosition>();
    public String name;
    public String startScene;
    public BlockPosition startPos = new BlockPosition(0,0,0); // relative from player
    public int width = 1;
    public int height = 1;
    public int depth = 1;
    public int socialPoints = 1;
    public boolean restrictRegion = true;
    
    /* Static */
    public static HashMap<String, Class> actionTypes = new HashMap<String, Class>();
    public static HashMap<String, Class> triggerTypes = new HashMap<String, Class>();
    public static HashMap<String, Class> generatorTypes = new HashMap<String, Class>();
    
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
    
    public void setAllowedMaterialsFromSectionValue(Object aValue) {
        if (aValue instanceof ArrayList) {
            for(Object lItem : ((ArrayList)aValue)) {
                Material lMat = Material.getMaterial(lItem.toString().toUpperCase());
                if (lMat == null) {
                    lMat = Material.getMaterial(Integer.parseInt(lItem.toString()));
                }
                quest.log("allowed material:" + lMat.name());
                restrictedRegion.allowedMaterials.add(lMat);
            }
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
        if (edge2.x == 0 && edge2.y == 0 && edge2.z == 0) {
            edge2.cloneFrom(edge1);
            edge2.add(width - 1, height - 1, depth - 1);
        }
        restrictedRegion.lowerEdge = edge1.clone();
        restrictedRegion.upperEdge = edge2.clone();
        if (restrictRegion) {
            Framework.plugin.getRestrictedRegions(world, true).add(restrictedRegion);
        }
        for(Scene lScene : scenes) {
            lScene.initilize();
        }
        for(Object lObject : objects.entrySet()) {
            if (lObject instanceof QuestObject) {
                ((QuestObject)lObject).quest = this;
            }
        }
        if (startScene != null) {
            currentScene = getScene(startScene);
        }
    }
    
    public void run() {
        if (currentScene != null) {
            for(Object lObject : objects.values()) {
                if (lObject instanceof QuestObject) {
                    ((QuestObject)lObject).quest = this;
                }
                if (lObject instanceof IQuestTick) {
                    ((IQuestTick)lObject).tick();
                }
            }
            currentScene.run();
        } else {
            stop();
        }
    }

    public void stop() {
        if (!stopped) {
            if (restrictRegion) {
                Framework.plugin.getRestrictedRegions(world, true).remove(restrictedRegion);
            }
            for(String lPlayerName:players) {
                Framework.plugin.getPlayerManager().increaseSocialPoint(lPlayerName, "Quest", socialPoints, name, "");
                Player lPlayer = Framework.plugin.getServer().getPlayer(lPlayerName);
                int y = world.getHighestBlockYAt(lPlayer.getLocation());
                Location lLoc = lPlayer.getLocation().clone();
                lLoc.setY(y);
                lPlayer.teleport(lLoc, PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
            stopped = true;
            log("Quest " + name + " stopped. (SP:" + socialPoints + ")");
        }
    }

    public void finish() {
    }
    
    public void log(String aText) {
        if (Framework.plugin.isDebugSet("quest")) {
            QuestPlugin.plugin.getLogger().info("Quest '" + name + "' '" + (currentScene == null ? "null" : currentScene.name) + "':" + aText);
        }
    }
    
    public Player getPlayer(String aName) {
        return QuestPlugin.plugin.getServer().getPlayer(aName);
    }

    public Player getPlayer(int aNumber) {
        if (aNumber < players.size()) {
            return getPlayer(players.get(aNumber));
        } else {
            return null;
        }
    }
}
