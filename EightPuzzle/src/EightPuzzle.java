import java.io.File;
import java.util.*;

public class EightPuzzle
{

	private char[][] state = new char[3][];

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
		state[0] = new char[] {newState.charAt(0), newState.charAt(1), newState.charAt(2)};
		state[1] = new char[] {newState.charAt(4), newState.charAt(5), newState.charAt(6)};
		state[2] = new char[] {newState.charAt(8), newState.charAt(9), newState.charAt(10)};
	}

	public void printState()
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (char[] c1 : state)
		{
			for (char c2 : c1)
				stringBuilder.append(c2);
			stringBuilder.append(" ");
		}
		System.out.println(stringBuilder.substring(0, 11));
	}

	//TODO: Remove when done
	@Override
	public String toString()
	{
		return Arrays.toString(state[0]) + "\n" + Arrays.toString(state[1]) + "\n" + Arrays.toString(state[2]);
	}

	public static void main(String[] args)
	{
		EightPuzzle puzzle = new EightPuzzle("C:\\Users\\ari\\git\\CSDS391-P1\\out\\production\\EightPuzzle\\EightPuzzle1.txt");
		puzzle.printState();
		System.out.println(puzzle);
	}
}