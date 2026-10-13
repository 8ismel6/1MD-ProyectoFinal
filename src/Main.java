package src;
//Main

import java.io.IOException;


import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

//import src.modules.Display;
import src.modules.Menu;
import src.modules.Game;

public class Main {
    public static void main(String[] args) {

        try {
            Screen screen = new DefaultTerminalFactory().createScreen();
            screen.startScreen();
            int choice = Menu.show(screen);
            if (choice == 0) {
                screen.clear();
                Game.game(screen);
            }

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}