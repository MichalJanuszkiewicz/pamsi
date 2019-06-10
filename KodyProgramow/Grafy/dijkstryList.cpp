#include "dijkstry.hh"
#include <iostream>
#include <climits>
#include <fstream>
#include <ctime>


using namespace std;


const int MAXINT = 2147483647;

// Dikstry dla listowego przedstawienia grafu

double dijkstryListAlgorithm(list *Lista){

ifstream plik("dane.txt");

clock_t poczatek,koniec,czas_trwania;

fstream plik2;

plik2.open("wynikiList.txt",ios::out|ios::app);


	  int i,j,m,n,v,u,w,x,y,sptr,*d,*p,*S;
	  bool *QS;                                  // Zbiory Q i S , jak są zbiory
		                                           

	  list **graf;                               // AdjacencyList
	  list *pw,*rw;

	  plik >> m  >> n >> v;  // Starting node, number of vertices and edges


	  // Creating a dynamic tables

	  d = new int [n];                // Table of costs of access
	  p = new int [n];                // Table of predecessors
	  QS = new bool [n];              // Zbiory Q i S
	  graf = new list * [n];          // AdjacencyList
	  S = new int [n];                // A stack
	  sptr = 0;                       // A stack pointer

	  // Initiating of dynamic tables

	  for(i = 0; i < n; i++)
	  {
	    d[i] = MAXINT;
	    p[i] = -1;
	    QS[i] = false;
	    graf[i] = NULL;
	  }

	  // Reading input data

	  for(i = 0; i < m; i++)
	  {
	    plik >> x >> y >> w;           // Reading edge with weight
	    pw = new list;                 // Creating a new adjacencyList element
	    pw->v = y;                     // Target edge tip
	    pw->w = w;                     // A weight of the edge
	    pw->next = graf[x];
	    graf[x] = pw;                  // Adding a new element to list
	  }

	  cout << endl;

	  d[v] = 0;                       // The cost of access to v is zero


    // Designate paths

poczatek = clock();

	  for(i = 0; i < n; i++)
	  {
	    // Dinding a vertex in Q with the lowest cost of d

	    for(j = 0; QS[j]; j++);
	    for(u = j++; j < n; j++)
	      if(!QS[j] && (d[j] < d[u])) u = j;

	    // We move the founded vertex to S

	    QS[u] = true;

	    // We modify all neighbors, who are in Q,

	    for(pw = graf[u]; pw; pw = pw->next)
	      if(!QS[pw->v] && (d[pw->v] > d[u] + pw->w))
	      {
	        d[pw->v] = d[u] + pw->w;
	        p[pw->v] = u;
	      }
	  }

koniec = clock();

	// Ready, we display results
	plik2 << endl;

	  for(i = 0; i < n; i++)
	  {
	    plik2 << i << ": ";

			// We go the path from the end to the beginning,
			// Writing next vertices on the stack

	    for(j = i; j > -1; j = p[j]) S[sptr++] = j;

	    // Display the path by retrieving vertices from the stack

	    while(sptr) plik2 << S[--sptr] << " ";

	    // At the end of the path, we write its cost

	    plik2 << "#" << d[i] << endl;
	  }

plik2.close();

czas_trwania = koniec - poczatek;
double tmp = (double)czas_trwania / CLOCKS_PER_SEC;
return tmp;

}
