#include "Blackjack.hpp"
#include <iostream>
#include <string>

int main()
{
    Blackjack game1;
    char selection = 'y';
    
    //while the player want to play, play
    while(selection != 'n')
    {
        game1.play();
        std::cout << "Wanna play again? (y/n) ";
        std::cin >> selection;
    }
    
    return 0;
}