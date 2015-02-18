package com.robotoole.connectfour.builder;

import com.robotoole.connectfour.model.ConnectModel;

public class PromptBuilder {

	public static String buildWelcome() {
		return "Welcome to ConnectK, where the power of K is up to you!";
	}

	public static String buildPlayerNamePrompt(String playerNumber) {
		return "What is your name, " + playerNumber + "? ";
	}

	public static String buildRowPrompt() {
		return "How many rows do you want to play with? ";
	}

	public static String buildColumnPrompt() {
		return "How many columns do you want to play with? ";
	}

	public static String buildWinCountPrompt() {
		return "What is the number of chips required to win? ";
	}

	public static String buildTurnPrompt(String playerName) {
		return "What column would you like to drop a piece into next, "
				+ playerName + "? ";
	}

	public static String buildGameRestartPrompt() {
		return "What you like to play again?  y/n  ";
	}

	public static String buildGameStart() {
		return ConnectModel.getInstance().player1.getName() + " vs "
				+ ConnectModel.getInstance().player2.getName()
				+ " begins ..... NOW!";
	}

	public static String buildFailedTurn() {
		return "Unfortunately, that move was not accepted. Please try again!";
	}

	public static String buildCongratulations(String playerName) {
		return "Congratulations, " + playerName
				+ "!!!!!\nYou are the ConnectK CHAMPION!";
	}

	public static String buildStalemate() {
		return "Sorry, but you've run out of chances. Please try again with a bigger " +
				"board to play longer games!";
	}

	public static String buildThanksForPlaying() {
		return "Thanks for playing!";
	}
}
