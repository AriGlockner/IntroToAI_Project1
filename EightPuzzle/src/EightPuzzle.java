import java.io.File;
import java.util.*;

public class EightPuzzle
{
	private String state;

	public EightPuzzle(String file)
	{
		try
		{
			setState(new Scanner(new File(file)).nextLine().substring(0, 11));

		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sets the puzzle state
	 *
	 * @param newState 'b' represents blank and numbers represent numbers. For example, '1b5' specifies a row with 1
	 * in the left tile, nothing in the middle tile and 5 in the right tile
	 */
	public void setState(String newState)
	{
		state = newState;
	}

	public void printState()
	{
		/*
		StringBuilder stringBuilder = new StringBuilder();
		for (char[] c1 : state)
		{
			for (char c2 : c1)
				stringBuilder.append(c2);
			stringBuilder.append(" ");
		}
		System.out.println(stringBuilder.substring(0, 11));
		 */
		System.out.println(state);
	}

	//TODO: Remove when done
	@Override
	public String toString()
	{
		return state.replaceAll(" ", "\n");
	}

	public enum Direction {
		up, down, left, right
	}

	public boolean move(Direction direction)
	{
		// where blank is at in puzzle
		int blankIndex = state.indexOf('b');

		// Unable to move left
		if (direction.equals(Direction.left) && blankIndex % 4 == 0)
			return false;
		// Unable to move right
		if (direction.equals(Direction.right) && blankIndex % 4 == 2)
			return false;
		// Unable to move up
		if (direction.equals(Direction.up) && blankIndex < 3)
			return false;
		// Unable to move down
		if (direction.equals(Direction.down) && blankIndex > 7)
			return false;

		// Swap left
		if (direction.equals(Direction.left))
			swap(blankIndex, blankIndex - 1);

		// Swap right
		else if (direction.equals(Direction.right))
			swap(blankIndex, blankIndex + 1);

		// Swap up
		else if (direction.equals(Direction.up))
			swap(blankIndex, blankIndex - 4);

		// Swap down
		else
			swap(blankIndex, blankIndex + 4);

		return true;
	}

	/**
	 * Helper method that preforms swapping function for move method
	 *
 	 * @param blankIndex index where the blank tile is at
	 * @param newBlankIndex index where blank index is going to be
	 */
	private void swap(int blankIndex, int newBlankIndex)
	{
		// swap
		char[] stateArray = state.toCharArray();
		stateArray[blankIndex] = stateArray[newBlankIndex];
		stateArray[newBlankIndex] = 'b';

		// convert array to String
		StringBuilder stringBuilder = new StringBuilder();
		for (char c : stateArray)
			stringBuilder.append(c);
		state = stringBuilder.substring(0, 11);
	}

	public static void main(String[] args)
	{
		EightPuzzle puzzle = new EightPuzzle("C:\\Users\\ari\\git\\CSDS391-P1\\out\\production\\EightPuzzle\\EightPuzzle1.txt");
		//puzzle.printState();
		System.out.println(puzzle);
		System.out.println(puzzle.move(Direction.left) + ":\n" + puzzle);
		System.out.println(puzzle.move(Direction.right) + ":\n" + puzzle);
		System.out.println(puzzle.move(Direction.left) + ":\n" + puzzle);
		System.out.println(puzzle.move(Direction.up) + ":\n" + puzzle);
		System.out.println(puzzle.move(Direction.up) + ":\n" + puzzle);
		System.out.println(puzzle.move(Direction.down) + ":\n" + puzzle);
	}
}