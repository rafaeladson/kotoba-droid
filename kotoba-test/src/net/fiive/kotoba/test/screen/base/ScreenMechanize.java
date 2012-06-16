package net.fiive.kotoba.test.screen.base;

import android.util.Pair;

public interface ScreenMechanize {

	/**
	 * Select a item of the menu
	 *
	 * @param menuItemId the identifier of the menu to select. It is a pair where the first element should be the id of the menu item in the id resources.
	 *                   The second element of the pair should be the string resource that contains the text of the menu.
	 */
	public void selectMenuItem(Pair<Integer, Integer> menuItemId);

	/**
	 * Type a text in a editText. If the editText has any text, the text will be erased.
	 *
	 * @param editTextId the editTextId used to localize which editText to type. It should always reference one element of the R.id resources.
	 * @param value      The value to fill the text.
	 */
	public void typeTextInEditText(int editTextId, String value);

	/**
	 * Click on a button on the screen.
	 *
	 * @param buttonId the identifier used to localize the button. For unit tests, this should be an element of the R.id resources.
	 *                 For acceptance tests, this should be one element for the R.string resources, which points to the text of the button.
	 */
	public void clickOnButton(int buttonId);

	/**
	 * Click on a ordinary testlib. It's expected that this testlib has a clickListener associated with it.
	 *
	 * @param viewId The identifier used to localize the testlib. It should be an element of the R.id resources.
	 */
	public void clickOnView(int viewId);

	/**
	 * Click on the menu item used to remove something. It assumes an alert box will appear, and it will click on Ok to confirm the deletion of the element.
	 *
	 * @param removeMenuId    a pair of ids used to localize the remove menu. The first element should be one element of the R.id resources, which should point to the menu.
	 *                        The second should be a element of the R.string resources, which points to the text of the menu.
	 * @param alertOkButtonId the id used to localize the OK button in the alert dialog.
	 *                        For unit tests, this will be a element of the R.id resources, which points to the button.
	 *                        For acceptance testis, this should be a elmeent of the R.string resources, which points to the text of the button.
	 */
	void selectRemoveMenuItem(Pair<Integer, Integer> removeMenuId, int alertOkButtonId);


}
