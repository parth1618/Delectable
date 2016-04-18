package web.module.utility;

import java.util.concurrent.atomic.AtomicInteger;

public class UniqueIdGenerator {

	static AtomicInteger atomicInteger_menuItem = new AtomicInteger(100);
	static AtomicInteger atomicInteger_order = new AtomicInteger(100);
	static AtomicInteger atomicInteger_customer = new AtomicInteger(100);

	public static int getUniqueItemID() {
		return atomicInteger_menuItem.incrementAndGet();
	}

	public static int getUniqueOrderID() {
		return atomicInteger_order.incrementAndGet();
	}

	public static int getUniqueCustomerID() {
		return atomicInteger_customer.incrementAndGet();
	}

}
