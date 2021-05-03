#include "Player.h"
#include "SDL2/SDL.h"
#include <SDL2/SDL_image.h>

Player::Player(float x, float y)
{
    startingX = x;
    startingY = y;
    setTextures();
    pitchAngle = 0;
    xPos = x;
    yPos = y;
    srcRect.x = 0;
    srcRect.y = 0;
    SDL_QueryTexture(playerTexture, NULL, NULL, &srcRect.w, &srcRect.h);

    dstRect.x = xPos;
    dstRect.y = yPos;
    dstRect.w = srcRect.w;
    dstRect.h = srcRect.h;

    gravity = 0.3f;
    maxSpeed = 8.0f;
    minSpeed = -5.0f;
    setVelocity(0);

    playerColider.x = dstRect.x - 1;
    playerColider.y = dstRect.y - 1;
    playerColider.w = dstRect.w - 1;
    playerColider.h = dstRect.h - 1;

}

Player::~Player()
{
}

void Player::setVelocity(float velY)
{
    this->velY = velY;
}
void Player::setMinVelocity()
{
    setVelocity(minSpeed);
}

void Player::updatePosition()
{
    velY += gravity;
    if (velY > maxSpeed)
    {
        setVelocity(maxSpeed);
    }
    yPos += velY;
    if (yPos < 0)
    {
        yPos = 0;
        setVelocity(0);
    }
    dstRect.y = yPos;
    playerColider.y = dstRect.y - 1;
    
}
void Player::updateFrame()
{
    currentFrame = ((SDL_GetTicks() / frameSpeed) % numOfFrames);
    setCurrentFrame();
}

void Player::render()
{
    TextureManager::Draw(playerTexture, srcRect, dstRect, pitchAngle, SDL_FLIP_NONE);
}

SDL_Rect Player::getCollider()
{
    return playerColider;
}

void Player::setMaxSpeed(float mS)
{
    maxSpeed = mS;
}

void Player::setTextures()
{
    frames[0] = TextureManager::LoadTexture("assets/sprites/bluebird-downflap.png");
    frames[1] = TextureManager::LoadTexture("assets/sprites/bluebird-midflap.png");
    frames[2] = TextureManager::LoadTexture("assets/sprites/bluebird-upflap.png");
    frames[3] = frames[1];
    numOfFrames = 4;
    frameSpeed = 100;
    currentFrame = 0;
    setCurrentFrame();
}

void Player::setCurrentFrame()
{
    playerTexture = frames[currentFrame];
}

void Player::reset()
{
    yPos = startingY;
    dstRect.y = startingY;
    setVelocity(0);
}