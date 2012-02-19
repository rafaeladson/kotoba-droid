package net.fiive.kotoba.dao;

import net.fiive.intern.random.RandomItemRepository;
import net.fiive.kotoba.domain.Question;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionDAO implements RandomItemRepository<Question>{

	private final List<Question> questions;

	public QuestionDAO() {
		questions = Arrays.asList(new Question( "空はあおい", "O céu é azul"), new Question( "あの女の人はとても美しいですね。", "Aquela mulher é muito bonita"),
						 new Question( "私はべんごしじゃないです。私はインギニアです。", "I'm not a lawyer. I'm an engineer" ));
	}

	public Question findById(int id) {
		return questions.get(id);
	}

	public List<Question> findAll() {
		return Collections.unmodifiableList(questions);
	}


	@Override
	public List<Question> findUpToNItems(int n) {
		return Collections.unmodifiableList(questions);
	}
}
