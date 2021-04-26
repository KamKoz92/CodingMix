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
    void changeGap(int x, int y)
    {
        upperPipe.x += x;
        gap.x += x;
        lowerPipe.x += x;

        if (y != 0)
        {
            upperPipe.h += y;
            gap.y += y;

            lowerPipe.y += y;
            lowerPipe.h -= y;

        }
    }
};