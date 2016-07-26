/*
 * This program calculates the maximum sum of all sub-arrays from an array of integers.
 * Not only does the program find the max sum, but it aims to find the solution with a
 * runtime complexity of O(n). Therefore Kadane's algorithm is ideal for this problem.
 */

public class MaximumSubarray {

	public static void main(String[] args) {
		
		int[] array = {1, -2, 3, 10, -4, 7, 2, -5};
		int size = array.length;
		System.out.printf("The max sum of all sub-arrays is %d",maxSum(array, size));

	}
	
	static int maxSum(int[] array, int size)
	{
		int sum = 0; // this is the max sum
		int accSum = 0; // this is the accumulated sum for each sub-array
		for(int i = 0; i < size; i++)
		{
			accSum += array[i];
			if(accSum < 0)
				accSum = 0;
			if(sum < accSum)
				sum = accSum;
		}
		return sum;
	}
}
