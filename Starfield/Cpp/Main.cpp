#include "Window.h"

using namespace std;

Window *window = nullptr;


int main(int argc, char *argv[]) {
    const int FPS = 60;
    const int frameDelay = 1000/FPS;

    Uint32 frameStart;
    int frameTime;

    window = new Window();
    window->init("Starfield", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, 800, 640, false);
    while(window->running()) {
        frameStart = SDL_GetTicks();
        window->handleEvents();
        window->update();
        window->render();
        frameTime = SDL_GetTicks() - frameStart;
        if(frameDelay > frameTime) {
            SDL_Delay(frameDelay - frameTime);
        }
    }
    window->clean();
    return 0;
}