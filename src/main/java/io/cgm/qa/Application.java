package io.cgm.qa;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import io.cgm.qa.repository.QARepositoryImpl;
import io.cgm.qa.service.QAService;
import io.cgm.qa.service.QAServiceImpl;

public class Application {
	
	private static Map<String, String> commands;
	private static QAService service;
	private static final String CREATE_QUESTION_OPTION = "1";
	private static final String ASK_QUESTION_OPTION = "2";
	private static final String EXIT_OPTION = "3";
	private static final String WELCOME_MESSAGE = 
			"Enter \"1\" to create a question with answers - \"2\" to ask a question - \"3\" to exit";
	private static final String CREATE_QUESTION_MESSAGE = 
			"Enter a question with answers with the following format <question>? “<answer1>” “<answer2>” “<answerX>”";
	private static final String ASK_QUESTION_MESSAGE = "Ask a Question";
	private static final String EXIT_MESSAGE = "Bye Bye!";		

	static {
		commands = new HashMap<>();
		commands.put(CREATE_QUESTION_OPTION, CREATE_QUESTION_MESSAGE);
		commands.put(ASK_QUESTION_OPTION, ASK_QUESTION_MESSAGE);
		commands.put(EXIT_OPTION, EXIT_MESSAGE);
		service = new QAServiceImpl(new QARepositoryImpl());
	}

	public static void main(String[] args) {

		System.out.println(WELCOME_MESSAGE);
		
		Scanner scanner = new Scanner(System.in);
		String option = scanner.nextLine();

		while (!option.equals(EXIT_OPTION)) {

			System.out.println(getCommand(option));
			
			if (option.equals(CREATE_QUESTION_OPTION)) {
				try {
					service.createQA(scanner.nextLine());
					System.out.println("Question added!");
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					System.out.println("Please try again");
				}
			}

			if (option.equals(ASK_QUESTION_OPTION)) {
				System.out.println(service.askQuestion(scanner.nextLine()));
			}

			System.out.println(WELCOME_MESSAGE);
			option = scanner.nextLine();
		}

		System.out.println(getCommand(option));
		scanner.close();
	}

	private static String getCommand(String command) {
		return commands.getOrDefault(command, "Command not found. Please try Again");
	} 
}