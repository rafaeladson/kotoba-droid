package net.fiive.kotoba.test.activities.stub;

import android.content.Context;
import net.fiive.intern.android.view.alerts.AlertHelper;
import net.fiive.intern.android.view.alerts.ErrorAlertInfo;

public class AlertHelperMock extends AlertHelper {

	private boolean showErrorAlertCalled = false;

	@Override
	public void showErrorAlert(Context context, ErrorAlertInfo errorAlertInfo, Callback callback) {
		showErrorAlertCalled = true;
	}

	public boolean showErrorAlertWasCalled() {
		return showErrorAlertCalled;
	}
}
