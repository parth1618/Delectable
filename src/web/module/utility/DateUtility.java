package web.module.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public static Date getTodayDate() {
		return Calendar.getInstance().getTime();
	}

	public static String getFormattedDate(Date date) {
		return sdf.format(date);
	}

	public static String getFormattedDateToday() {
		return getFormattedDate(getTodayDate());
	}

	public static String getFormattedDateTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();

		return getFormattedDate(tomorrow);
	}

	public static Date parseStringToDate(String date) throws ParseException {
		return sdf.parse(date);
	}

	public static boolean isDateWeekEnd(String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseStringToDate(date));

		return (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
	}

	public static boolean isDatesMatches(String delivery_date, String date) throws ParseException {
		Date dd = parseStringToDate(delivery_date);
		Date dt = parseStringToDate(date);

		return (dd.equals(dt));
	}

	public static boolean isDateInRange(String delivery_date, String start_date, String end_date)
			throws ParseException {
		Date startDate = null, endDate = null;

		startDate = parseStringToDate(start_date);

		endDate = parseStringToDate(end_date);

		Date deliveryDate = parseStringToDate(delivery_date);

		return (deliveryDate.compareTo(startDate) >= 0 && deliveryDate.compareTo(endDate) <= 0);
	}
}
