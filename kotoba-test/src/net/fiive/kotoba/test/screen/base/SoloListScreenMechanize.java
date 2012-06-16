package net.fiive.kotoba.test.screen.base;

import android.app.Instrumentation;
import com.google.common.base.Preconditions;
import com.jayway.android.robotium.solo.Solo;
import net.fiive.intern.android.activity.BaseActivity;

public class SoloListScreenMechanize<A extends BaseActivity> extends SoloScreenMechanize<A> implements ListScreenMechanize {

	public SoloListScreenMechanize(Instrumentation instrumentation, Solo solo) {
		super(instrumentation, solo);
	}

	@Override
	public <T> void clickOnItem(T lineIdentifier) {
		Preconditions.checkArgument(lineIdentifier instanceof String);
		getSolo().clickOnText((String) lineIdentifier);
	}
}
