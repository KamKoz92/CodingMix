#pragma once

#include "Game.h"

struct Gate
{
    SDL_Rect gap;
    int areaH;
    Gate(int x, int y, int w, int h, int areaH)
    {
        gap.x = x;
        gap.y = y;
        gap.w = w;
        gap.h = h;
        this->areaH = areaH;
    }
    ~Gate()
    {
    }
};