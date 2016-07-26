import java.util.Arrays;
import java.util.Scanner;

public class Recursion2
{
	
	// Problem #1
	// Directions: Return true if the array contains, somewhere,
	// three increasing consecutive numbers like ....4, 5, 6,... or
	// 23, 24, 25.

	//	FindThreeIncreasingNumbers({1, 4, 5, 6, 2}) → true
	//	FindThreeIncreasingNumbers({1, 2, 3}) → true
	//	FindThreeIncreasingNumbers({1, 2, 4}) → false
	
	/**
	 * 
	 * @param NumberList
	 * 		int[] list containing some numbers.
	 * 
	 * @return
	 * 		returns true if there are three increasing consecutive numbers
	 * 		returns false if there are not three increasing consecutive numbers
	 */
	static boolean FindThreeIncreasingNumbers(int[] NumberList) 
	{
		int count = 0; // counter for # of instances numbers are increasing
		for(int i = 0; i < NumberList.length - 1; i++) // does not iterate the last number in the array
		{
			if(NumberList[i+1] == NumberList[i]+1) // if the next int is 1 greater than the current int
			{	
				count++; // increment counter
				if(count == 2) // if there are 3 consecutive increasing numbers
					return true; // returns true
				continue; // go back to beginning of "for" loop
			}
			count = 0; // resets counter
		}
		return false; // returns false
	}

	//	Problem #2
	//	For each multiple of 10 in the given array, change all the values 
	//	following it to be that multiple of 10, until encountering another 
	//	multiple of 10. So {2, 10, 3, 4, 20, 5} yields {2, 10, 10, 10, 20, 20}.

	//	multiplesOfTen({2, 10, 3, 4, 20, 5}) → {2, 10, 10, 10, 20, 20}
	//	multiplesOfTen({10, 1, 20, 2}) → {10, 10, 20, 20}
	//	multiplesOfTen({10, 1, 9, 20}) → {10, 10, 10, 20}
	
	/**
	 * 
	 * @param NumberList
	 * 		int[] list containing some numbers.
	 * 
	 * @return NumberList
	 * 		int[] list of the same numbers changed to multiples of
	 * 		ten as they are encountered.
	 */
	static int[] multiplesOfTen(int[] NumberList) 
	{
		int duplicate = 0; // holds a dup of the current multiple of 10
		for(int i = 0; i < NumberList.length; i++) // iterates through all numbers in array
		{
			if(NumberList[i] % 10 == 0) // is the number is a multiple of 10
				duplicate = NumberList[i]; // set duplicate to the number that is a multiple of 10
			else
				if(duplicate > 0) // helps when the array starts with numbers that are not multiples of 10
					NumberList[i] = duplicate; // sets current number to previous multiple of ten
		}
		return NumberList; // returns the new array
	}	

	//	Problem #3
	//	We'll say that an element in an array is "alone" if there are 
	//	values before and after it, and those values are different 
	//	from it. Return a version of the given array where every instance 
	//	of the given value which is alone is replaced by whichever 
	//	value to its left or right is larger.

	//	CheckForAloneNumbers({1, 2, 3}, 2) → {1, 3, 3}
	//	CheckForAloneNumbers({1, 2, 3, 2, 5, 2}, 2) → {1, 3, 3, 5, 5, 2}
	//	CheckForAloneNumbers({3, 4}, 3) → {3, 4}
	
	/**
	 * 
	 * @param NumberList, changingNumber
	 * 		int[] list containing some numbers.
	 * 		int value of the number that should change in the array.
	 * 
	 * @return NumberList
	 * 		int[] list of numbers where every occurrence of changingNumber
	 * 		has been replaced by the larger of its two neighbors.
	 */
	static int[] CheckForAloneNumbers(int[] NumberList, int changingNumber) 
	{
		int change = 0;
		if(NumberList.length < 3) // if list is too small, then just return the array
			return NumberList;
		
		for(int i = 0; i < NumberList.length-1; i++) // iterates through array; leaving out the first and last elements since there is no number before or after it
		{
			if(NumberList[i] == changingNumber) // if the number is equal to the number that is changing
			{
				if(NumberList[i-1] > NumberList[i+1]) // if the number before the current is greater than the one after
					change = NumberList[i-1];
				else
					change = NumberList[i+1];
				NumberList[i] = change;
			} 
			change = 0;
		}
		return NumberList;
	}	

	//	Problem #4
	//	Return a version of the given array where each zero value in 
	//	the array is replaced by the largest odd value to the right 
	//	of the zero in the array. If there is no odd value to the 
	//	right of the zero, leave the zero as a zero. 

	//	ReplaceZerosWithLargestOdd({0, 5, 0, 3}) → {5, 5, 3, 3}
	//	ReplaceZerosWithLargestOdd({0, 4, 0, 3}) → {3, 4, 3, 3}
	//	ReplaceZerosWithLargestOdd({0, 1, 0}) → {1, 1, 0}
	
	/**
	 * 
	 * @param NumberList
	 * 		int[] list containing some numbers.
	 * 
	 * @return NumberList
	 * 		int[] list containing the numbers where the zeros have been
	 * 		replaced with the largest odd number to the right of them.
	 */
	public static int[] ReplaceZerosWithLargestOdd(int[] NumberList) 
	{
		int high = 0;
		for(int i = 0; i < NumberList.length; i++) // iterates through every number in array
		{
			if(NumberList[i] == 0)
			{
				for(int j = i; j < NumberList.length; j++) // iterates through every number on the right of the '0' number
					if(NumberList[j] % 2 != 0) // if number is odd
						if(NumberList[j] > high) // if the odd number is greater than the current largest odd
							high = NumberList[j]; // set the current odd number as the highest odd
				NumberList[i] = high; // sets the current element to the highest odd to the right of it
				high = 0; // resets high variable
			}
		}
		return NumberList;
	}
	
	//	Problem #5
	//	Given start and end numbers, return a new array containing 
	//	the sequence of integers from start up to but not including end, 
	//	so start=5 and end=10 yields {5, 6, 7, 8, 9}. The end number 
	//	will be greater or equal to the start number. 
	//	Note that a length-0 array is valid. 

	//	CreateIncreasingArray(5, 10) → {5, 6, 7, 8, 9}
	//	CreateIncreasingArray(11, 18) → {11, 12, 13, 14, 15, 16, 17}
	//	CreateIncreasingArray(1, 3) → {1, 2}	
	
	/**
	 * 
	 * @param start, end
	 * 		Two integers stating the start and end of the sequence.
	 * 
	 * @return NumberList
	 * 		int [] containg numbers ranging from start to end
	 * 		in order from least to greatest.
	 */
	static int[] CreateIncreasingArray(int start, int end) 
	{
		int length = end - start; // finds length of new array
		int[] array = new int[length]; // creates a new array
		array[0] = start; // sets the first element to the parameter "start"
		for(int i = 1; i < length; i++) // iterates through all elements excluding the first
			array[i] = array[i-1]+1; // sets the current element to the number (1 + previous number)

		return array; // returns array
	}
	
	//	Problem #6
	//	Given a non-empty array of ints, return a new array containing 
	//	the elements from the original array that come before the 
	//	first 4 in the original array. The original array will contain 
	//	at least one 4. Note that it is valid in java to create 
	//	an array of length 0.  

	//	CopyNumbersBeforeFour({1, 2, 4, 1}) → {1, 2}
	//	CopyNumbersBeforeFour({3, 1, 4}) → {3, 1}
	//	CopyNumbersBeforeFour({1, 4, 4}) → {1}
	
	/**
	 * 
	 * @param NumberList
	 * 		int[] list containing some numbers.
	 * 
	 * @return AbridgedList
	 * 		int[] list containing all the numbers that appeared
	 * 		before the first 4 in the array.
	 */
	static int[] CopyNumbersBeforeFour(int[] NumberList) 
	{	
		int length = 0, i;
		
		// finds the first '4' in the array
		for(i = 0; i < NumberList.length; i++) 
			if(NumberList[i] == 4) // if there is a '4'
				length = i; // the length of the new array will equal the index of the '4'
		
		int[] AbridgedList = new int[length]; // new array
		
		// creates the new array
		for(i = 0; i < length; i++) 
			AbridgedList[i] = NumberList[i];

		return AbridgedList; // returns the array to main
	}
	
	//	Problem #7
	//	Return an array that contains the exact same numbers as 
	//	the given array, but rearranged so that all the zeros 
	//	are grouped at the start of the array. The order of the 
	//	non-zero numbers does not matter. So {1, 0, 0, 1} becomes 
	//	{0 ,0, 1, 1}. You may modify and return the 
	//	given array or make a new array.  

	//	MoveZerosToFront({1, 0, 0, 1}) → {0, 0, 1, 1}
	//	MoveZerosToFront({0, 1, 1, 0, 1}) → {0, 0, 1, 1, 1}
	//	MoveZerosToFront({1, 0}) → {0, 1}
	
	/**
	 * 
	 * @param NumberList
	 * 		int[] list containing some numbers.
	 * 
	 * @return RearrangedList
	 * 		int[] list containing all the numbers from original
	 * 		list with the zeros moved to the front.
	 */
	static int[] MoveZerosToFront(int[] NumberList) 
	{
		int j = 0; // position to place the zero
		int swap;
		int[] RearrangedList = NumberList; // RearrangedList is NumberList
		for(int i = 0; i < RearrangedList.length; i++) // iterates through RearrangedList
			if(RearrangedList[i] == 0) // if the number is a '0'
			{
				// swaps the two indexes to put the '0' in front
				swap = RearrangedList[j];
				RearrangedList[j] = RearrangedList[i];
				RearrangedList[i] = swap;
				j++; // increment the position variable
			}
		return RearrangedList; // return the new array
	}
	
	//	Problem #8
	//	Return an array that contains the exact same numbers as 
	//	the given array, but rearranged so that all the even numbers 
	//	come before all the odd numbers. Other than that, the 
	//	numbers can be in any order. You may modify and 
	//	return the given array, or make a new array.  

	//	EvenFrontOddBack({1, 0, 1, 0, 0, 1, 1}) → {0, 0, 0, 1, 1, 1, 1}
	//	EvenFrontOddBack({3, 3, 2}) → {2, 3, 3}
	//	EvenFrontOddBack({2, 2, 2}) → {2, 2, 2}
	
	/**
	 * 
	 * @param NumberList
	 * 		int[] list containing some numbers.
	 * 
	 * @return RearrangedList
	 * 		int[] list containing all the numbers from original
	 * 		list with the even numbers in the front and the
	 * 		odd numbers in the back.
	 */
	static int[] EvenFrontOddBack(int[] NumberList) 
	{
		int swap, j = 0;
		for(int i = 0; i < NumberList.length; i++) // iterates through RearrangedList
			if((NumberList[i] % 2) == 0) // if the number is even
			{
				// swaps the two indexes to put the '0' in front
				swap = NumberList[j];
				NumberList[j] = NumberList[i];
				NumberList[i] = swap;
				j++; // increment the position variable
			}
		return NumberList; // return the new array
	}
	
	// This is where the testing happens
	
	public static void main(String[] args)
	{
		int[] NumberList = {1,4,4};

		int[] i = CopyNumbersBeforeFour(NumberList);
			for(int c:i)
				System.out.print(c + " ");
	}
	
}