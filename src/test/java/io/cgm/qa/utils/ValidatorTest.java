package io.cgm.qa.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidatorTest {
	
	@Test
	public void whenInputIsValidMethodReturnsTrue() {
		
		String validInput = "What is your favorite food?\"pizza\"\"coca cola\" \"helado\" \"coca cola\"";
		
		assertTrue(QAUtils.hasValidFormat(validInput));
	}
	
	@Test
	public void whenInputHasQuestionMarkAndEvenQuoteMarksThenIsValid() {
		
		String validInput = "? \"\"";
		
		assertTrue(QAUtils.hasValidFormat(validInput));
	}
	
	@Test
	public void whenInputHasQuestionMarkAndOddQuoteMarksThenIsInvalid() {
		
		String invalidInput = "? \"\"\"";
		
		assertFalse(QAUtils.hasValidFormat(invalidInput));
	}
	
	@Test
	public void whenInputHasNoQuestionThenIsInvalid() {
		
		String invalidInputWithNoQuestion = "\"pizza\"\"coca cola\" \"helado\" \"coca cola\"\"zsdzdf\"";
		
		assertFalse(QAUtils.hasValidFormat(invalidInputWithNoQuestion));
	}

	@Test
	public void whenInputHasNoAnswersThenIsInvalid() {
		
		String invalidInputQuestionNoAnswers = "What is your favorite food?";
		
		assertFalse(QAUtils.hasValidFormat(invalidInputQuestionNoAnswers));
	}
	
	@Test
	public void whenInputIsBlankThenIsInvalid() {
		
		String blankInput = "            ";
		
		assertFalse(QAUtils.hasValidFormat(blankInput));
	}
	
	@Test
	public void whenInputIsEmptyThenIsInvalid() {
		
		String emptyInput = "";
		
		assertFalse(QAUtils.hasValidFormat(emptyInput));
	}	
}