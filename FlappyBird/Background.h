#pragma once
#include "Game.h"

class Background
{
    bool state;
    SDL_Texture *dayTex;
    SDL_Texture *nightTex;
    SDL_Texture *base;
    SDL_Rect baseRect, backgroundRect;
    SDL_Rect baseDst, backgroundDst;
    

public:
    Background();
    ~Background();
    void render();
    SDL_Rect getCollider();
};