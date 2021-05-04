package io.cgm.qa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.cgm.qa.model.QA;
import io.cgm.qa.repository.QARepository;

public class QAServiceTest {

	@Mock
	private QARepository repository;

	private QAService qaService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		qaService = new QAServiceImpl(repository);
	}

	@Test
	public void whenInputIsValidInteractionAQAIsCreated() {

		String validInput = "What is love? \"baby don't hurt me\" \"don't hurt me\" \"no more\"";

		qaService.createQA(validInput);

		verify(repository).save(
				new QA("what is love?", Arrays.asList("\"baby don't hurt me\"", "\"don't hurt me\"", "\"no more\"")));
	}

	@Test
	public void whenQuestionInputIsInvalidAnExceptionIsExpected() {

		String invalidQuestionInput = "What is love \"baby don't hurt me\" \"don't hurt me\" \"no more\"";

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			qaService.createQA(invalidQuestionInput);
		});

		assertEquals("Invalid input format", exception.getMessage());
		verify(repository, never()).save(any(QA.class));
	}

	@Test
	public void whenNoAnswersAreProvidedAnExceptionIsExpected() {

		String input = "What is love?";

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			qaService.createQA(input);
		});

		assertEquals("Invalid input format", exception.getMessage());

		verify(repository, never()).save(any(QA.class));
	}

	@Test
	public void whenQuestionIsMoreThan255LengthAnExceptionIsExpected() {

		String generatedInput = generateInputWithQuestionLenghtGreaterThanAllowed();

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			qaService.createQA(generatedInput);
		});

		assertEquals("Question length must be 255 character or less", exception.getMessage());
		verify(repository, never()).save(any(QA.class));
	}

	@Test
	public void whenQuestionIsBlankAnExceptionIsExpected() {

		String input = "         ? \"baby don't hurt me\" \"don't hurt me\" \"no more\"";

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			qaService.createQA(input);
		});

		assertEquals("Question must be different from blank", exception.getMessage());
		verify(repository, never()).save(any(QA.class));
	}

	@Test
	public void whenAllAnswerAreBlankAnExceptionIsExpected() {

		String input = "What is love? \"\" \"     \" \" \"";

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			qaService.createQA(input);
		});

		assertEquals("All answers inserted are blank. A minimun of one valid answer must be inserted", exception.getMessage());
		verify(repository, never()).save(any(QA.class));
	}

	@Test
	public void whenBlankAnswersAreEnteredTheyAreRemoved() {

		String input = "What is love? \"\" \"     \" \"no more\"";

		qaService.createQA(input);

		verify(repository).save(new QA("what is love?", Arrays.asList("\"no more\"")));
	}

	@Test
	public void whenAQuestionAskedIsNotInRepositoryDefaultAnswerIsReturned() {

		when(repository.getAnswersByQuestion("What is love?")).thenReturn(Optional.empty());
		String answer = qaService.askQuestion("What is love?");

		assertEquals("The answer to life, universe and everything is 42", answer);
	}

	@Test
	public void whenAQuestionAskedIsInRepositoryProperAnswerIsReturned() {

		when(repository.getAnswersByQuestion("what is love?")).thenReturn(Optional.of("\"baby don't hurt me\"\n" + 
				"\"don't hurt me\"\n" + 
				"\"no more\""));

		String answer = qaService.askQuestion("what is love?");

		assertEquals("\"baby don't hurt me\"\n" + 
				"\"don't hurt me\"\n" + 
				"\"no more\"", answer);
	}

	private String generateInputWithQuestionLenghtGreaterThanAllowed() {

		StringBuilder sb = new StringBuilder(RandomStringUtils.randomAlphabetic(255)).append("?").append(" ")
				.append("\"testAnswer\"");

		return sb.toString();
	}
}