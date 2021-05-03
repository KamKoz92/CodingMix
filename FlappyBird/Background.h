#pragma once
#include "Game.h"
#include "Gate.h"
#include "Score.h"
#include <vector>
#include <stdlib.h>
#include <time.h>

class Score;

class Background
{
    bool state;
    SDL_Texture *dayTex;
    SDL_Texture *nightTex;
    SDL_Texture *base;
    SDL_Texture *pipe;
    SDL_Texture *menu;
    SDL_Texture *gameOver;


    SDL_Rect baseSrc, backgroundSrc, pipeSrc;
    SDL_Rect baseDst, backgroundDst, pipeDst;
    SDL_Rect menuDst, menuSrc;
    SDL_Rect gameOverDst, gameOverSrc;

    std::vector<Gate> gates;
    int maxGates;
    Score *score;
    int scorePoint;

public:
    Background(int scorePoint);
    ~Background();
    void render();
    void renderMenu();
    void renderScore();
    void renderGameOver();
    SDL_Rect getCollider();
    void drawGate();  
    void update();
    bool checkPipeColision(SDL_Rect playerRect);
    void setGates();
    void reset();
};