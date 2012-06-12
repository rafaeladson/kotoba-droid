package net.fiive.kotoba.test.screen.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import com.google.common.base.Optional;

public class BaseScreen<A extends FragmentActivity, F extends Fragment> {

	private A activity;
	private F fragment;
	private ScreenAutomator automator;


	public BaseScreen(A activity, F fragment, ScreenAutomator automator) {
		this.activity = activity;
		this.fragment = fragment;
		this.automator = automator;
	}

	public void onActivityCreated(Bundle bundle) {
		this.fragment.onActivityCreated(bundle);
	}

	public void onSaveInstanceState(Bundle bundle) {
		fragment.onSaveInstanceState(bundle);
	}

	public void onResume() {
		fragment.onResume();
	}

	public void selectMenuItem(int itemId) {
		automator.selectMenuItem(itemId);
	}

	public void clickOnView(int viewId) {
		automator.clickOnView(viewId);
	}

	protected String getTextFromEditText(int editTextId) {
		EditText editText = (EditText) activity.findViewById(editTextId);
		return editText.getText().toString();
	}

	protected Optional<String> getErrorFromEditText(int editTextId) {
		EditText editText = (EditText) activity.findViewById(editTextId);
		CharSequence error = editText.getError();
		return error != null ? Optional.of(error.toString()) : Optional.<String>absent();
	}

	protected F getFragment() {
		return fragment;
	}

	protected ScreenAutomator getAutomator() {
		return automator;
	}

	protected void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException exception) {
			//do nothing, the only thing that can happen is a test failure.
		}
	}
}
