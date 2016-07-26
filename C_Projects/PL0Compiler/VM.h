/*
 * Austin Miller
 * COP 3402: Homework 1
 * 6/3/2016
 */

#include <stdio.h>
#include <stdlib.h>

int MAX_STACK_HEIGHT = 2000;
int MAX_CODE_LENGTH = 500;
int count = 0;
int flag = 0; // notifys program when halt is hit
int bp = 1;
int sp = 0;

struct instruction
{
    int op; // opcode
    int l; // L
    int m; // M
};

int run()
{
    int stack[MAX_STACK_HEIGHT]; // this is the stack
    int val1, val2, val3, val4;// values from input file
    int i = 0; // counter for while-loop
    char *instructions[] = {"LIT","OPR","LOD","STO","CAL","INC","JMP","JPC","SIO","SIO","SIO"};
    struct instruction ins[MAX_CODE_LENGTH]; // array of structures to hold all instructions to be executed

    FILE *input = fopen("mcode.txt","r");
    FILE *output = fopen("stacktrace.txt","w");

    /**Output the first portion of data to file "stacktrace.txt"**/
    fprintf(output,"LINE\tOP\tL\tM\n==========================\n");

    while(fscanf(input, "%d %d %d", &val1, &val2, &val3) != EOF)
    {
        //fscanf(input, "%d", &val1);
        ins[i].op = val1;
        fprintf(output, "%d\t%s\t", i, instructions[val1-1]);

        //fscanf(input, "%d", &val1);
        ins[i].l = val2;
        fprintf(output, "%d\t", val2);

        //fscanf(input, "%d", &val1);
        ins[i].m = val3;
        fprintf(output, "%d\n", val3);

        i++;
    }

    int instructNum = i-1; // holds number of instructions in file
    i = 0; // reset counter

    /**Output the second portion of data to file "stacktrace.txt"**/

    int activateIndexes[500]; /* holds the end of each activation record */ int j = 0; // counter for activateIndexes
    int spIsLower = 0; // set to true when the sp starts less than activation record split
    int lastInstruction = 0; // set to true when the final instruction was reached

    fprintf(output, "\n\n\t\t\t\tPC\tBP\tSP\tSTACK\n");
    fprintf(output, "INITIAL VALUES\t\t\t%d\t%d\t%d\n", count, bp, sp);

    while(1)
    {
        int userInput; // user input for instruction SIO
        int l = ins[count].l; // holds 'l' value for selected instruction
        int m = ins[count].m; // holds 'm' value for selected instruction
        int op = ins[count].op-1; // holds opcode for selected instruction
        fprintf(output, "%d\t%s\t%d\t%d\t", count, instructions[op], l, m);

        if(count == instructNum) // notify program that this is the last instruction to be executed
            lastInstruction = 1;

        count++; // increments line counter
        switch(op)
        {
            case 0: // "LIT"
                sp++;
                stack[sp] = m;
                break;


            case 1: // "OPR"
                if(m == 0)
                {
                    sp = bp - 1;
                    count = stack[sp + 4];
                    bp = stack[sp + 3];
                    int c = 0;
                    while(1)
                    {
                        if(activateIndexes[c] == sp)
                        {
                            activateIndexes[c] = 0;
                            break;
                        }
                        c++;
                    }
                }
                else if(m == 1)
                {
                    stack[sp] = -stack[sp];
                }
                else if(m == 2)
                {
                    sp--;
                    stack[sp] = stack[sp] + stack[sp + 1];
                }
                else if(m == 3)
                {
                    sp--;
                    stack[sp] = stack[sp] - stack[sp + 1];
                }
                else if(m == 4)
                {
                    sp--;
                    stack[sp] = stack[sp] * stack[sp + 1];
                }
                else if(m == 5)
                {
                    sp--;
                    stack[sp] = stack[sp] / stack[sp + 1];
                }
                else if(m == 6)
                {
                    stack[sp] = stack[sp] % 2;
                }
                else if(m == 7)
                {
                    sp--;
                    stack[sp] = stack[sp] % stack[sp + 1];
                }
                else if(m == 8)
                {
                    sp--;
                    stack[sp] = stack[sp] == stack[sp + 1];
                }
                else if(m == 9)
                {
                    sp--;
                    stack[sp] = stack[sp] != stack[sp + 1];
                }
                else if(m == 10)
                {
                    sp--;
                    stack[sp] = stack[sp] < stack[sp + 1];
                }
                else if(m == 11)
                {
                    sp--;
                    stack[sp] = stack[sp] <= stack[sp + 1];
                }
                else if(m == 12)
                {
                    sp--;
                    stack[sp] = stack[sp] > stack[sp + 1];
                }
                else if(m == 13)
                {
                    sp--;
                    stack[sp] = stack[sp] >= stack[sp + 1];
                }
                break;


            case 2: // "LOD"
                sp++;
                stack[sp] = stack[base(l, bp, stack) + m];
                break;


            case 3: // "STO"
                stack[base(l, bp, stack) + m] = stack[sp];
                sp--;
                break;


            case 4: // "CAL"
                stack[sp + 1] = 0;
                stack[sp + 2] = base(l, bp, stack);
                stack[sp + 3] = bp;
                stack[sp + 4] = count;
                bp = sp + 1;
                count = m;
                break;


            case 5: // "INC"
                if(sp > 0)
                {
                    activateIndexes[j] = sp; // so the '|' can be outputted correctly
                    j++;
                }
                sp = sp + m;
                break;


            case 6: // "JMP"
                count = m;
                break;


            case 7: // "JPC"
                if(stack[sp] == 0)
                    count = m;
                sp--;
                break;


            case 8: // "SIO" for m == 1
                printf("SIO: %d\n", stack[sp]);
                sp--;
                break;


            case 9: // "SIO" for m == 2
                sp++;
                printf("\nNumber to input: ");
                scanf("%d", &userInput);
                stack[sp] = userInput;
                break;


            case 10:
                fprintf(output, "\nSuccessfully halted.\n");
                flag = 1;
                break;


        }
        if(flag == 0)
        {
            fprintf(output, "%d\t%d\t%d\t", count, bp, sp);
            int i = 1;
            int k = 0;
            for(i; i <= sp; i++)
            {
                if(i == activateIndexes[k]+1 && sp > activateIndexes[k]+1 && activateIndexes[k] != 0)
                {
                    fprintf(output, "| ");
                    k++;
                }
                fprintf(output, "%d ", stack[i]);
            }
            fprintf(output, "\n");
        }
        if(lastInstruction == 1)
            break;
    }
    return 0;
}

int base(int l, int base, int stack[])
{
    int b1 = base;
    while(l > 0)
    {
        b1 = stack[b1 + 1];
        l--;
    }
    return b1;
}
