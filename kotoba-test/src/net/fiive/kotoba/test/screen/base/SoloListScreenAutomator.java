package net.fiive.kotoba.test.screen.base;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import com.google.common.base.Preconditions;
import com.jayway.android.robotium.solo.Solo;

public class SoloListScreenAutomator<A extends FragmentActivity> extends SoloScreenAutomator<A> implements ListScreenAutomator {

	public SoloListScreenAutomator(Activity activity, Solo solo) {
		super(activity, solo);
	}

	@Override
	public <T> void clickOnItem(T lineIdentifier) {
		Preconditions.checkArgument(lineIdentifier instanceof String);
		getSolo().clickOnText((String) lineIdentifier);
	}
}
