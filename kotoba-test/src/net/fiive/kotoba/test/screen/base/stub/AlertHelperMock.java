package net.fiive.kotoba.test.screen.base.stub;

import android.content.Context;
import net.fiive.intern.android.view.alerts.AlertHelper;
import net.fiive.intern.android.view.alerts.ErrorAlertInfo;
import net.fiive.intern.android.view.alerts.RemoveAlertInfo;

public class AlertHelperMock extends AlertHelper {

	private boolean showErrorAlertCalled = false;
	private boolean showRemoveAlertCalled = false;

	@Override
	public void showErrorAlert(Context context, ErrorAlertInfo errorAlertInfo, Callback callback) {
		showErrorAlertCalled = true;
	}

	@Override
	public void showRemoveAlert(Context context, RemoveAlertInfo removeAlertInfo, Callback okCallback, Callback cancelCallback) {
		showRemoveAlertCalled = true;
		okCallback.notifyCallback();
	}

	public boolean showErrorAlertWasCalled() {
		return showErrorAlertCalled;
	}

	public boolean showRemoveAlertWasCalled() {
		return showRemoveAlertCalled;
	}
}
