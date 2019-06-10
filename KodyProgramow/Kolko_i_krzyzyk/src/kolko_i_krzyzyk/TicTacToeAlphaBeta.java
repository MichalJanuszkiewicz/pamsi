package kolko_i_krzyzyk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;





public class TicTacToeAlphaBeta extends JPanel {
    public static int rozmiar_tablicy;
    private static final int MAX_UTILITY = 1000;
    private static final int MIN_UTILITY = -1000;
    private static final int DRAW = 0;
    private static long START_TIME = System.currentTimeMillis();
    private static boolean IS_CUTOFF_OCCURRED = false;
    private static int PRUNING_WITH_MAX_VALUE_FUNCTION = 0;
    private static int PRUNING_WITH_MIN_VALUE_FUNCTION = 0;
    private static int TOTAL_NODES_GENERATED = 0;
    private static int MAX_DEPTH_REACHED = 0;
    private static String DIFFICULTY_LEVEL = "Hard";
    private static boolean isHumanTurn = false;
    private static JButton buttons[][];
    private static State humanMoveState = new State();
    private final static int DEPTH_CUTOFF_LEVEL = 6;
    public static int Ile_do_wygranej = 3;


    private ArrayList<State> listOfPossibleNextMoves = new ArrayList<>();

    public TicTacToeAlphaBeta() {
        initialize();
        setLayout(new GridLayout(rozmiar_tablicy, rozmiar_tablicy));
        initializebuttons();
    }

    //initialize buttons 2 d array
    public void initialize() {
        buttons = new JButton[rozmiar_tablicy][rozmiar_tablicy];
    }

    public void initializebuttons() {


        for (int i = 0; i <= rozmiar_tablicy - 1; i++) {
            for (int j = 0; j <= rozmiar_tablicy - 1; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setText("");
                buttons[i][j].addActionListener(new buttonListener());

                // to fetch row and columns of 2D button array
                buttons[i][j].putClientProperty("firstIndex", new Integer(i));
                buttons[i][j].putClientProperty("secondIndex", new Integer(j));

                add(buttons[i][j]);
            }
        }
    }

    private class buttonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {


            JButton buttonClicked = (JButton) e.getSource(); //get the particular button that was clicked
            if (isHumanTurn) { // button only works on human turn
                int[] humanInput = new int[2];
                humanInput[0] = (int) buttonClicked.getClientProperty("firstIndex");
                humanInput[1] = (int) buttonClicked.getClientProperty("secondIndex");
                buttons[humanInput[0]][humanInput[1]].setText("O");
                State newState;

                // get Successor of human Move state
                newState = getSuccessor(humanMoveState, humanInput);

                printUpdatedBoard(newState.board);

                if (checkForWin(newState) == true) {
                    JOptionPane.showMessageDialog(null, "Gratulacje! Wygraleś!", "Wynik gry", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Gratulacje! Wygraleś!");
                } else if (isTerminalNode(newState) == true) {
                    JOptionPane.showMessageDialog(null, "Remis!", "Wynik gry", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Remis!");
                } else computerMove(newState);

            }

        }
    }


    public void startGame() {

        State rootNode = this.initState();
        printUpdatedBoard(rootNode.board);

        String[] possibleValues = {"Łatwy", "Trudny"};

        Object selectedValue = JOptionPane.showInputDialog(null,
                "Wybierz poziom trudności", "Wybór poziomu trudności",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);


        if(selectedValue.toString()=="Łatwy") {
            DIFFICULTY_LEVEL = "Easy";
        }
        if(selectedValue.toString()=="Trudny") {
            DIFFICULTY_LEVEL = "Hard";
        }



        int reply = JOptionPane.showConfirmDialog(null,
                "Czy chcesz wykonać pierwszy ruch?", "Wybór rozpoczynającego", JOptionPane.YES_NO_OPTION);

        if (reply == JOptionPane.YES_OPTION) {
            isHumanTurn = true;
            rootNode.nextPlayer = "O";
            humanMoveState = rootNode;
        } else {
            isHumanTurn = false;
            rootNode.nextPlayer = "X";
            this.computerMove(rootNode);
        }
    }

    // Evaluate heuristic value of states
    public int evaluateHeuristicValue(State currentState) {
        if (checkForWin(currentState) == true) {
            if (currentState.nextPlayer.equals("X")) {
                return MIN_UTILITY;

            } else if (currentState.nextPlayer.equals("O")) {
                return MAX_UTILITY;
            }
        }
        return DRAW;

    }

    // get Successor of state
    public State getSuccessor(State currentState, int[] humanInput) {
        if (this.isTerminalNode(currentState) == true) return null;
        else {
            if (currentState.nextPlayer == "X") {
                return new State(this.updateBoard(currentState, humanInput), currentState, "O", this.evaluateHeuristicValue(currentState), currentState.depth + 1);
            } else
                return new State(this.updateBoard(currentState, humanInput), currentState, "X", this.evaluateHeuristicValue(currentState), currentState.depth + 1);

        }
    }

    //check for Leaf node
    public boolean isTerminalNode(State currentState) {
        return this.checkForWin(currentState) || !(this.isEmptySquareOnBoard(currentState.board));
    }

    // check for win on rows, columns and diagonals
    public boolean checkForWin(State currentState) {
        boolean isWin = false;
        isWin = (this.checkForWinOnRows(currentState) || this.checkForWinOnColumns(currentState) || this.checkForWinOnDiagonals(currentState));
        return isWin;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean checkForWinOnRows(State currentState) {

        String[][] aBoard = currentState.board;
        int j;

        for (int x = 0; x < rozmiar_tablicy; x++) {
            j = 0;
            for (int y = 0; y < rozmiar_tablicy - 1; y++) {
                if (aBoard[x][y] == null || aBoard[x][y + 1] == null) {
                    continue;
                } else {
                    if (aBoard[x][y] == aBoard[x][y + 1]) {
                        j++;
                    } else {
                        j = 0;
                    }

                    if (j == Ile_do_wygranej - 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkForWinOnColumns(State currentState) {

        String[][] aBoard = currentState.board;
        int j;

        for (int y = 0; y < rozmiar_tablicy; y++) {
            j = 0;
            for (int x = 0; x < rozmiar_tablicy - 1; x++) {
                if (aBoard[x][y] == null || aBoard[x + 1][y] == null) {
                    continue;
                } else {
                    if (aBoard[x][y] == aBoard[x + 1][y]) {
                        j++;
                    } else {
                        j = 0;
                    }

                    if (j == Ile_do_wygranej - 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkForWinOnDiagonals(State currentState) {

        String[][] aBoard = currentState.board;
        boolean isWinOnLeftDiagonal = false, isWinOnRightDiagonal = false;


        isWinOnLeftDiagonal = this.checkWinOnLeftDiagonal(currentState);

        //if(aBoard[3][0] != null && aBoard[0][3] != null && aBoard[2][1]!=null && aBoard[1][2]!=null)
        isWinOnRightDiagonal = this.checkWinOnRightDiagonal(currentState);

        return isWinOnLeftDiagonal || isWinOnRightDiagonal;

    }

    public boolean checkWinOnLeftDiagonal(State currentState) {
        String[][] aBoard = currentState.board;

        int j;
        // sprawdzanie skosne od lewego gornego do prawego dolnego, zmieniajac x
        for (int x = 0; x < rozmiar_tablicy - (Ile_do_wygranej - 1); x++) {

            j = 0;

            for (int i = 0; i < rozmiar_tablicy - 1 - x; i++) {


                if (aBoard[x + i][i] == null || aBoard[x + i + 1][i + 1] == null) {
                    continue;
                } else {
                    if (aBoard[i][x + i] == aBoard[i + 1][x + i + 1]) {
                        j++;
                    } else {
                        j = 0;
                    }

                    if (j == Ile_do_wygranej - 1) {

                        return true;

                    }
                }
            }
        }
        // sprawdzanie skosne od lewego gornego do prawego dolnego, zmieniajac y
        for (int y = 1; y < rozmiar_tablicy - (Ile_do_wygranej - 1); y++) {

            j = 0;
            for (int i = 0; i < rozmiar_tablicy - 1 - y; i++) {


                if (aBoard[y + i][i] == null || aBoard[y + i + 1][i + 1] == null) {
                    continue;
                } else {
                    if (aBoard[y + i][i] == aBoard[y + i + 1][i + 1]) {
                        j++;
                    } else {
                        j = 0;
                    }

                    if (j == Ile_do_wygranej - 1) {
                        return true;

                    }
                }

            }


        }
        return false;
    }

    public boolean checkWinOnRightDiagonal(State currentState) {
        String[][] aBoard = currentState.board;

        int j;
        // sprawdzanie skosne od lewego dolnego do prawego gornego, zmieniajac x
        for (int x = 0; x < rozmiar_tablicy - (Ile_do_wygranej - 1); x++) {

            j = 0;

            for (int i = 0; i < rozmiar_tablicy - 1 - x; i++) {


                if (aBoard[rozmiar_tablicy - 1 - i][x + i] == null || aBoard[rozmiar_tablicy - i - 2][x + i + 1] == null) {
                    continue;
                } else {
                    if (aBoard[rozmiar_tablicy - 1 - i][x + i] == aBoard[rozmiar_tablicy - i - 2][x + i + 1]) {
                        j++;
                    } else {
                        j = 0;
                    }

                    if (j == Ile_do_wygranej - 1) {

                        return true;

                    }
                }
            }
        }
        // sprawdzanie skosne od lewego dolnego do prawego gornego, zmieniajac y
        for (int y = 1; y < rozmiar_tablicy - (Ile_do_wygranej - 1); y++) {

            j = 0;
            for (int i = 0; i < rozmiar_tablicy - 1 - y; i++) {


                if (aBoard[rozmiar_tablicy - y - i - 1][i] == null || aBoard[rozmiar_tablicy - y - i - 2][i + 1] == null) {
                    continue;
                } else {
                    if (aBoard[rozmiar_tablicy - y - i - 1][i] == aBoard[rozmiar_tablicy - y - i - 2][i + 1]) {
                        j++;
                    } else {
                        j = 0;
                    }

                    if (j == Ile_do_wygranej - 1) {
                        return true;

                    }
                }

            }


        }
        return false;

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////


    // check for empty squares on board , return boolean
    public boolean isEmptySquareOnBoard(String[][] board) {
        boolean isEmptySquareOnBoard = false;
        int boardSize = board.length;
        for (int row = 0; row < boardSize; row++) {
            if (isEmptySquareOnBoard) break;
            for (int column = 0; column < boardSize; column++) {
                if (board[row][column] == null) {
                    isEmptySquareOnBoard = true;
                    break;
                }
            }
        }
        return isEmptySquareOnBoard;

    }


    // return all empty squares location on board
    public ArrayList<int[]> scanAllEmptySquareOnBoard(State currentNode) {
        int boardSize = currentNode.board.length;
        ArrayList<int[]> anArrayList = new ArrayList<int[]>();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (currentNode.board[row][column] == null) {
                    int[] arrayInput = new int[2];
                    arrayInput[0] = row;
                    arrayInput[1] = column;
                    anArrayList.add(arrayInput);
                }
            }
        }
        return anArrayList;
    }

    // update board according to given input
    public String[][] updateBoard(State currentState, int[] emptySquareOnBoard) {
        String[][] newBoard = this.replicateBoard(currentState.board);
        newBoard[emptySquareOnBoard[0]][emptySquareOnBoard[1]] = currentState.nextPlayer;
        return newBoard;
    }

    // copy board
    public String[][] replicateBoard(String[][] board) {
        int boardSize = board.length;
        String[][] newBoard = new String[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++)
                newBoard[row][column] = board[row][column];
        }
        return newBoard;
    }

    // Computer move - execute algorithms according to chosen difficulty level
    public void computerMove(State currentState) {
        isHumanTurn = false;
        State newState = this.initializeStateWithBoard(currentState.board);
        newState = this.nextStateToMove(newState);
        printStatistics();
        this.printUpdatedBoard(newState.board);
        if (this.checkForWin(newState) == true) {
            JOptionPane.showMessageDialog(null, "Niestety przegrałeś :(", "Wynik gry", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Niestety przegrałeś :(");
        } else if (this.isTerminalNode(newState) == true) {
            JOptionPane.showMessageDialog(null, "Remis!", "Wynik gry", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Remis!");
        } else {
            isHumanTurn = true;
            humanMoveState = newState;
            //   humanMove(newState);
        }

    }

    // Determine next state for move
    public State nextStateToMove(State currentState) {
        State newState = new State();
        int numberOfEmptySquares = scanAllEmptySquareOnBoard(currentState).size();
        ArrayList<State> successors = new ArrayList<State>();

        // Only 1 square is left. No need of applying alpha beta
        if (numberOfEmptySquares == 1) {
            successors = this.getAllSuccessors(currentState);
            TOTAL_NODES_GENERATED += 1;
            MAX_DEPTH_REACHED += 1;
            newState = successors.get(0);
        }

        // every square is empty or initial state. Assign random position for move
        else if (numberOfEmptySquares == 16) {
            successors = this.getAllSuccessors(currentState);
            Random r = new Random();
            TOTAL_NODES_GENERATED += 16;
            MAX_DEPTH_REACHED += 1;
            newState = successors.get(r.nextInt(16));
        } else {
            MAX_DEPTH_REACHED = currentState.depth;

            if (DIFFICULTY_LEVEL.equals("Easy")) {
                successors = this.getAllSuccessors(currentState);
                Random r = new Random();
                int numberOfSuccessors = successors.size();
                TOTAL_NODES_GENERATED += numberOfSuccessors;
                newState = successors.get(r.nextInt(numberOfSuccessors));
            } else {

                // apply alpha beta algorithm and store states. Then choose the Max Node in list

                currentState.heuristicValue = this.alphaBetaSearch(currentState, MIN_UTILITY, MAX_UTILITY);
                newState = (this.listOfPossibleNextMoves.size() > 0) ? this.getMaxNodeInList(this.listOfPossibleNextMoves) : getAllSuccessors(currentState).get(0);
                this.listOfPossibleNextMoves.clear();
            }
        }
        return newState;
    }


    // From a set of states , choose node with maximum heuristic value
    public State getMaxNodeInList(ArrayList<State> states) {
        State maxNode = states.get(0);
        int listSize = states.size();
        for (int index = 0; index < listSize; index++)
            if (maxNode.heuristicValue < states.get(index).heuristicValue) maxNode = states.get(index);
        return maxNode;
    }


    //////////////////////////////////////////////////////////////

    // Evaluation function executed on cutoff
    public int evaluationFunction(State state) {

        // Eval(s)= 6X3 + 3X2 + X1 - ( 6O3 + 3O2+ O1)
        // X n is the number of rows, columns, or diagonals with exactly n X 's
        // and no O’s. Similarly, On is the number of rows, columns, or diagonals with just n O’s
        int[] xArray = {0, 0, 0, 0};
        int[] oArray = {0, 0, 0, 0};
        int xCounter = 0, oCounter = 0;
        IS_CUTOFF_OCCURRED = true;
        String[][] board = state.board;
        for (int row = 0; row < board.length; row++) {
            xCounter = 0;
            oCounter = 0;
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == "X") {
                    xCounter = xCounter + 1;
                } else if (board[row][column] == "O") {
                    oCounter = oCounter + 1;
                }
            }
            if (xCounter == 0 && oCounter != 0) {
                oArray[oCounter] += 1;
            }
            if (xCounter != 0 && oCounter == 0) {
                xArray[xCounter] += 1;
            }
        }

        for (int column = 0; column < board.length; column++) {
            xCounter = 0;
            oCounter = 0;
            for (int row = 0; row < board.length; row++) {
                if (board[column][row] == "X") {
                    xCounter = xCounter + 1;
                } else if (board[column][row] == "O") {
                    oCounter = oCounter + 1;
                }
            }
            if (xCounter == 0 && oCounter != 0) {
                oArray[oCounter] += 1;
            }
            if (xCounter != 0 && oCounter == 0) {
                xArray[xCounter] += 1;
            }
        }


        for (int row = 0; row < board.length; row++) {
            xCounter = 0;
            oCounter = 0;
            for (int column = 0; column < board.length; column++) {

                if (row == column) {
                    if (board[row][column] == "X") {
                        xCounter = xCounter + 1;
                    } else if (board[row][column] == "O") {
                        oCounter = oCounter + 1;
                    }
                }
            }
            if (xCounter == 0 && oCounter != 0) {
                oArray[oCounter] += 1;
            }
            if (xCounter != 0 && oCounter == 0) {
                xArray[xCounter] += 1;
            }
        }

        for (int row = 0; row < board.length; row++) {
            xCounter = 0;
            oCounter = 0;
            for (int column = 0; column < board.length; column++) {

                if (row == 3 - column) {
                    if (board[row][column] == "X") {
                        xCounter = xCounter + 1;
                    } else if (board[row][column] == "O") {
                        oCounter = oCounter + 1;
                    }
                }
            }
            if (xCounter == 0 && oCounter != 0) {
                oArray[oCounter] += 1;
            }
            if (xCounter != 0 && oCounter == 0) {
                xArray[xCounter] += 1;
            }
        }


        /////////////////////////////////////////////////////////////////////////////////////////////////////

        if (DIFFICULTY_LEVEL.equals("Hard")) {
            state.heuristicValue = 6 * (xArray[2] - oArray[2]) + 3 * (xArray[1] - oArray[1]) + (xArray[0] - oArray[0]);

        } else {
            state.heuristicValue = 3 * (oArray[0] + oArray[1] + oArray[2]) - 2 * (xArray[0] + xArray[2] + xArray[1]);

        }

        return state.heuristicValue;

    }


    // MAX-VALUE FUNCTION
    public int maxValue(State state, int alpha, int beta, long time) {

        // Calculate MAX DEPTH reached
        if (MAX_DEPTH_REACHED < state.depth) MAX_DEPTH_REACHED = state.depth;
        // Check for terminal node and return heuristic
        if (isTerminalNode(state)) return this.evaluateHeuristicValue(state);
        long timeLimit = 6000;

        // depth cutoff level =6
        if ((System.currentTimeMillis() - time) > timeLimit || state.depth > DEPTH_CUTOFF_LEVEL) {
            return this.evaluationFunction(state);
        }
        state.heuristicValue = MIN_UTILITY;
        ArrayList<State> allSuccessors = this.getAllSuccessors(state);
        TOTAL_NODES_GENERATED = TOTAL_NODES_GENERATED + allSuccessors.size();
        for (int successorIndex = 0; successorIndex < allSuccessors.size(); successorIndex++) {
            State successor = allSuccessors.get(successorIndex);
            state.heuristicValue = Math.max(state.heuristicValue, minValue(successor, alpha, beta, time));
            if (state.heuristicValue > beta) {
                PRUNING_WITH_MAX_VALUE_FUNCTION += 1;
                return state.heuristicValue;
            }
            alpha = Math.max(alpha, state.heuristicValue);

        }

        // add state to list of possible next moves
        if (this.nextMoveStates(state) != null) listOfPossibleNextMoves.add(state);

        return state.heuristicValue;
    }

    // MIN VALUE function
    public int minValue(State state, int alpha, int beta, long time) {

        if (MAX_DEPTH_REACHED < state.depth) MAX_DEPTH_REACHED = state.depth;
        if (isTerminalNode(state)) return this.evaluateHeuristicValue(state);
        long timeLimit = 6000;

        if ((System.currentTimeMillis() - time) > timeLimit || state.depth > DEPTH_CUTOFF_LEVEL) {
            return evaluationFunction(state);
        }
        state.heuristicValue = MAX_UTILITY;
        ArrayList<State> allSuccessors = this.getAllSuccessors(state);
        TOTAL_NODES_GENERATED = TOTAL_NODES_GENERATED + allSuccessors.size();
        for (int successorIndex = 0; successorIndex < allSuccessors.size(); successorIndex++) {
            State successor = allSuccessors.get(successorIndex);
            state.heuristicValue = Math.min(state.heuristicValue, maxValue(successor, alpha, beta, time));
            if (state.heuristicValue < alpha) {
                PRUNING_WITH_MIN_VALUE_FUNCTION += 1;
                return state.heuristicValue;
            }
            beta = Math.min(beta, state.heuristicValue);


        }
        if (this.nextMoveStates(state) != null) listOfPossibleNextMoves.add(state);
        return state.heuristicValue;
    }


    // call maxValue function
    public int alphaBetaSearch(State currentNode, int alpha, int beta) {
        long start_time = System.currentTimeMillis();
        return this.maxValue(currentNode, alpha, beta, start_time);
    }


    // Get next move
    public State nextMoveStates(State currentNode) {
        if (currentNode.depth == 1) return currentNode;
        else return null;
    }

    // get All successor of node
    public ArrayList<State> getAllSuccessors(State currentState) {
        ArrayList<State> allSuccessors = new ArrayList<State>();
        ArrayList<int[]> emptySquaresOnBoard = this.scanAllEmptySquareOnBoard(currentState);
        int numberOfEmptySquareOnBoard = emptySquaresOnBoard.size();
        for (int i = 0; i < numberOfEmptySquareOnBoard; i++) {
            allSuccessors.add(this.getSuccessor(currentState, emptySquaresOnBoard.get(i)));
        }
        return allSuccessors;
    }

    public State initializeStateWithBoard(String[][] board) {
        State state = new State();
        state.board = board;
        state.nextPlayer = "X";
        return state;
    }

    // print board
    public void printUpdatedBoard(String[][] board) {
        int boardSize = board.length;
        START_TIME = System.currentTimeMillis();
        System.out.println("Board :");
        System.out.println();

        for (int row = 0; row < boardSize; row++) {
            System.out.print("|");
            for (int column = 0; column < boardSize; column++) {
                if (board[row][column] == null) {
                    System.out.print("  " + " |");
                    buttons[row][column].setText("");

                } else {
                    System.out.print(" " + board[row][column] + " |");
                    buttons[row][column].setText(board[row][column]);
                    buttons[row][column].setEnabled(false);
                }
            }
            System.out.println();
            System.out.print("-----------------");
            System.out.println();
        }
    }


    // print statistics
    public void printStatistics() {
        System.out.println("Statistics :");
        if (IS_CUTOFF_OCCURRED) {
            System.out.println("Cutoff occurred");
            IS_CUTOFF_OCCURRED = false;
        }
        System.out.println("Maximum depth reached : " + MAX_DEPTH_REACHED);
        System.out.println("Total nodes generated : " + TOTAL_NODES_GENERATED);
        System.out.println("Number of times pruning occured with MAX_VALUE function : " + PRUNING_WITH_MAX_VALUE_FUNCTION);
        System.out.println("Number of times pruning occured with MIN_VALUE function : " + PRUNING_WITH_MIN_VALUE_FUNCTION);
    }

    public State initState() {
        return new State();
    }

    public static void howmanywins(String[] possibleChooseWin) {

        Object choosedWinValue = JOptionPane.showInputDialog(null,
                "Wybierz ile znaczków w rzędzie wygrywa", "Wybór ilości znaków",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleChooseWin, possibleChooseWin[0]);

        if (choosedWinValue.toString() == "3") {
            Ile_do_wygranej = 3;
        }
        if (choosedWinValue.toString() == "4") {
            Ile_do_wygranej = 4;
        }
        if (choosedWinValue.toString() == "5") {
            Ile_do_wygranej = 5;
        }
        if (choosedWinValue.toString() == "6") {
            Ile_do_wygranej = 6;
        }
        if (choosedWinValue.toString() == "7") {
            Ile_do_wygranej = 7;
        }
        if (choosedWinValue.toString() == "8") {
            Ile_do_wygranej = 8;
        }
        if (choosedWinValue.toString() == "9") {
            Ile_do_wygranej = 9;
        }
        if (choosedWinValue.toString() == "10") {
            Ile_do_wygranej = 10;
        }
    }


    public static void main(String args[]) {

        String[] possibleChoose = {"3", "4", "5", "10"};


        Object choosedValue = JOptionPane.showInputDialog(null,
                "Wybierz rozmiar planszy", "Wybór rozmiaru planszy",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleChoose, possibleChoose[0]);

        if (choosedValue.toString() == "3") {
            rozmiar_tablicy = 3;
            String[] possibleChooseWin = {"3"};
            howmanywins(possibleChooseWin);
        }
        if (choosedValue.toString() == "4") {
            rozmiar_tablicy = 4;
            String[] possibleChooseWin = {"3", "4"};
            howmanywins(possibleChooseWin);


        }
        if (choosedValue.toString() == "5") {
            rozmiar_tablicy = 5;
            String[] possibleChooseWin = {"3", "4", "5"};
            howmanywins(possibleChooseWin);

        }

        if (choosedValue.toString() == "10") {
            rozmiar_tablicy = 10;
            String[] possibleChooseWin = {"3", "4", "5", "6", "7", "8", "9", "10"};
            howmanywins(possibleChooseWin);
        }


        TicTacToeAlphaBeta game = new TicTacToeAlphaBeta();
        JFrame window = new JFrame("Tic-Tac-Toe-Alpha-Beta");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(game);
        window.setBounds(300, 200, 300, 300);
        window.setVisible(true);
        game.startGame();
    }
}

