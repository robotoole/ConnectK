package com.robotoole.connectfour;

import com.robotoole.connectfour.builder.PromptBuilder;
import com.robotoole.connectfour.constants.Colors.PlayerColors;
import com.robotoole.connectfour.model.Cell;
import com.robotoole.connectfour.model.ConnectModel;
import com.robotoole.connectfour.model.Piece;
import com.robotoole.connectfour.model.Player;
import com.robotoole.connectfour.model.Turn;
import com.robotoole.connectfour.util.InputReader;

/**
 * ConnectK is a command line connect four.
 * 
 * @author robert
 * 
 */
public class Main {

	/**
	 * Singleton model to hold all the data.
	 */
	static ConnectModel model = ConnectModel.getInstance();

	/**
	 * Kick off the prompt of player name input, column, row, and winning piece
	 * count.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		initGame();
	}

	/**
	 * Start off the game with a clean slate!
	 */
	public static void initGame() {
		getInputs();

		model.lastPlayer = new Player();

		startGame();
	}

	/**
	 * Get the inputs from the user required to start the game.
	 */
	public static void getInputs() {
		// everything is ready to go!
		System.out.println(PromptBuilder.buildWelcome());

		// player names
		model.player1.setName(InputReader.readStringInput(PromptBuilder
				.buildPlayerNamePrompt("Player 1")));
		model.player1.setColor(PlayerColors.RED);
		model.player2.setName(InputReader.readStringInput(PromptBuilder
				.buildPlayerNamePrompt("Player 2")));
		model.player2.setColor(PlayerColors.BLACK);

		// columns
		model.board.setColumns(Integer.parseInt(InputReader
				.readIntegerInput(PromptBuilder.buildColumnPrompt())));
		// rows
		model.board.setRows(Integer.parseInt(InputReader
				.readIntegerInput(PromptBuilder.buildRowPrompt())));
		// win count
		model.board.setWinCount(Integer.parseInt(InputReader
				.readIntegerInput(PromptBuilder.buildWinCountPrompt())));
	}

	/**
	 * Runs a while loop until the game is over. Then alert the user of the game
	 * result. Finally prompt the user to play again.
	 */
	public static void startGame() {
		boolean winnerFound = false;
		boolean isStalemate = false;

		int turnCol;

		// everything is ready to go!
		System.out.println(PromptBuilder.buildGameStart());

		// generate and show the board
		model.board.generateBoard();
		model.board.displayBoard();

		// loop until we find a winner
		while (!winnerFound) {
			// set the last user to take their turn
			model.lastPlayer = (model.lastPlayer.getColor() != model.player1
					.getColor()) ? model.player1 : model.player2;

			// request a new turn
			// subtract one from turnCol to ensure we are adding to the correct
			// index of our array
			turnCol = requestTurn(model.lastPlayer) - 1;

			// execute turn
			Cell cell = model.board.addTurn(new Piece(model.lastPlayer,
					new Turn(turnCol)));

			if (cell != null) {
				// check to see if this was a game winning move
				winnerFound = model.board.isWinningMove(cell);
			} else {
				// alert the user that the turn was not accepted
				System.out.println(PromptBuilder.buildFailedTurn());
				// prompt the user again by setting the opposite player as last
				// player and letting the game loop continue
				model.lastPlayer = (model.lastPlayer.getColor() == model.player1
						.getColor()) ? model.player2 : model.player1;

				// check to see if a full board is the reason for failure.
				isStalemate = model.board.isFull();
				if (isStalemate) {
					// kill the game loop
					break;
				}
			}

			// display the board after every turn
			model.board.displayBoard();
		}
		// show the game result to the user
		showGameResult(isStalemate);

		// ask them if they want to replay
		promptForRestart();
	}

	/**
	 * Display the result of the game to the user.
	 * 
	 * @param isStalemate
	 */
	public static void showGameResult(boolean isStalemate) {
		if (isStalemate) {
			// notify that the game is over because of a stalemate
			System.out.println(PromptBuilder.buildStalemate());
		} else {
			// congratulate!
			System.out.println(PromptBuilder.buildCongratulations(ConnectModel
					.getInstance().lastPlayer.getName()));
		}
	}

	/**
	 * Prompt the user to choose to restart the game.
	 */
	public static void promptForRestart() {
		// prompt for game restart!
		String restart = InputReader.readStringInput(PromptBuilder
				.buildGameRestartPrompt());
		if (restart.equalsIgnoreCase("y") || restart.equalsIgnoreCase("yes")) {
			model.reset();
			initGame();
		} else if (restart.equalsIgnoreCase("n")
				|| restart.equalsIgnoreCase("no")) {
			// say thanks for playing
			System.out.println(PromptBuilder.buildThanksForPlaying());
		} else {
			// neither inputs found, ask again
			promptForRestart();
		}
	}

	/**
	 * Prompt the user for their next move.
	 * 
	 * @return
	 */
	public static int requestTurn(Player player) {
		return Integer.parseInt(InputReader.readIntegerInput(PromptBuilder
				.buildTurnPrompt(player.getName())));
	}
}
