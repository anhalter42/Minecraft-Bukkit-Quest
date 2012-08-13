/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.BlockPosition;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author andre
 */
public class QuestObject {
    public Quest quest = null;
    public void fromSectionValue(Object aObject) {
        Class lClass = getClass();
        //Logger.getLogger("fromSectionValue").info(aObject.toString());
        if (aObject instanceof ConfigurationSection) {
            fromSectionValue(((ConfigurationSection)aObject).getValues(false));
        } else if (aObject instanceof HashMap) {
            Map<String, Object> lValues = (HashMap)aObject;
            for(String lKey : lValues.keySet()) {
                try {
                    String lMethodName = lKey.substring(0, 1).toUpperCase() + lKey.substring(1);
                    try {
                        Method lMethod = lClass.getMethod("set" + lMethodName + "FromSectionValue", Object.class);
                        try {
                            lMethod.invoke(this, lValues.get(lKey));
                        } catch (Exception ex) {
                            Logger.getLogger(QuestObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (NoSuchMethodException exm) {
                        //Logger.getLogger(QuestObject.class.getName()).log(Level.SEVERE, null, exm);
                        Field lField = lClass.getField(lKey);
                        try {
                            Object lValue = lField.get(this);
                            if (lValue instanceof BlockPosition) {
                                if (quest != null) {
                                    BlockPosition lPos = quest.markers.get((String)lValues.get(lKey));
                                    if (lPos != null) {
                                        ((BlockPosition)lValue).cloneFrom(lPos);
                                    } else {
                                        ((BlockPosition)lValue).fromCSV((String)lValues.get(lKey), ",");
                                    }
                                } else {
                                    ((BlockPosition)lValue).fromCSV((String)lValues.get(lKey), ",");
                                }
                                Logger.getLogger("xxx").info(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                            } else if (lValue instanceof ArrayList) {
                                //Logger.getLogger("xxx").info(lField.getType().toString());
                            } else if (lValue instanceof HashMap) {
                                //Logger.getLogger("xxx").info(lField.getType().toString());
                            } else {
                                lField.set(this, lValues.get(lKey));
                                Logger.getLogger("xxx").info(getClass().getSimpleName() + ":" + lKey + "=" + lValues.get(lKey));
                            }
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(Quest.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            //Logger.getLogger(Quest.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (SecurityException ex) {
                        Logger.getLogger(QuestObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger("xxx").info(lClass.getSimpleName() + ":Field " + lKey + " not found!");
                    //Logger.getLogger(Quest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    //Logger.getLogger(Quest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
