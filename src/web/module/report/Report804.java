package web.module.report;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import web.module.order.Order;
import web.module.order.OrderManager;
import web.module.utility.DateUtility;

public class Report804 implements ReportGeneratorInterface {

	private ReportOrderBoundaryInterface orderManager = new OrderManager();

	@Override
	public Report generateReport(Report report, String start_date, String end_date) throws ParseException {
		OrderDeliveryReport orderReport = (OrderDeliveryReport) report;
		List<Order> orderList = null;

		if (start_date != null && end_date != null) {
			orderList = orderManager.getAllOrder(start_date, end_date);
		} else if (start_date != null) {
			orderList = orderManager.getAllOrder(start_date);
		} else {
			throw new InvalidDateParametersForReport804Exception();
		}

		Collections.sort(orderList, new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				try {
					return (DateUtility.parseStringToDate(o1.getDelivery_date())
							.compareTo(DateUtility.parseStringToDate(o2.getDelivery_date())));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return 0;

			}
		});

		orderReport.setOrderList(orderList);
		report.setStart_date(start_date);
		report.setEnd_date(end_date);
		return orderReport;
	}

	public class InvalidDateParametersForReport804Exception extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

}
