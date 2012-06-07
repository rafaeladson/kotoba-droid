package net.fiive.kotoba.test.screen.base;

import android.app.Activity;
import android.view.MenuItem;
import net.fiive.kotoba.test.activities.stub.MenuItemStub;

public abstract class BaseScreenUnitTestAutomator<T extends Activity> implements BaseScreenAutomator {

	private T activity;

	protected BaseScreenUnitTestAutomator(T activity) {
		this.activity = activity;
	}

	protected T getActivity() {
		return activity;
	}

	@Override
	public void selectMenuItem(int itemId) {
		MenuItem menuItem = new MenuItemStub((Integer) itemId);
		activity.onOptionsItemSelected(menuItem);
	}
}
