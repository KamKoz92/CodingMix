#ifndef GAME_H_
#define GAME_H_

#include "SDL2/SDL.h"
#include <SDL2/SDL_image.h>
#include "Player.h"
#include "TextureManager.h"
#include "Background.h"
#include "Collider.h"
#include <iostream>
#include <vector>

class Game
{
    int cnt;
    SDL_Window *window;

public:
    static SDL_Renderer *renderer;
    static SDL_Event event;
    static SDL_Texture *pipeTexture;
    static bool isRunning;

    Game();
    ~Game();
    void initalize(const char *title, int xpos, int ypos, int width, int height, bool fullscreen);
    void handleEvents();
    void update();
    void render();
    void clean();
    bool running()
    {
        return isRunning;
    }
    void keyBoardUpdate();
    void checkColliders();
};

#endif