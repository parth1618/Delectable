import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import web.module.admin.AdminBoundaryInterface;
import web.module.admin.AdminManager;
import web.module.customer.CustomerBoundaryInterface;
import web.module.customer.CustomerManager;
import web.module.customer.PersonalInfo;
import web.module.menu.Category;
import web.module.menu.MenuBoundaryInterface;
import web.module.menu.MenuItem;
import web.module.menu.MenuManager;
import web.module.order.Order;
import web.module.order.OrderBoundaryInterface;
import web.module.order.OrderDetail;
import web.module.order.OrderManager;
import web.module.order.OrderStatus;
import web.module.report.OrderDeliveryReport;
import web.module.report.Report;
import web.module.report.ReportBoundaryInterface;
import web.module.report.ReportList;
import web.module.report.ReportManager;
import web.module.report.Revenue;
import web.module.report.RevenueReport;
import web.module.utility.DateUtility;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDelectable {

	private MenuBoundaryInterface menuManager = new MenuManager();
	private AdminBoundaryInterface adminManager = new AdminManager();
	private OrderBoundaryInterface orderManager = new OrderManager();
	private CustomerBoundaryInterface customerManager = new CustomerManager();
	private ReportBoundaryInterface reportManager = new ReportManager();

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void test10_test_is_surcharge_set_should_be_false() {
		assertFalse(adminManager.isSurchargeSet());
	}

	@Test
	public void test11_test_menu_with_no_pre_value() {
		assertEquals(0, menuManager.getAllMenuItems().size());
	}

	@Test
	public void test12_test_menu_with_three_added_item() {
		createDummyMenuWithThreeItem();

		assertEquals(3, menuManager.getAllMenuItems().size());
	}

	@Test
	public void test13_test_menu_get_item_by_id_isNil() {
		int id1 = 101, id2 = 102, id3 = 103, invalid_id = 104;

		assertFalse(adminManager.getItem(id1).isNil());
		assertFalse(adminManager.getItem(id2).isNil());
		assertFalse(adminManager.getItem(id3).isNil());

		assertTrue(adminManager.getItem(invalid_id).isNil());
	}

	// @Test(expected = MenuManager.InvalidItemDetailException.class)
	// public void test14_test_add_menu_item_with_invalid_price(){
	//
	// MenuItem invalid_item = new MenuItem("Burger", 0, 1, catList_2);
	// adminManager.addItem(invalid_item);
	// }

	@Test
	public void test14_test_add_menu_item_with_invalid_price() {

		MenuItem invalid_item = new MenuItem("Burger", 0, 1, catList_2);

		exception.expect(MenuManager.InvalidItemDetailException.class);
		adminManager.addItem(invalid_item);
	}

	@Test
	public void test15_test_add_menu_item_with_invalid_minimum_count() {

		MenuItem invalid_item = new MenuItem("Bread Sticks", 0.99, 0, catList_3);

		exception.expect(MenuManager.InvalidItemDetailException.class);
		adminManager.addItem(invalid_item);
	}

	@Test
	public void test16_test_add_menu_item_with_invalid_price_and_minimum_person() {

		MenuItem invalid_item = new MenuItem("BBQ", 0, 0, catList_1);

		exception.expect(MenuManager.InvalidItemDetailException.class);
		adminManager.addItem(invalid_item);
	}

	@Test
	public void test17_update_menu_item_with_price() {
		int id = 101;
		MenuItem item = new MenuItem(null, 2.99, 0, null);

		assertEquals(2.49, menuManager.getItem(id).getPrice_per_person(), 0.01);
		adminManager.updateItem(id, item);
		assertEquals(2.99, menuManager.getItem(id).getPrice_per_person(), 0.01);
	}

	@Test
	public void test18_update_menu_item_with_invalid_price() {
		int id = 101;
		MenuItem item = new MenuItem(null, 0, 0, null);

		exception.expect(MenuManager.InvalidItemPriceForUpdateException.class);
		adminManager.updateItem(id, item);
	}

	@Test
	public void test19_test_surcharge_before_set() {
		assertEquals(0, adminManager.getSurcharge(), 0.01);
	}

	@Test
	public void test20_test_add_surcharge() {
		double surcharge = 5;
		adminManager.addSurcharge(surcharge);
		assertEquals(5, adminManager.getSurcharge(), 0.01);
	}

	@Test
	public void test21_test_order_with_no_pre_value() {
		assertEquals(0, orderManager.getAllOrder().size());
	}

	@Test
	public void test22_test_customer_with_no_pre_value() {
		assertEquals(0, customerManager.getAllCustomer().size());
	}

	@Test
	public void test23_test_order_with_six_added_order() throws ParseException {
		createSixDummyOrder();
		assertEquals(6, orderManager.getAllOrder().size());
	}

	@Test
	public void test24_test_order_get_order_by_id_isNil() {
		int id1 = 101, id2 = 102, id3 = 103, id4 = 104, id5 = 105, id6 = 106, invalid_id = 107;

		assertFalse(orderManager.getOrder(id1).isNil());
		assertFalse(orderManager.getOrder(id2).isNil());
		assertFalse(orderManager.getOrder(id3).isNil());
		assertFalse(orderManager.getOrder(id4).isNil());
		assertFalse(orderManager.getOrder(id5).isNil());
		assertFalse(orderManager.getOrder(id6).isNil());

		assertTrue(orderManager.getOrder(invalid_id).isNil());
	}

	@Test
	public void test25_test_order_with_invalid_order_detail_invalid_item_id() throws ParseException {
		OrderDetail invalid_od = new OrderDetail(1, 6);
		List<OrderDetail> od_list = new ArrayList<>();
		od_list.add(invalid_od);

		Order o = new Order(DateUtility.getFormattedDateToday(), "10 West 31st ST, Chicago IL 60616", p1, "Room SB-214",
				od_list);

		exception.expect(OrderManager.InvalidOrderDetailException.class);
		orderManager.addOrder(o);
	}

	@Test
	public void test26_test_order_with_invalid_order_detail_invalid_item_count() throws ParseException {

		OrderDetail invalid_od = new OrderDetail(101, 4);
		List<OrderDetail> od_list = new ArrayList<>();
		od_list.add(invalid_od);

		Order o = new Order(DateUtility.getFormattedDateToday(), "10 West 31st ST, Chicago IL 60616", p1, "Room SB-214",
				od_list);

		exception.expect(OrderManager.InvalidOrderDetailException.class);
		orderManager.addOrder(o);
	}

	@Test
	public void test27_test_order_with_invalid_order_detail_invalid_item_id_and_count() throws ParseException {

		OrderDetail invalid_od = new OrderDetail(1, 4);
		List<OrderDetail> od_list = new ArrayList<>();
		od_list.add(invalid_od);

		Order o = new Order(DateUtility.getFormattedDateToday(), "10 West 31st ST, Chicago IL 60616", p1, "Room SB-214",
				od_list);

		exception.expect(OrderManager.InvalidOrderDetailException.class);
		orderManager.addOrder(o);
	}

	@Test
	public void test28_test_order_with_invalid_order_date() throws ParseException {
		OrderDetail invalid_od = new OrderDetail(101, 8);
		List<OrderDetail> od_list = new ArrayList<>();
		od_list.add(invalid_od);

		Order o = new Order("20160417", "10 West 31st ST, Chicago IL 60616", p1, "Room SB-214", od_list);

		exception.expect(OrderManager.OrderDateCanNotBeInPastException.class);
		orderManager.addOrder(o);
	}

	@Test
	public void test29_test_customer_with_added_customer() {
		assertEquals(3, customerManager.getAllCustomer().size());
	}

	@Test
	public void test30_test_customer_get_customer_by_id_isNil() {
		int id1 = 101, id2 = 102, id3 = 103, invalid_id = 107;

		assertFalse(customerManager.getCustomer(id1).isNil());
		assertFalse(customerManager.getCustomer(id2).isNil());
		assertFalse(customerManager.getCustomer(id3).isNil());

		assertTrue(customerManager.getCustomer(invalid_id).isNil());
	}

	@Test
	public void test31_test_customer_known_alias() {
		int id1 = 101, id2 = 102, id3 = 103;

		assertEquals(1, customerManager.getCustomer(id1).getKnown_alias().size());
		assertEquals(0, customerManager.getCustomer(id2).getKnown_alias().size());
		assertEquals(0, customerManager.getCustomer(id3).getKnown_alias().size());
	}

	@Test
	public void test32_test_customer_get_all_customer_with_query_string() {

		assertEquals(1, customerManager.getAllCustomer("amp").size());
		assertEquals(3, customerManager.getAllCustomer("312").size());
		assertEquals(0, customerManager.getAllCustomer("abc").size());
	}

	@Test
	public void test33_order_cancel_order() throws ParseException {
		int id = 104;
		assertEquals(OrderStatus.OPEN, orderManager.getOrder(id).getStatus());
		orderManager.cancelOrder(id);
		assertEquals(OrderStatus.CANCELLED, orderManager.getOrder(id).getStatus());
	}

	@Test
	public void test34_order_cancel_order_for_today_invalid() throws ParseException {
		int id = 101;
		assertEquals(OrderStatus.OPEN, orderManager.getOrder(id).getStatus());

		exception.expect(OrderManager.OrderCanNotBeCancelledException.class);
		orderManager.cancelOrder(id);
	}

	@Test
	public void test35_admin_change_order_delivery_status() throws ParseException {
		int id = 102;
		assertEquals(OrderStatus.OPEN, orderManager.getOrder(id).getStatus());
		adminManager.updateDeliveryStatus(id);
		assertEquals(OrderStatus.DELIVERED, orderManager.getOrder(id).getStatus());
	}

	@Test
	public void test36_admin_change_order_delivery_status() throws ParseException {
		int id = 106;
		assertEquals(OrderStatus.OPEN, orderManager.getOrder(id).getStatus());

		exception.expect(OrderManager.DeliveryDateIsInFutureException.class);
		adminManager.updateDeliveryStatus(id);
	}

	@Test
	public void test37_customer_get_customer_order_history() {
		int id1 = 101, id2 = 102, id3 = 103;

		assertEquals(3, customerManager.getCustomer(id1).getOrder_history().size());
		assertEquals(2, customerManager.getCustomer(id2).getOrder_history().size());
		assertEquals(1, customerManager.getCustomer(id3).getOrder_history().size());
	}

	@Test
	public void test38_admin_check_order_isNil() {
		int id1 = 101, id2 = 102, id3 = 103, id4 = 104, id5 = 105, id6 = 106, invalid_id = 107;

		assertFalse(adminManager.getOrder(id1).isNil());
		assertFalse(adminManager.getOrder(id2).isNil());
		assertFalse(adminManager.getOrder(id3).isNil());
		assertFalse(adminManager.getOrder(id4).isNil());
		assertFalse(adminManager.getOrder(id5).isNil());
		assertFalse(adminManager.getOrder(id6).isNil());

		assertTrue(adminManager.getOrder(invalid_id).isNil());

	}

	@Test
	public void test39_get_all_order_by_date_query_string() throws ParseException {
		String date1 = DateUtility.getFormattedDateToday(), date2 = "20160521", invalid_date = "20160601";

		assertEquals(1, orderManager.getAllOrder(date1).size());
		assertEquals(1, orderManager.getAllOrder(date2).size());
		assertEquals(0, orderManager.getAllOrder(invalid_date).size());

		assertEquals(0, orderManager.getAllOrder(invalid_date).size());

	}

	@Test
	public void test40_report_get_four_report_list() {
		assertEquals(4, reportManager.getReportList().size());
	}

	@Test
	public void test41_report_check_for_null_report() {
		assertFalse(reportManager.getReport(ReportList.RID_801).isNil());
		assertFalse(reportManager.getReport(ReportList.RID_802).isNil());
		assertFalse(reportManager.getReport(ReportList.RID_803).isNil());
		assertFalse(reportManager.getReport(ReportList.RID_804).isNil());

		int RID_805 = 805;
		assertTrue(reportManager.getReport(RID_805).isNil());
	}

	@Test
	public void test42_report_get_report_801_with_valid_query_string() throws ParseException {
		OrderDeliveryReport report = (OrderDeliveryReport) reportManager.generateReport(ReportList.RID_801, null, null);
		assertEquals(1, report.getOrderList().size());
	}

	@Test
	public void test43_report_get_report_801_with_invalid_query_string_1() throws ParseException {
		exception.expect(ReportManager.InvalidDateParametersForReportException.class);
		reportManager.generateReport(ReportList.RID_801, "20160501", null);
	}

	@Test
	public void test44_report_get_report_801_with_invalid_query_string_2() throws ParseException {
		exception.expect(ReportManager.InvalidDateParametersForReportException.class);
		reportManager.generateReport(ReportList.RID_801, null, "20160430");
	}

	@Test
	public void test45_report_get_report_801_with_invalid_query_string_3() throws ParseException {
		exception.expect(ReportManager.InvalidDateParametersForReportException.class);
		reportManager.generateReport(ReportList.RID_801, "20160401", "20160430");
	}

	@Test
	public void test46_report_get_report_802_with_valid_query_string() throws ParseException {
		OrderDeliveryReport report = (OrderDeliveryReport) reportManager.generateReport(ReportList.RID_802, null, null);
		assertEquals(1, report.getOrderList().size());
	}

	@Test
	public void test47_report_get_report_802_with_invalid_query_string_1() throws ParseException {
		exception.expect(ReportManager.InvalidDateParametersForReportException.class);
		reportManager.generateReport(ReportList.RID_802, "20160501", null);
	}

	@Test
	public void test48_report_get_report_802_with_invalid_query_string_2() throws ParseException {
		exception.expect(ReportManager.InvalidDateParametersForReportException.class);
		reportManager.generateReport(ReportList.RID_802, null, "20160430");
	}

	@Test
	public void test49_report_get_report_802_with_invalid_query_string_3() throws ParseException {
		exception.expect(ReportManager.InvalidDateParametersForReportException.class);
		reportManager.generateReport(ReportList.RID_802, "20160401", "20160430");
	}

	@Test
	public void test50_report_get_report_804_with_valid_query_string_1() throws ParseException {
		OrderDeliveryReport report = (OrderDeliveryReport) reportManager.generateReport(ReportList.RID_804, "20160515",
				"20160530");
		assertEquals(2, report.getOrderList().size());
	}

	@Test
	public void test51_report_get_report_804_with_valid_query_string_2() throws ParseException {
		OrderDeliveryReport report = (OrderDeliveryReport) reportManager.generateReport(ReportList.RID_804, "20160518",
				null);
		assertEquals(1, report.getOrderList().size());
	}

	@Test
	public void test52_report_get_report_804_with_invalid_query_string_1() throws ParseException {
		exception.expect(ReportManager.InvalidDateParametersForReportException.class);
		reportManager.generateReport(ReportList.RID_804, null, "20160430");
	}

	@Test
	public void test53_report_get_report_804_with_invalid_query_string_2() throws ParseException {
		exception.expect(ReportManager.InvalidDateParametersForReportException.class);
		reportManager.generateReport(ReportList.RID_804, null, null);
	}

	@Test
	public void test54_report_get_report_803_with_valid_query_string_1() throws ParseException {
		RevenueReport report = (RevenueReport) reportManager.generateReport(ReportList.RID_803, "20160515", "20160530");
		Revenue revenue = report.getRevenue();

		assertEquals(ReportList.RID_803, report.getId());
		assertEquals("20160515", report.getStart_date());
		assertEquals("20160530", report.getEnd_date());

		assertEquals(3, revenue.getOrders_placed());
		assertEquals(2, revenue.getOrders_open());
		assertEquals(0, revenue.getOrders_delivered());
		assertEquals(1, revenue.getOrders_cancelled());
		assertEquals(65.92, revenue.getFood_revenue(), 0.01);
		assertEquals(10, revenue.getSurcharge_revenue(), 0.01);
	}

	@Test
	public void test55_report_get_report_803_with_valid_query_string_2() throws ParseException {
		RevenueReport report = (RevenueReport) reportManager.generateReport(ReportList.RID_803,
				DateUtility.getFormattedDateToday(), null);
		Revenue revenue = report.getRevenue();

		assertEquals(2, revenue.getOrders_placed());
		assertEquals(1, revenue.getOrders_open());
		assertEquals(1, revenue.getOrders_delivered());
		assertEquals(0, revenue.getOrders_cancelled());
		assertEquals(89.84, revenue.getFood_revenue(), 0.01);
	}

	@Test
	public void test56_report_get_report_803_with_invalid_query_string_1() throws ParseException {
		exception.expect(ReportManager.InvalidDateParametersForReportException.class);
		reportManager.generateReport(ReportList.RID_803, null, "20160430");
	}

	@Test
	public void test57_report_get_report_803_with_invalid_query_string_2() throws ParseException {
		exception.expect(ReportManager.InvalidDateParametersForReportException.class);
		reportManager.generateReport(ReportList.RID_803, null, null);
	}

	/*------------------------------Test For Testing Getting & Setter---------------------------------*/

	@Test
	public void test58_test_order_getter_methods() {
		Order o = orderManager.getOrder(101);
		assertEquals(101, o.getId());
		assertEquals(DateUtility.getFormattedDateToday(), o.getDelivery_date());
		assertEquals(DateUtility.getFormattedDateToday(), o.getOrder_date());
		assertEquals("10 West 31st ST, Chicago IL 60616", o.getDelivery_address());
		assertEquals("Room SB-214", o.getNote());
	}

	@Test
	public void test59_test_report_name() {
		Report r = reportManager.getReport(ReportList.RID_801);
		assertEquals(ReportList.RNAME_801, r.getName());
	}

	@Test
	public void test60_test_menu_item_getter_methods() {
		MenuItem item = menuManager.getItem(101);
		assertEquals(101, item.getId());
		assertEquals("Lasanga", item.getName());
		assertEquals(DateUtility.getFormattedDateToday(), item.getCreated_date());
		assertEquals(DateUtility.getFormattedDateToday(), item.getLast_modified_date());
		assertEquals(2.99, item.getPrice_per_person(), 0.01);
		assertEquals(6, item.getMinimum_order());
		assertNotNull(item.getCategories());
	}

	@Test
	public void test61_test_cancel_order_for_today() throws ParseException {
		assertEquals(OrderStatus.OPEN, orderManager.getOrder(101).getStatus());

		exception.expect(OrderManager.OrderCanNotBeCancelledException.class);
		orderManager.cancelOrder(101);
	}

	@Test
	public void test62_test_cateogry() {
		Category cat = new Category();
		cat.setName("vegan");
		assertEquals("vegan", cat.getName());
	}

	@Test
	public void test63_create_personalInfo_orderDetail_with_blank_constructor() {
		PersonalInfo pi = new PersonalInfo();
		OrderDetail od = new OrderDetail();

		assertNull(pi.getName());
		assertEquals(0, od.getCount());
	}

	@Test
	public void test64_test_order_with_invalid_order_date() throws ParseException {
		OrderDetail invalid_od = new OrderDetail(101, 8);
		List<OrderDetail> od_list = new ArrayList<>();
		od_list.add(invalid_od);

		Order o = new Order("20160418", "10 West 31st ST, Chicago IL 60616", p1, "Room SB-214", od_list);
		assertTrue(o.isOrderDateValid());
	}

	/*---------------------Dummy Value Creator--------------------------------------------------------*/

	private void createDummyMenuWithThreeItem() {
		cat_1 = new Category("organic");
		cat_2 = new Category("vegetarian");

		catList_1 = new ArrayList<>();
		catList_1.add(cat_1);
		catList_1.add(cat_2);

		catList_2 = new ArrayList<>();
		catList_2.add(cat_2);

		catList_3 = new ArrayList<>();

		item1 = new MenuItem("Lasanga", 2.49, 6, catList_1);
		item2 = new MenuItem("Buffalo Wings", 1.75, 12, catList_2);
		item3 = new MenuItem("Pizza", 5.49, 2, catList_3);

		adminManager.addItem(item1);
		adminManager.addItem(item2);
		adminManager.addItem(item3);
	}

	private void createSixDummyOrder() throws ParseException {
		od1 = new OrderDetail(101, 8);
		od2 = new OrderDetail(102, 24);
		od3 = new OrderDetail(103, 4);
		od4 = new OrderDetail(102, 20);
		od5 = new OrderDetail(103, 6);

		odList_1 = new ArrayList<>();
		odList_1.add(od1);

		odList_2 = new ArrayList<>();
		odList_2.add(od1);
		odList_2.add(od2);

		odList_3 = new ArrayList<>();
		odList_3.add(od1);
		odList_3.add(od2);
		odList_3.add(od3);

		odList_4 = new ArrayList<>();
		odList_4.add(od4);
		odList_4.add(od5);

		p1 = new PersonalInfo("Virgil B", "virgil@example.com", "123-456-7890");
		p2 = new PersonalInfo("Virgil", "virgil@example.com", "312-456-7890");
		p3 = new PersonalInfo("Parth J", "parth@gmail.com", "312-394-0854");
		p4 = new PersonalInfo("XYZ", "xyz@xyz.com", "987-654-3120");

		o1 = new Order(DateUtility.getFormattedDateToday(), "10 West 31st ST, Chicago IL 60616", p1, "Room SB-214",
				odList_1);
		o2 = new Order(DateUtility.getFormattedDateToday(), "10 West 31st ST, Chicago IL 60616", p2, "Room SB-214",
				odList_2);
		o3 = new Order(DateUtility.getFormattedDateTomorrow(), "10 West 31st ST, Chicago IL 60616", p3, "Room SB-214",
				odList_3);
		o4 = new Order("20160515", "10 West 31st ST, Chicago IL 60616", p4, "Room SB-214", odList_4);
		o5 = new Order("20160521", "10 West 31st ST, Chicago IL 60616", p1, "Room SB-214", odList_2);
		o6 = new Order("20160518", "10 West 31st ST, Chicago IL 60616", p3, "Room SB-214", odList_4);

		orderManager.addOrder(o1);
		orderManager.addOrder(o2);
		orderManager.addOrder(o3);
		orderManager.addOrder(o4);
		orderManager.addOrder(o5);
		orderManager.addOrder(o6);
	}

	/*---------------------------Private Attribute For Dummy Value Creation-------------------------------*/

	private Category cat_1;
	private Category cat_2;
	private List<Category> catList_1;
	private List<Category> catList_2;
	private List<Category> catList_3;
	private MenuItem item1;
	private MenuItem item2;
	private MenuItem item3;

	private OrderDetail od1;
	private OrderDetail od2;
	private OrderDetail od3;
	private OrderDetail od4;
	private OrderDetail od5;
	private List<OrderDetail> odList_1;
	private List<OrderDetail> odList_2;
	private List<OrderDetail> odList_3;
	private List<OrderDetail> odList_4;

	private PersonalInfo p1;
	private PersonalInfo p2;
	private PersonalInfo p3;
	private PersonalInfo p4;

	private Order o1;
	private Order o2;
	private Order o3;
	private Order o4;
	private Order o5;
	private Order o6;

}
