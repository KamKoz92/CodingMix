#pragma once
#include "Game.h"
#include "Gate.h"
#include <vector>

class Background
{
    bool state;
    SDL_Texture *dayTex;
    SDL_Texture *nightTex;
    SDL_Texture *base;
    SDL_Texture *pipe;
    SDL_Rect baseSrc, backgroundSrc, pipeSrc;
    SDL_Rect baseDst, backgroundDst, pipeDst;
    std::vector<Gate> gates;
    int maxGates;
    
public:
    Background();
    ~Background();
    void render();
    SDL_Rect getCollider();
    void drawGate();  
    void update();
};