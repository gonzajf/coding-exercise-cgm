package io.cgm.qa.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class QAUtils {
	
	private static Pattern qaPattern = Pattern.compile("([\\w\\s\\']*\\?)((\\s*\\\"[\\w\\s\\']*\\\")+)");
	private static Pattern questionPattern = Pattern.compile("[\\w\\s\\']*\\?");
	private static Pattern answerPattern = Pattern.compile("\"[\\w\\s\\']*\"");

	public static boolean hasValidFormat(String input) {		
		return qaPattern.matcher(input).matches();
	}

	public static String getQuestionFrom(String input) {
	
		Matcher m = questionPattern.matcher(input);

		if (m.find()) {
		    return m.group();
		}
		return "?";
	}
	
	public static List<String> getAnswersFromInput(String input) {
		
		Matcher m = answerPattern.matcher(input);
		List<String> answers = new ArrayList<>();

		while(m.find()) {
			answers.add(m.group());
		}
		return answers;
	}
}