#pragma once

#include "Game.h"

struct Gate
{
    SDL_Rect gap, upperPipe, lowerPipe;
    int areaH;
    Gate(int x, int y, int w, int h, int areaH)
    {
        this->areaH = areaH;

        upperPipe.x = x;
        upperPipe.y = 0;
        upperPipe.w = w;
        upperPipe.h = y;

        gap.x = x;
        gap.y = y;
        gap.w = w;
        gap.h = h;

        lowerPipe.x = x;
        lowerPipe.y = h + y;
        lowerPipe.w = w;
        lowerPipe.h = areaH - (h + y);
    }
    ~Gate()
    {
    }
    void moveGateHorizon(int x)
    {
        upperPipe.x += x;
        gap.x += x;
        lowerPipe.x += x;
    }
    void moveGateVertical(int y)
    {
        upperPipe.h = 150 + (y * 25);
        gap.y = 150 + (y * 15);
        lowerPipe.y = gap.h + 150 + (y * 25);
        lowerPipe.h = areaH - (gap.h + (150 + (y * 25)));
    }
};