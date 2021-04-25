#include <cstdlib>
#include <iostream>
#include "Game.h"

using namespace std;

Game *game = nullptr;

int main(int argc, char *argv[]) {
    const int FPS = 60;
    const int frameDelay = 1000/FPS;

    uint32_t frameStart;
    int frameTime;

    game = new Game();
    game->initalize("FlappyBird", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, 288, 512, false);
    while(game->running()) {
        frameStart = SDL_GetTicks();
        game->handleEvents();
        game->update();
        game->render();
        frameTime = SDL_GetTicks() - frameStart;
        if(frameDelay > frameTime) {
            SDL_Delay(frameDelay - frameTime);
        }
    }
    game->clean();
    return 1;

}