package net.fiive.kotoba.test.screen.base;

import android.R;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.widget.EditText;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import net.fiive.kotoba.activities.base.BaseActivity;

/**
 * A screen is an object used mainly to automate tests. Each screen has two functions:
 * <ol>
 * <li>It should know how to get information about the view that the screen represents. For example, if the view has an EditText, the screen should have a method
 * that can get the EditText contents. This makes the tests more maintainable because they don't have to know internals of the activity to test stuff.</li>
 * <li>It should know how to do things with the view, but it should not do it directly, and instead it should delegate the work to one of the mechanizes.
 * So if a test wants to click on a button, the screen will have a method called, for example, clickOnThisButton(), but its job will be only to delegate
 * the call to the appropriate method of the mechanize.
 * The mechanizes do exist because there are two different ways to do an action (one for unit testing and one for acceptance tests). The screen is generic,
 * and should work the same way for both type of tests.
 * </li>
 * </ol>
 *
 * @param <A> the type of the activity of the screen.
 * @param <F> the type of the only fragment of the activity. If the activity has more then one fragment, you'll probably want to change this code. So far it's not
 *            supported.
 */
public class BaseScreen<A extends BaseActivity, F extends Fragment> {

	private A activity;
	private F fragment;
	private ScreenMechanize mechanize;


	public BaseScreen(A activity, ScreenMechanize mechanize) {
		this.activity = activity;
		this.fragment = activity.getFragment();
		this.mechanize = mechanize;
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

	/**
	 * Click on the home menu item (top left button).
	 */
	public void selectHomeMenuItem() {
		Preconditions.checkState(Build.VERSION.SDK_INT >= 11, "Error: cannot select home menu item on pre-honeycomb SDKs");
		getMechanize().selectMenuItem(Pair.create(R.id.home, 0));
	}


	protected void clickOnView(int viewId) {
		mechanize.clickOnView(viewId);
	}

	protected String getEditTextContents(int editTextId) {
		EditText editText = (EditText) activity.findViewById(editTextId);
		return editText.getText().toString();
	}


	/**
	 * @param editTextId the identifier used to find the editText. It should be an element of the R.id resources.
	 * @return the error message of the editText, if any.
	 */
	protected Optional<String> getErrorFromEditText(int editTextId) {
		EditText editText = (EditText) activity.findViewById(editTextId);
		CharSequence error = editText.getError();
		return error != null ? Optional.of(error.toString()) : Optional.<String>absent();
	}


	protected F getFragment() {
		return fragment;
	}

	protected ScreenMechanize getMechanize() {
		return mechanize;
	}

	/**
	 * This will just sleep for some time, and if there is some error during this time it will ignore it.
	 *
	 * @param milliseconds the time to sleep, in milliseconds.
	 */
	protected void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException exception) {
			//do nothing, the only thing that can happen is a test failure.
		}
	}
}
