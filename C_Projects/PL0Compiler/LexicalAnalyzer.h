#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void lexemeTable(int c2);

FILE *input;
FILE *output;
FILE *output2;
FILE *output3;
char lexeme[30]; int counter = 0; // for lexemetable()

int analyze()
{
    char c, c2;
    int inComment = 0;
    input   = fopen("input.txt","r");
    output  = fopen("cleaninput.txt","w");
    output2 = fopen("lexemetable.txt","w");
    output3 = fopen("lexemelist.txt","w");


    //Placement of fscanf()'s in this sections are placed in odd spots to ignore an extra period that gets added to end of file
    /** Creating "cleaninput.txt" and "lexemetable.txt" **/

    fprintf(output2, "token\ttokentype\n");
    fscanf(input, "%c", &c);

    while(!feof(input))
    {
        if(c == '*' && inComment == 1) // if in comment and '*' is encountered
        {
            fscanf(input, "%c", &c2);
            if(c2 == '/') // end of comment was encountered
            {
                inComment = 0;
                fscanf(input, "%c", &c);
                continue;
            }
        }
        if(c == '/') // if '/' is encountered
        {
            fscanf(input, "%c", &c2);
            if(c2 == '*') // if '*', then we start comment
                inComment = 1;
            else if(inComment == 0)
            {
                fprintf(output, "%c", c);
                fprintf(output, "%c", c2);
                lexemeTable((int)c);
                lexemeTable((int)c2);
                fscanf(input, "%c", &c);
                continue;
            }
        }
        if(inComment == 0) // if not in comments, then print
        {
            fprintf(output, "%c", c);
            lexemeTable((int)c);
        }
        fscanf(input, "%c", &c);
    }

    fclose(input);
	fclose(output);
    fclose(output2);
	fclose(output3);
    return 0;
}

void lexemeTable(int c2)
{
    char c = (char)c2;
    if(newToken((int)c) == 0) // if there is not a new token, then exit
        return;

    /** All other characters **/

    char ch = 0;
    if(c == ' ' || c == '\n' || c == '\t' || (c >= 32 && c <= 47) || (c >= 58 && c <= 64) || (c >= 91 && c <= 96) || (c >= 123 && c <= 127)) // set [char array --> empty] and [counter --> 0]
    {
        /** Statements and Declarations **/

        if(strcmp(lexeme, "odd") == 0)
        {
            fprintf(output2, "odd\t8\n");
            fprintf(output3, "8 ");
        }
        else if(strcmp(lexeme, "<>") == 0)
        {
            fprintf(output2, "<>\t10\n");
            fprintf(output3, "10 ");
            if(c == '>')
                c = ' ';
        }
        else if(strcmp(lexeme, "<=") == 0)
        {
            fprintf(output2, "<=\t12\n");
            fprintf(output3, "12 ");
        }
        else if(strcmp(lexeme, ">=") == 0)
        {
            fprintf(output2, ">=\t14\n");
            fprintf(output3, "14 ");
        }
        else if(strcmp(lexeme, ":=") == 0) // ??? is this right ???
        {
            fprintf(output2, ":=\t20\n");
            fprintf(output3, "20 ");
        }
        else if(strcmp(lexeme, "begin") == 0)
        {
            fprintf(output2, "begin\t21\n");
            fprintf(output3, "21 ");
        }
        else if(strcmp(lexeme, "end") == 0)
        {
            fprintf(output2, "end\t22\n");
            fprintf(output3, "22 ");
        }
        else if(strcmp(lexeme, "if") == 0)
        {
            fprintf(output2, "if\t23\n");
            fprintf(output3, "23 ");
        }
        else if(strcmp(lexeme, "then") == 0)
        {
            fprintf(output2, "then\t24\n");
            fprintf(output3, "24 ");
        }
        else if(strcmp(lexeme, "while") == 0)
        {
            fprintf(output2, "while\t25\n");
            fprintf(output3, "25 ");
        }
        else if(strcmp(lexeme, "do") == 0)
        {
            fprintf(output2, "do\t26\n");
            fprintf(output3, "26 ");
        }
        else if(strcmp(lexeme, "call") == 0)
        {
            fprintf(output2, "call\t27\n");
            fprintf(output3, "27 ");
        }
        else if(strcmp(lexeme, "const") == 0)
        {
            fprintf(output2, "const\t28\n");
            fprintf(output3, "28 ");
        }
        else if(strcmp(lexeme, "var") == 0)
        {
            fprintf(output2, "var\t29\n");
            fprintf(output3, "29 ");
        }
        else if(strcmp(lexeme, "procedure") == 0)
        {
            fprintf(output2, "procedure\t30\n");
            fprintf(output3, "30 ");
        }
        else if(strcmp(lexeme, "write") == 0)
        {
            fprintf(output2, "write\t31\n");
            fprintf(output3, "31 ");
        }
        else if(strcmp(lexeme, "read") == 0)
        {
            fprintf(output2, "read\t32\n");
            fprintf(output3, "32 ");
        }
        else if(strcmp(lexeme, "else") == 0)
        {
            fprintf(output2, "else\t33\n");
            fprintf(output3, "33 ");
        }
        else if(strcmp(lexeme, " ") == 0 || strcmp(lexeme, "\t") == 0 || strcmp(lexeme, "") == 0)
            c = c;


        /** Digits or Identifiers **/

        else if(strcmp(lexeme, "+") == 1)
        {
            int numOfDigits = 0;
            while(isdigit(lexeme[ch]) == 1) // if char is a number
            {
                ch++;
                numOfDigits++;
            }
            if(numOfDigits == counter)
            {
                fprintf(output2, "%s\t3\n", lexeme);
                fprintf(output3, "3 %s ", lexeme);
            }
            else if(numOfDigits < counter && isdigit(lexeme[0]) != 1)
            {
                fprintf(output2, "%s\t2\n", lexeme);
                fprintf(output3, "2 %s ", lexeme);
            }
        }


        /** Special single-characters **/

        if(c == '+')
        {
            fprintf(output2, "+\t4\n");
            fprintf(output3, "4 ");
        }
        else if(c == '-')
        {
            fprintf(output2, "-\t5\n");
            fprintf(output3, "5 ");
        }
        else if(c == '*')
        {
            fprintf(output2, "*\t6\n");
            fprintf(output3, "6 ");
        }
        else if(c == '/')
        {
            fprintf(output2, "/\t7\n");
            fprintf(output3, "7 ");
        }
        else if(c == '=' && lexeme[0] != ':')
        {
            fprintf(output2, "=\t9\n");
            fprintf(output3, "9 ");
        }
        else if(c == '<')
        {
            fprintf(output2, "<\t11\n");
            fprintf(output3, "11 ");
        }
        else if(c == '>')
        {
            fprintf(output2, ">\t13\n");
            fprintf(output3, "13 ");
        }
        else if(c == '(')
        {
            fprintf(output2, "(\t15\n");
            fprintf(output3, "15 ");
        }
        else if(c == ')')
        {
            fprintf(output2, ")\t16\n");
            fprintf(output3, "16 ");
        }
        else if(c == ',')
        {
            fprintf(output2, ",\t17\n");
            fprintf(output3, "17 ");
        }
        else if(c == ';')
        {
            fprintf(output2, ";\t18\n");
            fprintf(output3, "18 ");
        }
        else if(c == '.')
        {
            fprintf(output2, ".\t19\n");
            fprintf(output3, "19 ");
        }
        counter = 0;
        memset(&lexeme[0], 0, sizeof(lexeme));
        if(c == ':' | (c >= 48 && c <= 57))
            lexeme[0] = c;
    }
}

int newToken(int ch)
{
    int c = (char) ch;
    if(c == '<')
    {
        lexeme[0] = c;
        return 0;
    }
    if(lexeme[0] == ':' && c == '=')
    {
        lexeme[1] = c;
        return 1;
    }

    if(lexeme[0] == '<')
    {
        if(c == '>')
        {
            lexeme[1] = c;
            return 1;
        }
        else
        {
            fprintf(output2, "<\t11\n");
            fprintf(output3, "11 ");
            counter = 0;
            memset(&lexeme[0], 0, sizeof(lexeme)); 
            return 0;
        }
    }

    if( c == '\n' || c == '\t' || c == ';' || (c >= 32 && c <= 47) || (c >= 58 && c <= 64) || (c >= 91 && c <= 96) || (c >= 123 && c <= 127)) // end of token
        return 1; // there is a new token
    lexeme[counter] = c; // add char to token
    counter++; // go to next index
    return 0; // there was no token
}
