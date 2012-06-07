package net.fiive.kotoba.test.screen.base;

import android.app.Activity;
import android.widget.EditText;
import com.jayway.android.robotium.solo.Solo;

public class SoloScreenAutomator<T extends Activity> implements ScreenAutomator {

	private Activity activity;
	private Solo solo;

	public SoloScreenAutomator(Activity activity, Solo solo) {
		this.activity = activity;
		this.solo = solo;
	}

	@Override
	public void selectMenuItem(int itemId) {
		solo.clickOnMenuItem(activity.getResources().getString(itemId));
	}

	public Activity getActivity() {
		return activity;
	}

	public Solo getSolo() {
		return solo;
	}

	public void typeTextInEditText(int editTextId, String value) {
		EditText editText = (EditText) activity.findViewById(editTextId);
		solo.clearEditText(editText);
		solo.enterText(editText, value);
	}

	public void clickOnButton(int buttonId) {
		getSolo().clickOnButton(getActivity().getResources().getString(buttonId));
	}

	@Override
	public void selectRemoveMenuItem(int removeMenuItemId, int alertOkButtonId) {
		this.selectMenuItem(removeMenuItemId);
		solo.clickOnButton(getActivity().getResources().getString(alertOkButtonId));
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
			//do nothing, this is just acceptance testing waiting.
		}
	}
}
