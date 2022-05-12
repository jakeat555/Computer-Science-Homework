#include <cmath>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <string>

#include "ppmToBmp.hpp" // provides the function int ppmToBmp(std::string ppmFileName);

using namespace std;

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

    string outputPPMfile;  // name of PPM file to create


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
MandelbrotConfig readConfig(string configFileLocation);

// Helper function: given a coordinate corresponding to an imaginary number,
// run the Escape Time algorithm and count how many iterations this number takes
int countIterations(MandelbrotConfig config, int col, int row);

// Helper function: decide what color corresponds to a given number of iterations
Color getPixelColor(MandelbrotConfig config, int iterations);

// Create the PPM file, including header, and build the image pixel by pixel
// Loop over the grid of pixels and call the above helper functions as needed
bool drawMandelbrot(MandelbrotConfig config);


int main(int argc, char* argv[]) {
    string filename;

    // Read in config file location from user

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
