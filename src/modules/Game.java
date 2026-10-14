package src.modules;


import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Game {
    static int size_board = 12;

    public static void game(Screen screen) throws IOException{
        TextGraphics txt = screen.newTextGraphics();
        TerminalSize size = screen.getTerminalSize();
        Player player = new Player();
        
        
    }

}

class Board{
    private int[][] board;
    public void board(int size){
        board = new int[size][size];
    }
    
    public int[][] get_board(){
        return board;
    }

    public boolean set_board(int x, int y){
        if(board[x][y] != 1){
            board[x][y] = 1;
            return true;
        }
        else{
            return false;
        }
    }
}

class Boat{

}

class Player{
    int[][] board_hidden;
    int[][] board_guess;
    Board bh;
    Board bg;
    public void Player(int size){
        bh = new Board();
        board_hidden = bh.get_board();
        bg = new Board();
        board_guess = bg.get_board();
    }

    public boolean set_bg(int x, int y){
        boolean setting = bg.set_board(x, y);;
        return setting;
    }

}
