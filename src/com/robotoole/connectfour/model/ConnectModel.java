package com.robotoole.connectfour.model;

public class ConnectModel {
	/**
	 * Singleton locker
	 */
	public static ConnectModel instance = null;

	public static ConnectModel getInstance() {
		if (instance == null) {
			instance = new ConnectModel();
		}
		return instance;
	}

	// players
	public Player player1 = new Player();
	public Player player2 = new Player();
	public Player lastPlayer = new Player();

	// game board
	public Board board = new Board();

	/**
	 * Reset the board.
	 */
	public void reset() {
		player1 = new Player();
		player2 = new Player();
		lastPlayer = new Player();
		board = new Board();
	}
}
