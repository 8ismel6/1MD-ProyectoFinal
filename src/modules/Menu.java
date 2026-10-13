package src.modules;
//Menu utilities

import java.io.IOException;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class Menu {
    //ascii
    private static final char[] box_style = {'┌', '┐', '└', '┘', '─', '│'};
    private static final int box_width = 60;
    private static final int box_height = 20;

    private static final String[] boat = {
        "    __|__ |___| |\\",
        "    |o__| |___| | \\",
        "    |___| |___| |o \\",
        "   _|___| |___| |__o\\",
        "  /...\\_____|___|____\\_/",
        "  \\   o * o * * o o  /",
        "   \\----------------/"
    };
    private static final String title = "--- Proyecto final batalla naval Ismael y Yamandu ---";
    private static final String[] options = {
        "[  Jugar  ]",
        "[  Salir  ]"
    };
    private static final String title_username = "--- Ingrese nombre de usuario ---";
    private static  String username = "";
    private static  String blank_space = "";
    private static  int  num_blank_space = 20;
    

    private static void draw_ascii(TextGraphics txt, int columns,  int menu) {
        txt.enableModifiers(SGR.BOLD);
        Display.draw_box(txt, Display.center_x(columns, box_width), 0, box_width, box_height, box_style);

        int boat_x = Display.center_x(columns, Display.max_line_width(boat));
        for (int i = 0; i < boat.length; i++) {
            txt.putString(boat_x, 2 + i, boat[i]);
        }
        if(menu == 0){
            txt.putString(Display.center_x(columns, title.length()), boat.length + 2, title);
            
        }
        else{
            txt.putString(Display.center_x(columns, title_username.length()), boat.length + 2, title_username);
            txt.setBackgroundColor(TextColor.ANSI.BLACK);
            blank_space = "";
            for(int i = 0;i<num_blank_space;i++){
                blank_space += " ";
            }
            txt.putString(Display.center_x(columns, blank_space.length()) , boat.length + 4, blank_space);
            txt.putString(Display.center_x(columns, username.length()), boat.length + 4, username);
            txt.setBackgroundColor(TextColor.ANSI.DEFAULT);

        }     
        
    }

    private static void draw_menu(TextGraphics txt, int columns, int selected,int menu) {
        draw_ascii(txt, columns, menu);
        int x = Display.center_x(columns, Display.max_line_width(options));
        int y = boat.length + 4;
        if(menu == 0){
            Display.draw_options(txt, options, selected, x, y, 2);
        }
    }

    public static int show(Screen screen) throws IOException {
        TextGraphics txt = screen.newTextGraphics();
        int selected = 0;
        int menu = 0;

        while (true) {
            TerminalSize size = screen.getTerminalSize();

            screen.clear();
            draw_menu(txt, size.getColumns(), selected,menu);
            screen.refresh();
                                                          
            if (menu == 0){

                KeyStroke key = screen.readInput();
            switch (key.getKeyType()) {
                case ArrowUp:
                    selected = 0;
                    break;
                case ArrowDown:
                    selected = 1;
                    break;
                case Enter:
                    if(selected == 1){
                        return selected;
                    }
                    else{
                        menu = 1;
                    }
                    break;
                default:
                    break;
            }
            }
            else{
                if (username.length() < 20){
                    username += Display.draw_username_input(screen);
                    blank_space = "";

                }
                else{
                    String a = Display.draw_username_input(screen);
                }
            }
        }
    }
}