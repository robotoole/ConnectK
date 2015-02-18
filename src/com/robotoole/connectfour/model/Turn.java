package com.robotoole.connectfour.model;

/**
 * A Turn is a decision to place a piece.
 * @author robert
 *
 */
public class Turn {
	public Turn(int column) {
		this.column = column;
	}

	private int column;

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
