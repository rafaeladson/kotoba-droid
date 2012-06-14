package net.fiive.kotoba.test.screen.base;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.TextView;
import com.google.common.base.Preconditions;
import net.fiive.kotoba.activities.base.BaseActivity;

public class BaseListScreen<A extends BaseActivity, F extends ListFragment> extends BaseScreen<A, F> {

	public BaseListScreen(A activity, ListScreenMechanize automator) {
		super(activity, automator);
	}

	/**
	 * @param itemId a identifier that describes which item to click.
	 * @param <T>    If you're doing an acceptance tests, itemId is a String describing the text that is in the line.
	 *               If you're doing unit tests, itemId is a int describing the index of the item.
	 *               This still does not necessarily supports scrolling of the list. (make sure itemId is present on the view before calling -- for now!)
	 */
	public <T> void clickOnListItem(T itemId) {
		getListScreenAutomator().clickOnItem(itemId);
	}

	public int getListSize() {
		return getFragment().getListView().getChildCount();
	}

	public boolean isEmpty() {
		return getListSize() == 0;
	}

	public String getTextAtLine(int cellIndex) {
		View cellView = getFragment().getListView().getChildAt(cellIndex);
		Preconditions.checkState(cellView instanceof TextView, "Error: This method should only be invoked on a TestView based cell");
		TextView textView = (TextView) cellView;
		return textView.getText().toString();
	}

	private ListScreenMechanize getListScreenAutomator() {
		return (ListScreenMechanize) getMechanize();
	}
}
