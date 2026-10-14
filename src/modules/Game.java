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
}

class Boat{

}

class Player{
    int[][] board_hidden;
    int[][] board_guess;
    public void Player(int size){
        Board bh = new Board();
        board_hidden = bh.get_board();
        Board bg = new Board();
        board_guess = bg.get_board();
    }

}
