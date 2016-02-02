package swt.swl.topcard;

import swt.swl.topcard.controller.logic.ViewBuilder;

public interface MainApp {

	/**
	 * Calls the buildView()-method on the {@link ViewBuilder}.INSTANCE using
	 * {@link String} argument "Login".
	 * 
	 * @see {@link ViewBuilder}
	 */
	public void initLoginView();

	/**
	 * 
	 * Draws 20 dots. <br>
	 * Execution-time: 5sec
	 * 
	 * @see {@link Thread}
	 */
	public static void drawDots() {

		try {
			System.out.println("starting..\r");
			for (int i = 0; i < 10; i++) {
				Thread.sleep(300);
				System.out.print(". ");
			}
			for (int i = 0; i < 10; i++) {
				Thread.sleep(200);
				System.out.print(".");
			}
			System.out.println("\r\r");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
