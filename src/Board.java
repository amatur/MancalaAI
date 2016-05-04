
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    
    public Board() {
        board = new int[14];

        initialize();
        printBoard();
    }
    
    public void initialize(){
        board[MANCALA_LEFT_BOTTOM] = board[MANCALA_RIGHT_TOP] = 0;
        for (int i = 1, j = 13; i <= 6; i++, j--) {
            board[i] = 4;
            board[j] = 4;
        }
    }

    public void updateFromMove(int move, int role){
        int startMovingCoins = -1;
        int startMovingPos = -1;
        if(role==LEFT_PLAYER){
            startMovingCoins = board[LEFT_POT[move]];
            startMovingPos = LEFT_POT[move];
        }
        else if(role==RIGHT_PLAYER){
            startMovingCoins = board[RIGHT_POT[move]];
            startMovingPos = RIGHT_POT[move];
        }
        //distribute coins
        int loop_counter = startMovingCoins;
        for(int i = 0; i<loop_counter; ){
            i++;
            int newpos = getIthPos(startMovingPos, i);
            if(role==LEFT_PLAYER && newpos==MANCALA_RIGHT_TOP){
                loop_counter++;
                continue;
            }
            if(role==RIGHT_PLAYER && newpos==MANCALA_LEFT_BOTTOM){
                loop_counter++;
                continue;
            }
            board[newpos]++;
        }
        board[startMovingPos] -= startMovingCoins;
        
        int totcoins = 0;
        totcoins+=board[MANCALA_LEFT_BOTTOM];
        totcoins+= board[MANCALA_RIGHT_TOP];
        for (int i = 1, j = 13; i <= 6; i++, j--) {
            totcoins+=board[i] ;
            totcoins+=board[j] ;
        }
        assert(totcoins==48);
    }
    
    public String toString() {
        String str = "\n";
        str += ("-------------------\n");

        str += ("MANCALA RIGHT\t" + board[MANCALA_RIGHT_TOP]);
        str += ("-------------------\n");

        for (int i = 1, j = 13; i <= 6; i++, j--) {
            str += (" L(" + i + ")  " + board[i] + "\t\t" + board[j] + "  R(" + i + ")" + "\n");
        }
        str += ("-------------------\n");
        str += ("MANCALA LEFT\t" + board[MANCALA_LEFT_BOTTOM]);
        str += ("-------------------\n");
        return str;
    }

    public void printBoard() {
        System.out.println("------------------------");

        System.out.println("\t\t" + board[MANCALA_RIGHT_TOP] + "  R(M)");
        System.out.println("------------------------");

        for (int i = 1, j = 13; i <= 6; i++, j--) {
            System.out.println(" L(" + i + ")  " + board[i] + "\t" + board[j] + "  R(" + i + ")");
        }
        System.out.println("------------------------");
        System.out.println(" L(M)  " + board[MANCALA_LEFT_BOTTOM]);
        System.out.println("------------------------");

    }

    public int getIthPos(int start, int i){
        return (start + i)%14;
    }
    public int getWinner() {
        int winner = -1;
        if (isEnd() && board[MANCALA_LEFT_BOTTOM] > board[MANCALA_RIGHT_TOP]) {
            winner = LEFT_PLAYER; //0
        } else if (isEnd() && board[MANCALA_LEFT_BOTTOM] < board[MANCALA_RIGHT_TOP]) {
            winner = RIGHT_PLAYER; //1
        } else if (isEnd() && board[MANCALA_LEFT_BOTTOM] == board[MANCALA_RIGHT_TOP]) {
            winner = -1; //DRAW
        }
        return winner;
    }

    public boolean isValidMove(int move, int role){
        if(move<1 || move >6){
            return false;
        } 
        boolean retval = false;
        if(role==LEFT_PLAYER){
             if(board[LEFT_POT[move]]!=0){
                 retval = true;
             }
        }
        else if(role==RIGHT_PLAYER){
            if(board[RIGHT_POT[move]]!=0){
                 retval = true;
             }
        }
        return retval;
    }
    public boolean isEnd() {
        return board[MANCALA_LEFT_BOTTOM] + board[MANCALA_RIGHT_TOP] == 48;
    }

    public ArrayList<Board> neighbors() // all neighboring boards
    {
        ArrayList<Board> ba = new ArrayList<Board>();
        return ba;
    }

    public int[] getBoard() {
        return board;
    }

    public boolean equals(Object y) // does this board equal y?
    {
        Board other = (Board) y;
        for (int i = 0; i < board.length; i++) {
            if (other.getBoard()[i] != board[i]) {
                return false;
            }
        }
        return true;
    }

    public int[] getCopy(int board[]) {
        int[] nxtBoard = new int[board.length];
        for (int i = 0; i < board.length; i++) {
            nxtBoard[i] = board[i];
        }
        return nxtBoard;
    }

    public static final int LEFT_PLAYER = 0;
    public static final int RIGHT_PLAYER = 1;
    private int[] board;
    public static final int[] LEFT_POT = {7,1,2,3,4,5,6};
    public static final int[] RIGHT_POT ={0,13,12,11,10,9,8};
    public static final int MANCALA_LEFT_BOTTOM = 7;
    public static final int MANCALA_RIGHT_TOP = 0;

}
