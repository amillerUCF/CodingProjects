/** Sobel.c - Applies the sobel edge detection to an image
 * and outputs three different images showing different results
 *
 * Running sobel.c:
 * 1. gcc sobel.c -o <name>
 * 2. <name> <input_image> <output_image>
 */

#include <stdio.h>
#include <math.h>
#include <stdlib.h>

		/* Function Prototypes */
		void GaussianBlur();

        int pic[256][256];
        int outpicx[256][256];
        int outpicy[256][256];
        int maskx[3][3] = {{-1,0,1}, {-2,0,2}, {-1,0,1}};
        int masky[3][3] = {{1,2,1}, {0,0,0}, {-1,-2,-1}};
        double ival[256][256], maxival;
		
		// Gaussian Blur Kernel
		int gausMask[3][3] = {{3,6,3}, {6,12,6}, {3,6,3}}, maxVal = 48;

void main(argc, argv)
int argc;
char **argv;
{
	int i, j, p, q, mr, sum1, sum2;
	double threshold, threshold2;
	FILE *fo1, *fo2, *fo3, *fp1, *fopen();
	char *foobar;

	// First argument: Input file
	argc--; argv++;
	fp1 = fopen(*argv,"rb");

	// Second argument: Output file
	argc--; argv++;
	fo1 = fopen(*argv,"wb");

	// Creates low and high threshold output images
	fo2 = fopen("sobel_low.pgm", "wb");
	fo3 = fopen("sobel_high.pgm", "wb");

	// Throw away offset of image
		char throwaway[80];
		fgets(throwaway, 80, fp1);
        fgets(throwaway, 80, fp1);
        fgets(throwaway, 80, fp1);
		if ( !( (throwaway[0]=='2') && (throwaway[1]=='5') && (throwaway[2]=='5')))
        	fgets(throwaway, 80, fp1);

	// Stores image in pic[][]
	for(i = 0; i < 256; i++)
	{
		for(j = 0; j < 256; j++)
		{
			pic[i][j]  =  getc (fp1);
			pic[i][j]  &= 0377;
		}
	}

	mr = 1;
	
	// Apply a Gaussian Blur
	//GaussianBlur(pic);
	
	/* Convolution */
	for(i = mr; i < (256 - mr); i++)
    { 
		for(j = mr; j < (256 - mr); j++)
		{
			sum1 = 0;
			sum2 = 0;
			for(p =- mr; p <= mr; p++)
			{
				for(q =- mr; q <= mr; q++)
				{
					sum1 += pic[i+p][j+q] * maskx[p+mr][q+mr];
					sum2 += pic[i+p][j+q] * masky[p+mr][q+mr];
				}
			}
			outpicx[i][j] = sum1;
			outpicy[i][j] = sum2;
		}
    }

    maxival = 0;
    for(i = mr; i < (256 - mr); i++)
    { 
		for(j = mr; j < (256 - mr); j++)
		{
			ival[i][j] = sqrt((double)((outpicx[i][j]*outpicx[i][j]) +
                                       (outpicy[i][j]*outpicy[i][j])));
			if(ival[i][j] > maxival)
				maxival = ival[i][j];
		}
	}

    // Added File header to magnitude output file
    fprintf(fo1, "P5\n256 256\n255\n");
    for(i = 0; i < 256; i++)
    { 
		for(j = 0; j < 256; j++)
        {
			ival[i][j] = (ival[i][j] / maxival) * 255;
			fprintf(fo1,"%c",(char)((int)(ival[i][j])));
        }
    }

	/* Threshold Output Files:
	 * - lowThresh: 
	 * - highThresh: 
	 */
    // File headers for the low and high thresholded images
    fprintf(fo2, "P5\n256 256\n255\n");
    fprintf(fo3, "P5\n256 256\n255\n");

    for(i = 0; i < 256; i++)
	{
        for(j = 0; j < 256; j++)
		{
			//Low Threshold
            if(ival[i][j] > 35)
				fprintf(fo2, "%c", 255);
            else
				fprintf(fo2, "%c", 0);
            //High Threshold
            if(ival[i][j] > 80)
				fprintf(fo3, "%c", 255);
            else
				fprintf(fo3, "%c", 0);
        }
    }
}


/* (Extra) Apply Gaussian Blur
 * - This Gaussian blur can help us get a more precise edge detection
 * later on because it will blur out most noise within an image
 */
void GaussianBlur()
{
	int i, j, p, q, mr, sum;
	
	mr = 1;
	for(i = mr; i < (256 - mr); i++)
	{
		for(j = mr; j < (256 - mr); j++)
		{
			sum = 0;
			for(p =- mr; p <= mr; p++)
				for(q =- mr; q <= mr; q++)
					sum += pic[i+p][j+q] * gausMask[p+mr][q+mr];
			pic[i][j] = sum / maxVal;
		}
	}
}
