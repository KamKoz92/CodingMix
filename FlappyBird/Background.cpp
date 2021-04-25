#include "Background.h"

Background::Background()
{
    state = false;
    dayTex = TextureManager::LoadTexture("assets/sprites/background-day.png");
    nightTex = TextureManager::LoadTexture("assets/sprites/background-night.png");
    base = TextureManager::LoadTexture("assets/sprites/base.png");
    pipe = TextureManager::LoadTexture("assets/sprites/pipe-green.png");

    backgroundSrc.x = 0;
    backgroundSrc.y = 0;
    SDL_QueryTexture(dayTex, NULL, NULL, &backgroundSrc.w, &backgroundSrc.h);

    backgroundDst.x = 0;
    backgroundDst.y = 0;
    backgroundDst.w = backgroundSrc.w;
    backgroundDst.h = backgroundSrc.h;

    baseSrc.x = 0;
    baseSrc.y = 0;
    SDL_QueryTexture(base, NULL, NULL, NULL, &baseSrc.h);
    baseSrc.w = backgroundSrc.w;

    baseDst.x = 0;
    baseDst.y = backgroundSrc.h - 112;
    baseDst.w = baseSrc.w;
    baseDst.h = baseSrc.h;

    pipeSrc.x = 0;
    pipeSrc.y = 0;
    SDL_QueryTexture(pipe, NULL, NULL, &pipeSrc.w, NULL);
    pipeSrc.h = 0;

    pipeDst.x = 0;
    pipeDst.y = 0;
    pipeDst.w = 0;
    pipeDst.h = 0;


    maxGates = 3;
    for(int i = 0; i < maxGates; i++) {
        gates.push_back(Gate(200 + (i * 140), 125, 50, 50, backgroundSrc.h - baseSrc.h));
    }
    
}

Background::~Background()
{
}
void Background::render()
{
    if (state)
    {
        TextureManager::Draw(dayTex, backgroundSrc, backgroundDst, 0);
    }
    else
    {
        TextureManager::Draw(nightTex, backgroundSrc, backgroundDst, 0);
    }
    drawGate();

    baseSrc.x++;
    if (baseSrc.x > 47)
        baseSrc.x = 0;
    TextureManager::Draw(base, baseSrc, baseDst, 0);
}

SDL_Rect Background::getCollider()
{
    return baseDst;
}

void Background::drawGate()
{
    for (int i = 0; i < gates.size(); i++)
    {
        Gate tempGate = gates[i];

        pipeSrc.h = tempGate.areaH - (tempGate.gap.h + tempGate.gap.y);

        pipeDst.x = tempGate.gap.x;
        pipeDst.y = tempGate.gap.h + tempGate.gap.y;
        pipeDst.w = pipeSrc.w;
        pipeDst.h = pipeSrc.h;
        TextureManager::Draw(pipe, pipeSrc, pipeDst, 0);

        pipeSrc.h = tempGate.gap.y;

        pipeDst.x = tempGate.gap.x;
        pipeDst.y = 0;
        pipeDst.w = pipeSrc.w;
        pipeDst.h = tempGate.gap.y;
        TextureManager::Draw(pipe, pipeSrc, pipeDst, 180);
    }
}

void Background::update()
{
    for(int i = 0; i < gates.size(); i++) {
        gates[i].gap.x--;
        if((gates[i].gap.x + gates[i].gap.w) < 0) {
            gates[i].gap.x = gates.back().gap.x + 140;
        }
    }
}