package planner.external.android;

import java.io.Serializable;

import planner.internal.core.C;

/**
 * Created by Griffin on 3/5/2018.
 */

public enum Interval implements Serializable {

    day("Day", C.DAY_FORMAT, C.TIME_FORMAT),
    week("Week", C.DAY_FORMAT, C.DATE_TIME_FORMAT),
    month("Month", C.MONTH_FORMAT, C.DATE_TIME_FORMAT),
    year("Year", C.YEAR_FORMAT, C.DATE_TIME_FORMAT);

    int tPosition; // Tab position;
    String fTitle; // Fragment title
    String iFormat; // Interval display format
    String lFormat; // Listed note date/time format

    Interval(String fTitle, String iFormat, String lFormat) {
        this.fTitle = fTitle;
        this.iFormat = iFormat;
        this.lFormat = lFormat;
    }
}
