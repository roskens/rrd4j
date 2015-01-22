package org.rrd4j.core;

import org.rrd4j.core.jrrd.RRDatabase;
import org.rrd4j.ConsolFun;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RrdToolReader extends DataImporter {
    private static final Logger LOG = LoggerFactory.getLogger(RrdToolReader.class);
    private RRDatabase rrd;

    RrdToolReader(String rrdPath) throws IOException {
        rrd = new RRDatabase(rrdPath);
    }

    @Override
    String getVersion() {
        return rrd.getHeader().getVersion();
    }

    @Override
    long getLastUpdateTime() {
        return Util.getTimestamp(rrd.getLastUpdate());
    }

    @Override
    long getStep() {
        return rrd.getHeader().getPDPStep();
    }

    @Override
    int getDsCount() {
        return rrd.getHeader().getDSCount();
    }

    @Override
    int getArcCount() throws IOException {
        return rrd.getNumArchives();
    }

    @Override
    String getDsName(int dsIndex) {
        return rrd.getDataSource(dsIndex).getName();
    }

    @Override
    String getDsType(int dsIndex) {
        return rrd.getDataSource(dsIndex).getType().toString();
    }

    @Override
    long getHeartbeat(int dsIndex) {
        return rrd.getDataSource(dsIndex).getMinimumHeartbeat();
    }

    @Override
    double getMinValue(int dsIndex) {
        return rrd.getDataSource(dsIndex).getMinimum();
    }

    @Override
    double getMaxValue(int dsIndex) {
        return rrd.getDataSource(dsIndex).getMaximum();
    }

    @Override
    double getLastValue(int dsIndex) {
        String valueStr = rrd.getDataSource(dsIndex).getPDPStatusBlock().getLastReading();
        return Util.parseDouble(valueStr);
    }

    @Override
    double getAccumValue(int dsIndex) {
        return rrd.getDataSource(dsIndex).getPDPStatusBlock().getValue();
    }

    @Override
    long getNanSeconds(int dsIndex) {
        return rrd.getDataSource(dsIndex).getPDPStatusBlock().getUnknownSeconds();
    }

    @Override
    ConsolFun getConsolFun(int arcIndex) {
        return ConsolFun.valueOf(rrd.getArchive(arcIndex).getType().toString());
    }

    @Override
    double getXff(int arcIndex) {
        return rrd.getArchive(arcIndex).getXff();
    }

    @Override
    int getSteps(int arcIndex) {
        return rrd.getArchive(arcIndex).getPdpCount();
    }

    @Override
    int getRows(int arcIndex) throws IOException {
        return rrd.getArchive(arcIndex).getRowCount();
    }

    @Override
    double getStateAccumValue(int arcIndex, int dsIndex) throws IOException {
        return rrd.getArchive(arcIndex).getCDPStatusBlock(dsIndex).getValue();
    }

    @Override
    int getStateNanSteps(int arcIndex, int dsIndex) throws IOException {
        return rrd.getArchive(arcIndex).getCDPStatusBlock(dsIndex).getUnknownDatapoints();
    }

    @Override
    double[] getValues(int arcIndex, int dsIndex) throws IOException {
        return rrd.getArchive(arcIndex).getValues()[dsIndex];
    }

    @Override
    void release() throws IOException {
        if (rrd != null) {
            rrd.close();
            rrd = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        release();
    }
}
