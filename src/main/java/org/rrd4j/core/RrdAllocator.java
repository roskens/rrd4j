package org.rrd4j.core;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RrdAllocator {
    private static final Logger LOG = LoggerFactory.getLogger(RrdAllocator.class);
    private long allocationPointer = 0L;

    long allocate(long byteCount) throws IOException {
        long pointer = allocationPointer;
        allocationPointer += byteCount;
        return pointer;
    }
}