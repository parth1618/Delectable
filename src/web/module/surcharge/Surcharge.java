package web.module.surcharge;

public class Surcharge {

	private static double surcharge = 0;
	private static boolean isSet = false;

	public static double getSurcharge() {
		return surcharge;
	}

	public static void setSurcharge(double surcharge) {
		Surcharge.surcharge = surcharge;
		isSet = true;
	}

	public static boolean isSet() {
		return isSet;
	}

}
