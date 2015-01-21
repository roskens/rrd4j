package org.rrd4j.graph;

import java.awt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HRule extends Rule {
    private static final Logger LOG = LoggerFactory.getLogger(HRule.class);
    final double value;

    HRule(double value, Paint color, LegendText legend, float width) {
        super(color, legend, width);
        this.value = value;
    }

    void setLegendVisibility(double minval, double maxval, boolean forceLegend) {
        legend.enabled &= (forceLegend || (value >= minval && value <= maxval));
    }
}