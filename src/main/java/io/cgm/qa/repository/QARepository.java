package io.cgm.qa.repository;

import java.util.Optional;

import io.cgm.qa.model.QA;

public interface QARepository {

	void save(QA qa);
	Optional<String> getAnswersByQuestion(String question);
}