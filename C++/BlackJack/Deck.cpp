#include "Deck.hpp"
#include <iostream>
#include <cstdlib>
#include <ctime>


Deck::Deck()
{
    for(int s = 0; s < 4; s++)
    {
        for(int c = 0; c <= 12; c++)
        {
            deck[13*s+c].setValue(c+1);      // sets the cards value to be mostly correct
            switch(c)   //sets the value in special cases (Ace, Royal cards) and sets the faces
            {
                case 0:
                    deck[13*s+c].setValue(11);
                    deck[13*s+c].setFace("Ace");
                    break;
                case 1:
                    deck[13*s+c].setFace("Two");
                    break;
                case 2:
                    deck[13*s+c].setFace("Three");
                    break;
                case 3:
                    deck[13*s+c].setFace("Four");
                    break;
                case 4:
                    deck[13*s+c].setFace("Five");
                    break;
                case 5:
                    deck[13*s+c].setFace("Six");
                    break;
                case 6:
                    deck[13*s+c].setFace("Seven");
                    break;
                case 7:
                    deck[13*s+c].setFace("Eight");
                    break;
                case 8:
                    deck[13*s+c].setFace("Nine");
                    break;
                case 9:
                    deck[13*s+c].setFace("Ten");
                    break;
                case 10:
                    deck[13*s+c].setValue(10);
                    deck[13*s+c].setFace("Jack");
                    break;
                case 11:
                    deck[13*s+c].setValue(10);
                    deck[13*s+c].setFace("Queen");
                    break;
                case 12:
                    deck[13*s+c].setValue(10);
                    deck[13*s+c].setFace("King");
                    break;
                default:
                    deck[13*s+c].setValue(0);
                    deck[13*s+c].setFace("0");
                    break;
            }
            switch(s)   //sets the first 13 cards to clubs, then spades, etc..
            {
                case 0:
                    deck[13*s+c].setSuit("Clubs");
                    break;
                case 1:
                    deck[13*s+c].setSuit("Spades");
                    break;
                case 2:
                    deck[13*s+c].setSuit("Hearts");
                    break;
                case 3:
                    deck[13*s+c].setSuit("Diamond");
                    break;
                default:
                    deck[13*s+c].setSuit("None");
                    break;
            }
        }
    }
}

PlayingCard Deck::draw()    // takes the data from the "top" card with data in it and wipes the data from the deck array
{
    for(int i = 0; i < 52; i++)
    {
        if(!(deck[i].getValue()==0))
        {
            PlayingCard temporaryCard = deck[i];
            deck[i].setFace("0");
            deck[i].setSuit("None");
            deck[i].setValue(0);
            return temporaryCard;
        }
    }
    std::cout << "Out of cards!" << std::endl;
}

void Deck::shuffle()
{
    srand(time(NULL));
    Deck newDeck;
    for(int i = 51; i >=0; i--)     //Takes the last card in the new deck and switches it with a random card
    {
        int newSpot = rand() % 52;
        PlayingCard temp = newDeck.deck[i];
        newDeck.deck[i] = newDeck.deck[newSpot];
        newDeck.deck[newSpot] = temp;
    }
    for(int i = 0; i <52; i++)      //Copies the new deck into the new full deck
    {
        deck[i] = newDeck.deck[i];
    }
}

void Deck::printDeck()
{
    for(int i = 0; i <52; i++)
    {
        std::cout << deck[i].toString() << std::endl;
    }
}