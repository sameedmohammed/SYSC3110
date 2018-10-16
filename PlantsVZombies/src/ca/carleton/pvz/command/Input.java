package ca.carleton.pvz.command;

import java.util.Scanner;

public class Input {
	private Scanner scanner;
	
	public Input() {
		scanner = new Scanner(System.in);
	}
	
	public String getInput() {
		return scanner.nextLine();
	}
}
