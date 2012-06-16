package net.fiive.kotoba.test.screen.base;

import android.support.v4.app.ListFragment;
import com.google.common.base.Preconditions;
import net.fiive.intern.android.activity.BaseActivity;

public class UnitTestListScreenMechanize<A extends BaseActivity, F extends ListFragment> extends UnitTestScreenMechanize<A, F>
	implements ListScreenMechanize {

	public UnitTestListScreenMechanize(A activity) {
		super(activity);
	}


	@Override
	public <T> void clickOnItem(T lineIdentifier) {
		Preconditions.checkArgument(lineIdentifier instanceof Integer);
		getFragment().onListItemClick(getFragment().getListView(), null, 0, (Integer) lineIdentifier);
	}
}
