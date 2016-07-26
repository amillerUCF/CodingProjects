import java.util.Scanner;

public class Recursion4
{

	//	Problem #1
	//	Given a string, count the number of words ending in 'y' 
	//	or 'z' -- so the 'y' in "heavy" and the 'z' in "fez" count, 
	//	but not the 'y' in "yellow" (not case sensitive). We'll say 
	//	that a y or z is at the end of a word if there is not an 
	//	alphabetic letter immediately following it. (Note: 
	//	Character.isLetter(char) tests if a char is an alphabetic letter.) 

	//	wordEndYZ("fez day") → 2
	//	wordEndYZ("day fez") → 2
	//	wordEndYZ("day fyyyz") → 2
	
	/**
	 * 
	 * @param str
	 * 		str containing the original string
	 * 
	 * @return int
	 * 		int containing the # of words that end in y or z
	 */
	static int wordEndYZ(String str) 
	{
		String split[] = str.split(" "); // splits string into an array of words
		int count = 0; // num of words counter
		for(int i = 0; i < split.length; i++)
			if(split[i].endsWith("y") || split[i].endsWith("z")) // if last letter is 'y' or 'z'
				count++; // increment num of words ending in specific letter
		return count; // return amount
	}

	//	Problem #2
	//	Given two strings, base and remove, return a version of the base 
	//	string where all instances of the remove string have been removed 
	//	(not case sensitive). You may assume that the remove string is length 
	//	1 or more. Remove only non-overlapping instances, so with "xxx" 
	//	removing "xx" leaves "x".

	//	removeFromBase("Hello there", "llo") → "He there"
	//	removeFromBase("Hello there", "e") → "Hllo thr"
	//	removeFromBase("Hello there", "x") → "Hello there"
	
	/**
	 * 
	 * @param base, remove
	 * 		base contains original string of characters
	 * 		remove contains original string that is to be removed from base
	 * 
	 * @return
	 * 		String containing the base with all instances of remove taken out
	 */
	static String removeFromBase(String base, String remove) 
	{
		return base.replaceAll(remove, ""); // removes all instances of "remove" from "base"
	}	

	//	Problem #3
	//	Given a string, return true if the number of appearances of 
	//	"is" anywhere in the string is equal to the number of appearances 
	//	of "not" anywhere in the string (case sensitive). 

	//	equalAppearance("This is not") → false
	//	equalAppearance("This is notnot") → true
	//	equalAppearance("noisxxnotyynotxisi") → true
	
	/**
	 * 
	 * @param str
	 * 		str contains the original string of characters
	 * 
	 * @return
	 * 		returns true if "is" appears as many times as "not"
	 * 		returns false if "is" does not appear as many times as "not"
	 */
	static boolean equalAppearance(String str) 
	{
		String replaceIs = str.replaceAll("is", ""); // "replaceIs" is original string without "is"
		String replaceNot = str.replaceAll("not", ""); // "replaceNot" is original string without "not"
		int numIs = (str.length() - replaceIs.length()) / 2; // mathematically gets number of "is" instances
		int numNot = (str.length() - replaceNot.length()) / 3; // mathematically gets number of "not" instances

		if(numIs != numNot)
			return false;
		return true;
	}	

	//	Problem #4
	//	We'll say that a lowercase 'g' in a string is "happy" if there 
	//	is another 'g' immediately to its left or right. Return true if 
	//	all the g's in the given string are happy. 

	//	gisHappy("xxggxx") → true
	//	gisHappy("xxgxx") → false
	//	gisHappy("xxggyygxx") → false
	
	/**
	 * 
	 * @param str
	 * 		String containing original string of characters
	 * 
	 * @return
	 * 		returns true if 'g' appears with another 'g' to it's right or left
	 * 		returns false if this is not the case
	 */
	static boolean gisHappy(String str) 
	{
		if(str.length() == 1) // if the string is too small; return false
			return false;
		
		String split = str.replaceAll("g", "");	// split is string without instances of 'g'
		int num = str.length() - split.length(); // gets number of "g" instances
		int index = str.indexOf('g'); // gets starting 'g' index in string
		
		for(int i = 0; i < num; i++) // loops through all g's in string
		{	
			if(index == 0) // calculates if 'g' is happy when it is at beginning of string
			{
				if(str.charAt(index+1) != str.charAt(index))
					return false;
			}
			else if(index == str.length() - 1) // calculates if 'g' is happy when it is at end of string
			{
				if(str.charAt(index-1) != str.charAt(index))
					return false;
			}
			else if(str.charAt(index-1) != str.charAt(index) && str.charAt(index+1) != str.charAt(index)) // if 'g' is not "happy" return false
					return false;
			
			index = str.indexOf('g', index + 1); // changes index to the index of the next 'g' instance
		}
		return true;
	}
	
	//	Problem #5
	//	We'll say that a "triple" in a string is a char appearing three times in a row. 
	//	Return the number of triples in the given string. The triples may overlap. 

	//	numberOfTriples("abcXXXabc") → 1
	//	numberOfTriples("xxxabyyyycd") → 3
	//	numberOfTriples("a") → 0	
	
	/**
	 * 
	 * @param str
	 * 		String containing the original string of characters
	 * 
	 * @return
	 * 		Integer containing the # of "triples" in str
	 */
	static int numberOfTriples(String str) 
	{
		if(str.length() < 3) // if string is too small to hold enough occurrences
			return 0;
		
		int count = 0; // number of triplets
		
		for(int i = 0; i < str.length()-2; i++)
		{
			if(str.charAt(i) == str.charAt(i+1) && str.charAt(i+1) == str.charAt(i+2)) // if three in a row, then increment
				count++;
		}		
		return count; // return number of triplets
	}
	
	//	Problem #6
	//	Given a string, return the sum of the digits 0-9 that appear in the 
	//	string, ignoring all other characters. Return 0 if there are no digits 
	//	in the string. (Note: Character.isDigit(char) tests if a char is one 
	//	of the chars '0', '1', .. '9'. Integer.parseInt(string) converts a string to an int.) 

	//	addUpDigits("aa1bc2d3") → 6
	//	addUpDigits("aa11b33") → 8
	//	addUpDigits("Chocolate") → 0
	
	/**
	 * 
	 * @param str
	 * 		String containing the original string of characters
	 * 
	 * @return 
	 * 		Integer containing the # sum of all digits that appear in str
	 */
	static int addUpDigits(String str) 
	{
		int sum = 0;
		for(int i = 0; i < str.length(); i++)
		{
			if(java.lang.Character.isDigit(str.charAt(i)))
				sum += (str.charAt(i) % 48);
		}
		return sum;
	}
	
	//	Problem #7
	//	Given a string, return the longest substring that appears at 
	//	both the beginning and end of the string without overlapping. 
	//	For example, beginningAndEndOfString("abXab") is "ab". 

	//	beginningAndEndOfString("abXYab") → "ab"
	//	beginningAndEndOfString("xx") → "x"
	//	beginningAndEndOfString("xxx") → "x"
	
	/**
	 * 
	 * @param string
	 * 		String containing the original string of characters
	 * 
	 * @return 
	 * 		String containing the beginning and ending substrings that are the same
	 */
	static String beginningAndEndOfString(String string) 
	{
		int prev = -1; // holds the value of the previous index of 'j'; helps with words like "xxx"
		int i = 0, j;
		char[] c = new char[string.length()]; // character array to hold substring to return
		int a = 0; // counter for the character array 'c'
		if(string.length() < 2) // if string is too small return null
			return null;

		for(j = 1; j < string.length(); j++)
		{
			if(string.charAt(i) == string.charAt(j) && i != prev) // if 'i' and 'j' indexes are the same and 'i' is not 'j's' previous index
			{
				prev = j; // previous j index
				c[a] = string.charAt(j); // add char to final string
				a++; // increment char array counter
				i++; // increment i
			}
			else
				a = 0; // reset char array counter
		}
		String s = new String(c); // convert char array to string
		return s;
	}
	
	//	Problem #8
	//	Given a string, look for a mirror image (backwards) string at both 
	//	the beginning and end of the given string. In other words, zero or more 
	//	characters at the very beginning of the given string, and at the very 
	//	end of the string in reverse order (possibly overlapping). For example, 
	//	the string "abXYZba" has the mirror end "ab". 

	//	beginningMirrorEnd("abXYZba") → "ab"
	//	beginningMirrorEnd("abca") → "a"
	//	beginningMirrorEnd("aba") → "aba"
	
	/**
	 * 
	 * @param string
	 * 		String containing the original string of characters
	 * 
	 * @return 
	 * 		String containing the beginning of the string that is mirrored at the end
	 */
	static String beginningMirrorEnd(String string) 
	{
		char[] c = new char[string.length()]; // character array
		int j = string.length()-1; // j is the last index in string
		for(int i = 0; i < string.length(); i++) // loop from beginning index to last index of string
		{
			if(string.charAt(i) != string.charAt(j)) // if the character stop matching, then return string
			{
				String s = new String(c); // convert char array to string
				return s; // return string
			}
			c[i] = string.charAt(i); // sets chars to character array
			j--; // j decrements
		}
		String s = new String(c); // convert char array to string
		return s;
	}
	
	//	Problem #9
	//	Given a string, return the length of the largest "block" in the string. 
	//	A block is a run of adjacent chars that are the same. 

	//	largestBlock("hoopla") → 2
	//	largestBlock("abbCCCddBBBxx") → 3
	//	largestBlock("") → 0
	
	/**
	 * 
	 * @param str
	 * 		String containing the original string of characters
	 * 
	 * @return 
	 * 		Integer containing the # of chars in the largest "block" in str
	 */
	static int largestBlock(String str) 
	{
		if(str.length() < 1) // if the string is empty
			return 0;
		if(str.length() == 1) // if the string is too small
			return 1;
		int max = 0, count = 0;
		for(int i = 1; i < str.length(); i++) // loop from second character in string to end of string
		{
			if(str.charAt(i) == str.charAt(i-1)) // while the current is the same as the previous
			{
				count++; // increment counter
				continue; // go back to beginning of for-loop
			}
			if(max < count) // if there is a new max
				max = count; // a new max block has been found
			count = 0; // reset the counter when characters stop matching
		}
		return max+1; // returns actual amount
	}
	
	//	Problem #10
	//	Given a string, return the sum of the numbers appearing in the string, 
	//	ignoring all other characters. A number is a series of 1 or more digit 
	//	chars in a row. (Note: Character.isDigit(char) tests if a char is one 
	//	of the chars '0', '1', .. '9'. Integer.parseInt(string) converts a string to an int.)

	//	addUpNumbers("abc123xyz") → 123
	//	addUpNumbers("aa11b33") → 44
	//	addUpNumbers("7 11") → 18
	
	/**
	 * 
	 * @param str
	 * 		String containing the original string of characters
	 * 
	 * @return 
	 * 		Integer containing the sum of all the numbers that appear in str
	 */
	static int addUpNumbers(String str) 
	{
		String s = ""; // empty string
		int sum = 0;
		for(int i = 0; i < str.length(); i++) // loop from second character in string to end of string
		{
			if(java.lang.Character.isDigit(str.charAt(i))) // if the curret index is a digit
			{
				s += str.charAt(i); // add the char to the string
				if(i != str.length()-1) // if the index is the last index, then continue to end of for loop
					continue; // go back to beginning of for-loop
			}
			if(s == "") // is the string is empty, jump back tobeginning of the for loop
				continue;
			int num = Integer.parseInt(s); // converts the string to an integer
			sum += num; // add to the sum
			s =""; // sets string back to empty
		}	
		return sum;
	}
	
	// This is where the testing happens
	
	public static void main(String[] args)
	{
		System.out.println(addUpNumbers("1a12v34bbbbb9")  );
	}
	
}