package net.fiive.kotoba.test.screen.base;

public interface ScreenAutomator {

	public void selectMenuItem(int itemId);

	public void typeTextInEditText(int editTextId, String value);

	public void clickOnButton(int buttonId);

	void selectRemoveMenuItem(int removeMenuItemId, int alertOkButtonId);
}
