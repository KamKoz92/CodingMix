#pragma once
#include "Game.h"
#include <string>

class Score
{
    SDL_Texture *numbers[10];
    SDL_Rect numberSrc;
    SDL_Rect numberDst;
    int scoreNumber;
    int screenWidth;
public:
    Score(int w, int h);
    ~Score();
    void render();
    void addScore();
    void resetScore();
};