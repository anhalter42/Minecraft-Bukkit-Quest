/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class QuestVariable extends QuestObject {

    public class Binding {
        public QuestObject object;
        public String field;
        
        public Binding(QuestObject aObject, String aField) {
            object = aObject;
            field = aField;
        }
    }
    
    public String name;
    public String value = "0";
    public ArrayList<Binding> bindings = new ArrayList<Binding>();
    
    public void setValue(String aValue) {
        value = aValue;
        executeBinding();
    }

    public void add(String aValue) {
        Double lValue = Double.parseDouble(value) + Double.parseDouble(aValue);
        value = lValue.toString();
        executeBinding();
    }

    public void sub(String aValue) {
        Double lValue = Double.parseDouble(value) - Double.parseDouble(aValue);
        value = lValue.toString();
        executeBinding();
    }

    public void mul(String aValue) {
        Double lValue = Double.parseDouble(value) * Double.parseDouble(aValue);
        value = lValue.toString();
        executeBinding();
    }

    public void div(String aValue) {
        Double lValue = Double.parseDouble(value) / Double.parseDouble(aValue);
        value = lValue.toString();
        executeBinding();
    }
    
    public void bitand(String aValue) {
        int lV1 = (int)Double.parseDouble(value);
        int lV2 = (int)Double.parseDouble(aValue);
        Integer lValue = lV1 & lV2;
        value = lValue.toString();
        executeBinding();
    }

    public void bitor(String aValue) {
        int lV1 = (int)Double.parseDouble(value);
        int lV2 = (int)Double.parseDouble(aValue);
        Integer lValue = lV1 | lV2;
        value = lValue.toString();
        executeBinding();
    }

    public void bitxor(String aValue) {
        int lV1 = (int)Double.parseDouble(value);
        int lV2 = (int)Double.parseDouble(aValue);
        Integer lValue = lV1 ^ lV2;
        value = lValue.toString();
        executeBinding();
    }

    public void random(String aValue) {
        if (aValue != null) {
            value = aValue;
        }
        Integer lValue = (int)Double.parseDouble(value);
        lValue = quest.random.nextInt(lValue);
        value = lValue.toString();
        executeBinding();
    }
    
    public int compare(String aValue) {
        int lResult = 0;
        Double lValue = Double.parseDouble(aValue);
        Double lThis = Double.parseDouble(value);
        lResult = lThis.compareTo(lValue);
        //quest.log("this = " + lThis + " compare to " + lValue + " = " + lResult);
        return lResult;
    }
    
    public void addBinding(QuestObject aObject, String aField) {
        bindings.add(new Binding(aObject, aField));
    }
    
    public void executeBinding() {
        for(Binding lBinding : bindings) {
            lBinding.object._setFieldFromSectionValue(lBinding.field, value);
        }
    }
}
