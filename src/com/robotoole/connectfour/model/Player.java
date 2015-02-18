package com.robotoole.connectfour.model;

import com.robotoole.connectfour.constants.Colors.PlayerColors;

/**
 * Player model.
 * 
 * @author robert
 * 
 */
public class Player {
	private PlayerColors color;
	private String name;

	public PlayerColors getColor() {
		return color;
	}

	public void setColor(PlayerColors color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
