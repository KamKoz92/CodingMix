#include "Game.h"

using namespace std;

SDL_Renderer *Game::renderer = nullptr;
SDL_Event Game::event;
bool Game::isRunning = false;
Player *player = nullptr;
Background *background;

Mix_Chunk *wing = nullptr;
Mix_Chunk *hit = nullptr;
Mix_Chunk *point = nullptr;

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

        player = new Player(50.0f, 175.0f);
        background = new Background(50);
        gameState = STATE::MENU;

        if (Mix_OpenAudio(44100, MIX_DEFAULT_FORMAT, 2, 2048) < 0)
        {
            cout << "SDL_Mixer could not initialize!" << endl;
            cout << Mix_GetError() << endl;
        }

        wing = Mix_LoadWAV("assets/audio/wing.wav");
        hit = Mix_LoadWAV("assets/audio/hit.wav");
        point = Mix_LoadWAV("assets/audio/point.wav");

        if (wing == NULL || hit == NULL || point == NULL)
        {
            cout << "failed to load wav" << Mix_GetError() << endl;
        }
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
    if (gameState == STATE::GAME)
    {
        background->update();
        player->updatePosition();
        checkColliders();
    }
    if (gameState == STATE::ENDGAME)
    {
        player->updatePosition();
    }

    keyBoardUpdate();
    player->updateFrame();
}
void Game::keyBoardUpdate()
{
    if (event.type == SDL_KEYDOWN)
    {
        if (event.key.keysym.sym == SDLK_SPACE)
        {
            if (getGameState() == STATE::MENU)
            {
                setGameState(STATE::GAME);
            }
            else if (getGameState() == STATE::GAME)
            {
                player->setMinVelocity();
                Game::playSound("wing");
            }
        }
        else if (event.key.keysym.sym == SDLK_RETURN)
        {
            if (getGameState() == STATE::ENDGAME)
            {
                player->reset();
                background->reset();
                setGameState(STATE::MENU);
            }
        }
    }
}
void Game::render()
{
    SDL_RenderClear(renderer);
    background->render();
    if (gameState == STATE::MENU)
    {
        background->renderMenu();
    }
    else if (gameState == STATE::GAME)
    {
        background->renderScore();
    }
    else if (gameState == STATE::ENDGAME)
    {
        background->renderScore();
        background->renderGameOver();
    }

    player->render();
    SDL_RenderPresent(renderer);
}

void Game::clean()
{
    SDL_DestroyWindow(window);
    SDL_DestroyRenderer(renderer);
    Mix_FreeChunk(wing);
    Mix_FreeChunk(hit);
    Mix_Quit();
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
    if (background->checkPipeColision(player->getCollider()))
    {
        Game::playSound("hit");
        gameState = STATE::ENDGAME;
        player->setMinVelocity();
    }
}

Game::STATE Game::getGameState()
{
    return gameState;
}
void Game::setGameState(Game::STATE state)
{
    gameState = state;
}

void Game::playSound(std::string name)
{
    Mix_Chunk *temp = nullptr;
    if (name == "wing")
    {
        temp = wing;
    }
    else if (name == "point")
    {
        temp = point;
    }
    else if (name == "hit")
    {
        temp = hit;
    }
    Mix_PlayChannel(-1, temp, 0);
}