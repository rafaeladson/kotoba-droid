package net.fiive.kotoba.activities.questionEdit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import net.fiive.kotoba.R;


public class QuestionEditFragment extends Fragment {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.question_edit, container);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.question_edit, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
}
