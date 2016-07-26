/*
 * This program uses information from multiple workers in order
 * to construct W-2 forms for each workers based on their hours
 * in a week and their pay per hour. All data is stored within
 * an array of structs where each employee has their own struct.
 * The data is first gathered from an input file and the W-2 forms
 * are outputted into a new file that would be called "w2.txt".
 */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define MAX_LEN 30
#define SIZE 20

FILE *fp1;
FILE *fp2;

struct employee {
    const char first[MAX_LEN];
    const char last[MAX_LEN];
    const double payperhr;
    double gross;
    double taxes;
    double hours_in_week;
};

double numHours(double hour1, double hour2, double min1, double min2);
double GP(double hours, double payPerHour);
double TAX(double hours, double payPerHour);

int main()
{
    double hour1, hour2, min1, min2;
    int n, i, j, k, c1, c2;
    double netPay;

    // Opens files
    fp1 = fopen("clock.txt", "r");
    fp2 = fopen("w2.txt", "w");
    //

    fscanf(fp1, "%d", &n);

    // Declares a separate structures for each employee
    struct employee e[SIZE];
    //
    // Counter for the total hours of each employee
    double TOTAL_HOURS[SIZE];
    //

    fprintf(fp2, "Number of employees: %d\n\n", n);

    // Grabs NAME and HOURLYWAGE for each employee
    for(i = 0; i < n; i++)
    {
        // Sets all hours in week to zero
        e[i].hours_in_week = 0;
        //
        fscanf(fp1, "%s" "%s", &e[i].first, &e[i].last);
        fscanf(fp1, "%lf", &e[i].payperhr);
    }
    //

    fscanf(fp1, "%d", &c1);

    // Variables to compare names
    char first2[MAX_LEN];
    char last2[MAX_LEN];
    //

    // Each Week
    for(i = 0; i < c1; i++)
    {
          fscanf(fp1, "%d", &c2);
          // Number of shifts
          for(j = 0; j < c2; j++)
          {
              fscanf(fp1, "%s" "%s", &last2, &first2);
              // Loop repeats for each employee
              for(k = 0; k < n; k++)
              {
                  if((strcmp(first2, e[k].first) == 0) && (strcmp(last2, e[k].last) == 0))
                  {
                        fscanf(fp1, "%lf %lf %lf %lf", &hour1, &min1, &hour2, &min2);
                        e[k].hours_in_week += numHours(hour1, hour2, min1, min2);
                  }
              }
              //
          }
          //
          // Finds gross pay and taxes for each employee
          double hours;
          double payPerHour;
          for(k = 0; k < n; k++)
          {
              hours = e[k].hours_in_week;
              payPerHour = e[k].payperhr;
              e[k].gross += GP(hours, payPerHour); // Error e[1].gross sent back as wrong number
              e[k].taxes += TAX(hours, payPerHour);
              TOTAL_HOURS[k] += e[k].hours_in_week;
              e[k].hours_in_week = 0;
              hours = 0;
          }
    //
    }
    //

    // Prints out the W2 forms
    for(i = 0; i < n; i++)
    {
        fprintf(fp2, "W2 Form\n-------\n");
        fprintf(fp2, "Name: %s %s\n", e[i].first, e[i].last);
        fprintf(fp2, "Gross Pay: $%.2lf\n", e[i].gross);
        fprintf(fp2, "Taxes Withheld: $%.2lf\n", e[i].taxes);
        // Calculates net pay
        netPay = e[i].gross - e[i].taxes;
        //
        fprintf(fp2, "Net Pay: $%.2lf\n\n\n", netPay);
    }
    //

    printf("All W2 forms were created successfully!\n");
    system("pause");
    return 0;
}

// Function calculates number of hours for each employee
double numHours(double hour1, double hour2, double min1, double min2)
{
    double minsTotal;
    double hoursTotal;
    if(min1 > min2)
    {
        minsTotal = (60 - min1) + min2;
        hoursTotal = (hour2 - hour1) - 1;
    }
    else
    {
        // Total minutes
        minsTotal = min2 - min1;
        // Total hours
        hoursTotal = hour2 - hour1;
    }
    hoursTotal += minsTotal/60;
    return hoursTotal;
}

// Function calculate gross pay for each employee
double GP(double hours, double payPerHour)
{
    double grossPay;
    if(hours > 40)
        grossPay = ((hours - 40)*(1.5)*(payPerHour)) + (40*payPerHour);
    else
        grossPay = hours*payPerHour;
    return grossPay;
}

// Function calculates tax withheld for each employee
double TAX(double hours, double payPerHour)
{
    double taxesWW;
    if(hours > 40)
        taxesWW = (((hours - 40)*(1.5)*(payPerHour))*.20) + ((40*payPerHour)*.10);
    else
        taxesWW = (hours*payPerHour)*.10;
    return taxesWW;
}
