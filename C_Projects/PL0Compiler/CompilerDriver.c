#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Parser.h"
#include "LexicalAnalyzer.h"
#include "VM.h"

FILE *lex;
char token[30];

int main(int argc, char *argv[])
{
	/** Lexical Analysis:
	 * input  - input.txt (assembly code)
	 * output - cleaninput.txt (code without comments)
	 * output - lexemetable.txt (each lexeme with its respective token type)
	 * output - lexemelist.txt (converted code into token type)
	 */
	analyze();


	/** Parser, Code Generation, and Symbol Table:
	 * input  - lexemelist.txt (converted code into token type)
	 * output - tokenOutput.txt (outputs lexemelist.txt and any errors in code)
     * output - mcode.txt (holds the intermediate code for the program)
     * output - symlist.txt ()
	 */
    int error = parse();

    if(error == 1)
    {
        printf("\n\nErrors were encountered, program terminated!\n");
        exit(-1);
    }


	/** Virtual Machine:
	 * input  - mcode.txt (VM/0 code generation)
	 * output - stacktrace.txt (shows variable manipulation and stack changes when executing each instruction)
	 */
    run();


    /** Prints to terminal
     * -1 : print out the list of lexemes
     * -a : print out the generated assembly code
     */
    if(strcmp(argv[1], "-l") == 0)
    {
        lex = fopen("lexemelist.txt", "r");
        while(fscanf(lex, "%s", token) == 1)
        {
            //fscanf(lex, "%s", token);
            printf("%s ", token);
        }
        fclose(lex);
    }

    else if(strcmp(argv[1], "-a") == 0) 
    {
        lex = fopen("mcode.txt", "r");
        while(fscanf(lex, "%s", token) == 1)
        {
            printf("%s ", token);
            fscanf(lex, "%s", token);
            printf("%s ", token);
            fscanf(lex, "%s", token);
            printf("%s\n", token);
        }
        fclose(lex);
    }

    else
    {
        printf("\n\nInvalid command.\n");
        exit(-1);
    }

    printf("\n\nYour code was successfully converted! No errors to report.\n");
    return 0;
}
