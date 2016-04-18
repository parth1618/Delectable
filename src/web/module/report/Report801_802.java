package web.module.report;

import java.text.ParseException;
import java.util.List;

import web.module.order.Order;
import web.module.order.OrderManager;
import web.module.utility.DateUtility;

public class Report801_802 implements ReportGeneratorInterface {

	private ReportOrderBoundaryInterface orderManager = new OrderManager();

	@Override
	public Report generateReport(Report report, String start_date, String end_date) throws ParseException {
		OrderDeliveryReport oderReport = (OrderDeliveryReport) report;
		List<Order> orderList = null;

		if (start_date != null || end_date != null) {
			throw new InvalidDateParametersForReport801_802Exception();
		} else {
			if (report.getId() == ReportList.RID_801)
				orderList = orderManager.getAllOrder(DateUtility.getFormattedDateToday());
			else
				orderList = orderManager.getAllOrder(DateUtility.getFormattedDateTomorrow());
		}
		oderReport.setOrderList(orderList);
		return report;
	}

	public class InvalidDateParametersForReport801_802Exception extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

}
