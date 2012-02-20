package net.fiive.kotoba.activities.questionList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.google.common.base.Preconditions;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.questionEdit.QuestionEditActivity;
import net.fiive.kotoba.data.dao.QuestionDAO;

public class QuestionListFragment extends ListFragment {

	private QuestionDAO questionDAO = new QuestionDAO();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setListAdapter(new QuestionListAdapter(this.getActivity().getApplicationContext(), questionDAO.findAll()));
		this.setHasOptionsMenu(true);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Preconditions.checkNotNull(id, "Id should not be null");
		super.onListItemClick(l, v, position, id);

		String uriValue = String.format("kotoba://kotoba.fiive.net/question/%d", id);
		Intent editQuestionIntent = new Intent(QuestionEditActivity.EDIT_QUESTION_ACTION, Uri.parse(uriValue));
		startActivity(editQuestionIntent);
	}


}
