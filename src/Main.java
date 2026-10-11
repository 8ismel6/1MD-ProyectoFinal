package src;
//Main

import java.io.IOException;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import src.modules.Menu;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        try {
            Screen screen = new DefaultTerminalFactory().createScreen();
            screen.startScreen();

            int choice = Menu.show(screen);
            if (choice == 0) {
                //juegofuncion
            }

            screen.stopScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}