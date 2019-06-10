#include "list.hh"
#include <iostream>
using namespace std;
#include <fstream>

// Definitions of functions
// that are declared in header
// file

void list::loadListFromFile (){

ifstream plik("dane.txt");

if(plik){

int a1,a2,a3;

plik >> a1;
plik >> a2;
plik >> a3;

this->loadAtributesFromFile(a1,a2,a3);

list **graf;
list *pw;

int m = this->returnEdge();

int x , y ,z;

graf = new list * [returnVertex()];       // An adjacencyList table

  for(int i = 0; i < returnVertex(); i++)
  {
    graf[i] = NULL;
  }

  for(int i = 0; i < m; i++)
  {
    plik >> x >> y >> w;     // Reading edge with weight
    pw = new list;           // Creating a new adjacencyList element
    pw->v = y;               // Target edge tip
    pw->w = w;               // A weight of the edge
    pw->next = graf[x];
    graf[x] = pw;            // Adding a new element to list 
  }

}
}
