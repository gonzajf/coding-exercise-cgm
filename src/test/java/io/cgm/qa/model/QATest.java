package io.cgm.qa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class QATest {

	@Test
	public void whenQAIsCreatedWithProperDataQAIsCreated() {

		String question = "What is love?";
		List<String> answers = Arrays.asList("\"baby don't hurt me\"", "\"don't hurt me\"", "\"no more\"");

		QA qa = new QA(question, answers);

		assertEquals(question, qa.getQuestion());
		assertEquals(answers, qa.getAnswers());
	}

	@Test
	public void whenQuestionIsMoreThan255LengthAnExceptionIsExpected() {

	    String generatedQuestion = generateQuestionWithLenghtGreaterThanAllowed();
		List<String> answers = Arrays.asList("\"baby don't hurt me\"", "\"don't hurt me\"", "\"no more\"");
	
	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new QA(generatedQuestion, answers);
		});
	    
		assertEquals("Question length must be 255 character or less", exception.getMessage());
	}

	@Test
	public void whenQuestionIsBlankAnExceptionIsExpected() {

	    String emptyQuestion = "            ?";
		List<String> answers = Arrays.asList("\"baby don't hurt me\"", "\"don't hurt me\"", "\"no more\"");
	
	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new QA(emptyQuestion, answers);
		});
	    
		assertEquals("Question must be different from blank", exception.getMessage());
	}
	
	@Test
	public void whenNoAnswerIsProvidedAnExceptionIsExpected() {

		String question = "What is love?";
	
	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new QA(question, new ArrayList<>());
		});
	    
		assertEquals("A minimun of one answer must be inserted", exception.getMessage());
	}
	
	@Test
	public void whenNoValidAnswersAreProvidedAnExceptionIsExpected() {

		String question = "What is love?";
		List<String> answers = Arrays.asList("   ", "", " ");
	
	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new QA(question, answers);
		});
	    
		assertEquals("All answers inserted are blank. A minimun of one valid answer must be inserted", exception.getMessage());
	}
	
	@Test
	public void whenLongAnswerIsProvidedAnExceptionIsExpected() {

		String question = "What is love?";
		List<String> answers = Arrays.asList(generateAnswerWithLenghtGreaterThanAllowed());
	
	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new QA(question, answers);
		});
	    
		assertEquals("All answers must be lower or equal than 255 characters", exception.getMessage());
	}
	
	@Test
	public void whenAtLeastOneValidAnswersIsEnteredAndBlankAnswersAreEnteredBlanksAreFiltered() {

		String question = "What is love?";
		List<String> answers = Arrays.asList("\"baby don't hurt me\"", "   ", "");
		
		QA qa = new QA(question, answers);
	    
		assertEquals(1, qa.getAnswers().size());
	}
	
	private String generateQuestionWithLenghtGreaterThanAllowed() {
		return new StringBuilder(RandomStringUtils.randomAlphabetic(255)).append("?").toString();
	}
	
	private String generateAnswerWithLenghtGreaterThanAllowed() {
		return new StringBuilder().append("\"")
				.append(RandomStringUtils.randomAlphabetic(256)).append("\"").toString();
	}
}