package org.rrd4j.graph;

import org.rrd4j.data.DataProcessor;
import org.rrd4j.data.Plottable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PDef extends Source {
    private static final Logger LOG = LoggerFactory.getLogger(PDef.class);
    private Plottable plottable;

    PDef(String name, Plottable plottable) {
        super(name);
        this.plottable = plottable;
    }

    void requestData(DataProcessor dproc) {
        dproc.addDatasource(name, plottable);
    }
}
