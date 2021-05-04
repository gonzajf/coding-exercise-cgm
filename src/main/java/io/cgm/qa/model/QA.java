package io.cgm.qa.model;

import java.util.List;
import java.util.stream.Collectors;

public class QA {
	
	private static final int MAX_LENGTH = 255;
	private String question;
	private List<String> answers;
	
	public QA(String question, List<String> answers) {
		setQuestion(question);
		setAnswers(answers);
	}

	private void setQuestion(String question) {
		
		if(question.replace("?", "").trim().isEmpty()) {
			throw new IllegalArgumentException("Question must be different from blank");
		}
		
		if(question.trim().length() > MAX_LENGTH) {
			throw new IllegalArgumentException("Question length must be 255 character or less");
		}
		this.question = question;
	}

	private void setAnswers(List<String> answers) {
	
		if(answers.isEmpty()) {
			throw new IllegalArgumentException("A minimun of one answer must be inserted");
		}
		
		List<String> notBlankAnswers = answers
				.stream()
				.filter(an -> !an.replaceAll("\"", "").trim().isEmpty())
				.collect(Collectors.toList());
		
		if(notBlankAnswers.isEmpty()) {
			throw new IllegalArgumentException("All answers inserted are blank. A minimun of one valid answer must be inserted");
		}
				
		if(notBlankAnswers.stream().anyMatch(an -> an.length() > MAX_LENGTH)) {
			throw new IllegalArgumentException("All answers must be lower or equal than 255 characters");
		}

		this.answers = notBlankAnswers;
	}

	public String getQuestion() {
		return question;
	}
	
	public List<String> getAnswers() {
		return answers;
	}
	
	public String getAnswersFromQuestion() {
		return answers.stream().collect(Collectors.joining("\n"));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QA other = (QA) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}
}