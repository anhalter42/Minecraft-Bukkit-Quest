/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahn42.anhalter42.quest;

import com.mahn42.framework.Building;
import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class QuestBuilding extends Building {
    public enum Kind {
        building,
        trap,
        adventure
    }
    
    public Kind kind = Kind.building;
    
    @Override
    protected void toCSVInternal(ArrayList aCols) {
        super.toCSVInternal(aCols);
        aCols.add(kind);
    }

    @Override
    protected void fromCSVInternal(DBRecordCSVArray aCols) {
        super.fromCSVInternal(aCols);
        kind = Kind.valueOf(aCols.pop());
    }
}
