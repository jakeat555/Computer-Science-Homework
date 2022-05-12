#include "Blackjack.hpp"
#include "Deck.hpp"
#include <iostream>


Blackjack::Blackjack()
{
    blackjackDeck.shuffle();
    setPlayerScore(0);
    setDealerScore(0);
}

int Blackjack::getPlayerScore()
{
    return playerScore;
}

int Blackjack::getDealerScore()
{
    return dealerScore;
}

void Blackjack::setPlayerScore(int newScore)
{
    playerScore = newScore;
}

void Blackjack::setDealerScore(int newScore)
{
    dealerScore = newScore;
}

bool Blackjack::playerTurn()
{
    char selection = ' ';
    bool playerEnded = false;
    
    while(getPlayerScore()<21 && !playerEnded)
    {
        std::cout << "Hit(h) or stand(s):";
        std::cin >> selection;
        
        //Logic flow for actions (hit or stand)
        if(selection==104) //Player wants to hit
        {
            PlayingCard temp = blackjackDeck.draw();
            std::cout << "Player Draws a " << temp.toString() << std::endl;
            setPlayerScore(temp.getValue() + getPlayerScore());
            std::cout << "That brings the Players score to " << getPlayerScore() << std::endl;
        }
        else if(selection == 115) //Player wants to stand
        {
            std::cout << "Player stands with " << getPlayerScore() << " points. Dealers turn." << std::endl << std::endl;
            return playerEnded;
        }
        else //Case exception for invalid input
        {
            std::cout << "Please enter a valid input (h OR s)" << std::endl;
        }
        
        //Ending conditions
        if(getPlayerScore()>21)
        {
            std::cout << "Player busts" << std::endl << std::endl;
            return playerEnded;
        }
        else if(getPlayerScore()==21)
        {
            std::cout << "Player got a Black Jack, Congrats" << std::endl << std::endl;
            return !playerEnded;
        }
    }
}

void Blackjack::dealerTurn()
{
    while(getDealerScore()<=17 && !(getPlayerScore() > 21)) // While the dealer has less than 18 and the player hasnt busted, the dealer goes
    {
        PlayingCard temp = blackjackDeck.draw();
        std::cout << "Dealer got the " << temp.toString() << std::endl;
        setDealerScore(temp.getValue() + getDealerScore());
    }
}

void Blackjack::play()
{
    //deals out 2 shuffled cards to each person
    blackjackDeck.shuffle();
    PlayingCard playerFirstCard = blackjackDeck.draw();
    PlayingCard playerSecondCard = blackjackDeck.draw();
    PlayingCard dealerFirstCard = blackjackDeck.draw();
    PlayingCard dealerSecondCard = blackjackDeck.draw();    //stays hidden from player
    
    setPlayerScore(playerFirstCard.getValue() + playerSecondCard.getValue());
    std::cout << "Player starts with " << playerFirstCard.toString() << " and " << playerSecondCard.toString() << std::endl;
    
    setDealerScore(dealerFirstCard.getValue() + dealerSecondCard.getValue());
    std::cout << "Dealer is showing the " << dealerFirstCard.toString() << std::endl;
    
    //Main control for BlackJack play. the player takes thier turn, then the dealer, then logic is applied to find the winner
    if(!playerTurn())
    {
        //Dealers turn
        std::cout << "Dealers face down card was " << dealerSecondCard.toString() << std::endl;
        dealerTurn();
        
        //print final results with the logic of winning the game
        std::cout  << std::endl << "Final scores: Player-" << getPlayerScore() << "  Dealer-" << getDealerScore() << std::endl;
        if(getDealerScore() == getPlayerScore() && getPlayerScore() >= 21 && getDealerScore())
            std::cout << "It's a tie (No one wins)" << std::endl << std::endl;
        else if(getPlayerScore() <= 21 && (getPlayerScore() > getDealerScore() || getDealerScore() > 21))
            std::cout << "Player wins, you beat the house" << std::endl;
        else
            std::cout << "Dealer wins, better luck next time" << std::endl;
    }
}