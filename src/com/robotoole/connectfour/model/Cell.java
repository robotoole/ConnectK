package com.robotoole.connectfour.model;

/**
 * A square on the board. It has a location and sometimes it has a piece in it..
 * 
 * @author robert
 * 
 */
public class Cell {
	public Cell(Piece piece, int x, int y) {
		this.piece = piece;
		locationX = x;
		locationY = y;
	}

	public Cell() {
	}

	private Piece piece;
	private int locationX;
	private int locationY;

	/**
	 * Build a string for the cell when it's empty or populated
	 * 
	 * @return
	 */
	public String buildCell() {
		String cell = "";
		if (piece != null) {
			cell = "| " + (piece.getPlayer().getColor().getDisplayPiece())
					+ " |";
		} else {
			cell = "|   |";
		}
		return cell;
	}

	/**
	 * Numbered footer to ensure we have a clear display
	 * 
	 * @param colName
	 * @return
	 */
	public String buildCellFooter(String colName) {
		return "| " + colName + " |";
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}
}
