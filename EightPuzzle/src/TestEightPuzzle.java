import org.junit.*;

public class TestEightPuzzle
{

	@Test
	public void testEightPuzzle() throws Exception
	{
		EightPuzzle puzzle = new EightPuzzle("C:\\Users\\ari\\git\\CSDS391-P1\\EightPuzzle\\src\\TestEightPuzzle.txt");

		// A* Large State Space
		puzzle.maxNodes(500);
	    puzzle.setState("257 18b 364");
		// Will not throw an exception
		puzzle.solveAStar();

		// Beam small state space
		puzzle.setState("753 64b 821");
		puzzle.maxNodes(10);
		// Will not throw an exception
		puzzle.solveBeam();

		// A* small state space
		puzzle.setState("753 64b 821");
		// Will throw an exception because cannot search that many nodes
		// When setting n as a really large value, sometimes solveA-star will not get a solution when the state space
		// becomes too large
		Assert.assertThrows(java.lang.Exception.class, puzzle::solveAStar);

		// Prove that puzzle will solve randomized state
		puzzle.maxNodes(5000);
	}
}
