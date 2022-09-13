import java.io.File;
import java.util.*;

public class EightPuzzle
{
	private String state;
	private final String goalState = "b12 345 678";
	private int maxNodes = Integer.MAX_VALUE;

	static class State
	{
		private String state;
		private final LinkedList<String> pathToState;

		public State(String state)
		{
			this.state = state;
			pathToState = new LinkedList<>();
			//pathToState.add(state);
		}

		public State(String state, LinkedList<String> pathToState)
		{
			this.state = state;
			this.pathToState = pathToState;
			if (pathToState.size() == 0 || !pathToState.getLast().equals(state))
				this.pathToState.add(state);
		}

		public State clone()
		{
			return new State(state, pathToState);
		}


		@Override
		public boolean equals(Object o)
		{
			if (o instanceof State)
				return state.equals(((State) o).state);
			return false;
		}
	}

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
	 *                 in the left tile, nothing in the middle tile and 5 in the right tile
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

	public enum Direction
	{
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

	private State getMove(State state, Direction direction)
	{
		// where blank is at in puzzle
		int blankIndex = state.state.indexOf('b');

		// Unable to move left
		if (direction.equals(Direction.left) && blankIndex % 4 == 0)
			return null;
		// Unable to move right
		if (direction.equals(Direction.right) && blankIndex % 4 == 2)
			return null;
		// Unable to move up
		if (direction.equals(Direction.up) && blankIndex < 3)
			return null;
		// Unable to move down
		if (direction.equals(Direction.down) && blankIndex > 7)
			return null;

		// Swap left
		if (direction.equals(Direction.left))
			stateSwap(state, blankIndex, blankIndex - 1);

			// Swap right
		else if (direction.equals(Direction.right))
			stateSwap(state, blankIndex, blankIndex + 1);

			// Swap up
		else if (direction.equals(Direction.up))
			stateSwap(state, blankIndex, blankIndex - 4);

			// Swap down
		else
			stateSwap(state, blankIndex, blankIndex + 4);

		return state;
	}

	/**
	 * Helper method that preforms swapping function for move method
	 *
	 * @param blankIndex    index where the blank tile is at
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

	private void stateSwap(State state, int blankIndex, int newBlankIndex)
	{
		// swap
		char[] stateArray = state.state.toCharArray();
		stateArray[blankIndex] = stateArray[newBlankIndex];
		stateArray[newBlankIndex] = 'b';

		// convert array to String
		StringBuilder stringBuilder = new StringBuilder();
		for (char c : stateArray)
			stringBuilder.append(c);
		state.state = stringBuilder.substring(0, 11);
	}

	//TODO: Find a way to make sure random state cannot undo the prior move
	public void randomizeState(int n)
	{
		// resets state
		setState(goalState);

		Random random = new Random();
		Direction[] directions = new Direction[] {Direction.up, Direction.down, Direction.right, Direction.left};
		Direction lastDirection = null;

		while (n > 0)
		{
			Direction nextDirection = directions[random.nextInt(4)];
			//boolean notReturningToPreviousState =

			if (/*lastDirection != nextDirection && */move(nextDirection))
			{
				n--;
				lastDirection = nextDirection;
			}
		}
	}

	public void solveAStar()
	{
		// Initialize Open List
		List<State> openList = new ArrayList<>(); // TODO: maybe change type of list

		// Initialize Closed List and put starting node on open list
		List<State> closedList = new ArrayList<>(); // TODO: maybe change type of list

		// put starting node at 0
		openList.add(new State(state));

		// while Open List is not empty
		while (!openList.isEmpty())
		{
			// Find the node with the least f on the open list, call it q
		}
	}

	public void solveBeam()
	{


		// List of States that have already been encountered
		HashSet<State> encountered = new HashSet<>();

		// List of future states to check
		Queue<State> nextStates = new ArrayDeque<>();
		nextStates.offer(new State(state));

		//
		int nodesCounted = 0;

		while (nodesCounted < maxNodes && !nextStates.isEmpty())
		{
			State testState = nextStates.remove();

			// make sure testState has not already been checked
			if (!encountered.contains(testState))
			{
				nodesCounted++;

				// testState is the destination
				if (testState.state.equals(goalState))
				{
					System.out.println("Success!\nNumber of nodes counted is: " + nodesCounted);
					System.out.println("Path is:");
					System.out.println(goalState);
					for (String s : testState.pathToState)
						System.out.println(s);
					return;
				}

				// testState is not the destination
				// Set testState as encountered
				encountered.add(testState);

				// add possible moves
				// left
				State move = getMove(testState.clone(), Direction.left);
				if (move != null)
					nextStates.add(move);

				// right
				move = getMove(testState.clone(), Direction.right);
				if (move != null)
					nextStates.add(move);

				// up
				move = getMove(testState.clone(), Direction.up);
				if (move != null)
					nextStates.add(move);

				// down
				move = getMove(testState.clone(), Direction.down);
				if (move != null)
					nextStates.add(move);
			}
		}
	}

	public void BFS()
	{
		// list of already encountered states
		HashSet<State> encountered = new HashSet<>();

		// Queue
		Queue<State> queue = new LinkedList<>();
		queue.add(new State(state));

		// make sure program does not check too many states
		int nodesCounted = 0;


		while (queue.size() > 0 && nodesCounted < maxNodes)
		{
			// remove 1st element from queue to test
			State currentState = queue.poll();

			// If element to test is the goal state
			if (currentState.state.equals(goalState))
			{
				System.out.println(currentState.state + " = " + goalState);
				System.out.println("Success!");
				System.out.println("Path is:");
				for (String s : currentState.pathToState)
					System.out.println(s);
				System.out.println(currentState.state);
				return;
			}

			// If element has not already been checked
			if (!encountered.contains(currentState))
			{
				nodesCounted++;
				encountered.add(currentState);

				State s;

				// Add left if possible
				State l = getMove(currentState.clone(), Direction.left);
				if (l != null)
					queue.offer(l);

				// Add right if possible
				State r = getMove(currentState.clone(), Direction.right);
				if (r != null)
					queue.offer(r);

				// Add up if possible
				State u = getMove(currentState.clone(), Direction.up);
				if (u != null)
					queue.offer(u);

				// Add down if possible
				State d = getMove(currentState.clone(), Direction.down);
				if (d != null)
					queue.offer(d);
			}
		}

		// Error
	}

	/**
	 * Specifies the maximum number of nodes to be considered during a search.  If this limit is
	 * exceeded during search an error message should be printed
	 *
	 * @param newMaxNodes maximum number of nodes to be considered during a search
	 */
	public void maxNodes(int newMaxNodes)
	{
		maxNodes = newMaxNodes;
	}


	public static void main(String[] args)
	{
		EightPuzzle puzzle = new EightPuzzle("C:\\Users\\ari\\git\\CSDS391-P1\\out\\production\\EightPuzzle\\EightPuzzle1.txt");

		//puzzle.randomizeState(10);

		puzzle.setState("12b 345 678");

		puzzle.printState();

		//puzzle.solveBeam();

		puzzle.BFS();

		//puzzle.printState();
	}
}