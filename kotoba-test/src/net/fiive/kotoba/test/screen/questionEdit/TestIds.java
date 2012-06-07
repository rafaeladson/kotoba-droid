package net.fiive.kotoba.test.screen.questionEdit;

import net.fiive.kotoba.R;

interface TestIds {

	int getSaveButtonId();

	int getCancelButtonId();

	int getAlertOkButtonId();

	int getRemoveMenuItemId();

	public static class UnitTestIds implements TestIds {

		@Override
		public int getSaveButtonId() {
			return R.id.save_question;
		}


		@Override
		public int getCancelButtonId() {
			return R.id.cancel_edit_question;
		}

		@Override
		public int getRemoveMenuItemId() {
			return R.id.remove_question_menu;
		}

		@Override
		public int getAlertOkButtonId() {
			return R.string.ok;
		}


	}

	public static class SoloTestIds implements TestIds {


		@Override
		public int getSaveButtonId() {
			return R.string.save;
		}

		@Override
		public int getCancelButtonId() {
			return R.string.cancel;
		}

		@Override
		public int getAlertOkButtonId() {
			return R.string.ok;
		}

		@Override
		public int getRemoveMenuItemId() {
			return R.string.remove;
		}
	}

}
