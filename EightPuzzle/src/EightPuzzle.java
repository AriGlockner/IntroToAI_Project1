import java.io.File;
import java.util.*;

public class EightPuzzle
{

	// state of puzzle
	private String state;
	private final String solvedState = "b12 345 678";

	/**
	 * Creates a new EightPuzzle that reads from a file
	 * @param file file to read to set initial state
	 */
	public EightPuzzle(String file)
	{
		try
		{
			state = new Scanner(new File(file)).nextLine().substring(0, 11);
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	// Possible directions to move
	enum move
	{
		up, down, left, right
	}

	/**
	 * Sets the puzzle's state.
	 *
	 * @param state specifies the puzzle tile positions with a sequence of 3 groups of 3 digits with the blank tile
	 *              represented by the letter 'b'. For example, '1b5' specifies a row with 1 in the left tile, nothing in the middle, and 5 in the right tile. The goal state is "b12 345 678"
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return true if puzzle is solved
	 */
	private boolean isSolved()
	{
		return state.equals(solvedState);
	}

	/**
	 * Prints the current puzzle state
	 */
	public void printState()
	{
		System.out.println(state);
	}

	/**
	 * @return state of puzzle
	 */
	@Override
	public String toString()
	{
		return state;
	}

	/**
	 * Move the blank tile 'up', 'down', 'left', or 'right'
	 *
	 * @param direction
	 * @return
	 */
	public boolean move(Enum direction)
	{
		return false;
	}

	/**
	 * Makes n random moves from the goal state. Note that the goal state is not reachable from all puzzle states,
	 * so this method of randomizing the puzzle ensures that a solution exists
	 *
	 * @param n number of random moves to make
	 */
	public void randomizeState(int n)
	{
		while (n > 0)
			if (move(move.up)) // replace move.up with a random direction
				n--;
	}


	/**
	 * Solve the puzzle from its current state using A-star search using heuristic equal to "h1" or "h2" (see section
	 * 3.6, p. 102). Briefly, h1 is the number of misplaced tiles; h2 is the sum of the distances of the tiles from
	 * their goal positions. You are free to try other heuristics, but be sure that they are admissible and describe
	 * them in your writeup. When the goal is found, your code should print the number of tile moves needed to obtain
	 * the solution followed by the solution as a sequence of moves (up, down, left, right) from the starting state to
	 * the goal state
	 */
	public void solveAStar()
	{

	}

	/**
	 * Solve the puzzle from its current state by adapting local beam search with k states. You will need to define an
	 * evaluation function which you should describe in your writeup. It should have a minimum of zero at the goal
	 * state. When the goal is found, print the number of tile moves and solution as for A-star search
	 *
	 * @param k number of states
	 */
	public void solveBeam(int k)
	{

	}

	/**
	 * Specifies the maximum number of nodes to be considered during a search. If this limit is exceeded during a search
	 * an error message should be printed
	 *
	 * @param n number of nodes to search
	 */
	public void maxNodes(int n)
	{

	}

	public static void main(String[] args)
	{
		EightPuzzle puzzle = new EightPuzzle("C:\\Users\\ari\\git\\CSDS391-P1\\out\\production\\EightPuzzle\\EightPuzzle1.txt");
		System.out.println(puzzle + " " + puzzle.isSolved());
		puzzle.setState("b12 345 678");
		System.out.println(puzzle + " " + puzzle.isSolved());
	}
}