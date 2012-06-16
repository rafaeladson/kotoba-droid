package net.fiive.kotoba.test.screen.base;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Build;
import android.util.Pair;
import android.widget.EditText;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.intern.android.activity.BaseActivity;

public class SoloScreenMechanize<T extends BaseActivity> implements ScreenMechanize {

	static final int DEFAULT_SLEEP_TIME = 500;
	private T activity;
	private Instrumentation instrumentation;

	private Solo solo;

	@SuppressWarnings("unchecked")
	public SoloScreenMechanize(Instrumentation instrumentation, Solo solo) {
		this.instrumentation = instrumentation;
		this.activity = (T) solo.getCurrentActivity();
		this.solo = solo;
	}

	@Override
	public void selectMenuItem(Pair<Integer, Integer> itemId) {
		if (Build.VERSION.SDK_INT < 11) {
			int textResourceId = itemId.second;
			solo.clickOnMenuItem(solo.getString(textResourceId));
		} else {
			int stubItemId = itemId.first;

			instrumentation.invokeMenuActionSync(activity, stubItemId, 0);
			solo.getActivityMonitor().waitForActivity();
			solo.sleep(DEFAULT_SLEEP_TIME);
			throw new UnsupportedOperationException("Error: Solo tests in SDK equal or greater than 11 are still not supported");
		}
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
		getSolo().clickOnButton(solo.getString(buttonId));
	}

	@Override
	public void clickOnView(int viewId) {
		getSolo().clickOnView(getActivity().findViewById(viewId));
	}

	@Override
	public void selectRemoveMenuItem(Pair<Integer, Integer> menuItemId, int alertOkButtonId) {
		this.selectMenuItem(menuItemId);
		solo.clickOnButton(solo.getString(alertOkButtonId));
		solo.sleep(500);
	}


}
