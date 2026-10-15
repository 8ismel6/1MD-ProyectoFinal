package src.modules;


import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;

public class Game {
    static int size_board = 12;

    public static void game(Screen screen) throws IOException{
        TextGraphics txt = screen.newTextGraphics();
        TerminalSize size = screen.getTerminalSize();
        int x = 0;
        int y = 0;
        while (true) {
            screen.clear();
            Display.draw_board(txt,20,0,25,x,y);
            screen.refresh();
            KeyStroke key = screen.readInput();
            switch (key.getKeyType()) {
                case ArrowUp:
                    if(y - 1 >= 0){
                        y -= 1;
                    }
                    break;
                case ArrowDown:
                    if(y + 1 <= 24){
                        y += 1;
                    }
                    break;
                case ArrowLeft:
                    if(x - 1 >= 0){
                        x -= 1;
                    }
                    break;
                case ArrowRight:
                    if(x + 1 <= 24){
                        x += 1;
                    }
                    break;
                default:
                    break;
            }
        }
       
    }

    public static boolean guess_boat(Player p1, Player p2, int x, int y){
        int[][] board = p2.get_bh();
        boolean a;
        if(board[x][y] == 1){
            a = p1.set_bg(x,y,true);
        }
        else{
            a = p1.set_bg(x,y,false);
        }
        return a;

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

    public boolean set_board(int x, int y, boolean boat_in_cell ){
        if(board[x][y] != 1){
            if(boat_in_cell){
                board[x][y] = 2;
            }
            else{
                board[x][y] = 1;
            }
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

    public boolean set_bg(int x, int y, boolean boat_in_cell){
        boolean setting = bg.set_board(x, y, boat_in_cell);;
        return setting;
    }

    public int[][] get_bh(){
        return  bh.get_board();
    }

}

