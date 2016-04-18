package web.module.report;

import java.text.ParseException;

public interface ReportRevenueBoundaryInterface {
	
	public Revenue calculateRevenue();
	public Revenue calculateRevenue(String startDate, String endDate) throws ParseException;

}
