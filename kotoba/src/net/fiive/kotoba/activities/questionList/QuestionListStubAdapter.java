package net.fiive.kotoba.activities.questionList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import net.fiive.kotoba.R;
import net.fiive.kotoba.domain.Question;

import java.util.List;

public class QuestionListStubAdapter extends BaseAdapter {

	private final List<Question> questions;
	private final Context context;

	public QuestionListStubAdapter(Context context, List<Question> questions) {
		this.questions = questions;
		this.context = context;
	}

	@Override
	public int getCount() {
		return questions.size();
	}

	@Override
	public Question getItem(int i) {
		return questions.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View itemView = inflater.inflate(R.layout.question_list_item, viewGroup, false);
		TextView questionListItemView = (TextView) itemView.findViewById(R.id.questionListItemValue);
		Question currentQuestion = getItem(i);
		questionListItemView.setText(currentQuestion.getValue());

		return itemView;

	}
}
