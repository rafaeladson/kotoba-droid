package net.fiive.kotoba.activities.questionEdit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.fiive.kotoba.R;


public class QuestionEditFragment extends Fragment {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View editQuestionView = inflater.inflate(R.layout.question_edit, container);
		return editQuestionView;
	}
}
