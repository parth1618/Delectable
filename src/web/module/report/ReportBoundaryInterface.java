package web.module.report;

import java.text.ParseException;
import java.util.List;

public interface ReportBoundaryInterface {
	
	public List<Report> getReportList();
	public Report getReport(int rid);
	public Report generateReport(int rid, String startDate, String endDate) throws ParseException;

}
