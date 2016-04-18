package web.module.report;

import java.text.ParseException;
import java.util.List;

import web.module.order.Order;
import web.module.order.OrderManager;
import web.module.utility.DateUtility;

public class Report801_802 implements ReportGeneratorInterface{
	
	private ReportOrderBoundaryInterface orderManager = new OrderManager();

	@Override
	public Report generateReport(Report report, String start_date, String end_date) throws ParseException {
		OrderDeliveryReport oderReport = (OrderDeliveryReport)report;
		List<Order> orderList = null;
		
		if(report.getId() == ReportList.RID_801) orderList = orderManager.getAllOrder(DateUtility.getFormattedDateToday());
		else orderList = orderManager.getAllOrder(DateUtility.getFormattedDateTomorrow());
		oderReport.setOrderList(orderList);
		oderReport.setStart_date("Not Applicable");
		oderReport.setEnd_date("Not Applicable");
		
		return report;
	}

}
