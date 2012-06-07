package net.fiive.kotoba.test.screen.base;

import android.app.Activity;
import com.jayway.android.robotium.solo.Solo;

public abstract class BaseScreenSoloAutomator<T extends Activity> implements BaseScreenAutomator {

	private Activity activity;
	private Solo solo;

	public BaseScreenSoloAutomator(Activity activity, Solo solo) {
		this.activity = activity;
		this.solo = solo;
	}

	@Override
	public void selectMenuItem(int itemId) {
		solo.clickOnMenuItem(activity.getResources().getString(itemId));
	}

	protected Activity getActivity() {
		return activity;
	}

	protected Solo getSolo() {
		return solo;
	}
}
