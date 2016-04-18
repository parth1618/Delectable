package web.module.report;

import java.text.ParseException;
import java.util.List;

import web.module.order.Order;

public interface ReportOrderBoundaryInterface {

	public List<Order> getAllOrder();

	public List<Order> getAllOrder(String date) throws ParseException;

	public List<Order> getAllOrder(String startDate, String endDate) throws ParseException;

}
