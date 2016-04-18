package web.module.report;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import web.module.order.Order;
import web.module.order.OrderManager;
import web.module.utility.DateUtility;

public class Report804 implements ReportGeneratorInterface{
	
	private ReportOrderBoundaryInterface orderManager = new OrderManager();

	@Override
	public Report generateReport(Report report, String start_date, String end_date) throws ParseException {
		OrderDeliveryReport orderReport = (OrderDeliveryReport)report;
		List<Order> orderList = null;
		
		if(start_date != null || end_date != null) {
			orderList = orderManager.getAllOrder(start_date, end_date);
			
			if(start_date == null)start_date = "Not Applicable";
			if(end_date == null)end_date = "Not Applicable";
			
			report.setStart_date(start_date);
			report.setEnd_date(end_date);
		}
		else{
			orderList = orderManager.getAllOrder();
			orderReport.setStart_date("Not Applicable");
			orderReport.setEnd_date("Not Applicable");
		}
		
		Collections.sort(orderList, new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				try {
					return (DateUtility.parseStringToDate(o1.getDelivery_date()).compareTo(DateUtility.parseStringToDate(o2.getDelivery_date())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 0;
				
			}
		});
		
		orderReport.setOrderList(orderList);
		return orderReport;
	}
	

}
