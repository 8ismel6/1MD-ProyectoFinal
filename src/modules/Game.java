package src.modules;


import java.io.IOException;
import java.util.Random;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;

public class Game {
    static int size_board = 12;
    
    public static void game(Screen screen) throws IOException{
        TextGraphics txt = screen.newTextGraphics();
        TerminalSize size = screen.getTerminalSize();
        int columns = size.getColumns();
        int mid_x = Display.center_x(columns, size_board * 2);
        Player player = new Player(size_board);
        Player bot = new Player(size_board);
        Boat boat = new Boat();
        for(int i = 0; i <= 5; i++){
            boat.generate_boat();
            int[] coordenates = put_boat(screen, txt,mid_x,boat.min_x,boat.max_x,boat.min_y,boat.max_y,player.get_bh());
        }
        /*while (true) {
            screen.clear();
            Display.draw_board(txt,20,0,size_board,x,y);
            screen.refresh();
            KeyStroke key = screen.readInput();
            switch (key.getKeyType()) {
                case ArrowUp:
                    if(y - 1 >= 0){
                        y -= 1;
                    }
                    break;
                case ArrowDown:
                    if(y + 1 < size_board){
                        y += 1;
                    }
                    break;
                case ArrowLeft:
                    if(x - 1 >= 0){
                        x -= 1;
                    }
                    break;
                case ArrowRight:
                    if(x + 1 < size_board){
                        x += 1;
                    }
                    break;
                default:
                    break;
            }
        }*/
       
    }

    public static int[] put_boat(Screen screen,TextGraphics txt,int mid,int x1,int x2,int y1, int y2,int[][] board)throws IOException{
        int min_x = x1;
        int max_x = x2;
        int min_y = y1;
        int max_y = y2;
        int[] coordenates = {min_x,max_x,min_y,max_y};


        while (true) {
            screen.clear();
            Display.draw_board(txt,mid, 0, size_board);
            Display.draw_boats(txt, board, size_board, mid, 0, min_x, max_x, min_y, max_y);
            screen.refresh();
            KeyStroke key = screen.readInput();
            switch (key.getKeyType()) {
                case ArrowUp:
                    if(min_y - 1 >= 0){
                        max_y -= 1;
                        min_y -= 1;
                    }
                    break;
                case ArrowDown:
                    if(max_y + 1 < size_board){
                        max_y += 1;
                        min_y += 1;
                    }
                    break;
                case ArrowLeft:
                    if(min_x - 1 >= 0){
                        min_x -= 1;
                        max_x -= 1;
                    }
                    break;
                case ArrowRight:
                    if(max_x + 1 < size_board){
                        max_x += 1;
                        min_x += 1;
                    }
                    break;
                case Enter:
                    return coordenates;
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
    public Board(int size){
        board = new int[size][size];
    }
    
    public int[][] get_board(){
        board[1][10] = 1;
        board[1][11] = 1;
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
    private boolean horizontal;
    public int size;
    public int min_y = 0;
    public int min_x = 0;
    public int max_y;
    public int max_x;

    public void generate_boat(){
        Random a = new Random();
        size = a.nextInt(6) + 3;
        if(a.nextInt(2) == 0){
            horizontal = false;
            max_x = 0;
            max_y = size -1;
        }
        else{
            horizontal = true;
            max_y = 0;
            max_x = size -1;
        }
    }
}


class Player{
    int[][] board_hidden;
    int[][] board_guess;
    Board bh;
    Board bg;
    public Player(int size){
        bh = new Board(size);
        board_hidden = bh.get_board();
        bg = new Board(size);
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

