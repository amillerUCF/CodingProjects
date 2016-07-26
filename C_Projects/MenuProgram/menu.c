/*
 * This is a menu-like program that features three different
 * programs in it:
 * - abundant.c: determines whether a number is abundant or
 *   not by taking the sum of its proper divisors.
 * - coaster.c: determines the max number of people that can
 *   be on a coaster while keeping the riders out of harm
 *   under safety protocols. (Numbers calculated in this program
 *   are not a reality but are only used to demonstrate the
 *   helpfulness of such a program for any theme park).
 * - lastnames.c: checks and notifys whether a name was used
 *   twice or more in a list given by the user.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int answer;
int c1;

int main() {
printf("\n");
while(answer == answer)
{
    printf("\t\t\t\t   Menu\n\n");
    printf("1) abundant.c  (determines whether a number is abundant or not)\n");
    printf("2) coaster.c  (determines the most people that can be on a coaster at one time)");
    printf("3) lastnames.c (checks to see if the first name in a list is repeated)\n");
    printf("\nEnter the number of the program you wish to run: ");
    scanf("%d", &answer);
    if(answer == 1)
    {
       system("CLS");
       int n, n1, n2, c2;
       int caseNum = 1;
       int modulus;
       int sum = 0;
    // User enter amount of numbers for 'n'
       printf("Please enter n followed by n numbers: ");
       scanf("%d", &n);

       int numbers[n];
       n1 = n;
       n2 = n;

    // User enters numbers for 'n' amount
       for(c1 = 0; n > c1; n--)
       { scanf(" %d", &numbers[n]); }

    // Repeat test cases by 'n' amount
       for(c1 = 0; n1 > c1; n1--)
       { printf("Test case #%d: %d ", caseNum, numbers[n1]); caseNum++;

       // Variable declares half of number
          int halfNum = numbers[n2]/2;

       // Find all divisors of number
          for(c2 = 1; c2 <= halfNum; c2++)
          {
                modulus = numbers[n2] % c2;

             // Checks if a number is a divisor, then adds to a sum
                if(modulus == 0)
                { sum = sum + c2; }
          }

       // Compares sum to original number
          if(sum > numbers[n1])
          { printf("is abundant.\n"); }
          else
              printf("is NOT abundant.\n");

          n2 = n2-1;
          numbers[n2];
          sum = 0;
        }
    system("pause");
    system("CLS");
    main();
    }
    else if(answer == 2)
    {
         system("CLS");
         int trackLength;
         int trainLength;
         int numOfCars, achievedCars;
         int numOfTrains;
         int otherLengths = 10;
         float people;
         int maxPeople = 100;
         int safe;
         int Cumul_car_len;
         float ratio_array[0];
         float avgRatio = 0;

   // User enters total length of track
       printf("What is the total length of the track, in feet? \n");
       scanf("%d", &trackLength);
       while(trackLength >= 10000)
       {
           printf("Track length too long. Try again with value less than 10,000\n");
           printf("What is the total length of the track, in feet? \n");
           scanf("%d", &trackLength);
       }

   // User enters length of one train
      printf("What is the maximum length of a train, in feet? \n");
      scanf("%d", &trainLength);
      while(trainLength <= 10 || trainLength >= 100)
      {
          printf("Train length is either too long or too short. Try again with a value between 10 and 100.\n");
          printf("What is the maximum length of a train, in feet? \n");
          scanf("%d", &trainLength);
      }

   // Number of trains on track
      safe = trackLength/4;

   // Test which number of cars and trains fits most people
      for(otherLengths; otherLengths <= trainLength; otherLengths += 8)
      {
          numOfTrains = safe/otherLengths;
      // Number of cars per train
          numOfCars = ((otherLengths-10)/8)+1;
      // Number of people total on all trains
          people = numOfTrains * (numOfCars * 4);
          if(maxPeople < people)
          {
                    maxPeople = people;
                    achievedCars = numOfCars;
          }
          Cumul_car_len = numOfTrains * otherLengths;
          ratio_array[c1] = people / Cumul_car_len;
          ratio_array[c1+1];
          avgRatio += ratio_array[c1];
      }
      avgRatio /= numOfCars;
      printf("Your coaster can have at most %d people on it at one time.\n", maxPeople);
      printf("This can be achieved with trains of %d cars.\n", achievedCars);
      printf("AVG Ratio: %.3f\n", avgRatio);
      system("pause");
      system("CLS");
      main();
    }
    else if(answer == 3)
    {
       system("CLS");
       int n, n1, n2, c1;

    // User enters value for 'n'
       printf("Enter n, followed by n Last names (each last name must be a single word) :\n");
       scanf(" %d", &n);
       n1 = 0;
       char lastNames[n][50];
    // User enters lastnames
       for(c1 = 0; c1 < n; c1++)
       {
            scanf(" %s", &lastNames[n1]);
            n1++;
       }
       n1 = 0;
       n2 = 1;
       c1 = 0;
    // Tests if the first name is repeated
       for(c1; c1 < n; c1++)
       {
        if(strcmp(lastNames[n1], lastNames[n2]) == 0)
        {
                printf("\nFirst name in list is repeated.\n");
                system("pause");
                return 0;
        }
        n2++;
       }
       printf("\nFirst name in list is not repeated.\n");
    system("pause");
    system("CLS");
    main();
    }
    else
    {
        system("CLS");
        printf("Your response was invalid; please try again\n");
    }
}
system("pause");
return 0;
}
