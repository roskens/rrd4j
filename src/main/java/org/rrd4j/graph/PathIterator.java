package org.rrd4j.graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PathIterator {
    private static final Logger LOG = LoggerFactory.getLogger(PathIterator.class);
    private double[] y;
    private int pos = 0;

    PathIterator(double[] y) {
        this.y = y;
    }

    int[] getNextPath() {
        while (pos < y.length) {
            if (Double.isNaN(y[pos])) {
                pos++;
            }
            else {
                int endPos = pos + 1;
                while (endPos < y.length && !Double.isNaN(y[endPos])) {
                    endPos++;
                }
                int[] result = {pos, endPos};
                pos = endPos;
                if (result[1] - result[0] >= 2) {
                    return result;
                }
            }
        }
        return null;
    }
}
