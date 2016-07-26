/*
 * This program simulates a fully-working foodbank by implementing
 * a menu so that donations and requests can be fulfilled. All
 * requests in this program are not removed but set aside until
 * the user fulfills the requests. All food items in bank can
 * be printed out at anytime in the program so that it makes
 * tracking the items easier.
 */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

int menu;
int donation();
int request();
int fulfillRequest();
int printStatus();
int count;
int flag;
int a = 0,b = 0,c = 0, d = 0, i, j;
char donation_type[100][20];
int donation_amount[100];
char request_type[100][20];
int request_amount[100];

int main(){

    printf(" Welcome to the Food Bank Program\n\n   1. Enter a Donation\n   2. Enter a Request\n   3. Fulfill the earliest Request\n   4. Print status report\n   5. Exit\n\n");
    printf(" Enter your choice: ");
    scanf("%d", &menu);
    switch(menu)
    {
           case 1:
                donation();
                break;
           case 2:
                request();
                break;
           case 3:
                fulfillRequest();
                break;
           case 4:
                printStatus();
                break;
           case 5:
                printf("\n\n Thank You for using this software. Bye for now.\n\n ");
                system("pause");
    }
return 0;
}

int donation(){

   printf("\n Enter inventory type: "); scanf("%s", &donation_type[a]);
   printf(" Enter the amount: "); scanf("%d", &donation_amount[a]);
   printf("\n Donation Added!\n ");
   system("pause");
   system("CLS");
   a++;
   if(strcmp(donation_type[a-2], donation_type[a-1]) == 0)
   {
       strcpy(donation_type[a-2], donation_type[a-1]);
       donation_amount[a-2] += donation_amount[a-1];
       a--;
   }
   main();
}

int request(){

    printf("\n Enter inventory type: "); scanf("%s", &request_type[c]);
    printf(" Enter the amount: "); scanf("%d", &request_amount[c]);
    printf("\n Request Added!\n ");
    system("pause");
    system("CLS");
    c++;
    if(strcmp(request_type[c-2], request_type[c-1]) == 0)
   {
       strcpy(request_type[c-2], request_type[c-1]);
       request_amount[c-2] += request_amount[c-1];
       c--;
   }
    main();
}

int fulfillRequest(){

    flag = 0;
    printf("\n-------- Fulfilling Requests --------\n\n");
    count = 0;
    for(j = 0; j < c; j++)
    {
    for(i = 0; i < a; i++)
    {
        if(request_amount[j] == donation_amount[i])
            flag = 1;
        else if(donation_amount[i] > request_amount[j])
            flag = 2;
        if( strcmp(donation_type[i], request_type[j])==0)
        {
            count = 1;
            b = i;
        }
    }
    if(count == 0)
    {}
    if(request_amount[0] <= donation_amount[b])
    {
        donation_amount[b] -= request_amount[0];
        for(i = 0; i < c - 1; i++)
        {
            if (a == 0)
            {
                strcpy(request_type[i], request_type[i+1]);
                strcpy(request_amount[i], request_amount[i+1]);
            }
        }
    c--;
    }
    }
    if(flag == 1)
        printf(" Request fulfilled\n ");
    else if(flag == 2)
        printf(" Partially fulfilled\n ");
    else
        printf(" Cannot be fulfilled\n ");

    system("pause");
    system("CLS");
    main();
}


int printStatus(){

    d = 0;
    b = 0;
    printf("\n Printing the Donations Table\n ");
    for(i = 0; i < a; i++)
    {
          printf("\n %s %d \n ", donation_type[b], donation_amount[b]);
          b++;
    }
    printf("\n Printing the Requests Table\n ");
    for(i = 0; i < c; i++)
    {
          printf("\n %s %d \n ", request_type[d], request_amount[d]);
          d++;
    }
    printf("\n ");
    system("pause");
    system("CLS");
    main();
}
