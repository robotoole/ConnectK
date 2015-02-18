package com.robotoole.connectfour.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.robotoole.connectfour.constants.Colors.PlayerColors;

/**
 * The main brain of the game. The board holds the important data as well as
 * processes the game Constraints.
 * 
 * @author robert
 * 
 */
public class Board {
	private int rows;
	private int columns;
	/**
	 * The number of consecutive pieces required to win.
	 */
	private int k;

	/**
	 * Grid used to track the board.
	 */
	private Cell[][] cells;

	/**
	 * List of moves that could be "undone" to play the game backwards.
	 */
	private List<Cell> moves;

	/**
	 * Instantiates the board array.
	 */
	public void generateBoard() {
		moves = new ArrayList<Cell>();
		cells = new Cell[rows][columns];
	}

	/**
	 * Builds out the board and prints each row as well as a footer for easier
	 * viewing
	 */
	public void displayBoard() {
		String display = "";
		// loop through each row, starting from the top since we're drawing
		for (int x = rows - 1; x >= 0; x--) {
			for (int y = 0; y < columns; y++) {
				if (cells[x][y] == null) {
					// build an empty cell
					display += new Cell().buildCell();
				} else {
					// show a populated cell
					display += cells[x][y].buildCell();
				}
			}

			// show the current row
			System.out.println(display);

			// clear it for the next output
			display = "";

			// check to see if we're at the last row to show the footer
			// footer won't work on large data sets > 230 columns :(
			if (x == 0) {
				printFooter();
			}
		}
	}

	/**
	 * Try and add the piece to the board.
	 * 
	 * @param piece
	 * @return Cell containing the piece and it's location.
	 */
	public Cell addTurn(Piece piece) {
		int column = piece.getTurn().getColumn();
		// first things first, stop it if the column doesn't exist
		if (column >= getColumns() || column == -1) {
			return null;
		}

		// get the next open slot
		int rowSlot = getNextAvailabileSlot(piece.getTurn().getColumn());
		if (rowSlot > -1) {
			addPiece(piece, rowSlot);
		} else {
			// if there isn't one, return null
			return null;
		}

		Cell move = new Cell(piece, rowSlot, piece.getTurn().getColumn());
		moves.add(move);

		return move;
	}

	/**
	 * From top down, check to see if there is an empty slot available and where
	 * the next available row is.
	 * 
	 * @param col
	 */
	public int getNextAvailabileSlot(int col) {
		int rowSlot = -1;
		for (int i = rows - 1; i >= 0; i--) {

			if (cells[i][col] == null) {
				// set rowSlot equal to the last empty cell we find.
				rowSlot = i;
			} else {
				// we will find available slots first. if we find a populated
				// slot, we have found all that we are going to find.
				break;
			}
		}

		return rowSlot;
	}

	/**
	 * Check all the cells to ensure we have an open slot
	 * 
	 * @return
	 */
	public boolean isFull() {
		boolean isFull = true;
		for (int x = rows - 1; x >= 0; x--) {
			for (int y = 0; y < columns; y++) {
				if (cells[x][y] == null) {
					isFull = false;
					break;
				}
			}
		}
		return isFull;
	}

	/**
	 * Create a cell object and insert it into the appropriate position
	 * 
	 * @param piece
	 * @param openSlot
	 */
	public void addPiece(Piece piece, int openSlot) {
		Cell cell = new Cell(piece, piece.getTurn().getColumn(), openSlot);
		cells[openSlot][piece.getTurn().getColumn()] = cell;
	}

	/**
	 * Check for winning combination of either vertical, horizontal, or diagonal
	 * connections.
	 * 
	 * @param location
	 * @param color
	 * @return
	 */
	public boolean isWinningMove(Cell cell) {
		boolean isWinner = false;

		Point location = new Point(cell.getLocationX(), cell.getLocationY());
		PlayerColors color = cell.getPiece().getPlayer().getColor();

		isWinner = checkVerticalWin(location, color);
		if (!isWinner) {
			isWinner = checkHorizontalWin(location, color);
		}

		if (!isWinner) {
			isWinner = checkDiagonalWin(location, color);
		}
		return isWinner;
	}

	/**
	 * Checks the column for a vertical win. The algorithm starts from the
	 * insertion point and works down until it finds a non matching cell or the
	 * end of the column.
	 * 
	 * @param y
	 * @param color
	 * @return
	 */
	public boolean checkVerticalWin(Point location, PlayerColors color) {
		int consecutivePieces = 1;
		boolean isWinner = false;

		int x = location.x;
		int y = location.y;

		// check inside the column for a vertical win
		// start from the row of insertion
		for (; x > 0; x--) {
			if (isMatch(cells[x][y], color)) {
				consecutivePieces++;

				if (checkConnects(consecutivePieces)) {
					return true;
				}
			} else {
				// we got to the first non match
				// no need to keep going.
				break;
			}
		}

		return isWinner;
	}

	/**
	 * Checks the row for a horizontal win. brute force, not optimized.
	 * 
	 * @param x
	 * @param color
	 * @return
	 */
	public boolean checkHorizontalWin(Point location, PlayerColors color) {
		int consecutivePieces = 1;

		// check to the left
		int x = location.x;
		int y = location.y - 1;
		while (y >= 0) {
			if (isMatch(cells[x][y], color)) {
				consecutivePieces++;
				// keep going down and to the left
				y--;
			} else {
				// break the loop
				break;
			}
		}

		if (checkConnects(consecutivePieces)) {
			return true;
		}

		// check to the right
		x = location.x;
		y = location.y + 1;
		// go up to the right
		while (y < getColumns()) {
			if (isMatch(cells[x][y], color)) {
				consecutivePieces++;
				// keep going up and to the right
				y++;
			} else {
				// break the loop
				break;
			}
		}

		// check to see if we found the winner
		if (checkConnects(consecutivePieces)) {
			return true;
		}

		return false;
	}

	/**
	 * Checks the board for a diagonal win in all four directions.
	 * 
	 * @param y
	 * @param color
	 * @return
	 */
	public boolean checkDiagonalWin(Point location, PlayerColors color) {
		int consecutivePieces = 1;

		// bottom left to top right
		int x = location.x - 1;
		int y = location.y - 1;
		// check the down angle left
		while (x >= 0 && y >= 0) {
			if (isMatch(cells[x][y], color)) {
				consecutivePieces++;
				// keep going down and to the left
				x--;
				y--;
			} else {
				break;
			}
		}

		if (checkConnects(consecutivePieces)) {
			return true;
		}

		x = location.x + 1;
		y = location.y + 1;
		// go up to the right
		while (x < getRows() && y < getColumns()) {
			if (isMatch(cells[x][y], color)) {
				consecutivePieces++;
				// keep going up and to the right
				x++;
				y++;
			} else {
				break;
			}
		}

		// check to see if we found the winner
		if (checkConnects(consecutivePieces)) {
			return true;
		}

		// reset for the new angle
		consecutivePieces = 1;

		// check up to top left
		x = location.x + 1;
		y = location.y - 1;
		// up and left
		while (x < getRows() && y >= 0) {
			if (isMatch(cells[x][y], color)) {
				consecutivePieces++;
				// keep going left and down
				x++;
				y--;
			} else {
				break;
			}
		}

		if (checkConnects(consecutivePieces)) {
			return true;
		}

		// check down to bottom right
		x = location.x - 1;
		y = location.y + 1;
		while (x >= 0 && y < getColumns()) {
			if (isMatch(cells[x][y], color)) {
				consecutivePieces++;
				x--;
				y++;
			} else {
				break;
			}
		}
		if (checkConnects(consecutivePieces)) {
			return true;
		}

		return false;
	}

	/**
	 * Check to see if the two colors are matching.
	 * 
	 * @param pieceColor
	 * @param playerColor
	 * @return
	 */
	public boolean isMatch(Cell cell, PlayerColors playerColor) {
		if (cell != null
				&& cell.getPiece().getPlayer().getColor() == playerColor) {
			return true;
		}

		return false;
	}

	/**
	 * Helper method to check consecutive pieces against the goal.
	 * 
	 * @param consecutivePieces
	 * @return
	 */
	public boolean checkConnects(int consecutivePieces) {
		if (consecutivePieces >= k) {
			return true;
		}
		return false;
	}

	/**
	 * Print the footer at the bottom of the board.
	 */
	public void printFooter() {
		String separator = "";
		String display = "";

		for (int y = 0; y < columns; y++) {
			display += new Cell().buildCellFooter((y + 1) + "");
			separator += "=====";
		}

		System.out.println(separator);
		System.out.println(display);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public List<Cell> getMoves() {
		return moves;
	}

	public void setMoves(List<Cell> movesList) {
		this.moves = movesList;
	}

	public int getWinCount() {
		return k;
	}

	public void setWinCount(int winCount) {
		this.k = winCount;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
}
