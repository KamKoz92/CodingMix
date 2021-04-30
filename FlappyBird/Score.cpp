#include "Score.h"
#include <string>

Score::Score(int w, int h)
{

    for (int i = 0; i < 10; i++)
    {
        std::string location = "assets/sprites/";
        std::string png = ".png";
        location.append(std::to_string(i));
        location.append(png);
        numbers[i] = TextureManager::LoadTexture(location.c_str());
        scoreNumber = 0;

        numberSrc.x = 0;
        numberSrc.y = 0;
        SDL_QueryTexture(numbers[0], NULL, NULL, &numberSrc.w, &numberSrc.h);

        numberDst.x = (w - numberSrc.w) / 2;
        numberDst.y = 75;
        numberDst.w = numberSrc.w;
        numberDst.h = numberSrc.h;
        screenWidth = w;
    }
}

Score::~Score()
{
}

void Score::render()
{
    if (scoreNumber < 10)
    {
        TextureManager::Draw(numbers[scoreNumber], numberSrc, numberDst, 0, SDL_FLIP_NONE);
    }
    else if (scoreNumber < 100)
    {
        numberDst.x = (screenWidth - (numberSrc.w * 2)) / 2;
        TextureManager::Draw(numbers[scoreNumber/10], numberSrc, numberDst, 0, SDL_FLIP_NONE);

        numberDst.x += numberSrc.w;
        TextureManager::Draw(numbers[scoreNumber%10], numberSrc, numberDst, 0, SDL_FLIP_NONE);
    }
    else
    {
        numberDst.x = (screenWidth - (numberSrc.w * 3)) / 2;
        TextureManager::Draw(numbers[scoreNumber/100], numberSrc, numberDst, 0, SDL_FLIP_NONE);

        numberDst.x += numberSrc.w;
        TextureManager::Draw(numbers[scoreNumber/10], numberSrc, numberDst, 0, SDL_FLIP_NONE);

        numberDst.x += numberSrc.w;
        TextureManager::Draw(numbers[scoreNumber%10], numberSrc, numberDst, 0, SDL_FLIP_NONE);
    }
}
void Score::addScore()
{
    scoreNumber++;
}
void Score::resetScore()
{
    scoreNumber = 0;
}