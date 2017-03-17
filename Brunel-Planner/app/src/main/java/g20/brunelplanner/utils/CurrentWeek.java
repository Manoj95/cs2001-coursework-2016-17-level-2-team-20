package g20.brunelplanner.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrentWeek {

    public static int getCurrentWeek() {
        String format = "yyyyMMdd";
        String input = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());

        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            Date date = df.parse(input);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int week = cal.get(Calendar.WEEK_OF_YEAR);

            week = week - 38;
            if (week < 0) {
                return week + 52;
            } else {
                return week;
            }

        } catch (ParseException e) {
            return 0;

        }

    }

}
