/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.BlockPosition;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
                                quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                            } else if (lValue instanceof ArrayList) {
                                //Logger.getLogger("xxx").info(lField.getType().toString());
                            } else if (lValue instanceof HashMap) {
                                //Logger.getLogger("xxx").info(lField.getType().toString());
                            } else {
                                Class lDClass = lField.getType();
                                lValue = lValues.get(lKey);
                                if (lDClass.isAssignableFrom(lValue.getClass())) {
                                    lField.set(this, lValue);
                                    quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValues.get(lKey));
                                } else {
                                    if (lDClass.isAssignableFrom(String.class)) {
                                        lField.set(this, lValue.toString());
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(int.class)) {
                                        lField.setInt(this, Integer.parseInt(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(Integer.class)) {
                                        lField.setInt(this, Integer.parseInt(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(short.class)) {
                                        lField.setShort(this, Short.parseShort(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(Short.class)) {
                                        lField.setShort(this, Short.parseShort(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(byte.class)) {
                                        lField.setByte(this, Byte.parseByte(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(Short.class)) {
                                        lField.setByte(this, Byte.parseByte(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(boolean.class)) {
                                        lField.setBoolean(this, Boolean.parseBoolean(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(Boolean.class)) {
                                        lField.setBoolean(this, Boolean.parseBoolean(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(double.class)) {
                                        lField.setDouble(this, Double.parseDouble(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(Double.class)) {
                                        lField.setDouble(this, Double.parseDouble(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(float.class)) {
                                        lField.setFloat(this, Float.parseFloat(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else if (lDClass.isAssignableFrom(Float.class)) {
                                        lField.setFloat(this, Float.parseFloat(lValue.toString()));
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue);
                                    } else {
                                        quest.log(getClass().getSimpleName() + ":" + lKey + "=" + lValue + " not assignable!!!");
                                    }
                                }
                            }
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(Quest.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                        }
                    } catch (SecurityException ex) {
                        Logger.getLogger(QuestObject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (NoSuchFieldException ex) {
                    quest.log(lClass.getSimpleName() + ":Field " + lKey + " not found!");
                } catch (SecurityException ex) {
                }
            }
        }
    }
}
