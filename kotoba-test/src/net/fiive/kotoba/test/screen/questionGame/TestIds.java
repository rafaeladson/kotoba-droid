package net.fiive.kotoba.test.screen.questionGame;

import android.util.Pair;
import net.fiive.kotoba.R;

abstract class TestIds {

	public Pair<Integer, Integer> getManageQuestionsMenuId() {
		return Pair.create(R.id.manage_questions_menu, R.string.manage_questions);
	}

	public abstract int getNextQuestionButtonId();

	public abstract int getShowAnswerButtonId();


	public static class UnitTestIds extends TestIds {

		@Override
		public int getNextQuestionButtonId() {
			return R.id.nextQuestionButton;
		}

		@Override
		public int getShowAnswerButtonId() {
			return R.id.showAnswerButton;
		}


	}

	public static class SoloTestIds extends TestIds {

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
