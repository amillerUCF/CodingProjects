/*
 * This is an image processing program that inputs a PGM image file
 * and allows the user to manipulate the picture from 3 different
 * options:
 * - Brightness: This is obvious; just changing the brightness of the picture
 * - Subtract: This allows the user to subtract two pictures and output one
 *   that is the first picture minus the pixel values from the second.
 * - Sobel Filter: Shows edge detection by highlighting the edges of
 *   a picture by using the sobel operator.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>

#define  BUFFER_SIZE  70
#define  TRUE          1
#define  FALSE         0

int    option;
int**  sobelout;
int** temppicx, temppicy;
int**  img;
int**  img2;
int    numRows;
int    numCols;
int    maxVal;
FILE*  fo1;

void  writeoutpic(char* fileName, int** imgtemp);
int** readpic(char* fileName);
void  readHeader(FILE* imgFin);
int   isComment(char* line);
void  readImgID(char* line);
void  readImgSize(char* line);
void  readMaxVal(char* line);
int** setImage();
void  readBinaryData(FILE* imgFin, int** imgtemp);

// Option Functions
void brightness(int** imgtemp);
int subtractPics(int** imgtemp);
void sobelfunc(int** pic, int** edges, int** tempx, int** tempy);

int main()
{
        char fileName[BUFFER_SIZE];
        int i,j,rows,cols;
        char ci;

        // Menu
        printf("1) Option 1 (Brightness)\n");
        printf("2) Option 2 (Subtract Pics)\n");
        printf("3) Option 3 (Sobel Edge Highlighter)\n");
        printf("Option: ");
        scanf("%d", &option);
        // Menu (end)

        printf("Enter image filename: ");
        scanf("%s", fileName);

        img = readpic(fileName);

        printf("Successfully read image file '%s'\n", fileName);
        // Option 1
        if(option == 1)
                brightness(img);
        // Option 2
        if(option == 2)
        {
                printf("Enter image filename: ");
                scanf("%s", fileName);

                img2 = readpic(fileName);
                printf("Successfully read image file '%s'\n", fileName);
                subtractPics(img);
        }
        // Option 3
        if(option == 3)
        {
                  sobelout= setImage();

                  temppicx= setImage();
                  temppicy= setImage();

                  sobelfunc(img,sobelout,temppicx,temppicy);
        }

        printf("Enter image filename for output: ");
        scanf("%s", fileName);
        if(option == 3)
                  writeoutpic(fileName,sobelout);
        else
                  writeoutpic(fileName,img);

        free(img);
        img = NULL;

        return(EXIT_SUCCESS);
}

void brightness(int** imgtemp)
{
        int value;
        printf("Brightness: ");
        scanf("%d",&value);
        int i,j;

        for (i=0;i<numRows;i++)
        { for (j=0;j<numCols;j++)
                {
                  imgtemp[i][j] += value;
                }
        }
}

int subtractPics(int** imgtemp)
{
    int i,j;
        for (i=0;i<numRows;i++)
        { for (j=0;j<numCols;j++)
          {
               imgtemp[i][j] = (abs(img[i][j] - img2[i][j]));
          }
        }
}

void sobelfunc(int** pic, int** edges, int** tempx, int** tempy){

        int maskx[3][3] = {{-1,0,1},{-2,0,2},{-1,0,1}};
        int masky[3][3] = {{1,2,1},{0,0,0},{-1,-2,-1}};
        int maxival;




        int i,j,p,q,mr,sum1,sum2;
        double threshold;


        mr = 1;


        for (i=mr;i<numRows-mr;i++)
        { for (j=mr;j<numCols-mr;j++)
          {
             sum1 = 0;
             sum2 = 0;
             for (p=-mr;p<=mr;p++)
             {
                for (q=-mr;q<=mr;q++)
                {
                   sum1 += pic[i+p][j+q] * maskx[p+mr][q+mr];
                   sum2 += pic[i+p][j+q] * masky[p+mr][q+mr];
                }
             }
             tempx[i][j] = sum1;
             tempy[i][j] = sum2;
          }
        }

        maxival = 0;
        for (i=mr;i<numRows-mr;i++)
        { for (j=mr;j<numCols-mr;j++)
          {
             edges[i][j]=(int) (sqrt((double)((tempx[i][j]*tempx[i][j]) +
                                      (tempy[i][j]*tempy[i][j]))));
             if (edges[i][j] > maxival)
                maxival = edges[i][j];

           }
        }



        for (i=0;i<numRows;i++)
          { for (j=0;j<numCols;j++)
            {
             edges[i][j] = ((edges[i][j]*1.0) / maxival) * 255;

            }
          }
}

void writeoutpic(char* fileName, int** imgtemp)
{
        int i,j;
        char ci;
        FILE* fo1;

        if((fo1 = fopen(fileName, "wb")) == NULL)
        {
                printf("Unable to open out image file '%s'\n", fileName);
                exit(EXIT_FAILURE);
        }

        fprintf(fo1,"P5\n");
        fprintf(fo1,"%d %d\n", numRows, numCols);
        fprintf(fo1,"255\n");

        for (i=0;i<numRows;i++)
        { for (j=0;j<numCols;j++)
                {
                  ci   =  (char) (imgtemp[i][j]);
                  fprintf(fo1,"%c", ci);
                }
        }
}

int** readpic(char* fileName)
{
        FILE* imgFin;
        int** imgtemp;

        if((imgFin = fopen(fileName, "rb")) == NULL)
        {
                printf("Unable to open image file '%s'\n", fileName);
                exit(EXIT_FAILURE);
        }

        readHeader(imgFin);


        imgtemp  = setImage();

        readBinaryData(imgFin, imgtemp);

        fclose(imgFin);

        return  imgtemp;

}

void readHeader(FILE* imgFin)
{
        int  haveReadImgID   = FALSE;
        int  haveReadImgSize = FALSE;
        int  haveReadMaxVal  = FALSE;
        char line[BUFFER_SIZE];

        while(!(haveReadImgID && haveReadImgSize && haveReadMaxVal))
        {
                fgets(line, BUFFER_SIZE, imgFin);

                if((strlen(line) == 0) || (strlen(line) == 1))
                        continue;

                if(isComment(line))
                        continue;

                if(!(haveReadImgID))
                {
                        readImgID(line);
                        haveReadImgID = TRUE;
                }
                else if(!(haveReadImgSize))
                {
                        readImgSize(line);
                        haveReadImgSize = TRUE;
                }
                else if(!(haveReadMaxVal))
                {
                        readMaxVal(line);
                        haveReadMaxVal = TRUE;
                }
        }

}

int isComment(char *line)
{
        if(line[0] == '#')
                return(TRUE);

        return(FALSE);
}

void readImgID(char* line)
{
        if(strcmp(line, "P5\n") != 0)
        {
                printf("Invalid image ID\n");
                exit(EXIT_FAILURE);
        }
}

void readImgSize(char* line)
{
        unsigned i;

        for(i = 0; i < strlen(line); ++i)
        {
                if(!((isdigit((int) line[i])) || (isspace((int) line[i]))))
                {
                        printf("Invalid image size\n");
                        exit(EXIT_FAILURE);
                }
        }

        sscanf(line, "%d %d", &numRows, &numCols);
}

void readMaxVal(char* line)
{
        unsigned i;

        for(i = 0; i < strlen(line); ++i)
        {
                if(!((isdigit((int) line[i])) || (isspace((int) line[i]))))
                {
                        printf("Invalid image max value\n");
                        exit(EXIT_FAILURE);
                }
        }

        maxVal = atoi(line);
}

int** setImage()
{
        int** imgtemp;
        unsigned i;

        imgtemp = (int**) calloc(numRows, sizeof(int*));

        for(i = 0; i < numRows; ++i)
        {
                imgtemp[i] = (int*) calloc(numCols, sizeof(int));
        }
        return imgtemp;
}

void readBinaryData(FILE* imgFin, int** imgtemp)
{
        unsigned  i;
        unsigned  j;
        for(i = 0; i < numRows; ++i)
        {
                for(j = 0; j < numCols; ++j)
                {
                            imgtemp[i][j] =
                            fgetc(imgFin);
                }
        }
}
