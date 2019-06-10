#include <iostream>
#include "graph.hh"

/* Definitions of other functions
   declared  in class graph       */

// Funcion of type int that returns
// vertex value

int graph::returnVertex(){

   int tmp;
   tmp = vertex;

   return tmp;
}


// Funcion of type int that returns
// edge value

int graph::returnEdge(){

   int tmp;
   tmp = edge;

   return tmp;
}

// Funcion of type void that allows user
// to load varaible vertex

void graph::loadVertexNumber(){

   int tmp;
   std::cin >> tmp;
   vertex=tmp;

}

// Funcion of type void that allows user
// to load varaible edge

void graph::loadEdgeNumber(){

   int tmp;
   std::cin >> tmp;
   edge=tmp;

}

// Function of type void that loads src number

void graph::loadSrcNumber(){

   int tmp;
   std::cin >> tmp;
   src=tmp;

}

// Function of type int that returns src number

int graph::returnSrc(){

     int tmp;
     tmp = src;

     return tmp;
}

// Function that we need to load graph
// from file

void graph::loadAtributesFromFile(int x,int y,int z)
{
   edge = x;
   vertex = y;
   src = z;

}
