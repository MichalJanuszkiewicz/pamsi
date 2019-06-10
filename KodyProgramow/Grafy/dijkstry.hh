#ifndef DIJKSTRY_HH_INCLUDED
#define DIJKSTRY_HH_INCLUDED

#include "list.hh"
#include "matrix.hh"

// Deklaracje algorytmow 

// Funkcje dla reprezentacji macierzowej

int minimumDist(int dist[], bool Dset[],int vertex);

double dijkstryMatrixAlgorithm(matrix *newGraph);

// Funkcje dla reprezentacji listowej

double dijkstryListAlgorithm(list *Lista);


#endif // DIJIKSTRY_HH_INCLUDED
