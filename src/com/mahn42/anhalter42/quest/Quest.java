/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.anhalter42.quest.action.Action;
import com.mahn42.anhalter42.quest.action.ActionList;
import com.mahn42.anhalter42.quest.trait.QuestNPC;
import com.mahn42.framework.BlockAreaList;
import com.mahn42.framework.BlockPosition;
import com.mahn42.framework.EntityReachedPathItemEvent;
import com.mahn42.framework.Framework;
import com.mahn42.framework.ProjectionArea;
import com.mahn42.framework.ProjectionAreas;
import com.mahn42.framework.RestrictedRegion;
import com.mahn42.framework.SyncBlockList;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 *
 * @author andre
 */
public class Quest extends QuestObject {
    
    /* Runtime */
    public Random random = new Random();
    public World world = null;
    public ArrayList<String> players = new ArrayList<String>();
    public ArrayList<Location> playerStartingLocations = new ArrayList<Location>();
    public Scene currentScene = null;
    public SyncBlockList syncList = null;
    public BlockPosition edge1 = new BlockPosition();
    public BlockPosition edge2 = new BlockPosition();
    public BlockAreaList frames = new BlockAreaList();
    public boolean stopped = false;
    public HashMap<String, Object> objects = new HashMap<String, Object>();
    public RestrictedRegion restrictedRegion = new RestrictedRegion();
    public ArrayList<QuestTaskInteraction> interactions;
    public File workingDirectory = null;
    public BuildingQuest buildingQuest;
    public ArrayList<PlayerCheckPoint> playerCheckpoints = new ArrayList<PlayerCheckPoint>();
    public ProjectionAreas projectionAreas;
    
    /* Meta */
    public QuestObjectArray<Scene> scenes = new QuestObjectArray<Scene>(this, Scene.class);
    public HashMap<String, BlockPosition> markers = new HashMap<String, BlockPosition>();
    public QuestObjectHashMap<QuestInventory> inventories = new QuestObjectHashMap<QuestInventory>(this, QuestInventory.class);
    public QuestObjectHashMap<QuestVariable> variables = new QuestObjectHashMap<QuestVariable>(this, QuestVariable.class);
    public QuestObjectArray<PlayerPosition> playerPositions = new QuestObjectArray<PlayerPosition>(this, PlayerPosition.class);
    public QuestObjectArray<QuestCheckPoint> checkpoints = new QuestObjectArray<QuestCheckPoint>(this, QuestCheckPoint.class);
    public QuestObjectArray<QuestNPC> npcs = new QuestObjectArray<QuestNPC>(this, QuestNPC.class);
    public int minPlayerCount = 1;
    public int maxPlayerCount = 1;
    public String name;
    public String startScene;
    public BlockPosition startPos = new BlockPosition(0,0,0); // relative from player
    public int width = 1;
    public int height = 1;
    public int depth = 1;
    public int socialPoints = 1;
    public boolean restrictRegion = true;
    public ActionList stopActions = new ActionList(this);
    public ActionList startActions = new ActionList(this);
    public boolean useQuestWorld = true;
    
    /* Static */
    public static HashMap<String, Class> actionTypes = new HashMap<String, Class>();
    public static HashMap<String, Class> triggerTypes = new HashMap<String, Class>();
    public static HashMap<String, Class> generatorTypes = new HashMap<String, Class>();
    public static HashMap<String, Class> traitTypes = new HashMap<String, Class>();
    
    public Quest() {
        quest = this;
    }
    
    public boolean load(File aFile) {
        YamlConfiguration lConf = new YamlConfiguration(); 
        try {
            lConf.load(aFile);
            fromSectionValue(lConf.get("quest"));
            frames.load(new File(aFile.toString().replaceAll(".yml", ".frm")));
            workingDirectory = aFile.getParentFile();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Quest.class.getName()).log(Level.SEVERE, null, ex);
            return false;
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

    public void setMarkersFromSectionValue(Object aValue) {
        if (aValue instanceof ArrayList) {
            for(Object lItem : ((ArrayList)aValue)) {
                HashMap<String, String> lMap = (HashMap)lItem;
                BlockPosition lPos = new BlockPosition();
                lPos.fromCSV(lMap.get("pos"), "\\,");
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
    
    public QuestVariable getVariable(String aName) {
        QuestVariable lResult = variables.get(aName);
        if (lResult == null) {
            lResult = new QuestVariable();
            lResult.quest = this;
            lResult.name = aName;
            variables.put(lResult.name, lResult);
        }
        return lResult;
    }
    
    public QuestInventory getInventory(String aName) {
        QuestInventory lInv = inventories.get(aName);
        if (lInv == null) {
            lInv = new QuestInventory();
            lInv.quest = this;
            inventories.put(aName, lInv);
        }
        return lInv;
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
        if (projectionAreas == null) {
            projectionAreas = new ProjectionAreas(world);
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
        for(Action lAction : stopActions) {
            lAction.initialize();
        }
        for(Action lAction : startActions) {
            lAction.initialize();
        }
        for(Action lAction : startActions) {
            quest.log("action " + lAction.type + " executed.");
            lAction.execute();
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
            for(QuestCheckPoint lCP : checkpoints) {
                for(String lPlayerName : players) {
                    Player lPlayer = getPlayer(lPlayerName);
                    if (lPlayer != null) {
                        if (lCP.region.isBetween(new BlockPosition(lPlayer.getLocation()))) {
                            setPlayerCheckpoint(lCP, lPlayer);
                        }
                    }
                }
            }
            currentScene.run();
        } else {
            stop();
        }
    }

    public void stop() {
        if (!stopped) {
            syncList = new SyncBlockList(world);
            for(Action lAction : stopActions) {
                quest.log("action " + lAction.type + " executed.");
                lAction.execute();
            }
            syncList.execute();
            if (restrictRegion) {
                Framework.plugin.getRestrictedRegions(world, true).remove(restrictedRegion);
            }
            if (projectionAreas != null) {
                ProjectionAreas lAreas = Framework.plugin.getProjectionAreas(world, false);
                if (lAreas != null) {
                    for(ProjectionArea lArea : projectionAreas) {
                        lAreas.remove(lArea);
                    }
                    projectionAreas.clear();
                }
            }
            int lIndex = 0;
            for(String lPlayerName:players) {
                Framework.plugin.getPlayerManager().increaseSocialPoint("", "Quest", socialPoints, name, lPlayerName);
                Player lPlayer = Framework.plugin.getServer().getPlayer(lPlayerName);
                Location lLoc = playerStartingLocations.get(lIndex);
                lPlayer.teleport(lLoc, PlayerTeleportEvent.TeleportCause.PLUGIN);
                lIndex++;
            }
            stopped = true;
            QuestPlugin.plugin.stopQuest(this);
            log("Quest " + name + " stopped. (SP:" + socialPoints + ")");
        }
    }

    public void finish() {
        stop();
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
    
    public void sendPlayerMessage(String aText, Object... aObjects) {
        for(String lPlayerName: players) {
            Player lPlayer = getPlayer(lPlayerName);
            if (lPlayer != null) {
                lPlayer.sendMessage(QuestPlugin.plugin.getText(lPlayer, aText, aObjects));
            }
        }
    }

    public void addPlayer(Player aPlayer) {
        if (!players.contains(aPlayer.getName())) {
            players.add(aPlayer.getName());
            playerStartingLocations.add(aPlayer.getLocation());
        }
    }

    public void addCheckpoint(QuestCheckPoint aCP) {
        for(QuestCheckPoint lCP : checkpoints) {
            if (lCP.region.equals(aCP.region)) {
                lCP.text = aCP.text;
                return;
            }
        }
        checkpoints.add(aCP);
    }
    
    public void setPlayerCheckpoint(QuestCheckPoint aCheckpoint, Player aPlayer) {
        boolean lDone = false;
        for(PlayerCheckPoint lCP : playerCheckpoints) {
            if (lCP.playerName == aPlayer.getName()) {
                lCP.fromPlayer(aPlayer);
                lDone = true;
            }
        }
        if (!lDone) {
            PlayerCheckPoint lCP = new PlayerCheckPoint(aPlayer);
            playerCheckpoints.add(lCP);
        }
        if (aCheckpoint != null) {
            aPlayer.sendMessage(aCheckpoint.text);
        }
    }
    
    public boolean activatePlayerCheckpoint(Player aPlayer) {
        for(PlayerCheckPoint lCP : playerCheckpoints) {
            if (lCP.playerName == aPlayer.getName()) {
                lCP.updatePlayer();
                return true;
            }
        }
        return false;
    }

    public void removePlayer(String aPlayerName) {
        int lIndex = -1;
        for(String lPlayerName : players) {
            lIndex++;
            if (lPlayerName == aPlayerName) {
                break;
            }
        }
        playerStartingLocations.remove(lIndex);
        players.remove(lIndex);
        PlayerCheckPoint lfCP = null;
        for(PlayerCheckPoint lCP : playerCheckpoints) {
            if (lCP.playerName == aPlayerName) {
                break;
            }
        }
        if (lfCP != null) {
            playerCheckpoints.remove(lfCP);
        }
    }

    public void playerIsDied(String aPlayerName) {
        if (!activatePlayerCheckpoint(getPlayer(aPlayerName))) {
            removePlayer(aPlayerName);
        }
    }

    public void addProjectionArea(ProjectionArea aArea) {
        projectionAreas.add(aArea);
        ProjectionAreas lAreas = Framework.plugin.getProjectionAreas(world, true);
        lAreas.add(aArea);
    }

    public void removeProjectionArea(BlockPosition aDestination) {
        ProjectionArea lFArea = null;
        for(ProjectionArea lArea : projectionAreas) {
            if (lArea.destination.equals(aDestination)) {
                lFArea = lArea;
                break;
            }
        }
        if (lFArea != null) {
            projectionAreas.remove(lFArea);
            ProjectionAreas lAreas = Framework.plugin.getProjectionAreas(world, false);
            if (lAreas != null) {
                lAreas.remove(lFArea);
            }
        }
    }

    public void entityReachedPosition(EntityReachedPathItemEvent aEvent) {
        
    }
}
