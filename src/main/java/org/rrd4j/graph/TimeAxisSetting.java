package org.rrd4j.graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TimeAxisSetting {
    private static final Logger LOG = LoggerFactory.getLogger(TimeAxisSetting.class);
    final long secPerPix;
    final int minorUnit, minorUnitCount, majorUnit, majorUnitCount;
    final int labelUnit, labelUnitCount, labelSpan;
    final String format;

    TimeAxisSetting(long secPerPix, int minorUnit, int minorUnitCount, int majorUnit, int majorUnitCount,
                    int labelUnit, int labelUnitCount, int labelSpan, String format) {
        this.secPerPix = secPerPix;
        this.minorUnit = minorUnit;
        this.minorUnitCount = minorUnitCount;
        this.majorUnit = majorUnit;
        this.majorUnitCount = majorUnitCount;
        this.labelUnit = labelUnit;
        this.labelUnitCount = labelUnitCount;
        this.labelSpan = labelSpan;
        this.format = format;
    }

    TimeAxisSetting(TimeAxisSetting s) {
        this.secPerPix = s.secPerPix;
        this.minorUnit = s.minorUnit;
        this.minorUnitCount = s.minorUnitCount;
        this.majorUnit = s.majorUnit;
        this.majorUnitCount = s.majorUnitCount;
        this.labelUnit = s.labelUnit;
        this.labelUnitCount = s.labelUnitCount;
        this.labelSpan = s.labelSpan;
        this.format = s.format;
    }

    TimeAxisSetting(int minorUnit, int minorUnitCount, int majorUnit, int majorUnitCount,
                    int labelUnit, int labelUnitCount, int labelSpan, String format) {
        this(0, minorUnit, minorUnitCount, majorUnit, majorUnitCount,
                labelUnit, labelUnitCount, labelSpan, format);
    }

}
