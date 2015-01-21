package org.rrd4j.graph;

import org.rrd4j.data.DataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PercentileDef extends Source {
    private static final Logger LOG = LoggerFactory.getLogger(PercentileDef.class);
    private final String defName;
    private final double percent;

    PercentileDef(String name, String defName, double percent) {
        super(name);
        this.defName = defName;
        this.percent = percent;
    }

    @Override
    void requestData(DataProcessor dproc) {
        dproc.addDatasource(name, defName, percent);
    }
}
