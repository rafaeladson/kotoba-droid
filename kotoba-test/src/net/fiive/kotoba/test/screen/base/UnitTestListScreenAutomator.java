package net.fiive.kotoba.test.screen.base;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import com.google.common.base.Preconditions;

public class UnitTestListScreenAutomator<A extends FragmentActivity, F extends ListFragment> extends UnitTestScreenAutomator<A, F>
	implements ListScreenAutomator {

	public UnitTestListScreenAutomator(A activity, F fragment) {
		super(activity, fragment);
	}


	@Override
	public <T> void clickOnItem(T lineIdentifier) {
		Preconditions.checkArgument(lineIdentifier instanceof Integer);
		getFragment().onListItemClick(getFragment().getListView(), null, 0, (Integer) lineIdentifier);
	}
}
