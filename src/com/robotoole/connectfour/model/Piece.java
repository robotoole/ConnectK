package com.robotoole.connectfour.model;

/**
 * Each piece is aware of the move that brought it in to the game and the player
 * that put it there.
 * 
 * @author robert
 * 
 */
public class Piece {
	public Piece(Player player, Turn turn) {
		this.player = player;
		this.turn = turn;
	}

	private Player player;
	private Turn turn;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Turn getTurn() {
		return turn;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}
}
