package web.module.report;

import java.text.ParseException;

import web.module.order.OrderManager;

public class Report803 implements ReportGeneratorInterface {

	private ReportRevenueBoundaryInterface orderManager = new OrderManager();

	@Override
	public Report generateReport(Report report, String start_date, String end_date) throws ParseException {

		RevenueReport revenueReport = (RevenueReport) report;
		Revenue revenue = null;

		if (start_date != null && end_date != null) {
			revenue = orderManager.calculateRevenue(start_date, end_date);
		} else if (start_date != null) {
			revenue = orderManager.calculateRevenue(start_date);
		} else {
			throw new InvalidDateParametersForReport803Exception();
		}

		revenueReport.setRevenue(revenue);
		revenueReport.setStart_date(start_date);
		revenueReport.setEnd_date(end_date);
		return revenueReport;
	}

	public class InvalidDateParametersForReport803Exception extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

}
