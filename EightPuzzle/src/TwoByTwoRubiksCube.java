import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class TwoByTwoRubiksCube
{
	// The current state of this puzzle
	private String state;
	// The state that is the solved position
	private final static String goalState = "wwww bbbb oooo gggg rrrr yyyy";
	// Number of nodes that can be searched
	private int maxNodes = Integer.MAX_VALUE;

	public TwoByTwoRubiksCube()
	{
		state = goalState;
	}

	public enum Direction
	{
		up, down, right, rightPrime
	}

	/**
	 * This class represents a state of the 2x2x2 Rubik's Cube. A state contains the state of the puzzle and the paths and
	 * directions to get to this state from the scrambled position when one of the search methods is called from
	 * the TwoByTwoRubiksCube class
	 */
	public static class State
	{
		// This state's state
		String state;
		// Path to get to this state
		private LinkedList<State> pathToState;
		// Moves to get to this state
		private LinkedList<Direction> directionsToState;

		/**
		 * Instantiates an empty state with its current state as the only parameter
		 * @param state
		 */
		public State(String state)
		{
			this.state = state;
			pathToState = null;
			directionsToState = null;
		}

		public State(String state, LinkedList<State> pathToState, LinkedList<Direction> directionsToState)
		{
			this.state = state;
			this.pathToState = pathToState;
			this.directionsToState = directionsToState;
		}

	}


	/**
	 * Sets the puzzle state
	 *
	 * @param state represents the state of the puzzle. Colors can be white (w), blue (b), orange (o), green (g),
	 *                 red (r), or yellow (y). puzzle is solved when it is in the form "wwww bbbb oooo gggg rrrr yyyy"
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * Print the current puzzle state
	 */
	public void printState()
	{
		System.out.println(state);
	}

	/**
	 * Rotates a puzzle side in the direction specified
	 * @param direction direction to rotate a side of the cube in
	 */
	public void move(Direction direction)
	{

	}

	/**
	 * Make n random moves from the goal state.  Note that the goal state is not reachable from all
	 * puzzle states, so this method of randomizing the puzzle ensures that a solu8on exists.
	 *
	 * @param n makes n random moves from the goal state
	 */
	public void randomizeState(int n)
	{
		state = goalState;
		Direction[] directions = new Direction[] { Direction.down, Direction.up, Direction.right, Direction.rightPrime};

	}

	/*
	public void solveAStar()
	{
		// list of already encountered states
		HashSet<State> encountered = new HashSet<>();

		// Heap for determining best choice
		PriorityQueue<State> heap = new PriorityQueue<>(State::compareToAStar);
		heap.add(new State(state));

		// make sure program does not check too many states
		int nodesCounted = 0;

		//
		EightPuzzle.State bestState = null;

		while (heap.size() > 0 && nodesCounted < maxNodes)
		{
			// remove 1st element from queue to test
			State currentState = heap.poll();

			// If element has not already been checked
			if (!encountered.contains(currentState))
			{
				// If element to test is the goal state
				if (currentState.state.equals(goalState))
				{
					bestState = currentState;
					break;
				}

				nodesCounted++;

				// set the current state as encountered and add its directions it can move to into the heap
				aStarAddPaths(currentState, encountered, heap);
			}
		}

		// No such path to goal state
		if (bestState == null)
			throw new Exception("Either there are no solutions or the number of nodes searched has exceeded the maximum number that can be searched by this program");

		//
		while (heap.size() > 0 && nodesCounted < maxNodes)
		{
			State currentState = heap.poll();

			// no faster paths can be reached
			if (currentState.pathToState.size() >= bestState.pathToState.size())
			{
				System.out.println("Number of tiles moved: " + currentState.pathToState.size());

				for (EightPuzzle.Direction d : currentState.directionsToState)
					System.out.println(d);

				return;
			}

			// current state is goal state
			if (currentState.state.equals(goalState))
			{
				bestState = currentState;
				encountered.add(currentState);
			}

			// Add other possible outcomes to heap if it has the possibility of being faster than the current best route
			else if (currentState.compareToAStar(bestState) < 0)
				aStarAddPaths(currentState, encountered, heap);
		}
	}

	public void solveBeam()
	{

	}

	/**
	 * Sets the number of moves that can be done to get to the solved state
	 * @param maxNodes
	 */
	public void maxNodes(int maxNodes)
	{
		this.maxNodes = maxNodes;
	}

	public static void main(String[] args)
	{

	}
}
