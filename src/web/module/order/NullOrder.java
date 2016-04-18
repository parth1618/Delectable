package web.module.order;

public class NullOrder extends Order {
	@Override
	public boolean isNil() {
		return true;
	}

}
