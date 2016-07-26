package hillcipher;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Character;

public class hillcipher {

    public static void main(String[] args) throws FileNotFoundException, IOException{
       
        //Variables
        int length = 1000; //Length of plaintext array
        int c = 0, i, j, index = 0; //Counter
        int sum = 0; //Used for matrix multiplication
        char current;
        String s = null;
        char[] plainText = new char[length];
        char[] cipherText = new char[length];
        
        //Instances
        FileInputStream fis = new FileInputStream(args[1]);
        Scanner scanny = new Scanner(new FileInputStream(args[0]));
        
        System.out.println("\n\nKey Matrix:\n");
        //Stores key to 2-D array
        int dim = scanny.nextInt();
        int[][] key = new int[dim][dim];
        for(i = 0; i < dim; i++)
        {
            for(j = 0; j < dim; j++)
            {
                key[i][j] = scanny.nextInt();
                System.out.printf("%d ", key[i][j]);
            }
            System.out.println();
        }
        
        System.out.println("\n\nPlaintext:\n");
        //Stores message to array
        while(fis.available() > 0)
        {
            current = (char) fis.read();
            if(Character.isLetter(current))
            {
                if(current > 96 && current < 123)   //Character is lower-case
                    plainText[c] = current;
                else if(current > 64 && current < 91)   //Character is upper-case
                {
                    char ch = Character.toLowerCase(current);
                    plainText[c] = ch;
                }
                System.out.print(plainText[c]);
                c++;
            }
        }
        while(c%4 != 0) //Pads the end of the array with an 'x'
        {
            plainText[c] = 'x';
            System.out.print(plainText[c]);
            c++;
        }
            
        c = 0; //Resets c
        System.out.println("\n\n\nCiphertext:\n");
        //Encrypts message
        while(plainText[c] != '\0')
        {
            for(i = 0; i < dim; i++)
            {
                for(j = 0; j < dim; j++)
                {
                    sum += (plainText[c] - 97)*key[i][j];
                    c++;
                }
                c -= 4;
                cipherText[index] = (char)('a' + (sum%26));
                //System.out.print(cipherText[index]);
                sum = 0;
                index++;
            }
            c += 4;
        }
        for(char ch : cipherText) //Prints out array to make sure it's correct
            System.out.print(ch);

    }
    
}
