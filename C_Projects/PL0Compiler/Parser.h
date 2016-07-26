#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_SYMBOL_TABLE_SIZE 100
#define MAX_CODE_SIZE 500


/** Function Prototypes **/
//int parser();
void program();
void block();
void statement();
void expression();
void term();
void factor();
void condition();
void error(char* s);
void printLexeme();
void get();
void emit(int op, int level, int modifier);
void addSymbol(int kind, char name[], int val, int level, int addr);
void printMcode();
void printSymbolTable();


/** Symbol Data Structure **/
typedef struct symbol
{
    int kind; // const = 1, var = 2, proc = 3
    char name[12]; // name up to 11 chars
    int val; // number (ASCII value)
    int level; // L level
    int addr; // M address
} symbol;


/** Code Generation Data Structure **/
typedef struct
{
    int op; // opcode
    int l; // level
    int m; // modifier
} curr_instruction;


/** Global Variables **/
symbol symbol_table[MAX_SYMBOL_TABLE_SIZE];
curr_instruction instructions[MAX_CODE_SIZE];
int pc = 0;
int var = 0;
int cur_level = 0; // Current lexicographical level
int total_sym = 0; //Total number of symbols in symbol table
int errorDetect = 0; // 1 if detected error
FILE *input;
FILE *output;
char token[30];


/** Main of Parser **/
int parse()
{
    /* Variable declarations */
    input = fopen("lexemelist.txt", "r");
    output = fopen("tokenOutput.txt", "w");

    fprintf(output, "1.-\n\n"); // Prints out first section of output

    printLexeme();

    fprintf(output, "\n\n2.- "); // Prints out second section of output

    program();

    fclose(input);

    /* Check if errors */
    if(errorDetect == 0) // no errors were encountered
    {
        fprintf(output, "No errors, program is syntactically correct!!!");
        fclose(output);
        printMcode();
        printSymbolTable();
        return 0;
    }

    fclose(output);
    printMcode();
    printSymbolTable();
    return 1;
}


void program()
{
    //var = 4;
    get(); // Grabs next token
    block(); // Call block section
    if(strcmp(token, "18") == 0)
    {
        get(); // Grabs next token
        block(); // Call block section
    }
    else if(strcmp(token, "19") != 0)
        error("Period expected.\n");
    emit(11, 0, 3); // Emit a halt instruction
}


/** Detects errors in blocks **/
void block()
{
    var = 4;
    cur_level++;
    int jump_addr = pc;
    char name[12]; //Symbol name
    int val;       //Number (ASCII value)
    emit(7, 0, 0); //Generate a jmp instruction

    //If token is constsym
    if(strcmp(token, "28") == 0)
    {
        do
        {
            get();
            if(strcmp(token, "2") != 0) //If token is not identsym
                error("const must be followed by identifier.\n");
            get(); //Get token name
            strcpy(name, token); // Set token name
            get();
            if(strcmp(token, "9") != 0) //If token is not eqlsym
                error("Identifier must be followed by =.\n");
            get();
            if(strcmp(token, "3") != 0) //If token is not a number
                error("= must be followed by a number.\n");
            get();
            val = atoi(token); //Set token value
            addSymbol(1, name, val, cur_level, 0); //Add token to symbol table
            get();
        }while(strcmp(token, "17") == 0); //Repeat until token is not a commasym

        if(strcmp(token, "18") != 0) //If token is not a semicolonsym
            error("Semicolon or comma missing.\n");
        get();
    }

    //If token is varsym
    if(strcmp(token, "29") == 0)
    {
        int sym_addr = 4;
        do
        {
            get();
            if(strcmp(token, "2") != 0) //If token is not identsym
                error("var must be followed by identifier.\n");
            get();
            strcpy(name, token);
            addSymbol(2, name, 0, cur_level, sym_addr++);
            var++;
            get();
        }while(strcmp(token, "17") == 0);

        if(strcmp(token, "18") != 0)
            error("Semicolon or comma missing.\n");
        get();
    }

    //If token is procsym
    while(strcmp(token, "30") == 0)
    {
        get();
        if(strcmp(token, "2") != 0) //If token is not identsym
            error("procedure must be followed by identifier.\n");
        get();
        strcpy(name, token);
        addSymbol(3, name, 0, cur_level, pc);
        get(); 
        if(strcmp(token, "18") != 0)
            error("Semicolon or comma missing.\n");
        get();
        block();

        /* Check for a period after "end" */
        if(strcmp(token, "19") == 0)
            return;

        if(strcmp(token, "18") != 0)
            error("Incorrect symbol after procedure declaration.\n");
        get();
        emit(2, 0, 0);
    }

    instructions[jump_addr].m = pc;

    emit(6, 0, var);
    var++;
    statement();

    cur_level--;
}


/** Detects errors in statements **/
void statement()
{
    int sym_index;
    if(strcmp(token, "2") == 0) //If token is identsym
    {
        get();
        sym_index = searchSymbolTable(token);
        if (sym_index == -1)
            error("Undeclared identifier.\n");
        if(symbol_table[sym_index].kind != 2)
            error("Assignment operator expected.\n");
        get(); //ADDED IN
        if(strcmp(token, "20") != 0)
            error("Assignment operator expected.\n");
        get();
        expression();
        emit(4, cur_level - symbol_table[sym_index].level, symbol_table[sym_index].addr);
    }
    else if(strcmp(token, "27") == 0) //If token is callsym
    {
        get();
        if(strcmp(token, "2") != 0)
            error("call must be followed by an identifier.\n");
        get();
        sym_index = searchSymbolTable(token);
        if (sym_index == -1)
            error("Procedure not declared.\n");
        emit(5, cur_level - symbol_table[sym_index].level, symbol_table[sym_index].addr);
        get();
    }
    else if(strcmp(token, "21") == 0) //If token is beginsym
    {
        get();
        statement();
        while(strcmp(token, "18") == 0)
        {
            get();
            statement();
        }
        if(strcmp(token, "22") != 0)
            error("end expected.\n");
        get();
    }
    else if(strcmp(token, "23") == 0) //If token is ifsym
    {
        get();
        condition();
        if(strcmp(token, "24") != 0)
            error("then expected.\n");
        int then_statement = pc;
        emit(8, 0, 0);
        get();
        statement();

        /* Check for "else" */
        if(strcmp(token, "33") != 0)
        {
            emit(7, 0, pc+1);
            instructions[then_statement].m = pc;
        }

        if(strcmp(token, "33") == 0)
        {
            int else_statement = pc;
            emit(7, 0, 0);
            instructions[then_statement].m = pc;
            get();
            statement();
            instructions[else_statement].m = pc;
        }

    }
    else if(strcmp(token, "25") == 0) //If token is whilesym
    {
        get();
        int pc_before = pc;
        condition();
        int pc_after = pc;
        emit(8, 0, 0);
        if(strcmp(token, "26") != 0)
            error("do expected.\n");
        get();
        statement();
        emit(7, 0, pc_before);
        instructions[pc_after].m = pc;
    }
    else if(strcmp(token, "31") == 0) //If token is writesym
    {
        get();
        if(strcmp(token, "2") != 0)
            error("write must be followed by an identifier.\n");
        get();
        sym_index = searchSymbolTable(token);
        if (sym_index == -1)
            error("Undeclared identifier.\n");
        if(symbol_table[sym_index].kind == 1)
        {
            emit(1, 0, symbol_table[sym_index].val);
            emit(9, 0, 1);
        }
        else if(symbol_table[sym_index].kind == 2)
        {
            emit(3, cur_level - symbol_table[sym_index].level, symbol_table[sym_index].addr);
            emit(9, 0, 1);
        }
        else
            error("Write of a procedure is not allowed.\n");
        get();
    }
    else if(strcmp(token, "32") == 0) //If token is readsym
    {
        get();
        if(strcmp(token, "2") != 0)
            error("read must be followed by an identifier.\n");
        get();
        sym_index = searchSymbolTable(token);
        if (sym_index == -1)
            error("Undeclared identifier.\n");
        if(symbol_table[sym_index].kind != 2)
            error("Assignment to constant or procedure is not allowed.\n");
        emit(10, 0, 2);
        emit(4, cur_level - symbol_table[sym_index].level, symbol_table[sym_index].addr);
        get();
    }
}


/** Detects errors in expressions **/
void expression()
{
    if(strcmp(token, "4") == 0 || strcmp(token, "5") == 0)
    {
        char temp[12]; 
        strcpy(temp, token);
        get();
        term();
        if(strcmp(temp, "5") == 0)
            emit(2, 0, 1);
    }
    else
        term();

    while(strcmp(token, "4") == 0 || strcmp(token, "5") == 0)
    {
        char temp[12]; 
        strcpy(temp, token);
        get();
        term();

        if(strcmp(temp, "4") == 0)
            emit(2, 0, 2);
        else
            emit(2, 0, 3);
    }
}


/** Terms **/
void term()
{
    factor();
    while(strcmp(token, "6") == 0 || strcmp(token, "7") == 0)
    {
        char temp[12]; 
        strcpy(temp, token);
        get();
        factor();

        if(strcmp(temp, "6") == 0)
            emit(2, 0, 4);
        else
            emit(2, 0, 5);
    }
}


/** Factors **/
void factor()
{
    int sym_index = 0;
    if(strcmp(token, "2") == 0)
    {
        get();
        sym_index = searchSymbolTable(token);
        if (sym_index == -1)
            error("Undeclared identifier.\n");
		if(symbol_table[sym_index].kind == 1)
            emit(1, 0, symbol_table[sym_index].val);
        else if(symbol_table[sym_index].kind == 2)
            emit(3, cur_level - symbol_table[sym_index].level, symbol_table[sym_index].addr);
        else
            error("Expression must not contain a procedure identifier.\n");
        get();
    }
    else if(strcmp(token, "3") == 0)
    {
        get();
        int number = atoi(token);
        emit(1, 0, number);
        get();
    }
    else if(strcmp(token, "15") == 0)
    {
        get();
        expression();
        if(strcmp(token, "16") != 0)
            error("Right parenthesis missing.\n");
        get();
    }
    else
        error("An expression cannot begin with this symbol.\n");
}


/** Detects errors in conditional statements **/
void condition()
{
    char temp[2];
    if(strcmp(token, "8") == 0)
    {
        get();
        expression();
        emit(2, 0, 6);
    }
    else
    {
        expression();
        if(strcmp(token, "9") != 0 && strcmp(token, "10") != 0 && strcmp(token, "11") != 0 && strcmp(token, "13") != 0 && strcmp(token, "12") != 0 && strcmp(token, "14") != 0)
            error("Relational operator expected.\n");
        strcpy(temp, token);
        get();
        expression();
        if(strcmp(temp, "9") == 0)
            emit(2, 0, 8);
        else if(strcmp(temp, "10") == 0)
            emit(2, 0, 9);
        else if(strcmp(temp, "11") == 0)
            emit(2, 0, 10);
        else if(strcmp(temp, "12") == 0)
            emit(2, 0, 11);
        else if(strcmp(temp, "13") == 0)
            emit(2, 0, 12);
        else if(strcmp(temp, "14") == 0)
            emit(2, 0, 13);
    }
}


/** Prints out error message and exits parser **/
void error(char* s)
{
    errorDetect = 1; // an error was encountered
    fprintf(output, "\nError: %s\n", s);
    printf("\nError: %s\n", s);
}


/** Prints out the lexemelist.txt file and resets scanner **/
void printLexeme()
{
    char c[30];
    while(!feof(input))
    {
        fscanf(input, "%s", c);
        fprintf(output, "%s ", c);
    }

    /* Reset scanner */
    fclose(input);
    input = fopen("lexemelist.txt", "r");
}


/** Gets token from lexemelist.txt **/
void get()
{
    char c[30];
    fscanf(input, "%s", c);
    strcpy(token, c);
}

void emit(int op, int level, int modifier)
{
    if(pc >= MAX_CODE_SIZE)
        error("Generated code exceeds max code size.\n");

    instructions[pc].op = op;
    instructions[pc].l = level;
    instructions[pc].m = modifier;
    pc++;
}

/** Adds token to symbol table **/
void addSymbol(int kind, char name[], int val, int level, int addr)
{
    symbol_table[total_sym].kind = kind;
    strcpy(symbol_table[total_sym].name, name);
    symbol_table[total_sym].val = val;
    symbol_table[total_sym].level = level;
    symbol_table[total_sym].addr = addr;
    total_sym++;
}

int searchSymbolTable(char name[])
{
    int i;
    for(i = total_sym; i >= 0; i--)
    {
        if(strcmp(name, symbol_table[i].name) == 0)
            return i;
    }

    return -1;
}

void printMcode()
{
    int i;

    output = fopen("mcode.txt", "w");

    for(i = 0; i < pc; i++)
        fprintf(output, "%d\t%d\t%d\n", instructions[i].op, instructions[i].l, instructions[i].m);

    fclose(output);
}

void printSymbolTable()
{
    int i;

    output = fopen("symlist.txt", "w");

    fprintf(output, "Name\tType\tLevel\tValue\n");
    for(i = 0; i < total_sym; i++)
    {
        if(symbol_table[i].kind == 1) //If symbol is a constant
            fprintf(output, "%s\tconst\t0\t%d\n", symbol_table[i].name, symbol_table[i].val);
        else if(symbol_table[i].kind == 2) //If symbol is a variable
            fprintf(output, "%s\tvar\t%d\t%d\n", symbol_table[i].name, symbol_table[i].level - 1, symbol_table[i].addr);
        else if(symbol_table[i].kind == 3) //If symbol is a procedure
            fprintf(output, "%s\tproc\t%d\t%d\n", symbol_table[i].name, symbol_table[i].level - 1, symbol_table[i].addr);
    }
    fclose(output);
}
