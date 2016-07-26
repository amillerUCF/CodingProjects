import java.util.Scanner;

public class RecursiveProblems
{
	
	// Problem #1
	// Directions: Return the number of even ints in the given 
	// array (The number '0' counts as an even number). 
	// Note: the % "mod" operator computes the remainder, 
	// e.g. 5 % 2 is 1. 

	// CountEvenNumbersInArray({2, 1, 2, 3, 4}) → 3
	// CountEvenNumbersInArray({2, 2, 0}) → 3
	// CountEvenNumbersInArray({1, 3, 5}) → 0
	
	/**
	 * 
	 * @param NumberList
	 * 		int[] list containing some numbers.
	 * 
	 * @return
	 * 		int with the number of even numbers in NumberList
	 */
	static int CountEvenNumbersInArray(int[] NumberList) 
	{
		int evenNums = 0; // variable for number of even #'s
		for(int i = 0; i < NumberList.length; i++) // iterates through every # in array
		{
			if(((NumberList[i] % 2) == 0) || NumberList[i] == 0) // if even # including '0' is encountered
				evenNums++; // evenNums = evenNums + 1
		}
		return evenNums; // return number of even #'s
	}

	// Problem #2
	// Given an array of ints, return true if the array contains no 
	//   1's and no 3's.

	// LookForLucky13({0, 2, 4}) → true
	// LookForLucky13({1, 2, 3}) → false
	// LookForLucky13({1, 2, 4}) → false
	
	/**
	 * 
	 * @param NumberList
	 * 		int[] list containing some numbers.
	 * 
	 * @return
	 * 		returns false if there is a 1 or 3 in the list.
	 * 		returns true if there are no 1s or 3s in the list.
	 */
	static boolean LookForLucky13(int[] NumberList)
	{
		for(int i = 0; i < NumberList.length; i++) // iterates through every # in array
		{
			if((NumberList[i] == 1) || (NumberList[i] == 3)) // if # is a '1' or '3'
				return false; // return false
		}
		return true; // else return true
	}	

	// Problem #3
	// Given arrays NumberList1 and NumberList2 of the same length, 
	//   for every element in NumberList1, consider the 
	//   corresponding element in NumberList2 (at the same index). 
	//   Return the count of the number of times that the two 
	//   elements differ by 2 or less, but are not equal. 

	// MatchUpLists({1, 2, 3}, {2, 3, 10}) → 2
	// MatchUpLists({1, 2, 3}, {2, 3, 5}) → 3
	// MatchUpLists({1, 2, 3}, {2, 3, 3}) → 2
	
	static int MatchUpLists(int[] NumberList1, int[] NumberList2)
	{
		int count = 0; // variable to hold number of occurrences
		for(int i = 0; i < NumberList1.length; i++) // iterates through every # in array
		{
			int diff = NumberList1[i]-NumberList2[i]; // gets the difference between the two #'s
			if(diff == 0) // if diff is '0' then continue to iterate
				continue;
			else if(diff < 3 && diff > -3) // is they differ by 2 or less, than count
				count++; // count = count + 1
			else
				continue; // if any other difference, continue
		}
		return count;
	}	

	// Problem #4
	// Given an array of ints, return true if the array 
	//   contains either 3 even or 3 odd values all next 
	//   to each other. 

	// ModThreeNumbers({2, 1, 3, 5}) → true
	// ModThreeNumbers({2, 1, 2, 5}) → false
	// ModThreeNumbers({2, 4, 2, 5}) → true
	
	/**
	 * 
	 * @param NumberList
	 * 		int[] list containing some numbers.
	 * 
	 * @return
	 * 		return true if there are three consecutive evens
	 * 			or three consecutive odds
	 * 
	 * 		otherwise returns false
	 */
	public static boolean ModThreeNumbers(int[] NumberList)
	{
		int odd = 0, even = 0;
		for(int i = 0; i < NumberList.length; i++) // iterates through every # in array
		{
			while((NumberList[i] % 2) == 0) // if # is even
			{
				odd = 0; // resets counter when transition from odd to even
				even++; // increment counter
				if(even == 3) // if there is 3 even #'s in a row
					return true;
				break; // jump to next index
			}
			while((NumberList[i] % 2) != 0) // if # is odd
			{
				even = 0; // resets counter when transition from even to odd
				odd++; // increment counter
				if(odd == 3) // if there is 3 odd #'s in a row
					return true;
				break;
			}	
		}
		return false; // there were no 3 consecutive even or odd #'s
	}

	// Problem #5
	// Return the "centered" average of an array of ints, 
	//   which we'll say is the mean average of the values, 
	//   except ignoring the largest and smallest values in 
	//   the array. If there are multiple copies of the 
	//   smallest value, ignore just one copy, and likewise 
	//   for the largest value. Use int division to produce 
	//   the final average. You may assume that the array is 
	//   length 3 or more. 

	// FindCenteredAverage({1, 2, 3, 4, 100}) → 3
	// FindCenteredAverage({1, 1, 5, 5, 10, 8, 7}) → 5
	// FindCenteredAverage({-10, -4, -2, -4, -2, 0}) → -3	
	
	/**
	 * 
	 * @param NumberList
	 * 		int[] list containing some numbers.
	 * 
	 * @return
	 * 		Average of the list of numbers without the
	 * 		first of the lowest numbers and the last of the
	 * 		highest numbers.
	 */
	static int FindCenteredAverage(int[] NumberList)
	{	
		int maxIndex = 0;
		int minIndex = 0;
		
		//----Find index of the max and min values----
		
		for(int i = 0; i < NumberList.length; i++)
		{
			if(NumberList[i] > NumberList[maxIndex]) // changes max index if current is less than
				maxIndex = i;
			
			if(NumberList[i] < NumberList[minIndex]) // changes min index if greater than
				minIndex = i;
		}
		
		//--------------------------------------------
		
		//--Creates new array without the max and min--
		
		int[] clone = new int[NumberList.length-2]; // array without the max and min
		int index = 0; // cursor for array without min and max
		for(int i = 0; i < NumberList.length; i++) // creates array without the max and min
		{
			if(i == maxIndex || i == minIndex) // skip the max and min indexes
			{
				continue;
			}
			else
			{
				clone[index] = NumberList[i]; // adds every other number in array besides max and min
				index++;
			}
			
		}
		NumberList = clone; // NumberList is the array without min and max
		
		//---------------------------------------------
		
		//-----------Removes all duplicates------------
		
		int[] array = new int[NumberList.length]; // duplicate of the original array
		
		int newLength = 0; // length of new array
		int sum = 0; // sum for new array
		
		for(int i = 0; i < NumberList.length; i++) // i iterates up
		{	
			array[i] = NumberList[i]; // sets index of array equal to NumberList
			newLength++;
			for(int j = 0; j < i; j++)
			{
				if(NumberList[i] == NumberList[j]) // if there is a duplicate
				{
					array[i] = 0; // fake erase of the duplicate by converting it to '0'
					newLength--; // decrement length when a duplicate is erased
				}
			}
			sum += array[i]; // gets the sum of the new array
		}
		return sum / newLength; // return the average
		
		//---------------------------------------------
	}
	
	// Problem #6
	// Given an array of ints, return true if every 2 that 
	//   appears in the array is next to another 2. 

	// LookForTwoTwo({4, 2, 2, 3}) → true
	// LookForTwoTwo({2, 2, 4}) → true
	// LookForTwoTwo({2, 2, 4, 2}) → false
	
	/**
	 * 
	 * @param NumberList
	 * 		int[] list containing some numbers.
	 * 
	 * @return
	 * 		true if every 2 is adjacent to another 2
	 * 		otherwise false
	 */
	static boolean LookForTwoTwo(int[] NumberList)
	{
		int count = 0;
		for(int i = 0; i < NumberList.length; i++) // iterates through all #'s in array
		{
			if(NumberList[i] == 2) // if the # is a '2'
			{
				count++;
				continue;
			}
			if(count == 1) // when a '2' is not next to another '2'
			    return false;
			count = 0; // reset count
				
		}
		if(count == 1) // when a '2' is not next to another '2' at the end of the array
			return false;
		return true;
	}
	
	
	// This is where the testing happens
	
	public static void main(String[] args)
	{
            int[] NumberList = {2, 2, 4, 2};
            boolean c = LookForTwoTwo(NumberList);
            System.out.println(c);
	}
	
}