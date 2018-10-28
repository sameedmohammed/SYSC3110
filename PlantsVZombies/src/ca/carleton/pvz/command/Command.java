package ca.carleton.pvz.command;

/**
 * This class stores the tokens in a given command.
 *
 */
public class Command {

	private String commandWord;
	private String secondWord;
	private String thirdWord;
	private String fourthWord;

	/**
	 * Create a command object. 1st, 2nd, 3rd and 4th words must be supplied, but
	 * any (or all) can be null.
	 * 
	 * @param firstWord  The first word of the command. Null if the command was not
	 *                   recognized.
	 * @param secondWord The second word of the command.
	 * @param thirdWord  The third word of the command.
	 * @param fourthWord The fourth word of the command.
	 */
	public Command(String firstWord, String secondWord, String thirdWord, String fourthWord) {
		commandWord = firstWord;
		this.secondWord = secondWord;
		this.thirdWord = thirdWord;
		this.fourthWord = fourthWord;
	}

	/**
	 * Return the command word (the first word) of this command. If the command was
	 * not understood, the result is null.
	 * 
	 * @return The command word, or null if not understood.
	 */
	public String getCommandWord() {
		return commandWord;
	}

	/**
	 * Returns the second word of the command. Returns null if there was no second
	 * word.
	 * 
	 * @return The second word of this command, or null if only one word.
	 */
	public String getSecondWord() {
		return secondWord;
	}

	/**
	 * Returns the third word of the command. Returns null if there was no third
	 * word.
	 * 
	 * @return The third word of this command, or null if only two words.
	 */
	public String getThirdWord() {
		return thirdWord;
	}

	/**
	 * Returns the fourth word of the command. Returns null if there was no fourth
	 * word.
	 * 
	 * @return The fourth word of this command, or null if only three words.
	 */
	public String getFourthWord() {
		return fourthWord;
	}

	/**
	 * Returns true if the command is unknown.
	 * 
	 * @return true if this command was not understood, false otherwise.
	 */
	public boolean isUnknown() {
		return commandWord == null;
	}

	/**
	 * Returns true if the command has a second word.
	 * 
	 * @return true if the command has a second word, false otherwise.
	 */
	public boolean hasSecondWord() {
		return secondWord != null;
	}

	/**
	 * Returns true if the command has a third word.
	 * 
	 * @return true if the command has a third word, false otherwise.
	 */
	public boolean hasThirdWord() {
		return thirdWord != null;
	}

	/**
	 * Returns true if the command has a fourth word.
	 * 
	 * @return true if the command has a fourth word, false otherwise.
	 */
	public boolean hasFourthWord() {
		return fourthWord != null;
	}
}
