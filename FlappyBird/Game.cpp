#include "Game.h"

using namespace std;

SDL_Renderer *Game::renderer = nullptr;
SDL_Event Game::event;
bool Game::isRunning = false;
Player *player = nullptr;
Background *background;
SDL_Texture *Game::pipeTexture = nullptr;

Game::Game()
{
}
Game::~Game()
{
}

void Game::initalize(const char *title, int xpos, int ypos, int width, int height, bool fullscreen)
{
    int flags = 0;
    if (fullscreen)
    {
        flags = SDL_WINDOW_FULLSCREEN;
    }

    if (SDL_Init(SDL_INIT_EVERYTHING) == 0)
    {
        cout << "Subsystem Initializsed!.." << endl;
        window = SDL_CreateWindow(title, xpos, ypos, width, height, flags);
        if (window)
        {
            cout << "Window Created!" << endl;
            SDL_Surface *tempSurf = IMG_Load("assets/sprites/icon.png");
            SDL_SetWindowIcon(window, tempSurf);
            SDL_FreeSurface(tempSurf);
        }
        renderer = SDL_CreateRenderer(window, -1, 0);
        if (renderer)
        {
            SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255);
            cout << "Renderer Created!" << endl;
        }
        isRunning = true;

        player = new Player(50.0f, 100.0f);
        background = new Background();
        Game::pipeTexture = TextureManager::LoadTexture("assets/sprites/pipe-green.png");
    }
}

void Game::handleEvents()
{
    SDL_PollEvent(&event);
    switch (event.type)
    {
    case SDL_QUIT:
        isRunning = false;
        break;

    default:
        break;
    }
}
void Game::update()
{
    checkColliders();
    keyBoardUpdate();
    player->update();
}
void Game::keyBoardUpdate()
{
    if (event.type == SDL_KEYDOWN)
    {
        if (event.key.keysym.sym == SDLK_SPACE)
        {
            player->setVelocity(-8);
        }
    }
}
void Game::render()
{
    SDL_RenderClear(renderer);
    background->render();
    player->render();
    SDL_RenderPresent(renderer);
}

void Game::clean()
{
    SDL_DestroyWindow(window);
    SDL_DestroyRenderer(renderer);
    SDL_Quit();
    cout << "Game Closed!" << endl;
}

void Game::checkColliders()
{
    if (Collider::AABB(player->getCollider(), background->getCollider()))
    {
        player->setMaxSpeed(0);
    }
    else
    {
        player->setMaxSpeed(10.0f);
    }
}