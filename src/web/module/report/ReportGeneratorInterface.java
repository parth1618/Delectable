package web.module.report;

import java.text.ParseException;

public interface ReportGeneratorInterface {
	
	public Report generateReport(Report report, String start_date, String end_date) throws ParseException;

}
