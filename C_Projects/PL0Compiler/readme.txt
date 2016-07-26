Names:
Austin Miller
Camilo Junoy

/**"How to compile and run project?"**/
 1) Create a new "input.txt" in the same directory as "CompilerDriver.c" if it does not exist in the folder already. 2) Put the code you wish to compile into "input.txt".
 3) Open up any terminal or Eustis.
 4) Change directory to same location as "CompilerDriver.c" if needed.
 5) Type in the following to compile: "gcc -o <name of application> CompilerDriver.c".
 6) Now you have 2 choices to run the program:
	- "./<name of application> -l". /* This prints out the lexemelist
	- "./<name of application> -a". /* This prints out the intermediate code

<NOTE>
Depending on what terminal you are using, you may not need the "./" before the application name when running.

<NOTE>
Remember that the .h files must be in same directory as "CompilerDriver.c"