package net.fiive.kotoba.test.screen.questionEdit;

import net.fiive.kotoba.R;

interface TestIds {

	int getSaveMenuItemId();

	int getCancelButtonId();

	int getCancelMenuItemId();

	int getAlertOkButtonId();

	int getRemoveMenuItemId();

	int getSaveAndNewButtonId();

	public static class UnitTestIds implements TestIds {

		@Override
		public int getSaveMenuItemId() {
			return R.id.save_question_menu;
		}


		@Override
		public int getCancelButtonId() {
			return R.id.cancel_edit_question;
		}

		@Override
		public int getCancelMenuItemId() {
			return R.id.cancel_edit_question_menu;
		}

		@Override
		public int getRemoveMenuItemId() {
			return R.id.remove_question_menu;
		}

		@Override
		public int getAlertOkButtonId() {
			return R.string.ok;
		}

		@Override
		public int getSaveAndNewButtonId() {
			return R.id.question_save_and_new_menu;
		}


	}

	public static class SoloTestIds implements TestIds {


		@Override
		public int getSaveMenuItemId() {
			return R.string.save;
		}

		@Override
		public int getCancelButtonId() {
			return R.string.cancel;
		}

		@Override
		public int getCancelMenuItemId() {
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

		@Override
		public int getSaveAndNewButtonId() {
			return R.string.save_and_new;
		}
	}

}
