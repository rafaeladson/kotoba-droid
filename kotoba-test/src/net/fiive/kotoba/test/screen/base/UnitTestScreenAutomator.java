package net.fiive.kotoba.test.screen.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import net.fiive.intern.android.view.alerts.AlertHelper;
import net.fiive.kotoba.test.activities.stub.AlertHelperMock;
import net.fiive.kotoba.test.activities.stub.MenuItemStub;

import java.lang.reflect.Method;

public class UnitTestScreenAutomator<A extends Activity, F extends Fragment> implements ScreenAutomator {

	private A activity;
	private F fragment;

	public UnitTestScreenAutomator(A activity, F fragment) {
		this.activity = activity;
		this.fragment = fragment;
	}

	public A getActivity() {
		return activity;
	}

	public F getFragment() {
		return fragment;
	}

	@Override
	public void selectMenuItem(int itemId) {
		MenuItem menuItem = new MenuItemStub((Integer) itemId);
		activity.onOptionsItemSelected(menuItem);
		fragment.onOptionsItemSelected(menuItem);
	}

	@Override
	public void typeTextInEditText(int editTextId, String value) {
		EditText editText = (EditText) activity.findViewById(editTextId);
		editText.setText(value);
	}

	@Override
	public void clickOnButton(int buttonId) {
		Button saveButton = (Button) this.getActivity().findViewById(buttonId);
		saveButton.performClick();
	}

	@Override
	public void clickOnView(int viewId) {
		View viewToBeClicked = this.getActivity().findViewById(viewId);
		viewToBeClicked.performClick();
	}

	@Override
	public void selectRemoveMenuItem(int removeMenuItemId, int alertOkButtonId) {
		AlertHelperMock alertHelperMock = new AlertHelperMock();
		try {
			Method mockAlertHelperMethod = fragment.getClass().getMethod("mockAlertHelper", AlertHelper.class);
			mockAlertHelperMethod.invoke(fragment, alertHelperMock);
			this.selectMenuItem(removeMenuItemId);
		} catch (Exception e) {
			throw new IllegalStateException("Can not invoke click on remove button! Possible cause is the fragment has not a mockAlertHelper method");
		}

	}


}
