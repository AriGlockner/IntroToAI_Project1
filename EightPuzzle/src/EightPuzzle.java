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
		private LinkedList<String> pathToState;

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
			//if (pathToState.size() == 0 || !pathToState.getLast().equals(state))
			//	this.pathToState.add(state);
		}

		public static State clone(State s)
		{
			//return new State(state, pathToState);
			/*
			State s = new State(state, pathToState);
			s.pathToState = this.pathToState;

			//System.out.println(state + ":\t" + pathToState);

			return s;
			 */
			return new State(s.state, (LinkedList<String>) s.pathToState.clone());
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

		private boolean canMove(Direction direction)
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
			return !direction.equals(Direction.down) || blankIndex <= 7;
		}

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

	/*
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
	*/
	// TODO: Error is that method will print out all states checked instead of
	// just printing out the states necessary to get to the solved state
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
				setState(goalState);
				return;
			}

			// If element has not already been checked
			if (!encountered.contains(currentState))
			{
				nodesCounted++;
				encountered.add(currentState);

				//System.out.println(currentState.state + ":\t" + currentState.pathToState);
				System.out.println(currentState.state);

				// TODO:
				// Objects should be cloned
				// Not all states should be printed out

				// left
				State left = currentState.clone(currentState);
				if (left.canMove(Direction.left))
				{
					left.pathToState.add(currentState.state);
					left.move(Direction.left);
					System.out.println("Left:\t" + left.state + ":\t" + left.pathToState);
					queue.add(left);
				}

				// right
				State right = currentState.clone(currentState);
				if (right.canMove(Direction.right))
				{
					right.pathToState.add(currentState.state);
					right.move(Direction.right);
					System.out.println("Right:\t" + right.state + ":\t" + right.pathToState);
					queue.add(right);
				}

				// up
				State up = currentState.clone(currentState);
				if (up.canMove(Direction.up))
				{
					up.pathToState.add(currentState.state);
					up.move(Direction.up);
					System.out.println("Up:\t" + up.state + ":\t" + up.pathToState);
					queue.add(up);
				}

				// down
				State down = currentState.clone(currentState);
				if (down.canMove(Direction.down))
				{
					down.pathToState.add(currentState.state);
					down.move(Direction.down);
					System.out.println("Down:\t" + down.state + ":\t" + down.pathToState);
					queue.add(down);
				}

				/*
				State left = currentState.clone();
				if (left.move(Direction.left))
					queue.add(left);

				State right = currentState.clone();
				if (right.move(Direction.right))
					queue.add(right);

				State up = currentState.clone();
				if (up.move(Direction.up))
					queue.add(up);

				State down = currentState.clone();
				if (down.move(Direction.down))
					queue.add(down);

				//System.out.println(currentState.state + ":\t" + left.state + "\t\t" + right.state + "\t\t" + up.state + "\t\t" + down.state);

				/*
				// Add left if possible
				State l = getMove(currentState.clone(), Direction.left);
				State r = getMove(currentState.clone(), Direction.right);
				State u = getMove(currentState.clone(), Direction.up);
				State d = getMove(currentState.clone(), Direction.down);

				// Add left if possible
				if (l != null)
					queue.offer(new State(l.state, currentState));

				// Add right if possible
				//State r = getMove(currentState.clone(), Direction.right);
				if (r != null)
					queue.offer(new State(r.state, currentState));

				// Add up if possible
				//State u = getMove(currentState.clone(), Direction.up);
				if (u != null)
					queue.offer(new State(u.state, currentState));

				// Add down if possible
				//State d = getMove(currentState.clone(), Direction.down);
				if (d != null)
					queue.offer(new State(d.state, currentState));
				 */
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

		//puzzle.printState();

		//puzzle.solveBeam();

		puzzle.BFS();

		//puzzle.printState();
	}
}