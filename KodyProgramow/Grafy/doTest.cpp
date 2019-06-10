#include "doTest.hh"
#include "matrix.hh"
#include "graph.hh"
#include "list.hh"
#include "dijkstry.hh"
#include <cstdlib>
#include <iostream>

using namespace std;

#include <fstream>

double doTest (int reprezentacjaGrafu,int gestosc,int iloscElementow,int source){

double tmp;

if(reprezentacjaGrafu==1)
  {
  generateGraph(gestosc,iloscElementow,source);

  matrix * newGraph = new matrix;
  tmp=dijkstryMatrixAlgorithm(newGraph);
  cout << endl;

  delete newGraph;

  return tmp;
  }

if(reprezentacjaGrafu==2)
  {
  generateGraph(gestosc,iloscElementow,source);


  list *newList = new list;
  tmp=dijkstryListAlgorithm(newList);


  delete newList;


  return tmp;
  }

}


void generateGraph(float gestosc,float iloscElementow,int source){

fstream plik4;
plik4.open("dane.txt", ios::out | ios::trunc);

int tmp = (int)(100/gestosc); // to jest co ile

float iloscKrawedzi;

iloscKrawedzi = ((iloscElementow)*(iloscElementow-1)*(float)(gestosc/100));

plik4 << iloscKrawedzi;
plik4 << " "  << iloscElementow << " ";
plik4 << source << endl;

cout << tmp;

int help =1;

for(int i=0;i<iloscElementow;i++)
{
  for(int j=i+1;j<iloscElementow;){

     int wylosowana_liczba =( std::rand() % 100 )+1;

      plik4 << i << " ";
      plik4 << j << " ";
      plik4 << wylosowana_liczba << endl;

      plik4 << j << " ";
      plik4 << i << " ";
      plik4 << wylosowana_liczba << endl;

       j=j+tmp;
 }
}

plik4.close();

}
