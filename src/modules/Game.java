package src.modules;


import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Game {
    static int size_board = 12;

    public static void game(Screen screen){
        TextGraphics txt = screen.newTextGraphics();
        TerminalSize size = screen.getTerminalSize();
        
        
        Display.draw_board(txt, Display.center_x(size.getColumns(), 48), 0, 25, 3, 5);
    }

}

class Board{

}

class Boat{

}
