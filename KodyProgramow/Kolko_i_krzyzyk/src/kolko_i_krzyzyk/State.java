package kolko_i_krzyzyk;

import static kolko_i_krzyzyk.TicTacToeAlphaBeta.rozmiar_tablicy;


public class State {
    String[][] board = new String[rozmiar_tablicy][rozmiar_tablicy];
    String nextPlayer;
    int heuristicValue;
    int depth;
    State parentState;


    public State(String[][] board,State parentState, String nextPlayer,int heuristicValue, int depth) {
        this.board = board;
        this.nextPlayer = nextPlayer;
        this.heuristicValue = heuristicValue;
        this.depth = depth;
        this.parentState=parentState;
    }

    public State(String[][] board) {
        this(board,null, null, 0, 0);
    }

    public State(){

    }

}