#pragma once

#include "Game.h"

using namespace std;

class KeyboardController
{
private:
    /* data */
public:
    KeyboardController(/* args */);
    ~KeyboardController();
    void update();
};

void KeyboardController::update() {
    if(Game::event.type == SDL_KEYDOWN) {
        // if()
    }
}

KeyboardController::KeyboardController(/* args */)
{
}

KeyboardController::~KeyboardController()
{
}
