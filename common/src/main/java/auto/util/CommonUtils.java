package auto.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.lang.time.DateUtils;

import auto.datamodel.service.TimePeriod;

@CommonsLog
public class CommonUtils {
    
    public static final long DAY_IN_MILLS = 1000L * 60 * 60 * 24;
    
    public static final SimpleDateFormat SECOND_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    
    public static final SimpleDateFormat SECOND_SLASH_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyyMMdd");
    
    public static final SimpleDateFormat DAY_SLASH_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    
    public static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("yyyyMM");
    
    public static final SimpleDateFormat MILLISECOND_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    
    public static final SimpleDateFormat DAY_CHINESE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");
    
    public static final SimpleDateFormat SHORT_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    public static boolean equals(Object o1, Object o2) {
        if (o1 == null && o2 == null) return true;
        if (o1 == null || o2 == null) return false;
        return o1.equals(o2);
    }
    
    public static boolean isSameDay(Date d1, Date d2) {
        if (d1 == null && d2 == null) return true;
        if (d1 == null || d2 == null) return false;
        return org.apache.commons.lang.time.DateUtils.isSameDay(d1, d2);
    }
    
    public static String getMonthFormat(Date date) {
        if (date == null) return null;
        synchronized(MONTH_FORMAT) {
            return MONTH_FORMAT.format(date);
        }
    }
    
    public static String getDayFormat(Date date) {
        if (date == null) return null;
        synchronized(DAY_FORMAT) {
            return DAY_FORMAT.format(date);
        }
    }
    
    public static String getSecondFormat(Date date) {
        if (date == null) return null;
        synchronized(SECOND_FORMAT) {
            return SECOND_FORMAT.format(date);
        }
    }
    
    public static String getMillisecondFormat(Date date) {
        if (date == null) return null;
        synchronized(MILLISECOND_FORMAT) {
            return MILLISECOND_FORMAT.format(date);
        }
    }
    
    public static String getSlashDayFormat(Date date) {
        if (date == null) return null;
        synchronized(DAY_SLASH_FORMAT) {
            return DAY_SLASH_FORMAT.format(date);
        }
    }
    
    public static String getChineseDayFormat(Date date) {
        if (date == null) return null;
        synchronized(DAY_CHINESE_FORMAT) {
            return DAY_CHINESE_FORMAT.format(date);
        }
    }
    
    public static String getSlashSecondFormat(Date date) {
        if (date == null) return null;
        synchronized(SECOND_SLASH_FORMAT) {
            return SECOND_SLASH_FORMAT.format(date);
        }
    }
    
    public static String getShortTimeFormat(Date date) {
        if (date == null) return null;
        synchronized(SHORT_TIME_FORMAT) {
            return SHORT_TIME_FORMAT.format(date);
        }
    }
    
    public static Date getDateFromDay(String day) throws ParseException {
        if (day == null) return null;
        synchronized(DAY_FORMAT) {
            return DAY_FORMAT.parse(day);
        }
    }
    
    public static Date getDateFromSecond(String second) throws ParseException {
        if (second == null) return null;
        synchronized(SECOND_FORMAT) {
            return SECOND_FORMAT.parse(second);
        }
    }
    
    public static Date getDateFromMillisecond(String millisecond) throws ParseException {
        if (millisecond == null) return null;
        synchronized(MILLISECOND_FORMAT) {
            return MILLISECOND_FORMAT.parse(millisecond);
        }
    }
    
    public static void main(String[] args) throws Exception {
        //System.out.println(getDateFromSecond("20160226212820"));
        System.out.println(getMillisecondFormat(new Date()));
        System.out.println(getShortTimeFormat(new Date()));
    }
    
    public static int getDaysBetween(Date d1, Date d2) {
        long t1 = d1.getTime();
        long t2 = d2.getTime();
        return Math.max(0, (int)((t2 - t1) / DAY_IN_MILLS));
    }
    
    public static TimePeriod getPeriodBetween(String s1, String s2) throws ParseException {
        Date d1 = getDateFromDay(s1);
        Date d2 = getDateFromDay(s2);
        return getPeriodBetween(d1, d2);
    }
    
    public static TimePeriod getPeriodBetween(Date d1, Date d2) {
        TimePeriod period = new TimePeriod();
        period.year = 0;
        while (!DateUtils.addYears(d1, period.year + 1).after(d2)) {
            period.year++;
        }
        Date date = DateUtils.addYears(d1, period.year);
        period.month = 0;
        while (!DateUtils.addMonths(date, period.month + 1).after(d2)) {
            period.month++;
        }
        date = DateUtils.addMonths(date, period.month);
        period.day = CommonUtils.getDaysBetween(date, d2);
        
        if (period.day > 15 || period.year + period.month == 0) {
            period.recommendFinishDate = CommonUtils.getDayFormat(DateUtils.addMonths(date, 1));
        } else if (period.day > 0) {
            period.recommendFinishDate = CommonUtils.getDayFormat(date);
        } else {
            period.recommendFinishDate = null;
        }
        return period;
    }
    
    public static Date getDay(Date date) {
        return org.apache.commons.lang.time.DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
    }
    
    public static boolean isValidLocation(Double x, Double y) {
        if (x == null || y == null) return false;
        //in beijing
        if (x < 115.655154 || x > 117.271816 || y < 39.519989 || y > 40.555826) {
            return false;
        }
        return true;
    }
    
    public static void fatal(String msg, Throwable t) {
        fatal(msg, msg, t);
    }
    
    public static void fatal(String msg, String content, Throwable t) {
        log.fatal(msg + "\n" + content, t);
//        EmailUtils.sendSystemMail("[Fatal]" + msg, content);
    }
    
    public static void fatal(String msg, String content) {
        fatal(msg, content, null);
    }
    
    public static Integer convertToAccountUnit(Integer amount) {
        return amount * 100;
    }
    
    public static Integer convertToContractUnit(Integer amount) {
        return amount / 100;
    }
    
    public static Float convertToContractUnitInFloat(Integer amount) {
        return amount / 100f;
    }
    
    public static boolean or(Collection<Boolean> conditions) {
        boolean flag = false;
        if (conditions != null) {
            for (Boolean condition : conditions) {
                flag |= condition;
            }
        }
        return flag;
    }
    
    public static void updateInfo(Object obj, Map<String, Object> info) {
        if (obj == null || info == null) return ;
        for (String key : info.keySet()) {
            try {
                Field field = obj.getClass().getDeclaredField(key);
                if (field != null) {
                    field.setAccessible(true);
                    field.set(obj, info.get(key));
                }
            } catch(Exception e) {
                log.error(e, e);
            }
        }
    }
    
    public static void filterFields(Map<String, Object> info, String... fields) {
        Set<String> set = new HashSet<String>();
        for (String field : fields) {
            set.add(field);
        }
        Iterator<Entry<String, Object>> it = info.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            if (!set.contains(entry.getKey())) {
                it.remove();
            }
        }
    }
    
}
