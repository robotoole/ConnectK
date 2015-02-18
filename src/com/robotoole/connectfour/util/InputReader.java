package com.robotoole.connectfour.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {

	/**
	 * Read and return an input string.
	 * 
	 * @param prompt
	 * @return
	 */
	public static String readStringInput(String prompt) {
		// prompt the user
		System.out.print(prompt);

		// open up standard input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String result = null;

		// read the result
		try {
			result = br.readLine();
		} catch (IOException ioe) {
			System.out.println("IO exception!");
			System.exit(1);
		}

		return result;
	}

	/**
	 * Read an integer input and somewhat gracefully handle number format
	 * exception.
	 * 
	 * @param prompt
	 * @return
	 */
	public static String readIntegerInput(String prompt) {
		// prompt the user
		System.out.print(prompt);

		// open up standard input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String result = null;

		// read the result
		try {
			result = br.readLine();
		} catch (IOException ioe) {
			System.out.println("IO exception!");
			System.exit(1);
		}

		try {
			Integer.parseInt(result);
		} catch (NumberFormatException e) {
			result = "0";
		}

		return result;
	}
}
