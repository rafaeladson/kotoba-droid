package net.fiive.kotoba.test.screen.questionList;

import android.util.Pair;
import net.fiive.kotoba.R;

abstract class TestIds {

	public Pair<Integer, Integer> getNewQuestionMenuId() {
		return Pair.create(R.id.add_question_menu, R.string.add);
	}

	public Pair<Integer, Integer> getInfoMenuId() {
		return Pair.create(R.id.info_menu, R.string.info);
	}

	public abstract int getAddNewQuestionItemId();
}

class UnitTestIds extends TestIds {

	@Override
	public int getAddNewQuestionItemId() {
		return R.id.question_add_new_link;
	}
}

class SoloTestIds extends TestIds {

	@Override
	public int getAddNewQuestionItemId() {
		return R.id.question_add_new_link;
	}


}
