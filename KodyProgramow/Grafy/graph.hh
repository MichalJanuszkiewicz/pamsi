#ifndef GRAPH_HH_INCLUDED
#define GRAPH_HH_INCLUDED


class graph {

private:

    // Vertexes and edges of the graph and the
    // src which means a begining vertex of
    // the graph [ source ]

    int vertex;
    int edge;
    int src;

public:

    // Definition of the default class constructor

    graph(){

      vertex = 0;
      edge   = 0;
      src    = 0;

           }

    // Declaration of the other functions
    // that are in sourse file ["graph.cpp"]

    void loadVertexNumber ();
    void loadEdgeNumber   ();
    void loadSrcNumber    ();

    int  returnVertex     ();
    int  returnEdge       ();
    int  returnSrc        ();

    void loadAtributesFromFile (int x,int y,int z);

};


#endif // GRAPH_HH_INCLUDED
