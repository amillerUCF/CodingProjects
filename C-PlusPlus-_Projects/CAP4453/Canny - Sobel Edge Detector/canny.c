/** canny.c - Applies the canny edge detection to an image
* and outputs three different images showing different results
*
* Running canny.c:
* 1. gcc -g -o canny.c canny
* 2. canny garb34.pgm cannymag.pgm cannypeaks.pgm cannyfinal.pgm 1.0 5
*/

#include <stdlib.h>
#include <stdio.h>            
#include <math.h>
#define  PICSIZE 256
#define  MAXMASK 100

/* Function Prototypes */
void   GaussianBlur();
void double_threshold(int i, int j);

/* Global Variables */
int    hi, lo;
int    pic[PICSIZE][PICSIZE]; // image
int    edgeflag[PICSIZE][PICSIZE]; // 'peaks' operation of image
double xmask[MAXMASK][MAXMASK]; // x gradient of "pic"
double ymask[MAXMASK][MAXMASK]; // y gradient of "pic"
double xconv[PICSIZE][PICSIZE]; // x convolution of "pic" and "xmask"
double yconv[PICSIZE][PICSIZE]; // y convolution of "pic" and "ymask"
double ival[PICSIZE][PICSIZE], maxival; // 'magnitude' operation of image
int    gausMask[3][3] = { { 3,6,3 },{ 6,12,6 },{ 3,6,3 } }, maxVal = 48; // Gaussian blur kernel
int    threshold_pic[PICSIZE][PICSIZE]; // pic for "double-thresholding"

void main(argc, argv)
int argc;
char **argv;
{
    int     i, j, p, q, mr, sig, centx, centy, areaOfTops = 0;
    double  xsum, ysum, maxival, slope, percent, cutoff, maskvalx, maskvaly;
    FILE    *fo1, *fo2, *fo3, *fp1, *fopen();
    char    *foobar;
    long int histogram[256];

    // Input file
    argc--; argv++;
    foobar = *argv;
    fp1 = fopen(foobar, "rb");

    // Magnitude output file
    argc--; argv++;
    foobar = *argv;
    fo1 = fopen(foobar, "wb");

    // Peaks output file
    argc--; argv++;
    foobar = *argv;
    fo2 = fopen(foobar, "wb");

    // Final output file
    argc--; argv++;
    foobar = *argv;
    fo3 = fopen(foobar, "wb");

    // Sigma parameter
    argc--; argv++;
    foobar = *argv;
    sig = atoi(foobar);

    // Percent parameter
    argc--; argv++;
    foobar = *argv;
    percent = atoi(foobar);

    mr = (int)(sig * 3);
    centx = (MAXMASK / 2);
    centy = (MAXMASK / 2);

    // Add file headers
    fprintf(fo1, "P5\n256 256\n255\n");
    fprintf(fo2, "P5\n256 256\n255\n");
    fprintf(fo3, "P5\n256 256\n255\n");

    // Throw away offset of image
    char throwaway[80];
    fgets(throwaway, 80, fp1);
    fgets(throwaway, 80, fp1);
    fgets(throwaway, 80, fp1);
    if (!((throwaway[0] == '2') && (throwaway[1] == '5') && (throwaway[2] == '5')))
        fgets(throwaway, 80, fp1);

    // Get and store pixels values into pic[][]
    for (i = 0; i < 256; i++)
        for (j = 0; j < 256; j++)
            pic[i][j] = getc(fp1);

    // (Optional) Put a Gaussian blur filter on the picture
    //GaussianBlur();

    // Compute the x- and y- derivatives
    for (p = -mr; p <= mr; p++)
    {
        for (q = -mr; q <= mr; q++)
        {
            maskvalx = (p*(exp(-1 * (((p*p) + (q*q)) / (2 * (sig*sig))))));
            maskvaly = (q*(exp(-1 * (((p*p) + (q*q)) / (2 * (sig*sig))))));

            (xmask[p + centy][q + centx]) = maskvalx;
            (ymask[p + centy][q + centx]) = maskvaly;
        }
    }

    // Convolution
    for (i = mr; i <= (255 - mr); i++)
    {
        for (j = mr; j <= (255 - mr); j++)
        {
            xsum = 0;
            ysum = 0;
            for (p = -mr; p <= mr; p++)
            {
                for (q = -mr; q <= mr; q++)
                {
                    xsum += pic[i + p][j + q] * xmask[p + centy][q + centx];
                    ysum += pic[i + p][j + q] * ymask[p + centy][q + centx];
                }
            }
            xconv[i][j] = xsum;
            yconv[i][j] = ysum;
        }
    }

    // Sqrt (of squares) code from sobel
    maxival = 0;
    for (i = mr; i < (256 - mr); i++)
    {
        for (j = mr; j < (256 - mr); j++)
        {
            ival[i][j] = sqrt((double)((xconv[i][j] * xconv[i][j]) +
                (yconv[i][j] * yconv[i][j])));
            if (ival[i][j] > maxival)
                maxival = ival[i][j];
        }
    }

    // Print out file 1
    for (i = 0; i < 256; i++)
    {
        for (j = 0; j < 256; j++)
        {
            ival[i][j] = (ival[i][j] / maxival) * 255;
            fprintf(fo1, "%c", (char)((int)(ival[i][j])));
        }
    }

    // Calculate peaks in picture
    for (i = mr; i < (256 - mr); i++)
    {
        for (j = mr; j < (256 - mr); j++)
        {
            if (xconv[i][j] == 0.0) {
                xconv[i][j] = 0.00001;
            }
            slope = xconv[i][j] / yconv[i][j];
            if ((slope <= .4142) && (slope > -.4142))
            {
                if ((ival[i][j] > ival[i][j - 1]) && (ival[i][j] > ival[i][j + 1])) {
                    edgeflag[i][j] = 255;
                }
            }
            else if ((slope <= 2.4142) && (slope > .4142))
            {
                if ((ival[i][j] > ival[i - 1][j - 1]) && (ival[i][j] > ival[i + 1][j + 1])) {
                    edgeflag[i][j] = 255;
                }
            }
            else if ((slope <= -.4142) && (slope > -2.4241))
            {
                if ((ival[i][j] > ival[i + 1][j - 1]) && (ival[i][j] > ival[i - 1][j + 1])) {
                    edgeflag[i][j] = 255;
                }
            }
            else
            {
                if ((ival[i][j] > ival[i - 1][j]) && (ival[i][j] > ival[i + 1][j])) {
                    edgeflag[i][j] = 255;
                }
            }
        }
    }


    for (i = 0; i < 256; i++)
    {
        for (j = 0; j < 256; j++)
        {
            fprintf(fo2, "%c", (char)((int)(edgeflag[i][j])));
        }
    }

    // Clean histogram
    memset(histogram, 0, sizeof(histogram));

    /* Double Thresholding */
    // Automatically get histogram
    for (i = 0; i < 256; i++)
    {
        for (j = 0; j < 256; j++)
        {
            histogram[(int)ival[i][j]]++;
        }
    }

    // Find cutoff
    cutoff = (percent / 100) * 256 * 256;

    // Find hi and lo
    for (i = 255; i >= 0; i--)
    {
        areaOfTops += histogram[i];
        if (areaOfTops > cutoff)
            break;
    }
    hi = i;
    lo = .35*hi;

    // Double thresholding
    for (i = mr; i < (256 - mr); i++)
    {
        for (j = mr; j < (256 - mr); j++)
        {
            if (edgeflag[i][j] == 255)
            {
                if (ival[i][j] >= hi)
                {
                    threshold_pic[i][j] = 255;
                    double_threshold(i - 1, j - 1);
                    double_threshold(i - 1, j);
                    double_threshold(i - 1, j + 1);
                    double_threshold(i, j - 1);
                    double_threshold(i, j + 1);
                    double_threshold(i + 1, j - 1);
                    double_threshold(i + 1, j);
                    double_threshold(i + 1, j + 1);
                }

            }
        }
    }

    for (i = 0; i < 256; i++)
    {
        for (j = 0; j < 256; j++)
        {
            fprintf(fo3, "%c", (char)((int)(threshold_pic[i][j])));
        }
    }
}


/* (Extra) Apply Gaussian Blur
* - This Gaussian blur can help us get a more precise edge detection
* later on because it will blur out some noise within an image
*/
void GaussianBlur()
{
    int i, j, p, q, mr, sum;

    mr = 1;
    for (i = mr; i < (256 - mr); i++)
    {
        for (j = mr; j < (256 - mr); j++)
        {
            sum = 0;
            for (p = -mr; p <= mr; p++)
                for (q = -mr; q <= mr; q++)
                    sum += pic[i + p][j + q] * gausMask[p + mr][q + mr];
            pic[i][j] = sum / maxVal;
        }
    }
}


void double_threshold(int i, int j)
{
    // Base Case: Indexes out of bounds
    if (i < 0 || j < 0 || i > 255 || j > 255)
        return;

    // Base Case 2: Ignore pixels that are final and not an edge
    if (edgeflag[i][j] != 255 || threshold_pic[i][j] == 255)
        return;

    // If pixel passes low threshold, find connecting pixels.
    if (ival[i][j] > lo)
    {
        threshold_pic[i][j] = 255;
        double_threshold(i - 1, j - 1);
        double_threshold(i - 1, j);
        double_threshold(i - 1, j + 1);
        double_threshold(i, j - 1);
        double_threshold(i, j + 1);
        double_threshold(i + 1, j - 1);
        double_threshold(i + 1, j);
        double_threshold(i + 1, j + 1);
    }
    return;
}
