package net.fiive.kotoba.activities.questionEdit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import net.fiive.kotoba.R;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.activities.questionList.QuestionListActivity;


public class QuestionEditFragment extends Fragment {



	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View editQuestionView =  inflater.inflate(R.layout.question_edit, container);
		Button cancelButton = (Button) editQuestionView.findViewById(R.id.cancel_edit_question);
		cancelButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				cancelEdit();
			}
		});

		return editQuestionView;
	}



	private void cancelEdit() {
		Intent intent = new Intent(this.getActivity(), QuestionListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
