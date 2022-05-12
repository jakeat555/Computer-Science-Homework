/*
*   Header file for a singular game of blackjack
*/

#ifndef Blackjack_hpp__INCLUDED
#define Blackjack_hpp__INCLUDED
#include "Deck.hpp"

class Blackjack
{
    private:
        Deck blackjackDeck;
        int playerScore;
        int dealerScore;
        bool playerTurn();
        void dealerTurn();
        bool playerWin;
    public:
        Blackjack();
        void play();
        int getPlayerScore();
        int getDealerScore();
        void setPlayerScore(int);
        void setDealerScore(int);
        bool getPlayerWin();
        void setPlayerWin(bool);
};

#endif