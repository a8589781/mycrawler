package my.test.one;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class Test1 {

    public static void main(String[] args) {
        String url = "https://www.itjuzi.com/investevents/16391";
        int i = url.lastIndexOf("/");
        String substring = url.substring(i + 1);
    }

    @Test
    public void tst() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(format.parse("2015.1.5"));
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        Date date = calendar.getTime();
        System.out.println(date);
    }
}
