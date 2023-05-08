package application;

import java.util.Calendar;
import java.util.GregorianCalendar;

// Fischer - s214411
public class DateServer {
    private Calendar calendar;
    public DateServer() {
        calendar = new GregorianCalendar();
    }

    public Calendar getDate() {
        Calendar c = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        return c;
    }
}