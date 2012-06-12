package net.fiive.kotoba.test.screen.base;

public interface ListScreenAutomator extends ScreenAutomator {

	/**
	 * @param lineIdentifier the identifier for the line to click.
	 * @param <T>            in Unit tests, it should be passed an Integer, which is the index of the line on the view.
	 *                       in Acceptance tests, you should pass a string, saying which text is appearing on the line.
	 */
	public <T> void clickOnItem(T lineIdentifier);

}
