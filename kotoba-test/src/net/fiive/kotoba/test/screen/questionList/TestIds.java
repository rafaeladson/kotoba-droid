package net.fiive.kotoba.test.screen.questionList;

import net.fiive.kotoba.R;

interface TestIds {

	public int getNewQuestionMenuId();

	public int getInfoMenuId();

	public int getAddNewQuestionItemId();
}

class UnitTestIds implements TestIds {

	@Override
	public int getNewQuestionMenuId() {
		return R.id.add_question_menu;
	}

	@Override
	public int getInfoMenuId() {
		return R.id.info_menu;
	}

	@Override
	public int getAddNewQuestionItemId() {
		return R.id.question_add_new_link;
	}
}

class SoloTestIds implements TestIds {

	@Override
	public int getNewQuestionMenuId() {
		return R.string.add;
	}

	@Override
	public int getInfoMenuId() {
		return R.string.info;
	}

	@Override
	public int getAddNewQuestionItemId() {
		return R.id.question_add_new_link;
	}


}
