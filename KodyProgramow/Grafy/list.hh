#ifndef LIST_HH_INCLUDED
#define LIST_HH_INCLUDED

#include "graph.hh"

class list : public graph{

public:

   // A pointer type list class that will allow us to add new
   // elements for lists

   list * next;

   // Two intigers that will inform us about weights
   // and vertexes

   int v;
   int w;

   // A default class constructor

   list(){

     v = 0;
     w = 0;
     next = nullptr;

          }

   // Declarations of other functions that are in
   // source file "list.cpp"

   void loadListFromFile ();

};

#endif // LIST_HH_INCLUDED
