import java.util.Scanner;

public class Recursion5
{

	//	Problem #1
	//	We want to make a row of bricks that is goal inches long. We have a number of 
	//	small bricks (1 inch each) and big bricks (5 inches each). Return true if it 
	//	is possible to make the goal by choosing from the given bricks. This is a 
	//	little harder than it looks and can be done without any loops.

	//	makeRowOfGoalBricks(3, 1, 8) → true
	//	makeRowOfGoalBricks(3, 1, 9) → false
	//	makeRowOfGoalBricks(3, 2, 10) → true
	
	/**
	 * 
	 * @param small, big, goal
	 * 		int containing the number of 1inch bricks available
	 * 		int containing the number of 5inch bricks available
	 * 		int containing the number of inches for the goal
	 * 
	 * @return 
	 * 		returns true if the goal can be reached with the available bricks
	 * 		returns false if the goal cannot be reached with the available bricks
	 */
	static boolean makeRowOfGoalBricks(int small, int big, int goal) 
	{
		if(small == 0 || big == 0 || goal == 0) // if one of the parameters are '0'
		{
			System.out.println("Error: Input is not acceptable!");
			return false;
		}
		if(big*5 > goal) // all cases when big*5 > goal
		{
			if(goal % 5 == 0) // if goal is a multiple of 5
				return true;
			else if((goal - small) > (goal - (goal % 5))) // if there are not enough small bricks to fill in the difference of the goal and it's rounded down value that is a multiple of 5
				return false;
			else
				return true;
		}
		if(goal - (5*big) <= small) // all cases when big*5 <= goal
			return true;
		return false;
	}

	//	Problem #2
	//	Given 3 int values, a b c, return their sum. However, if one of the values 
	//	is the same as another of the values, it does not count towards the sum.

	//	sumExcludingDuplicates(1, 2, 3) → 6
	//	sumExcludingDuplicates(3, 2, 3) → 2
	//	sumExcludingDuplicates(3, 3, 3) → 0
	
	/** 
	 * 
	 * @param a, b, c
	 * 		ints containing the original integers to sum
	 * 
	 * @return
	 * 		returns the sum of the input where duplicates are not included
	 */
	static int sumExcludingDuplicates(int a, int b, int c) 
	{
		if(a == b && a == c) // if all parameters are the same
			return 0;
		if(a == b) // if the two parameters match
		{
			a = 0; b = 0; // set to '0' so they don't count towards the sum
		}
		if(a == c)
		{
			a = 0; c = 0;
		}
		if(b == c)
		{
			b = 0; c = 0;
		}
		return a + b + c; // return sum
	}	

	//	Problem #3
	//	Given 3 int values, a b c, return their sum. However, if one of the values is 
	//	13 then it does not count towards the sum and values to its right do not 
	//	count. So for example, if b is 13, then both b and c do not count. 

	//	sumExcludingUnluckyNums(1, 2, 3) → 6
	//	sumExcludingUnluckyNums(1, 2, 13) → 3
	//	sumExcludingUnluckyNums(1, 13, 3) → 1
	
	/**
	 * 
	 * @param a, b, c
	 * 		ints containing the original integers to sum
	 * 
	 * @return
	 * 		returns the sum of the input where values to the right of 13, inclusive, are not included
	 */
	static int sumExcludingUnluckyNums(int a, int b, int c) 
	{
		if(a == 13) // if a is the unlucky number
			return 0; // return 0
		if(b == 13)
			return a; // return just a
		if(c == 13)
			return a + b; // return a + b
		return a + b + c; // return the sum of all if none are the unlucky number
	}	

	//	Problem #4
	//	Given 3 int values, a b c, return their sum. However, if any of the values is a 
	//	teen -- in the range 13..19 inclusive -- then that value counts as 0, except 15 
	//	and 16 do not count as teens. Write a separate helper "public int fixTeen(int n) 
	//	{"that takes in an int value and returns that value fixed for the teen rule. In 
	//	this way, you avoid repeating the teen code 3 times (i.e. "decomposition").

	//	sumExcludingTeens(1, 2, 3) → 6
	//	sumExcludingTeens(2, 13, 1) → 3
	//	sumExcludingTeens(2, 1, 14) → 3
	
	/**
	 * 
	 * @param a, b, c
	 * 		ints containing the original integers to sum
	 * 
	 * @return
	 * 		returns the sum of the input where teens are not included
	 */
	static int sumExcludingTeens(int a, int b, int c) 
	{
		a = fixTeen(a); // 'a' becomes the optimized 'a' passed through fixTeen()
		b = fixTeen(b);
		c = fixTeen(c);
		return a + b + c; // return the sum
	}
	
	static int fixTeen(int num)
	{
		if((num >= 13 && num <= 19) && !(num == 15 || num == 16)) // if the parameter is a "teen" number excluding 15 and 16
			return 0; // don't count parameter towards final sum
		else
			return num; // return parameter if no optimization
	}
	
	//	Problem #5
	//	For this problem, we'll round an int value up to the next multiple of 10 if its rightmost 
	//	digit is 5 or more, so 15 rounds up to 20. Alternately, round down to the previous multiple 
	//	of 10 if its rightmost digit is less than 5, so 12 rounds down to 10. Given 3 ints, 
	//	a b c, return the sum of their rounded values. To avoid code repetition, write a separate 
	//	helper "public int round10(int num) {" and call it 3 times. Write the helper entirely below 
	//	and at the same indent level as roundSum().

	//	roundedSum(16, 17, 18) → 60
	//	roundedSum(12, 13, 14) → 30
	//	roundedSum(6, 4, 4) → 10
	
	/**
	 * 
	 * @param a, b, c
	 * 		ints containing the original integers to sum
	 * 
	 * @return
	 * 		returns the sum of the input where each value is rounded to the nearest tens place
	 */
	static int roundedSum(int a, int b, int c) 
	{
		a = round(a); // 'a' becomes the optimized 'a' passed through round()
		b = round(b);
		c = round(c);
		return a + b + c; // return the sum
	}
	
	static int round(int num)
	{
		if(num % 10 < 5) // if true, the number needs to be rounded down
			return num - (num % 10); // round down to the nearest multiple of ten
		return num + (10 - (num % 10)); // round up to the nearest multiple of ten
	}
	
	//	Problem #6
	//	Given three ints, a b c, return true if one of b or c is "close" (differing from 
	//	a by at most 1), while the other is "far", differing from both other values by 2 
	//	or more. Note: Math.abs(num) computes the absolute value of a number. 

	//	isCloseAndFar(1, 2, 10) → true
	//	isCloseAndFar(1, 2, 3) → false
	//	isCloseAndFar(4, 1, 3) → true
	
	/**
	 * 
	 * @param a, b, c
	 * 		ints with original integers to compute relativity
	 * 
	 * @return 
	 * 		returns true if one of b or c is close to a and if the other is far from both other values
	 */
	static boolean isCloseAndFar(int a, int b, int c) 
	{
		if(Math.abs(a - b) == 1 && Math.abs(b - c) == 1) // if all numbers are "close"
			return false;
		if(Math.abs(a - b) == 1 && (Math.abs(b - c) > 1 || Math.abs(a - c) > 1)) // if a,b are "close" and [b,c are "far"] or [a,c are "far"]
			return true;
		if(Math.abs(b - c) == 1 && (Math.abs(b - a) > 1 || Math.abs(a - c) > 1)) // if b,c are "close" and [b,a are "far"] or [a,c are "far"]
			return true;
		if(Math.abs(a - c) == 1 && (Math.abs(c - b) > 1 || Math.abs(a - b) > 1)) // if a,c are "close" and [a,c are "far"] or [a,b are "far"]
			return true;
		return false;
	}
	
	//	Problem #7
	//	Given 2 int values greater than 0, return whichever value is nearest to 21 without 
	//	going over. Return 0 if they both go over. 

	//	blackjack(19, 21) → 21
	//	blackjack(21, 19) → 21
	//	blackjack(19, 22) → 19
	
	/**
	 * 
	 * @param a, b
	 * 		ints representing the values of two cards in a game of black jack
	 * 
	 * @return 
	 * 		returns the value of the int that is closest to 21 without going over
	 */
	static int blackjack(int a, int b) 
	{
		if(a == 21 || b == 21) // is either integer is 21
			return 21;
		if(a > 21 && b > 21) // if both go over 21
			return 0;
		if(a > 21) // if 'a' goes over 21, return 'b'
			return b;
		if(b > 21)
			return a;
		if(21 - a < 21 - b) // if the difference btw 'a' and 21 is less, then return 'a'
			return a;
		else // last case: just return 'b'
			return b;
	}
	
	//	Problem #8
	//	Given three ints, a b c, one of them is small, one is medium and one is large. 
	//	Return true if the three values are evenly spaced, so the difference between 
	//	small and medium is the same as the difference between medium and large. 

	//	spacedEvenly(2, 4, 6) → true
	//	spacedEvenly(4, 6, 2) → true
	//	spacedEvenly(4, 6, 3) → false
	
	/**
	 * 
	 * @param a, b, c
	 * 		ints containing original integers to compute with
	 * 
	 * @return 
	 * 		returns true if the input values are evenly spaced
	 * 		returns false if the input values are not evenly spaced
	 */
	static boolean spacedEvenly(int a, int b, int c) 
	{
		int small /* smallest number */, med, big;
		
		if(a < b && a < c) { // if 'a' is the smallest number
			small = a;
			if(b < c) // if 'b' is the 2nd smallest
			{
				med = b;
				big = c;
			}
			else // if 'c' is the 2nd smallest
			{
				med = c;
				big = b;
			}
		}
		else if(b < a && b < c) { // if 'b' is the smallest number
			small = b; 
			if(a < c)
			{
				med = a;
				big = c;
			}
			else
			{
				med = c;
				big = a;
			}
		}
		else // if 'c' is the smallest number
		{
			small = c;
			if(a < b)
			{
				med = a;
				big = b;
			}
			else
			{
				med = b;
				big = a;
			}
		}
		
		if((big - med) == (med - small)) // if the differences are the same, return true
			return true;
		return false;
	}
	
	//	Problem #9
	//	We want to make a package of goal kilos of chocolate. We have small bars 
	//	(1 kilo each) and big bars (5 kilos each). Return the number of small bars 
	//	to use, assuming we always use big bars before small bars. Return -1 
	//	if it can't be done.

	//	makeKilosOfChocolate(4, 1, 9) → 4
	//	makeKilosOfChocolate(4, 1, 10) → -1
	//	makeKilosOfChocolate(4, 1, 7) → 2
	
	/**
	 * 
	 * @param small, big, goal
	 * 		int containing the number of 1kilo bars available
	 * 		int containing the number of 5kilo bars available
	 * 		int containing the number of kilos for the goal
	 * 
	 * @return 
	 * 		returns the value of the number of small bars needed to meet the goal
	 */
	static int makeKilosOfChocolate(int small, int big, int goal) 
	{
		if(small == 0 || big == 0 || goal == 0) // if one of the parameters are '0'
		{
			System.out.println("Error: Input is not acceptable!");
			return -1;
		}
		if(big*5 > goal) // all cases when big*5 > goal
		{
			if(goal % 5 == 0) // if goal is a multiple of 5
				return 0; // no small bars needed
			else if((goal - small) > (goal - (goal % 5))) // if there are not enough small bricks to fill in the difference of the goal and it's rounded down value that is a multiple of 5
				return -1; // package is not possible
			else
				return goal % 5; // return number of small bars
		}
		if(goal - (5*big) <= small) // all cases when big*5 <= goal
			return (goal - 5*big); // return the amount of small bars needed
		return -1; // package is not possible
		
	}
	
	// This is where the testing happens

	public static void main(String[] args)
	{
	}
	
}