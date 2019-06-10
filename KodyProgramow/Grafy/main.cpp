#include <iostream>
#include "matrix.hh"
#include "graph.hh"
#include "list.hh"
#include "dijkstry.hh"
#include "doTest.hh"
#include <fstream>
#include <cstdlib>
#include <ctime>

using namespace std;

// Main function

int main()
{

// To generate random weights
srand( time( NULL ) );
// To save results in .txt file
fstream plik;

// A variable we need for out
// program menu = decision

while(1){

int decision;

cout << "Instrukcja :" << endl;
cout << "1 = Dijkstry dla reprezentacji macierzowej grafu dla pliku 'dane.txt'" << endl;
cout << "2 = Dijkstry dla reprezentacji listowej grafu dla pliku 'dane.txt'" << endl;
cout << "3 = Przeprowadz test " << endl;
cout << "4 = Zakoncz program" << endl;
cout << "Decyzja : " << endl;
cin >> decision;
cout << endl;

// If decision = 1 , djkstryAlgorithm
// for matrix graph representation
if(decision==1){
                matrix * newGraph = new matrix;
                dijkstryMatrixAlgorithm(newGraph);
                cout << endl;
                delete newGraph;
               }
// If decision = 2 ,  djkstryAlgorithm
// for list graph representation
if(decision==2){
                 list *newList = new list;
                 dijkstryListAlgorithm(newList);
                 delete newList;
               }
// If decision = 3 , then we use function doTest
// to test Dijikstry algorithm speed
if(decision==3){

      int ileRazy = 1;

      int reprezentacjaGrafu;
      float gestosc;
      float iloscElementow;
      int source = 0;

     cout << endl;
     cout << "Podaj po kolei : " << endl;
     cout << "reprezentacje grafu (1->macierzowa,2->listowa),gestosc grafu,rozmiar grafu" << endl;
     cout << endl;

     cin >> reprezentacjaGrafu;
     cin >> gestosc;
     cin >> iloscElementow;
     cout << endl;

      double wynikDzialania=0;

      for(int i=0;i<ileRazy;i++)
      {
      wynikDzialania += doTest(reprezentacjaGrafu,gestosc,iloscElementow,source);
      }
          wynikDzialania = wynikDzialania/ileRazy;

          plik.open("testyDziałania.txt",ios::out|ios::app);

             plik << endl;
             plik << "Średnia dlugość działania algorytmu" << endl;
             plik << "dla reprezentacji :" ;

             if(reprezentacjaGrafu==1) plik << " macierzowej" << endl;
             if(reprezentacjaGrafu==2) plik << " listwej"     << endl;

             plik << "rozmiaru : " << iloscElementow << endl;
              plik << "gestosci : " << gestosc << " %" << endl;
               plik << "wynosi : " << wynikDzialania << " s" << endl;
                plik << endl;
plik.close();

              }


// If decision = 4
// we close our program
if(decision==4){
                return 0;
               }
};

    return 0;
}
