package io.cgm.qa.service;

import io.cgm.qa.model.QA;
import io.cgm.qa.repository.QARepository;
import io.cgm.qa.utils.QAUtils;

public class QAServiceImpl implements QAService {

	private static final String DEFAULT_ANSWER = 
			"The answer to life, universe and everything is 42";
	
	private final QARepository repository;
	
	public QAServiceImpl(QARepository repository) {
		this.repository = repository;
	}

	public void createQA(String input) {
		
		if(!QAUtils.hasValidFormat(input)) {
			throw new IllegalArgumentException("Invalid input format");
		}
		repository.save(new QA(QAUtils.getQuestionFrom(input).toLowerCase(), 
				QAUtils.getAnswersFromInput(input)));
	}

	public String askQuestion(String question) {
		return repository.getAnswersByQuestion(question.toLowerCase()).orElse(DEFAULT_ANSWER);
	}
}