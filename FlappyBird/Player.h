#pragma once

#include "Game.h"

class Player
{
    SDL_Texture *playerTexture;
    SDL_Rect srcRect, dstRect;
    SDL_Renderer* renderer;
    float xPos;
    float yPos;
    float velY;
    float maxSpeed;
    float gravity;

    SDL_Texture* frames[3];
    int frameSpeed;
    int numOfFrames;
    int currentFrame;

public:
    Player(float x, float y);
    ~Player();
    void update();
    void render();
    void setVelocity(float velY);
    SDL_Rect getCollider();
    void setMaxSpeed(float mS);
    void setTextures();
    SDL_Texture* getCurrentFrame();
};