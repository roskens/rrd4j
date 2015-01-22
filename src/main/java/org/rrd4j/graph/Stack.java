package org.rrd4j.graph;

import org.rrd4j.data.DataProcessor;

import java.awt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Stack extends SourcedPlotElement {
    private static final Logger LOG = LoggerFactory.getLogger(Stack.class);
    private final SourcedPlotElement parent;

    Stack(SourcedPlotElement parent, String srcName, Paint color) {
        super(srcName, color);
        this.parent = parent;
    }

    @Override
    void assignValues(DataProcessor dproc) {
        double[] parentValues = parent.getValues();
        double[] procValues = dproc.getValues(srcName);
        values = new double[procValues.length];
        for (int i = 0; i < values.length; i++) {
            if (Double.isNaN(parentValues[i])) {
                values[i] = procValues[i];
            }
            else if (Double.isNaN(procValues[i])){
                values[i] = parentValues[i];
            }
            else {
                values[i] = parentValues[i] + procValues[i];
                
            }
        }
    }

    float getParentLineWidth() {
        if (parent instanceof Line) {
            return ((Line) parent).width;
        }
        else if (parent instanceof Area) {
            return -1F;
        }
        else /* if(parent instanceof Stack) */ {
            return ((Stack) parent).getParentLineWidth();
        }
    }

    Paint getParentColor() {
        return parent.color;
    }
}
