#include "PlayingCard.hpp"

PlayingCard::PlayingCard() //sets the default card to be valueless
{
    value = 0;
    face = "0";
    suit="None";
}

PlayingCard::PlayingCard(std::string newSuit, std::string newFace, int newValue)
{
    value = newValue;
    face = newFace;
    suit = newSuit;
}

std::string PlayingCard::toString()
{
    return face + " of "+ suit;
}

int PlayingCard::getValue()
{
    return value;
}

void PlayingCard::setValue(int newValue)
{
    value = newValue;
}

void PlayingCard::setFace(std::string newFace)
{
    face = newFace;
}

void PlayingCard::setSuit(std::string newSuit)
{
    suit = newSuit;
}