package org.rrd4j.graph;

import org.rrd4j.core.FetchData;
import org.rrd4j.data.DataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Mathias Bogaert */
class TDef extends Source {
    private static final Logger LOG = LoggerFactory.getLogger(TDef.class);
    private final FetchData fetchData;

    TDef(String name, FetchData fetchData) {
        super(name);
        this.fetchData = fetchData;
    }

    @Override
    void requestData(DataProcessor dproc) {
        dproc.addDatasource(name, fetchData);
    }
}
