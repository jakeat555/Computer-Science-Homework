#include <iostream>
#include <string>
#include "yahtzee.h"
#include "fourKind.h"

struct round
{
    bool roundAttempt = false;
    bool roundWin = false;
};

void yahtzeeRound(round yahtzee)
{
    yahtzee.roundAttempt = true;
    yahtzee.roundWin = yahtzeeGame();
}

void fourKindRound(round fourKind)
{
    fourKind.roundAttempt = true;
    fourKind.roundWin = fourKindGame();
}

void straightRound(round straight)
{
    straight.roundAttempt = true;
    straight.roundWin = straightGame();
}

void fullHouseRound(round fullHouse)
{
    fullHouse.roundAttempt = true;
    fullHouse.roundWin = fullHouseGame();
}

int printMainMenu(round yahtzee, round fourKind, round straight, round fullHouse)
{
    int temp = 0;
    std::cout << "1. Yahtzee " << statusOutput(yahtzee.roundAttempt, yahtzee.roundWin) << std::endl;
    std::cout << "2. Four in a Row " << statusOutput(fourKind.roundAttempt, fourKind.roundWin) << std::endl;
    std::cout << "3. Straight " << statusOutput(straight.roundAttempt, straight.roundWin) << std::endl;
    std::cout << "4. Full House " << statusOutput(fullHouse.roundAttempt, fullHouse.roundWin) << std::endl;
    std::cout << "5. Exit" << std::endl;
    std::cin >> temp;
    return temp;
}

std::string statusOutput(bool attempt, bool win)
{
    if(!attempt)
    {
        return "NOT_ATTEMPTED";
    }
    else if(!win)
    {
        return "FAILED";
    }
    else
    {
        return "COMPLETED";
    }
}

void mainMenuControl(int selection, round yahtzee, round fourKind, round straight, round fullHouse)
{
    switch(selection)
    {
        case 1:
            yahtzeeRound(yahtzee);
            break;
        case 2:
            fourKindRound(fourKind);
            break;
        case 3:
            straightRound(straight);
            break;
        case 4:
            fullHouseRound(fullHouse);
            break;
        default:
            break;
    }
}

bool straightGame()
{
    return true;
}

bool straightWinCondition(int x[])
{
    return true;
}

bool fullHouseGame()
{
    return true;
}

bool fullHouseWinCondition(int x[])
{
    return true;
}

void printRoll(int x[])
{
    std::cout << x[0] << " " << x[1] << " " << x[2] << " " << x[3] << " "<< x[4] << std::endl;
}

int holdRoll()
{
    int temp=0;
    std::cout << "What number do you want to hold?: " ;
    std::cin >> temp;
    return temp;
}

void rollDice(int x[], int hold)
{
    for(int i = 0; i< 6; i++)
    {
        if(x[i] != hold)
        {
            x[i] = rand() % 6 + 1;
        }
    }
}

int main()
{
    bool gameWin = false;
    round yahtzee, fourKind, straight, fullHouse;
    int selection = printMainMenu(yahtzee, fourKind, straight, fullHouse);
    mainMenuControl(selection, yahtzee, fourKind, straight, fullHouse);
    return 0;
}