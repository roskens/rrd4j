package org.rrd4j.core;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RrdInt extends RrdPrimitive {
    private static final Logger LOG = LoggerFactory.getLogger(RrdInt.class);
    private int cache;
    private boolean cached = false;

    RrdInt(RrdUpdater updater, boolean isConstant) throws IOException {
        super(updater, RrdPrimitive.RRD_INT, isConstant);
    }

    RrdInt(RrdUpdater updater) throws IOException {
        this(updater, false);
    }

    void set(int value) throws IOException {
        if (!isCachingAllowed()) {
            writeInt(value);
        }
        // caching allowed
        else if (!cached || cache != value) {
            // update cache
            writeInt(cache = value);
            cached = true;
        }
    }

    int get() throws IOException {
        if (!isCachingAllowed()) {
            return readInt();
        }
        else {
            if (!cached) {
                cache = readInt();
                cached = true;
            }
            return cache;
        }
    }
}
