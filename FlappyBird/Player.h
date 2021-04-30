#pragma once

#include "Game.h"


class Player
{
    SDL_Texture *playerTexture;
    SDL_Rect srcRect, dstRect;
    SDL_Renderer *renderer;
    float startingX;
    float startingY;
    float xPos;
    float yPos;
    float velY;
    float maxSpeed;
    float minSpeed;
    float gravity;

    SDL_Texture *frames[4];
    int frameSpeed;
    int numOfFrames;
    int currentFrame;
    double pitchAngle;



public:
    Player(float x, float y);
    ~Player();
    void updatePosition();
    void updateFrame();
    void render();
    void setVelocity(float velY);
    void setMinVelocity();
    SDL_Rect getCollider();
    void setMaxSpeed(float mS);
    void setTextures();
    void setCurrentFrame();
    void reset();
};