package io.cgm.qa.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.cgm.qa.model.QA;

public class QARepositoryImpl implements QARepository {
	
	private Map<String, QA> repository = new HashMap<>();
	
	public void save(QA qa) {
		repository.put(qa.getQuestion(), qa);
	}

	public Optional<String> getAnswersByQuestion(String question) {
		return Optional.ofNullable(repository.get(question)).map(QA::getAnswersFromQuestion);
	}
}