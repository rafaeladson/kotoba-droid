package net.fiive.kotoba.test.screen.questionEdit;

import android.util.Pair;
import net.fiive.kotoba.R;

abstract class TestIds {

	Pair<Integer, Integer> getSaveMenuItemId() {
		return Pair.create(R.id.save_question_menu, R.string.save);
	}

	Pair<Integer, Integer> getCancelMenuItemId() {
		return Pair.create(R.id.cancel_edit_question_menu, R.string.cancel);
	}

	Pair<Integer, Integer> getRemoveMenuItemId() {
		return Pair.create(R.id.remove_question_menu, R.string.remove);

	}

	Pair<Integer, Integer> getSaveAndNewButtonId() {
		return Pair.create(R.id.question_save_and_new_menu, R.string.save_and_new);

	}

	abstract int getCancelButtonId();

	abstract int getAlertOkButtonId();


	public static class UnitTestIds extends TestIds {

		@Override
		public int getCancelButtonId() {
			return R.id.cancel_edit_question;
		}

		@Override
		public int getAlertOkButtonId() {
			return R.string.ok;
		}

	}

	public static class SoloTestIds extends TestIds {


		@Override
		public int getCancelButtonId() {
			return R.string.cancel;
		}


		@Override
		public int getAlertOkButtonId() {
			return R.string.ok;
		}

	}

}
