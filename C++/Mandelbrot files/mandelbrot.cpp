#include <cmath>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <string>
#include "ppmToBmp.hpp" // provides the function int ppmToBmp(std::string ppmFileName);

struct Color {
    int r;
    int g;
    int b;
};

struct MandelbrotConfig {
    //
    // These values come from the configuration file
    //
    int pixels;            // dimensions of square image, in pixels

    double midX, midY;     // center of image in imaginary coords
    double axisLen;        // width and height of image in imaginary units

    int maxIterations;     // max number of iterations to test for escape

    Color colorOne;        // color of pixels outside of Mandelbrot set
    Color colorTwo;        // color of pixels within Mandelbrot set

    std::string outputPPMfile;  // name of PPM file to create


    //
    // These values can be calculated from the above, and will
    // make your algorithm more convenient and efficient
    //
    double minX, minY;     // X coords in imaginary units of left & right sides of image
    double maxX, maxY;     // Y coords in imaginary units of top & bottom sides of image
    double pixelSize;      // how many imaginary units of length/height each pixel takes

    double step_r;         // how far the red channel changes between iterations
    double step_g;         // how far the green channel changes between iterations
    double step_b;         // how far the blue channel changes between iterations
};



// Read fractal config file and return a MandelbrotConfig struct
MandelbrotConfig readConfig(std::string configFileLocation)
{
    // Open file, copy contents into new mandelbrot config and return
    MandelbrotConfig cfg;
    
    std::ifstream inFile;
    inFile.open(configFileLocation);
    inFile >> cfg.pixels;
    inFile >> cfg.midX >> cfg.midY;
    inFile >> cfg.axisLen;
    inFile >> cfg.maxIterations;
    inFile >> cfg.colorOne.r >> cfg.colorOne.g >> cfg.colorOne.b;
    inFile >> cfg.colorTwo.r >> cfg.colorTwo.g >> cfg.colorTwo.b;
    inFile >> cfg.outputPPMfile;
    inFile.close();
    cfg.minX = (double)(cfg.midX - (cfg.axisLen/2));
    cfg.minY = (double)(cfg.midY - (cfg.axisLen/2));
    cfg.maxX = (double)(cfg.minX + cfg.axisLen);
    cfg.maxY = (double)(cfg.minY + cfg.axisLen);
    cfg.pixelSize = (double)(cfg.axisLen / cfg.pixels);
    cfg.step_r = (double)((cfg.colorOne.r-cfg.colorTwo.r)*cfg.pixels/cfg.maxIterations);
    cfg.step_g = (double)((cfg.colorOne.g-cfg.colorTwo.g)*cfg.pixels/cfg.maxIterations);
    cfg.step_b = (double)((cfg.colorOne.b-cfg.colorTwo.b)*cfg.pixels/cfg.maxIterations);
    return cfg;
}

// Helper function: given a coordinate corresponding to an imaginary number,
// run the Escape Time algorithm and count how many iterations this number takes
int countIterations(MandelbrotConfig config, int col, int row)
{
    // figure out if the imaginary number, real number combo is part of the set
        // and if so, how many iterations it takes to get it
    double x0 = config.maxX + (double)(col * config.pixelSize);
    double y0 = config.maxY - (double)(row * config.pixelSize);
    double x = 0.0;
    double y = 0.0;
    int iteration =0;
    while( (x*x + y*y) < 4.0 && iteration <  config.maxIterations) 
    {
        double xtemp = x*x - y*y + x0;
        y = 2*x*y + y0;
        x = xtemp;
        iteration++;
    }
    return iteration;
}

// Helper function: decide what color corresponds to a given number of iterations
Color getPixelColor(MandelbrotConfig config, int iterations)
{
    // if the number of iterations is the max, do the outer color
    // else if there is one iteration, do inner color
    // else decide whwat color based on how many iterations
    Color temp;
    double t = iterations/(double)config.maxIterations;
    if(iterations>= config.maxIterations)
    {
        return config.colorOne;
    }
    // temp.r = config.step_r/iterations + config.colorTwo.r;
    // temp.g = config.step_g/iterations + config.colorTwo.g;
    // temp.b = config.step_b/iterations + config.colorTwo.b;
    temp.r = config.step_r/iterations + config.colorOne.r;
    temp.g = config.step_g/iterations + config.colorOne.g;
    temp.b = config.step_b/iterations + config.colorOne.b;
    // temp.r = (int)(9*(1-t)*t*t*t)*255;
    // temp.g = (int)(15*(1-t)*t*t*t)*255;
    // temp.b = (int)(8.5*(1-t)*t*t*t)*255;
    return temp;
}

// Create the PPM file, including header, and build the image pixel by pixel
// Loop over the grid of pixels and call the above helper functions as needed
bool drawMandelbrot(MandelbrotConfig config)
{
    // format the file
    std::ofstream outFile;
    outFile.open(config.outputPPMfile);
    outFile << std::left << "P3" << std::endl;
    outFile << config.pixels << " " << config.pixels << std::endl;
    outFile << "255" << std::endl;
    // for each pixel in the picture
        //determine if its part of the set and how many iterations
        //print to the file the color
    for(int i = 0; i < config.pixels; i++)
    {
        for(int j = 0; j < config.pixels; j++)
        {
            int it = countIterations(config,i,j);
            Color finalColor = getPixelColor(config,it);
            outFile << std::setw(4) << finalColor.r;
            outFile << std::setw(4) << finalColor.g;
            outFile << std::setw(6) << finalColor.b;
        }
        outFile << std::endl;
    }
    outFile.close();
}


int main(int argc, char* argv[])
{
    std::string filename;

    // Read in config file location from user
    std::cout<< "What is the name of your file: ";
    std::cin >> filename;
    // Create a configuration struct from the file
    MandelbrotConfig cfg = readConfig(filename);

    // Compute and write specified mandelbrot image to PPM file
    if (drawMandelbrot(cfg)) {
        // use the provided function to create a BMP image
        ppmToBmp(cfg.outputPPMfile);
    }
    else {
        return 1;
    }

    return 0;
}
