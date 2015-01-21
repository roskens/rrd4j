package org.rrd4j.graph;

import org.rrd4j.data.DataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CDef extends Source {
    private static final Logger LOG = LoggerFactory.getLogger(CDef.class);
    private final String rpnExpression;

    CDef(String name, String rpnExpression) {
        super(name);
        this.rpnExpression = rpnExpression;
    }

    void requestData(DataProcessor dproc) {
        dproc.addDatasource(name, rpnExpression);
    }
}
