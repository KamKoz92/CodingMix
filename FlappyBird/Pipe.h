#pragma once

#include "Game.h"

class Pipe
{
public:
    static SDL_Texture *PIPE_TEX;
    SDL_Rect src, dst;

    Pipe();
    ~Pipe();
    void setLocation(int x, int y);
    void update();
    void render();
};