#include <iostream>

struct Rational {
	int n, d;
};

void printRational(Rational r) {
    if(r.d == 1)
    {
        std::cout << r.n;
    }
    else if(r.n==0)
    {
        std::cout << "0";
    }
    else
    {
        std::cout << r.n << '/' << r.d;
    }
}

// For best results, use Eulclid's algorithm
// https://en.wikipedia.org/wiki/Greatest_common_divisor#Using_Euclid.27s_algorithm
int gcd(int n, int d);

// Each function returns false if the resulting Rational number would have a
// zero in the denominator. To get full credit you must check this result after
// every use of these functions!
bool simplify(Rational &r);
bool invert(Rational &r);
bool create(Rational &r, int num = 1, int denom = 1);

// The result of the operation between Rationals a and b is stored in c
bool add(Rational a, Rational b, Rational &c);
bool sub(Rational a, Rational b, Rational &c);
bool mul(Rational a, Rational b, Rational &c);
bool div(Rational a, Rational b, Rational &c);

bool mainMenuControl(char selection);
void printAnswer(Rational a, Rational b, Rational result, std::string operand);
bool userInput(Rational &a, Rational &b);

int main(void) {
    // In a loop,
    bool keepGoing = true;
    while(keepGoing)
    {
        char selection = 0;
        Rational a;
        Rational b;
        Rational result;
        create(result);
        std::cout << "Which arithmetic operation do you want to perform? [+, -, *, /] (or 'q' to quit):";
        std::cin >> selection;
        keepGoing = mainMenuControl(selection);
        std::cout << std::endl;
        selection = 0;
    }
    //     Continually ask the user which math operation they wish to perform [+, -, *, /]
    //     Quit the program if the user enters 'q' or 'Q'
    //     Nag them until they input a valid operation or quit

    //     Ask the user to enter a numerator for the first Rational number
    //     Ask the user to enter a denominator for the first Rational number
    //     Print an error message and restart this loop if they enter 0 as the denominator

    //     Repeat the above procedure for the second Rational number

    //     Print the equation involving the Rational numbers 

    //     Perform the arithmetic operation and print the resulting Rational number
    return 0;
}

int gcd(int n, int d)
{
    if(d==0)
        return n;
    else
        return gcd(d,n%d);
}

bool simplify(Rational &r)
{
    int greatestCommonFactor = gcd(r.n,r.d);
    r.n /= greatestCommonFactor;
    r.d /= greatestCommonFactor;
    if(r.d<0 && r.n>0)
    {
        r.n = -1*r.n;
        r.d = -1*r.d;
    }
    return (r.d>0);
}

bool invert(Rational &r)
{
    int temp = r.n;
    r.n = r.d;
    r.d = temp;
    return (r.d!=0);
}

bool create(Rational &r, int n, int d)
{
    r.n = n;
    r.d = d;
    simplify(r);
    return (r.d!=0);
}

bool add(Rational a, Rational b, Rational &c)
{
    c.n = a.n*b.d + a.d*b.n;
    c.d = a.d*b.d;
    return (c.d!=0);
}

bool sub(Rational a, Rational b, Rational &c)
{
    c.n = a.n*b.d - a.d*b.n;
    c.d = a.d*b.d;
    return (c.d!=0);
}

bool mul(Rational a, Rational b, Rational &c)
{
    c.n = a.n*b.n;
    c.d = a.d*b.d;
    return (c.d!=0);
}

bool div(Rational a, Rational b, Rational &c)
{
    invert(b);
    mul(a,b,c);
    return (c.d!=0);
}

bool userInput(Rational &a, Rational &b)
{
    std::cout << "Enter a numerator/denominator pair:";
    std::cin >> a.n >> a.d;
    std::cout << "Enter another numerator/denominator pair:";
    std::cin >> b.n >> b.d;
    return (a.d!=0 && b.d!=0);
}

void printAnswer(Rational a, Rational b, Rational result, std::string operand)
{
    simplify(a);
    printRational(a);
    std::cout << operand;
    simplify(b);
    printRational(b);
    std::cout << " = ";
    if(result.d == 0)
    {
        std::cout << "Divide by 0";
    }
    else
    {
        simplify(result);
        printRational(result);
    }
    std::cout << std::endl;
}

bool mainMenuControl(char selection)
{
    Rational a;
    Rational b;
    Rational result;
    create(result);
    if(selection == 81 || selection == 113)
    {
        std::cout << "Okay, Goodbye! \n";
        return false;
    }
    switch(selection)
    {
        case 42:    //multiply
            if(!userInput(a,b))
            {
                std::cout << "Invalid rational number" << std::endl;
            }
            mul(a,b,result);
            printAnswer(a,b,result," * ");
            break;
        case 43:    //add
            if(!userInput(a,b))
            {
                std::cout << "Invalid rational number" << std::endl;
            }
            add(a,b,result);
            printAnswer(a,b,result," + ");
            break;
        case 45:    //subtract
            if(!userInput(a,b))
            {
                std::cout << "Invalid rational number" << std::endl;
            }
            sub(a,b,result);
            printAnswer(a,b,result," - ");
            break;
        case 47:    //divide
            if(!userInput(a,b))
            {
                std::cout << "Invalid rational number" << std::endl;
            }
            // if(create(a,a.n,a.d) && create(b,b.n,b.d))
            // {
            //     (div(a,b,result))? printAnswer(a,b,result," / "): printFail();
            // }
            div(a,b,result);
            printAnswer(a,b,result," / ");
            break;
        default:
            std::cout<<"Please enter a valid input\n";
            break;
    }
    return true;
}