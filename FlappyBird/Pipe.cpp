#include "Pipe.h"

Pipe::Pipe()
{
    this->src.x = 0;
    this->src.y = 0;
    SDL_QueryTexture(Pipe::PIPE_TEX, NULL, NULL, &this->src.w, &this->src.h);


    this->dst.x = 0;
    this->dst.y = 0;
    this->dst.w = src.w;
    this->dst.h = src.h;
}

Pipe::~Pipe()
{
}

void Pipe::setLocation(int x, int y)
{
    this->dst.x = x;
    this->dst.y = y;
}
void Pipe::update()
{
}
void Pipe::render()
{
    TextureManager::Draw(Pipe::PIPE_TEX,this->src, this->dst);
}