/*
*   Header file for a singular playing card
*/

#ifndef PlayingCard_hpp__INCLUDED
#define PlayingCard_hpp__INCLUDED

#include <string>

class PlayingCard
{
    private:
        std::string suit;
        std::string face;
        int value;
    public:
        PlayingCard();
        PlayingCard(std::string newSuit, std::string newFace, int newValue);
        std::string toString();
        int getValue();
        void setValue(int);
        void setFace(std::string);
        void setSuit(std::string);
};

#endif