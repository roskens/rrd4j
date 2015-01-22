package org.rrd4j.core;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract backend factory which is used to store RRD data to ordinary files on the disk.
 * <p>
 * Every backend factory storing RRD data as ordinary files should inherit from it, some check are done
 * in the code for instanceof.
 */
public abstract class RrdFileBackendFactory extends RrdBackendFactory {
    private static final Logger LOG = LoggerFactory.getLogger(RrdFileBackendFactory.class);
    
    /**
     * Method to determine if a file with the given path already exists.
     *
     * @param path File path
     * @return True, if such file exists, false otherwise.
     */
    @Override
    protected boolean exists(String path) {
        return Util.fileExists(path);
    }

    @Override
    protected boolean shouldValidateHeader(String path) throws IOException {
        return true;
    }
}
