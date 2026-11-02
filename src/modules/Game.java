package src.modules;

import java.io.IOException;
import java.util.Random;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;
//import java.util.Random;

public class Game {
    static int size_board = 12;
    static int dificult = 0;
    static int velocity = 1;
    static int sleep_time = 750;
    static int plays = 1;
    static int acerted = 0;
    static int missed = 0;

    public static void game_loop(Screen screen) throws IOException{
        TextGraphics txt = screen.newTextGraphics();
        TerminalSize size = screen.getTerminalSize();
        int columns = size.getColumns();
        int rows = size.getRows();
        int mid_x = Display.center_x(columns, size_board * 2 - 1);

        Player player = new Player(size_board);
        Player bot = new Player(size_board);
        Boat boat = new Boat();
        Random random = new Random();

        boolean game = true;
        int boat_quantity = 5;
        int[] boat_sizes = new int[5];

        Boat bot_boat = new Boat();
        for (int i = 0; i < boat_quantity; i++) {
            bot_boat.generate_boat(random.nextInt(3)+3);
            boat_sizes[i] = bot_boat.size;
            bot.set_boat_random_place(bot.get_board(), bot_boat.max_x, bot_boat.max_y, random, size_board);
        }
        for(int i = 0; i < boat_quantity; i++){
            boat.generate_boat(boat_sizes[i]);
            put_boat(columns,screen, txt,mid_x,boat.min_x,boat.max_x,boat.min_y,boat.max_y,player.get_board(),player);
        }
        game(screen, txt, mid_x, columns, bot, player,player.get_board());
    }

    public static void game(Screen screen,TextGraphics txt,int mid_x,int columns,Player bot,Player player,int[][] pboard) throws IOException{
        int turn = 0;
        int x = 0;
        int y = 0;
        reset_game(screen, player, bot);
        if(size_board % 2 == 0){
            x= size_board /2;
            y = size_board /2;
        }
        else{
            x= (int) Math.ceil(size_board/2);
            y=(int) Math.ceil(size_board/2);
        }
        boolean playing = true;

        while (playing) {
            if (turn == 0){
                screen.clear();
                txt.enableModifiers(SGR.BOLD);
                Display.draw_board(txt,mid_x, 3, size_board,bot.pboard);
                Display.draw_pointer(mid_x, 3, txt, x, y);
                if (plays > 1){
                    txt.putString(Display.center_x(columns, Texts.GameIndication4.length()), 1, Texts.GameIndication4);
                }
                else{
                    txt.putString(Display.center_x(columns, Texts.GameIndication1.length()), 1, Texts.GameIndication1);
                    txt.putString(Display.center_x(columns, Texts.GameIndication2.length()),(size_board + 4), Texts.GameIndication2);
                }
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
                    case Enter:
                        int[][] target = bot.pboard;
                        if (target[x][y] == 1) {
                            target[x][y] = 2;
                            acerted += 1;
                            plays += 1;
                            screen.clear();
                            txt.enableModifiers(SGR.BOLD);
                            Display.draw_board(txt,mid_x, 3, size_board,bot.pboard);
                            Display.draw_pointer(mid_x, 3, txt, x, y);
                            txt.putString(Display.center_x(columns, Texts.acerted.length()), 1, Texts.acerted);
                            screen.refresh();
                            Display.sleep(sleep_time);
                        } else if (target[x][y] == 0) {
                            target[x][y] = 3; 
                            turn = 1;
                            missed += 1;
                            plays += 1;
                            screen.clear();
                            txt.enableModifiers(SGR.BOLD);
                            Display.draw_board(txt,mid_x, 3, size_board,bot.pboard);
                            Display.draw_pointer(mid_x, 3, txt, x, y);
                            txt.putString(Display.center_x(columns, Texts.missed.length()), 1, Texts.missed);
                            screen.refresh();
                            Display.sleep(sleep_time);
                        }
                        break;
                    default:
                        break;
                    
                }
            }
            
            else{
                screen.clear();
                txt.putString(Display.center_x(columns, Texts.GameIndication5.length()), 1, Texts.GameIndication5);
                Display.draw_board(txt,mid_x, 3, size_board,player.pboard);
                Display.draw_boats(txt, pboard, size_board, mid_x, 3);
                screen.refresh();
                Display.sleep(sleep_time);
                if(!Bot.Play(player.get_board(), size_board)){
                    turn = 0;
                    txt.putString(Display.center_x(columns, Texts.bot_missed.length()), 1, Texts.bot_missed);

                }
                else{
                    txt.putString(Display.center_x(columns, Texts.bot_acerted.length()), 1, Texts.bot_acerted);
                }
                Display.draw_board(txt,mid_x, 3, size_board,player.pboard);
                Display.draw_boats(txt, pboard, size_board, mid_x, 3);
                screen.refresh();
                Display.sleep(sleep_time);
            }
            int a = 3;
            if (!has_boats(bot.pboard)) {
                playing = false;
                a = Menu.menu_end(txt, screen, true, columns);   
            } else if (!has_boats(player.get_board())) {
                playing = false;  
                a = Menu.menu_end(txt, screen, false, columns);
            }
            if( a == 0){
                reset_game(screen,player, bot);
            }
        
    }   }

    public static void put_boat(int columns, Screen screen,TextGraphics txt,int mid,int x1,int x2,int y1, int y2,int[][] board,Player player)throws IOException{
        int min_x = x1;
        int max_x = x2;
        int min_y = y1;
        int max_y = y2;
        boolean boat_on = false;


        while (true) {
            screen.clear();
            txt.enableModifiers(SGR.BOLD);
            txt.putString(Display.center_x(columns, Texts.title_put_boat.length()), 1, Texts.title_put_boat);
            Display.draw_board(txt,mid, 3, size_board,player.get_board());
            Display.draw_boats_and_pointer(txt, board, size_board, mid, 3, min_x, max_x, min_y, max_y);
            txt.putString(Display.center_x(columns, Texts.title_down_put_boat.length()),(size_board + 4), Texts.title_down_put_boat);
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
                    boat_on = player.set_boat(min_x, max_x, min_y, max_y);
                    break;
                default:
                    break;
                
            }
            if(boat_on){
                break;
            }
        }
    }


    static boolean has_boats(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1) return true;
            }
        }
        return false;
    }

    public static boolean guess_boat(Player p1, Player p2, int x, int y){
        int[][] board = p2.get_board();
        boolean a;
        if(board[x][y] == 1){
            a = p1.set_board(x,y);
        }
        else{
            a = p1.set_board(x,y);
        }
        return a;

    }

    public static void reset_game(Screen screen,Player player, Player bot) throws IOException{
        bot.reset_board(size_board);
        player.reset_board(size_board);
        //game_loop(screen);
    }

}

class Board{
    private int[][] board;
    public Board(int size){
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
            if(board[x][y] == 1){
                board[x][y] = 2;
                return true;
            }
            else{
                return false;
            }

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

    public void generate_boat(int size_boat){
        Random a = new Random();
        size = size_boat;
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
    int[][] pboard;
    Board board;
    public Player(int size){
        board = new Board(size);
        pboard = board.get_board();
    }
    public void reset_board(int size){
        board = new Board(size);
        pboard = board.get_board();
    }

    public boolean set_boat(int min_x, int max_x,int min_y, int max_y){
        boolean boat_in = verifyi_boat_in(min_x, max_x, min_y, max_y);
        if(!boat_in){
            for(int x = min_x; x<= max_x;x++){
                for(int y =min_y;y<=max_y;y++){
                    pboard[x][y] = 1;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    public boolean verifyi_boat_in(int min_x, int max_x,int min_y, int max_y){
        boolean boat_in = false;
        for(int x = min_x; x<= max_x;x++){
            for(int y =min_y;y<=max_y;y++){
                if(pboard[x][y] == 1){
                    boat_in = true;
                }
            }
        }
        return boat_in;
    }

    public int[][] get_board(){
        return  board.get_board();
    }

    public boolean set_board(int x,int y){
        return board.set_board(x, y);

    }

   public void set_boat_random_place(int[][] board, int max_x, int max_y, Random random, int size_board){
    while (true) {
        int x = random.nextInt(size_board - max_x);
        int y = random.nextInt(size_board - max_y);

        boolean free = true;
        for (int i = x; i <= x + max_x && free; i++) {
            for (int j = y; j <= y + max_y; j++) {
                if (board[i][j] == 1) {
                    free = false;
                    break;
                }
            }
        }

        if (free) {
            for (int i = x; i <= x + max_x; i++) {
                for (int j = y; j <= y + max_y; j++) {
                    board[i][j] = 1;
                }
            }
            return;
        }
    }
}

}

