package com.robotoole.connectfour.constants;

public class Colors {
	public enum PlayerColors {
		RED("r"), BLACK("b");

		private String displayPiece;

		private PlayerColors(String displayPiece) {
			this.displayPiece = displayPiece;
		}

		public String getDisplayPiece() {
			return displayPiece;
		}
	}
}
