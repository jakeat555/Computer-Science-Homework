/*
*   Header file for a deck of Playing Card
*/

#ifndef Deck_hpp__INCLUDED
#define Deck_hpp__INCLUDED
#include "PlayingCard.hpp"
#include <array>


class Deck
{
    private:
        PlayingCard deck[52];
    public:
        Deck();
        PlayingCard draw();
        void switchCards(PlayingCard&, PlayingCard&);
        void shuffle();
        void printDeck();
};

#endif