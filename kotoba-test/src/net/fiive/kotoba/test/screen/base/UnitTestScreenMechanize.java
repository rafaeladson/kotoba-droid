package net.fiive.kotoba.test.screen.base;

import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import net.fiive.intern.android.view.alerts.AlertHelper;
import net.fiive.kotoba.activities.base.BaseActivity;
import net.fiive.kotoba.test.activities.stub.AlertHelperMock;
import net.fiive.kotoba.test.activities.stub.MenuItemStub;

import java.lang.reflect.Method;

public class UnitTestScreenMechanize<A extends BaseActivity, F extends Fragment> implements ScreenMechanize {

	private A activity;
	private F fragment;

	public UnitTestScreenMechanize(A activity) {
		this.activity = activity;
		this.fragment = activity.getFragment();

	}

	public A getActivity() {
		return activity;
	}

	public F getFragment() {
		return fragment;
	}

	@Override
	public void selectMenuItem(Pair<Integer, Integer> itemId) {
		MenuItem menuItem = new MenuItemStub(itemId.first);
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
	public void selectRemoveMenuItem(Pair<Integer, Integer> removeItemId, int alertOkButtonId) {
		AlertHelperMock alertHelperMock = new AlertHelperMock();
		try {
			Method mockAlertHelperMethod = fragment.getClass().getMethod("mockAlertHelper", AlertHelper.class);
			mockAlertHelperMethod.invoke(fragment, alertHelperMock);
			this.selectMenuItem(removeItemId);
		} catch (Exception e) {
			throw new IllegalStateException("Can not invoke click on remove button! Possible cause is the fragment has not a mockAlertHelper method");
		}

	}


}
