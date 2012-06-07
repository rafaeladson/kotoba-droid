package net.fiive.kotoba.test.screen.questionGame;

import net.fiive.kotoba.R;

interface TestIds {

	public int getNextQuestionButtonId();

	public int getShowAnswerButtonId();


	public static class UnitTestIds implements TestIds {

		@Override
		public int getNextQuestionButtonId() {
			return R.id.nextQuestionButton;
		}

		@Override
		public int getShowAnswerButtonId() {
			return R.id.showAnswerButton;
		}
	}

	public static class SoloTestIds implements TestIds {

		@Override
		public int getNextQuestionButtonId() {
			return R.string.next_question;
		}

		@Override
		public int getShowAnswerButtonId() {
			return R.string.show_answer;
		}
	}

}
