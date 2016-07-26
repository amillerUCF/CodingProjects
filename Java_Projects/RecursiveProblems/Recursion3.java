
public class Recursion3 
{

	//	Problem #1
	//	Given an array of ints, is it possible to choose a group 
	//	of some of the ints, such that the group sums to the given 
	//	target? This is a classic backtracking recursion problem. 
	//	Once you understand the recursive backtracking strategy in 
	//	this problem, you can use the same pattern for many problems to
	//	search a space of choices. Rather than looking at the whole array, 
	//	our convention is to consider the part of the array starting at 
	//	index start and continuing to the end of the array. The caller 
	//	can specify the whole array simply by passing start as 0. No loops 
	//	are needed -- the recursive calls progress down the array. 

	//	groupSumsTarget(0, {2, 4, 8}, 10) → true
	//	groupSumsTarget(0, {2, 4, 8}, 14) → true
	//	groupSumsTarget(0, {2, 4, 8}, 9) → false
	
	/**
	 * 
	 * @param start, nums, target
	 * 		int start tells you where to start in the array nums
	 * 		int[] nums is the given array
	 * 		int target is the value to which the group should sum to
	 * 
	 * @return
	 * 		returns true if there is a group that sums to target
	 * 		returns false if there is no group that sums to target
	 */
	static boolean groupSumsTarget(int start, int[] nums, int target) 
	{
		if(target == 0) // if the target is a sum in the group
			return true;
		if(start == nums.length) // backtracks in recursion when the end of array is hit
			return false;
		if(groupSumsTarget(start + 1, nums, target - nums[start])) // recursively goes to last element and subtracts numbers from the target
			return true;
		return groupSumsTarget(start + 1, nums, target); // adds the next number to the target and checks
	}

	//	Problem #2
	//	Given an array of ints, is it possible to choose a group of 
	//	some of the ints, beginning at the start index, such that 
	//	the group sums to the given target? However, with the additional 
	//	constraint that all 6's must be chosen. (No loops needed.)

	//	groupSumsTarget6(0, {5, 6, 2}, 8) → true
	//	groupSumsTarget6(0, {5, 6, 2}, 9) → false
	//	groupSumsTarget6(0, {5, 6, 2}, 7) → false
	
	/**
	 * 
	 * @param start, nums, target
	 * 		int start tells you where to start in the array nums
	 * 		int[] nums is the given array
	 * 		int target is the value to which the group should sum to
	 * 
	 * @return
	 * 		returns true if there is a group that sums to target including all 6's in the group
	 * 		returns false if there is no group that sums to target
	 */
	static boolean groupSumsTarget6(int start, int[] nums, int target) 
	{
		if(start == nums.length) // if start index is at the end of the array
	 	{
			if(target == 0)
				return true; // there is a group that sums to target
			return false;
	 	}
	 	if(nums[start] == 6)
	 		return groupSumsTarget6(start + 1, nums, target - nums[start]); // use the '6' to check if there is a sum
	 	if(groupSumsTarget6(start + 1, nums, target - nums[start])) // traverses the array
	 		return true;
	 	return groupSumsTarget6(start + 1, nums, target); // check with next number if the target == 0
	}	

	//	Problem #3
	//	Given an array of ints, is it possible to choose a group of some 
	//	of the ints, such that the group sums to the given target with this 
	//	additional constraint: If a value in the array is chosen to be in 
	//	the group, the value immediately following it in the array 
	//	must not be chosen. (No loops needed.)

	//	groupSumsTargetNoAdj(0, {2, 5, 10, 4}, 12) → true
	//	groupSumsTargetNoAdj(0, {2, 5, 10, 4}, 14) → false
	//	groupSumsTargetNoAdj(0, {2, 5, 10, 4}, 7) → false
	
	/**
	 * 
	 * @param start, nums, target
	 * 		int start tells you where to start in the array nums
	 * 		int[] nums is the given array
	 * 		int target is the value to which the group should sum to
	 * 
	 * @return
	 * 		returns true if there is a group that sums to target including the specified constraints
	 * 		returns false if there is no group that sums to target
	 */
	static boolean groupSumsTargetNoAdj(int start, int[] nums, int target) 
	{
		if(target == 0) // if there is a sum of the target
			return true;
		if(start >= nums.length) // if end of array was reached
		  	return false;
	    if(groupSumsTargetNoAdj(start + 2, nums, target - nums[start])) // skips every other number so there are no adjacent numbers
		  	return true;
		return groupSumsTargetNoAdj(start + 1, nums, target); // adds the number after the next one
	}	

	//	Problem #4
	//	Given an array of ints, is it possible to choose a group of some 
	//	of the ints, such that the group sums to the given target with these 
	//	additional constraints: all multiples of 5 in the array must be 
	//	included in the group. If the value immediately following a multiple 
	//	of 5 is 1, it must not be chosen. (No loops needed.) 

	//	groupSumsTarget5(0, {2, 5, 10, 4}, 19) → true
	//	groupSumsTarget5(0, {2, 5, 10, 4}, 17) → true
	//	groupSumsTarget5(0, {2, 5, 10, 4}, 12) → false
	
	/**
	 * 
	 * @param start, nums, target
	 * 		int start tells you where to start in the array nums
	 * 		int[] nums is the given array
	 * 		int target is the value to which the group should sum to
	 * 
	 * @return
	 * 		returns true if there is a group that sums to target including the specified constraints
	 * 		returns false if there is no group that sums to target
	 */
	static boolean groupSumsTarget5(int start, int[] nums, int target) 
	{
		if(start >= nums.length) // if at end of array or beyond
	 	{
			if(target == 0) // if there is a sum in the array
				return true;
			return false;
	 	}
	 	if(nums[start] % 5 == 0) // if the element is a multiple of '5'
	 	{
	 		if(start < nums.length - 1 && nums[start+1] == 1) // checks if the next number after the multiple of '5' is a '1'
		 		return groupSumsTarget5(start + 2, nums, target - nums[start]); // if the next number is a '1', then don't use in sum value
		 	return groupSumsTarget5(start + 1, nums, target - nums[start]); // use the '5' and the next number to check if a proper sum
	 	}
	 	if(groupSumsTarget5(start + 1, nums, target - nums[start])) // if not a '5' just check next number if there is a sum
	 		return true;
	 	return groupSumsTarget5(start + 1, nums, target); // adds the next number
	}
	
	//	Problem #5
	//	Given an array of ints, is it possible to choose a group of some of 
	//	the ints, such that the group sums to the given target, with this 
	//	additional constraint: if there are numbers in the array that are adjacent 
	//	and the identical value, they must either all be chosen, or none of 
	//	them chosen. For example, with the array {1, 2, 2, 2, 5, 2}, either all 
	//	three 2's in the middle must be chosen or not, all as a group. (one loop 
	//	can be used to find the extent of the identical values). 

	//	groupSumsTargetClump(0, {2, 4, 8}, 10) → true
	//	groupSumsTargetClump(0, {1, 2, 4, 8, 1}, 14) → true
	//	groupSumsTargetClump(0, {2, 4, 4, 8}, 14) → false	
	
	/**
	 * 
	 * @param start, nums, target
	 * 		int start tells you where to start in the array nums
	 * 		int[] nums is the given array
	 * 		int target is the value to which the group should sum to
	 * 
	 * @return
	 * 		returns true if there is a group that sums to target including the specified constraints
	 * 		returns false if there is no group that sums to target
	 */
	static boolean groupSumsTargetClump(int start, int[] nums, int target) 
	{
		if(start >= nums.length) // if at end of array or beyond 
	 	{
			if(target == 0) // if there is a sum possible
				return true;
			return false;
	 	}
	 	int i = start + 1; // i is the adjacent number's index
	 	for(;  i < nums.length && nums[start] == nums[i]; i++);
	 	if(groupSumsTargetClump(i, nums, target - ((i - start) * nums[start]))) // traverses to end of array and subtracts numbers from the target
	 		return true;
	 	return groupSumsTargetClump(i, nums, target); // checks if there is a sum equal to target using the next adjacent number
	}
	
	//	Problem #6
	//	Given an array of ints, is it possible to divide the ints into two 
	//	groups, so that the sums of the two groups are the same. Every int must 
	//	be in one group or the other. Write a recursive helper method that takes 
	//	whatever arguments you like, and make the initial call to your recursive 
	//	helper from splitArray(). (No loops needed.)    

	//	splitArray({2, 2}) → true
	//	splitArray({2, 3}) → false
	//	splitArray({5, 2, 3}) → true
	
	/**
	 * 
	 * @param nums
	 * 		int[] nums is the given array
	 * 
	 * @return 
	 * 		returns true if the array can be divided so that the constraints are met
	 * 		returns false if the array cannot be divided so that the constraints are met
	 */
	static boolean splitArray(int[] nums) 
	{
		return helperEqual(nums, 0, 0); // calls helper function
	}
	
	static boolean helperEqual(int[] nums, int i, int balance)
	{
		if(i == nums.length) // if the end of the array was reached
			return (balance == 0); // return true if bal == 0 or return false
		if(helperEqual(nums, i + 1, balance + nums[i])) // traverses to end of array
			return true;
		return helperEqual(nums, i + 1, balance - nums[i]); // checks balance ("bal") when the current number is subtracted from bal
	}
	
	//	Problem #7
	//	Given an array of ints, is it possible to divide the ints into two groups, 
	//	so that the sum of one group is a multiple of 10, and the sum of the 
	//	other group is odd. Every int must be in one group or the other. Write 
	//	a recursive helper method that takes whatever arguments you like, and 
	//	make the initial call to your recursive helper from 
	//	splitOdd10(). (No loops needed.)  

	//	oddDivide10({5, 5, 5}) → true
	//	oddDivide10({5, 5, 6}) → false
	//	oddDivide10({5, 5, 6, 1}) → true
	
	/**
	 * 
	 * @param nums
	 * 		int[] nums is the given array
	 * 
	 * @return 
	 * 		returns true if the array can be divided so that the constraints are met
	 * 		returns false if the array cannot be divided so that the constraints are met 
	 */
	static boolean oddDivide10(int[] nums) 
	{
		return helperOdd(nums, 0, 0, 0);
	}
	
	static boolean helperOdd(int[] nums, int i, int g1, int g2)
	{
		if(i == nums.length) // if i is at the end of the array
			return (((g1 % 2 == 1) && (g2 % 10 == 0)) || ((g2 % 2 == 1) && (g1 % 10 == 0))); // if this logical operation is true then there are two possible groups
		if(helperOdd(nums, i + 1, g1 + nums[i], g2)) // adds current i index to group 1 and checks. When true there are two possible groups
			return true; // there are two possible groups
		return helperOdd(nums, i + 1, g1, g2 + nums[i]); // adds current i index to group 2
	}
	
	//	Problem #8
	//	Given an array of ints, is it possible to divide the ints into 
	//	two groups, so that the sum of the two groups is the same, with 
	//	these constraints: all the values that are multiple of 5 must 
	//	be in one group, and all the values that are a multiple of 3 
	//	(and not a multiple of 5) must be in the other. (No loops needed.)  

	//	divide53({1,1}) → true
	//	divide53({1, 1, 1}) → false
	//	divide53({2, 4, 2}) → true
	
	/**
	 * 
	 * @param nums
	 * 		int[] nums is the given array
	 * 
	 * @return 
	 * 		returns true if the array can be divided so that the constraints are met
	 * 		returns false if the array cannot be divided so that the constraints are met
	 */
	static boolean divide53(int[] nums) 
	{
		return sidesEqual(nums, 0, 0);
	}

	static boolean sidesEqual(int[] nums, int i, int balance)
	{
		if(i == nums.length) // if index at end of array
			return (balance == 0); // return true if balance = 0
		if(nums[i] % 5 == 0) // if the current number is a multiple of 5
			return sidesEqual(nums, i + 1, balance + nums[i]); // adding 5's to one group to create a greater balance
		if(nums[i] % 3 == 0) // if the current number is a multiple of 3
			return sidesEqual(nums, i + 1, balance - nums[i]); // subtracting 3's to another group to creates a lesser balance
		if(sidesEqual(nums, i + 1, balance + nums[i])) // goes to end of array
			return true; 
		return sidesEqual(nums, i + 1, balance - nums[i]); // checks conditions with next index
	}
	
	// This is where the testing happens
	
	public static void main(String[] args)
	{
	}
	
}