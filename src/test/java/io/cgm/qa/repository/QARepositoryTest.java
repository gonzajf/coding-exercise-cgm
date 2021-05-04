package io.cgm.qa.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import io.cgm.qa.model.QA;

public class QARepositoryTest {

	private QARepository repository;

	@Before
	public void setUp() {
		repository = new QARepositoryImpl();
	}

	@Test
	public void whenQAIsSavedItCanBeRecoveredFromRepository() {

		QA qa = new QA("What is love?", Arrays.asList("\"baby don't hurt me\"", "\"don't hurt me\"", "\"no more\""));
		repository.save(qa);

		Optional<String> qaFromRepository = repository.getAnswersByQuestion("What is love?");
		assertEquals(qa.getAnswersFromQuestion(), qaFromRepository.get());
	}

	@Test
	public void whenQANotExistsInRepositoryOptionalAnswerIsNotPresent() {

		Optional<String> qaFromRepository = repository.getAnswersByQuestion("What is love?");
		assertFalse(qaFromRepository.isPresent());
	}
}