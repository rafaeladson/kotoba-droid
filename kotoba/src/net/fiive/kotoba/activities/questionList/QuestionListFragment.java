package net.fiive.kotoba.activities.questionList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import com.google.common.base.Preconditions;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.dao.QuestionDAO;
import net.fiive.kotoba.domain.Question;

public class QuestionListFragment extends ListFragment {

	private QuestionDAO questionDAO = new QuestionDAO();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		this.setListAdapter(new QuestionListAdapter(this.getActivity().getApplicationContext(), questionDAO.findAll()));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Preconditions.checkNotNull(id, "Id should not be null");
		super.onListItemClick(l, v, position, id);
		Question questionFromRepository = questionDAO.findById((int)id);
		Preconditions.checkNotNull(questionFromRepository, "Could not find question in repository");

		Intent editQuestionIntent = new Intent(QuestionEditActivity.EDIT_QUESTION_ACTION);
		editQuestionIntent.putExtra("question", questionFromRepository);
		startActivity(editQuestionIntent);

	}
}
