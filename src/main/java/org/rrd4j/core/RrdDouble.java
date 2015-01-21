package org.rrd4j.core;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RrdDouble extends RrdPrimitive {
    private static final Logger LOG = LoggerFactory.getLogger(RrdDouble.class);
    
    private double cache;
    private boolean cached = false;

    RrdDouble(RrdUpdater updater, boolean isConstant) throws IOException {
        super(updater, RrdDouble.RRD_DOUBLE, isConstant);
    }

    RrdDouble(RrdUpdater updater) throws IOException {
        super(updater, RrdDouble.RRD_DOUBLE, false);
    }

    void set(double value) throws IOException {
        if (!isCachingAllowed()) {
            writeDouble(value);
        }
        // caching allowed
        else if (!cached || !Util.equal(cache, value)) {
            // update cache
            writeDouble(cache = value);
            cached = true;
        }
    }

    double get() throws IOException {
        if (!isCachingAllowed()) {
            return readDouble();
        }
        else {
            if (!cached) {
                cache = readDouble();
                cached = true;
            }
            return cache;
        }
    }
}
