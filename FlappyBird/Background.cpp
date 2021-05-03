#include "Background.h"

Background::Background(int scorePoint)
{
    state = false;

    dayTex = TextureManager::LoadTexture("assets/sprites/background-day.png");
    nightTex = TextureManager::LoadTexture("assets/sprites/background-night.png");
    base = TextureManager::LoadTexture("assets/sprites/base.png");
    pipe = TextureManager::LoadTexture("assets/sprites/pipe-green.png");
    menu = TextureManager::LoadTexture("assets/sprites/message.png");
    gameOver = TextureManager::LoadTexture("assets/sprites/gameover.png");

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

    menuSrc.x = 0;
    menuSrc.y = 0;
    SDL_QueryTexture(menu, NULL, NULL, &menuSrc.w, &menuSrc.h);

    menuDst.x = (backgroundDst.w - menuSrc.w) / 2;
    menuDst.y = (backgroundDst.h - menuSrc.h) / 2;
    menuDst.w = menuSrc.w;
    menuDst.h = menuSrc.h;

    gameOverSrc.x = 0;
    gameOverSrc.y = 0;
    SDL_QueryTexture(gameOver, NULL, NULL, &gameOverSrc.w, &gameOverSrc.h);

    gameOverDst.x = (backgroundDst.w - gameOverSrc.w) / 2;
    gameOverDst.y = 25;
    gameOverDst.w = gameOverSrc.w;
    gameOverDst.h = gameOverSrc.h;

    maxGates = 3;

    setGates();

    srand(time(NULL));
    score = new Score(backgroundSrc.w, backgroundSrc.h);
    this->scorePoint = scorePoint;
}

Background::~Background()
{
}
void Background::render()
{
    if (state)
    {
        TextureManager::Draw(dayTex, backgroundSrc, backgroundDst, 0, SDL_FLIP_NONE);
    }
    else
    {
        TextureManager::Draw(nightTex, backgroundSrc, backgroundDst, 0, SDL_FLIP_NONE);
    }
    drawGate();
    TextureManager::Draw(base, baseSrc, baseDst, 0, SDL_FLIP_NONE);
}

void Background::renderScore()
{
    score->render();
}
void Background::renderMenu()
{
    TextureManager::Draw(menu, menuSrc, menuDst, 0, SDL_FLIP_NONE);
}
void Background::renderGameOver()
{
    TextureManager::Draw(gameOver, gameOverSrc, gameOverDst, 0, SDL_FLIP_NONE);
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

        pipeSrc.h = tempGate.upperPipe.h;
        TextureManager::Draw(pipe, pipeSrc, tempGate.upperPipe, 0, SDL_FLIP_VERTICAL);

        pipeSrc.h = tempGate.lowerPipe.h;
        TextureManager::Draw(pipe, pipeSrc, tempGate.lowerPipe, 0, SDL_FLIP_NONE);
    }
}

void Background::update()
{
    baseSrc.x += 2;
    if (baseSrc.x > 47)
        baseSrc.x = 0;

    for (int i = 0; i < gates.size(); i++)
    {
        gates[i].moveGateHorizon(-2);

        if (gates[i].gap.x == scorePoint)
        {
            score->addScore();
        }
        if ((gates[i].gap.x + gates[i].gap.w) < 0)
        {
            gates[i].moveGateHorizon(backgroundSrc.w + pipeSrc.w + 20);
            gates[i].moveGateVertical(rand() % 7 - 3);
        }
    }
}

bool Background::checkPipeColision(SDL_Rect playerRect)
{

    for (Gate g : gates)
    {
        if (Collider::AABB(playerRect, g.upperPipe) ||
            Collider::AABB(playerRect, g.lowerPipe))
        {
            return 1;
        }
    }
    return 0;
}

void Background::setGates()
{
    gates.clear();
    for (int i = 0; i < maxGates; i++)
    {
        gates.push_back(Gate(300 + (i * 120), 150, pipeSrc.w, 125, backgroundSrc.h - baseSrc.h));
    }
}
void Background::reset()
{
    setGates();
    score->resetScore();
}