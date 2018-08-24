package planner.view.android;

import java.io.Serializable;
import java.util.Calendar;

import planner.model.core.C;

/**
 * Created by Griffin on 3/5/2018.
 */

public enum Interval implements Serializable {

    day("Day", C.DAY_FORMAT, C.TIME_FORMAT, Calendar.DAY_OF_YEAR),
    week("Week", C.DAY_FORMAT, C.DATE_TIME_FORMAT, Calendar.WEEK_OF_YEAR),
    month("Month", C.MONTH_FORMAT, C.DATE_TIME_FORMAT, Calendar.MONTH),
    year("Year", C.YEAR_FORMAT, C.DATE_TIME_FORMAT, Calendar.YEAR);

    public int tPosition; // Tab position;
    public String fTitle; // Fragment title
    public String iFormat; // Interval display format
    public String lFormat; // Listed note date/time format
    public int cConst;

    Interval(String fTitle, String iFormat, String lFormat, int cConst) {
        this.fTitle = fTitle;
        this.iFormat = iFormat;
        this.lFormat = lFormat;
        this.cConst = cConst;
    }
}
