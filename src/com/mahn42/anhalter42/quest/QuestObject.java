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
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author andre
 */
public class QuestObject {
    public Quest quest = null;
    
    public void _setFieldFromSectionValue(String aName, Object aValue) {
        Class lClass = getClass();

        try {
            if (aValue instanceof String && ((String)aValue).startsWith("$")) {
                String lVarName = ((String)aValue).substring(1);
                QuestVariable lVar = quest.getVariable(lVarName);
                lVar.addBinding(this, aName);
                aValue = lVar.value;
            }
            String lMethodName = aName.substring(0, 1).toUpperCase() + aName.substring(1);
            lMethodName = "set" + lMethodName + "FromSectionValue";
            try {
                Method lMethod = lClass.getMethod(lMethodName, Object.class);
                try {
                    lMethod.invoke(this, aValue);
                    quest.log(getClass().getSimpleName() + ":" + aName + " is setted via separate method " + lMethodName + ".");
                } catch (Exception ex) {
                    Logger.getLogger(QuestObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException exm) {
                Field lField = lClass.getField(aName);
                try {
                    boolean lSet = false;
                    Object lValue = lField.get(this);
                    if (lValue instanceof BlockPosition) {
                        if (quest != null) {
                            BlockPosition lPos = quest.markers.get(aValue.toString());
                            if (lPos != null) {
                                ((BlockPosition)lValue).cloneFrom(lPos);
                            } else {
                                ((BlockPosition)lValue).fromCSV(aValue.toString(), ",");
                            }
                        } else {
                            ((BlockPosition)lValue).fromCSV(aValue.toString(), ",");
                        }
                        lSet = true;
                    } else if (lValue instanceof ArrayList) {
                        //Logger.getLogger("xxx").info(lField.getType().toString());
                    } else if (lValue instanceof HashMap) {
                        //Logger.getLogger("xxx").info(lField.getType().toString());
                    } else {
                        Class lDClass = lField.getType();
                        lValue = aValue;
                        if (lDClass.isAssignableFrom(lValue.getClass())) {
                            lField.set(this, lValue);
                            lSet = true;
                        } else {
                            if (lDClass.isAssignableFrom(String.class)) {
                                lField.set(this, lValue.toString());
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(int.class)) {
                                lField.setInt(this, Integer.parseInt(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(Integer.class)) {
                                lField.setInt(this, Integer.parseInt(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(short.class)) {
                                lField.setShort(this, Short.parseShort(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(Short.class)) {
                                lField.setShort(this, Short.parseShort(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(byte.class)) {
                                lField.setByte(this, Byte.parseByte(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(Short.class)) {
                                lField.setByte(this, Byte.parseByte(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(boolean.class)) {
                                lField.setBoolean(this, Boolean.parseBoolean(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(Boolean.class)) {
                                lField.setBoolean(this, Boolean.parseBoolean(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(double.class)) {
                                lField.setDouble(this, Double.parseDouble(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(Double.class)) {
                                lField.setDouble(this, Double.parseDouble(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(float.class)) {
                                lField.setFloat(this, Float.parseFloat(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(Float.class)) {
                                lField.setFloat(this, Float.parseFloat(lValue.toString()));
                                lSet = true;
                            } else if (lDClass.isAssignableFrom(Material.class)) {
                                Material lMat = Material.getMaterial(lValue.toString().toUpperCase());
                                if (lMat == null) {
                                    lMat = Material.getMaterial(Integer.parseInt(lValue.toString()));
                                }
                                lField.set(this, lMat);
                                lSet = true;
                            } else if (lDClass.isEnum()) {
                                Field lEnumField = lDClass.getField(lValue.toString());
                                if (lEnumField != null) {
                                    lField.set(this, lEnumField.get(this));
                                    lSet = true;
                                }
                            }
                        }
                    }
                    if (lSet) {
                        quest.log(getClass().getSimpleName() + ":" + aName + "=" + lValue);
                    } else {
                        quest.log(getClass().getSimpleName() + ":" + aName + "=" + lValue + " not assignable!!!");
                    }
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Quest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                }
            } catch (SecurityException ex) {
                Logger.getLogger(QuestObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchFieldException ex) {
            quest.log(lClass.getSimpleName() + ":Field " + aName + " not found!");
        } catch (SecurityException ex) {
        }
    }
    
    public void fromSectionValue(Object aObject) {
        //Class lClass = getClass();
        if (aObject instanceof ConfigurationSection) {
            fromSectionValue(((ConfigurationSection)aObject).getValues(false));
        } else if (aObject instanceof HashMap) {
            Map<String, Object> lValues = (HashMap)aObject;
            for(String lKey : lValues.keySet()) {
                _setFieldFromSectionValue(lKey, lValues.get(lKey));
            }
        }
    }
}
