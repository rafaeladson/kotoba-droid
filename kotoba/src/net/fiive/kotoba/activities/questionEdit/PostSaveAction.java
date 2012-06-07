package net.fiive.kotoba.activities.questionEdit;

/**
 * This tells the method saveQuestion from {@link QuestionEditFragment} what to do after the question is saved.
 */
public enum PostSaveAction {

	/**
	 * If this is passed as the argument to the method, the method will redirect the user back to the previous activity he was in.
	 */
	GO_BACK,
	/**
	 * If this is passed as the argument to the method, the user will continue in the same activity, but will begin to edit a new question.
	 */
	CREATE_NEW
}
