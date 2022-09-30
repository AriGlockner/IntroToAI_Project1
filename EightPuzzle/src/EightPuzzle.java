import java.io.File;
import java.util.*;

public class EightPuzzle
{
	// The current state of this puzzle
	private String state;
	// The state that is the solved position
	private final static String goalState = "b12 345 678";
	// Number of nodes that can be searched
	private int maxNodes = 4;

	/**
	 * This class represents a state of the 8-puzzle. A state contains the state of the puzzle and the paths and
	 * directions to get to this state from the scrambled position when one of the search methods is called from
	 * the EightPuzzle class
	 */
	static class State
	{
		// Current state of this state
		private String state;
		// Path to get to this state from initial scrambled position
		private final LinkedList<String> pathToState;
		// Directions to get to the current state from the initial scrambled position
		private final LinkedList<Direction> directionsToState;
		private int distanceToSolved;
		//  = cost to reach current node + estimated cost to reach goal node
		private int aStarWeight;

		/**
		 * @param state current state of puzzle
		 */
		public State(String state)
		{
			this.state = state;
			pathToState = new LinkedList<>();
			directionsToState = new LinkedList<>();
			calculateAndSetDistanceToSolved();
		}

		/**
		 * @param state       current state of puzzle
		 * @param pathToState path to get to the current state of the puzzle
		 * @param directions  directions to get to the current state of the puzzle
		 */
		public State(String state, LinkedList<String> pathToState, LinkedList<Direction> directions)
		{
			this.state = state;
			this.pathToState = pathToState;
			directionsToState = directions;
			calculateAndSetDistanceToSolved();
		}

		/**
		 * Calculates and sets the distance to solved variable which is equal to the sum of the displacement of every
		 * tile. This is used in the compareTo method in this class which is used for the beam search method in the
		 * EightPuzzle class
		 */
		private void calculateAndSetDistanceToSolved()
		{
			// number of tiles to be moved to get to solved position if each tile ignored other tiles in its path
			distanceToSolved = 0;

			for (int i = 0; i < 11; i++)
			{
				char character = state.charAt(i);
				if (character != ' ')
				{
					int characterGoalLocation = EightPuzzle.goalState.indexOf(character);

					if (i != characterGoalLocation)
					{
						// Distance moved in left/right direction
						distanceToSolved += Math.abs((i % 4) - (characterGoalLocation % 4));

						// Distance moved in up/down direction
						distanceToSolved += Math.abs((i / 4) - (characterGoalLocation / 4));
					}
				}
			}

			aStarWeight = directionsToState.size() + distanceToSolved;
		}

		/**
		 * @param s state to duplicate
		 * @return a duplicate copy of the parameter s
		 */
		public static State clone(State s)
		{
			return new State(s.state, (LinkedList<String>) s.pathToState.clone(), (LinkedList<Direction>) s.directionsToState.clone());
		}

		/**
		 * @param direction Moves the blank tile in the puzzle in the direction of this parameter of either 'left',
		 *                  'right', 'up', or 'down'
		 * @return true if the move is successful or false if it cannot be moved in that direction
		 */
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
				swap(blankIndex, blankIndex - 1, Direction.left);

				// Swap right
			else if (direction.equals(Direction.right))
				swap(blankIndex, blankIndex + 1, Direction.right);

				// Swap up
			else if (direction.equals(Direction.up))
				swap(blankIndex, blankIndex - 4, Direction.up);

				// Swap down
			else
				swap(blankIndex, blankIndex + 4, Direction.down);

			return true;
		}

		/**
		 * @param direction direction to move blank tile in
		 * @return true if the puzzle can move in the direction specified, false otherwise
		 */
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

		/**
		 * Helper method that preforms swapping function for move method
		 *
		 * @param blankIndex    index where the blank tile is at
		 * @param newBlankIndex index where blank index is going to be
		 * @param direction     direction to get to current the next state
		 */
		private void swap(int blankIndex, int newBlankIndex, Direction direction)
		{
			directionsToState.add(direction);

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

		/**
		 * @param o the State to be compared.
		 * @return this state's sum of the number of moves for each tile to move to its goal state if the other tiles
		 * don't exist minus the other state's sum of the number of moves for each tile to move to its goal state if
		 * the other tiles don't exist
		 */
		public int compareToBeam(State o)
		{
			return distanceToSolved - o.distanceToSolved;
		}

		/**
		 * f(n) = g(n) + h(n)
		 *
		 * @param o the State to be compared.
		 * @return this state's sum of the number of moves for each tile to move to its goal state if the other tiles
		 * don't exist plus the number of moves to get to this state minus the other state's sum of the number of moves for each tile to move to its goal state if
		 * the other tiles don't exist plus the number of moves to get to the other state
		 */
		public int compareToAStar(State o)
		{
			return aStarWeight - o.aStarWeight;
		}
	}

	/**
	 * Reads the state from the file specified and constructs a new EightPuzzle
	 *
	 * @param file file to read
	 */
	public EightPuzzle(String file)
	{
		state = goalState;
		try
		{
			Scanner scanner = new Scanner(new File(file));

			while (scanner.hasNextLine())
			{
				// Get methods and parameters from input
				String line = scanner.nextLine();
				String methodName = line;
				if (line.contains(" "))
					methodName = line.substring(0, line.indexOf(' '));

				String methodParameters = line.substring(line.indexOf(' ') + 1);

				// Invoke methods
				switch (methodName)
				{
					case "setState":
						this.getClass().getMethod(methodName, String.class).invoke(this, methodParameters);
						break;
					case "printState":
					case "solveBeam":
						this.getClass().getMethod(methodName).invoke(this);
						break;
					case "solveA-star":
						this.getClass().getMethod("solveAStar").invoke(this);
						break;
					case "move":
						this.getClass().getMethod(methodName, Direction.class).invoke(this, Direction.valueOf(methodParameters));
						break;
					case "randomizeState":
					case "maxNodes":
						this.getClass().getMethod(methodName, int.class).invoke(this, Integer.parseInt(methodParameters));
				}
			}

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

	/**
	 * Print the current puzzle state
	 */
	public void printState()
	{
		System.out.println(state);
	}

	/**
	 * Direction of 'left', 'right', 'up', or 'down' to move blank tile towards
	 */
	public enum Direction
	{
		up, down, left, right
	}

	/**
	 * @param direction Moves the blank tile in the puzzle in the direction of this parameter of either 'left',
	 *                  'right', 'up', or 'down'
	 * @return true if the move is successful or false if it cannot be moved in that direction
	 */
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

	/**
	 * Make n random moves from the goal state.  Note that the goal state is not reachable from all
	 * puzzle states, so this method of randomizing the puzzle ensures that a solu8on exists.
	 *
	 * @param n makes n random moves from the goal state
	 */
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

			if (directionsAreNotOpposites(lastDirection, nextDirection) && move(nextDirection))
			{
				n--;
				lastDirection = nextDirection;
			}
		}
	}

	/**
	 * Used to help test same randomized state with both the beam and A* search methods
	 *
	 * @return current state of puzzle
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * Helper method for randomizeState method that makes sure that the next random direction does not undo the prior random direction
	 *
	 * @param a last direction
	 * @param b next direction
	 * @return true if a != the opposite of b or when a is null
	 */
	private boolean directionsAreNotOpposites(Direction a, Direction b)
	{
		if (a == null)
			return true;

		if (a.equals(Direction.left))
			return !b.equals(Direction.right);

		if (a.equals(Direction.right))
			return !b.equals(Direction.left);

		if (a.equals(Direction.up))
			return !b.equals(Direction.down);

		return !b.equals(Direction.up);
	}

	/**
	 * Solves the 8-puzzle using an A* algorithm
	 *
	 * @throws Exception if there is no such solution or if the number of moves exceeds the number of moves allowed to search
	 */
	public void solveAStar() throws Exception
	{
		// list of already encountered states
		HashSet<State> encountered = new HashSet<>();

		// Heap for determining best choice
		PriorityQueue<State> heap = new PriorityQueue<>(State::compareToAStar);
		heap.add(new State(state));

		// make sure program does not check too many states
		int nodesCounted = 0;

		//
		State bestState = null;

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

		State currentState = null;

		//
		while (heap.size() > 0 && nodesCounted < maxNodes)
		{
			currentState = heap.poll();

			// no faster paths can be reached
			if (currentState.pathToState.size() >= bestState.pathToState.size())
			{
				break;
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

		if (currentState != null)
		{
			System.out.println("Number of tiles moved: " + currentState.pathToState.size());

			for (Direction d : currentState.directionsToState)
				System.out.println(d);
			setState(goalState);
		}
	}

	/**
	 * Helper method for solveAStar that sets the current state as encountered and adds possible moves to the heap
	 *
	 * @param currentState current state of puzzle algorithm is searching
	 * @param encountered  list of states that have already been checked
	 * @param heap         next states to check
	 */
	private void aStarAddPaths(State currentState, HashSet<State> encountered, PriorityQueue<State> heap)
	{
		// Set the current state as encountered
		encountered.add(currentState);

		// left
		State left = State.clone(currentState);
		if (left.canMove(Direction.left))
		{
			left.pathToState.add(currentState.state);
			left.move(Direction.left);
			heap.add(left);
		}

		// right
		State right = State.clone(currentState);
		if (right.canMove(Direction.right))
		{
			right.pathToState.add(currentState.state);
			right.move(Direction.right);
			heap.add(right);
		}

		// up
		State up = State.clone(currentState);
		if (up.canMove(Direction.up))
		{
			up.pathToState.add(currentState.state);
			up.move(Direction.up);
			heap.add(up);
		}

		// down
		State down = State.clone(currentState);
		if (down.canMove(Direction.down))
		{
			down.pathToState.add(currentState.state);
			down.move(Direction.down);
			heap.add(down);
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
	public void BFS() throws Exception
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

			// If element has not already been checked
			if (!encountered.contains(currentState))
			{
				// If element to test is the goal state
				if (currentState.state.equals(goalState))
				{
					System.out.println("Number of tiles moved: " + currentState.pathToState.size());

					for (Direction d : currentState.directionsToState)
						System.out.println(d);

					setState(goalState);
					return;
				}

				nodesCounted++;

				// Set the current state as encountered
				encountered.add(currentState);

				// left
				State left = State.clone(currentState);
				if (left.canMove(Direction.left))
				{
					left.pathToState.add(currentState.state);
					left.move(Direction.left);
					queue.add(left);
				}

				// right
				State right = State.clone(currentState);
				if (right.canMove(Direction.right))
				{
					right.pathToState.add(currentState.state);
					right.move(Direction.right);
					queue.add(right);
				}

				// up
				State up = State.clone(currentState);
				if (up.canMove(Direction.up))
				{
					up.pathToState.add(currentState.state);
					up.move(Direction.up);
					queue.add(up);
				}

				// down
				State down = State.clone(currentState);
				if (down.canMove(Direction.down))
				{
					down.pathToState.add(currentState.state);
					down.move(Direction.down);
					queue.add(down);
				}
			}
		}

		throw new Exception("Either there are no solutions or the number of nodes searched has exceeded the maximum number that can be searched by this program");
	}

	/**
	 * Solve the puzzle from its current state by adapting local beam search with k states.  You will
	 * need to define an evaluation function which you should describe in your writeup.  It should
	 * have a  minimum of zero at the goal state. When the goal is found, your code
	 * should print the number of tile moves needed to obtain the solution followed by the solution as
	 * a sequences of moves (up, down, leI, or right) from the starting state to the goal state.
	 */
	public void solveBeam() throws Exception
	{
		// Number of nodes counted
		int numNodesCounted = 0;

		// Keep track of k states
		int k = (int) maxNodes;
		State[] states = new State[k];
		states[0] = new State(state);

		// Repeat keeping track of k states, generating successors for all k states, and keeping the k best states until
		// either solved or until the program has run more times than it is alloted to
		while (numNodesCounted < maxNodes)
		{
			// Heap to decide the k-best successors for all k states
			PriorityQueue<State> successors = new PriorityQueue<>(State::compareToBeam);

			// Add all possible successors to the successors heap except the last returning to the prior state
			for (State s : states)
			{
				if (s != null)
				{
					// If the current state is the goal state
					if (s.state.equals(goalState))
					{
						System.out.println("Number of tiles moved: " + s.pathToState.size());

						for (Direction d : s.directionsToState)
							System.out.println(d);
						setState(goalState);
						return;
					}

					State parent = null;
					if (s.pathToState.size() > 0)
						parent = new State(s.pathToState.getLast());

					// left successor
					State left = State.clone(s);
					if (left.canMove(Direction.left))
					{
						left.pathToState.add(s.state);
						left.move(Direction.left);
						// Add successor to heap of successors if this isn't undoing the prior move
						if (parent == null || !parent.equals(left))
							successors.add(left);
					}

					// right successor
					State right = State.clone(s);
					if (right.canMove(Direction.right))
					{
						right.pathToState.add(s.state);
						right.move(Direction.right);
						// Add successor to heap of successors if this isn't undoing the prior move
						if (parent == null || !parent.equals(right))
							successors.add(right);
					}

					// up successor
					State up = State.clone(s);
					if (up.canMove(Direction.up))
					{
						up.pathToState.add(s.state);
						up.move(Direction.up);
						// Add successor to heap of successors if this isn't undoing the prior move
						if (parent == null || !parent.equals(up))
							successors.add(up);
					}

					// down successor
					State down = State.clone(s);
					if (down.canMove(Direction.down))
					{
						down.pathToState.add(s.state);
						down.move(Direction.down);
						// Add successor to heap of successors if this isn't undoing the prior move
						if (parent == null || !parent.equals(down))
							successors.add(down);
					}
				}
				else
					break;
			}

			// Store the k best states in the array
			states = new State[k];
			for (int i = 0; i < k; i++)
				states[i] = successors.poll();
		}

		//throw new Exception("Either there are no solutions or the number of nodes searched has exceeded the maximum number that can be searched by this program");
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
		//EightPuzzle puzzle = new EightPuzzle("C:\\Users\\ari\\git\\CSDS391-P1\\out\\production\\EightPuzzle\\EightPuzzle1.txt");

		/*
		//puzzle.setState("432 175 6b8");

		//puzzle.setState("142 3b5 678");

		puzzle.randomizeState(10);

		try
		{
			puzzle.solveBeam();
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		/*
		puzzle.randomizeState(15);

		//puzzle.setState("12b 345 678");

		//puzzle.printState();

		//puzzle.solveBeam();

		System.out.println("Shuffled:");
		puzzle.printState();

		try
		{
			//puzzle.BFS();
			puzzle.solveAStar();
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		//puzzle.printState();
		 */

		/*
		State state1 = new State("b12 345 678");
		System.out.println(state1.state + ": " + state1.aStarWeight);
		State state2 = new State("125 b48 367");
		System.out.println(state2.state + ": " + state2.aStarWeight);
		State state3 = new State("432 175 6b8");
		System.out.println(state3.state + ": " + state3.aStarWeight);
		 */
	}
}