/**
 * This program solves different graph problems such as: number of paths with or 
 * without blocked points, through certain points on a graph, etc.
 * 
 * NOTES:
 * All grids up to [14 x 12] and [12 x 14] work for this because numbers start getting to large for a long variable
 * Grids are assigned as the following: [cols x rows]
 * 
 * TO COME:
 * 1) Number of paths given blocked points
 * 2) Number of paths within a 3-D plane
 * 
 * @author Austin
 *
 */

public class GraphTheory {

	public static void main(String[] args) {
		
		GraphTheory gt = new GraphTheory();
		System.out.println(gt.numOfPaths(0, 0, 4, 3));
		System.out.println(gt.pathsThrough(0, 0, 4, 3, 2, 2));
		
		int[] blockedPointsX = {2};
		int[] blockedPointsY = {2};
		System.out.println(gt.pathsAround(0, 0, 3, 4, blockedPointsX, blockedPointsY));
		
	}
	
	/** Determines the number of paths between two points on a graph without obstacles
	 * 
	 * @param startX = x-position of starting point
	 * @param startY = y-position of starting point
	 * @param destX = x-position of destination
	 * @param destY = y-position of destination
	 * @return 
	 */
	private long numOfPaths(int startX, int startY, int destX, int destY)
	{
		// Need number of steps to get from starting point to destination
		int steps = Math.abs(startX-destX) + Math.abs(startY-destY);
		int moves1 = Math.abs(startY-destY);
		int moves2 = Math.abs(startX-destX);
		
		/** If statement finds the largest amount of single moves and implements into combinations formula
		 *  - Helps when the number of cols is greater than the number of rows
		 */
		if(moves2 > moves1)
			return comb(steps, moves2);
		return comb(steps, moves1);
		
	}
	
	/** Calculates the factorial of a number
	 * 
	 * @param num = number to implement factorial with
	 * @return
	 */
	private long fact(int num)
	{
		int fact = 1;
		for(int i = 1; i <= num; i++)
			fact *= i;
		return fact;
	}
	
	/** Implements the combinations formula optimized to handle a graph up to [12 x 14] & [14 x 12]
	 * 
	 * @param n = total number of moves
	 * @param r = number of moves to reorder
	 * @return C(n, r)
	 */
	private long comb(int n, int r)
	{
		long fact = 1;
		for(int i = r+1; i <= n; i++)
			fact *= i;
		
		return fact / fact(n-r);
	}

	/** Determines the number of paths between two points on a graph going through a specific point
	 * 
	 * @param startX = x-pos of starting point
	 * @param startY = y-pos of starting point
	 * @param destX = x-pos of destination
	 * @param destY = y-pos of destination
	 * @param ptX = x-pos of point to go through
	 * @param ptY = y-pos of point to go through
	 * @return
	 */
	private long pathsThrough(int startX, int startY, int destX, int destY, int ptX, int ptY)
	{
		if(ptX > destX || ptY > destY)
		{
			System.out.println("Error: Given point is not a valid for the " + (destX-startX) + " x " + (destY-startY) + " graph");
			return 0;
		}
		// (number of paths to point) x (number of paths from point to destination)
		return numOfPaths(startX, startY, ptX, ptY) * numOfPaths(ptX, ptY, destX, destY);
	}

	/** Determines the number of paths between two points given "blocked" points (NOT DONE!!!)
	 * 
	 * @param startX = x-pos of start point
	 * @param startY = y-pos of start point
	 * @param destX = x-pos of destination
	 * @param destY = y-pos of destination
	 * @param ptX = array of x-pos' of blocked points
	 * @param ptY = array of y-pos' of blocked points
	 * @return
	 */
	private int pathsAround(int startX, int startY, int destX, int destY, int[] ptX, int[] ptY)
	{
		if(ptX.length != ptY.length) {
			System.out.println("");
		}
		return 0;
	}
}
