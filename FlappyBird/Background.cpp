#include "Background.h"

Background::Background()
{
    state = false;
    dayTex = TextureManager::LoadTexture("assets/sprites/background-day.png");
    nightTex = TextureManager::LoadTexture("assets/sprites/background-night.png");
    base = TextureManager::LoadTexture("assets/sprites/base.png");

    backgroundRect.x = 0;
    backgroundRect.y = 0;
    SDL_QueryTexture(dayTex, NULL, NULL, &backgroundRect.w, &backgroundRect.h);

    backgroundDst.x = 0;
    backgroundDst.y = 0;
    backgroundDst.w = backgroundRect.w;
    backgroundDst.h = backgroundRect.h;

    baseRect.x = 0;
    baseRect.y = 0;
    SDL_QueryTexture(base, NULL, NULL, NULL, &baseRect.h);
    baseRect.w = backgroundRect.w;

    baseDst.x = 0;
    baseDst.y = backgroundRect.h - 112;
    baseDst.w = baseRect.w;
    baseDst.h = baseRect.h;
}

Background::~Background()
{
}
void Background::render()
{
    if (state)
    {
        TextureManager::Draw(dayTex, backgroundRect, backgroundDst);
    }
    else
    {
        TextureManager::Draw(nightTex, backgroundRect, backgroundDst);
    }

    baseRect.x++;
    if(baseRect.x > 47) baseRect.x = 0;
    TextureManager::Draw(base, baseRect, baseDst);
}


SDL_Rect Background::getCollider()
{
    return baseDst;
}