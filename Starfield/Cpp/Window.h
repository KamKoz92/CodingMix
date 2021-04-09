#ifndef GAME_H_
#define GAME_H_

#include "SDL2/SDL.h"
#include <SDL2/SDL_image.h>
#include <iostream>
#include <vector>

class Window
{
public:
    Window();
    ~Window();
    void init(const char *title, int xpos, int ypos, int width, int height, bool fullscreen);
    void handleEvents();
    void update();
    void render();
    void clean();
    bool running() { return isRunning; }

    static SDL_Renderer *renderer;
    static SDL_Event event;
    static bool isRunning;

private:
    int cnt;
    SDL_Window *window;
    SDL_Texture *texPlayer;
};
#endif