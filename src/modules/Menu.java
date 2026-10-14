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
    static char[] box_style = {'┌', '┐', '└', '┘', '─', '│'};
    static int box_width = 60;
    static int box_height = 20;
    static String username = "";
    static String blank_space = "                      ";
    static String blank_space_board = "    ";
    static int size_board = 12;
    static String[] options = {
        "[  Jugar  ]",
        "[  Salir  ]"
    };
<<<<<<< HEAD
=======
    private static  String username = "";
    private static  final String blank_space = "                      ";
    private static final String warning = "(No se puede borrar y max. 20 caracteres)";
    private static final String warning_two = "(Up para aumentar Down para disminuir)";
    private static final String blank_space_board = "    ";
    public static int size_board = 12;
    
>>>>>>> d2834d6b8ad751b3379a6ad61ac971682856908e

    private static void draw_ascii(TextGraphics txt, int columns,  int menu) {
        txt.enableModifiers(SGR.BOLD);
        Display.draw_box(txt, Display.center_x(columns, box_width), 0, box_width, box_height, box_style);

        int boat_x = Display.center_x(columns, Display.max_line_width(Texts.boat));
        for (int i = 0; i < Texts.boat.length; i++) {
            txt.putString(boat_x, 2 + i, Texts.boat[i]);
        }
        if(menu == 0){
            txt.putString(Display.center_x(columns, Texts.title.length()), Texts.boat.length + 2, Texts.title);
            
        }
        else{
            if(menu == 1){
                txt.putString(Display.center_x(columns, Texts.title_username.length()), Texts.boat.length + 2, Texts.title_username);
                txt.putString(Display.center_x(columns, Texts.warning.length()), Texts.boat.length + 7, Texts.warning);
                txt.setBackgroundColor(TextColor.ANSI.BLACK);
                txt.putString(Display.center_x(columns, blank_space.length()) , Texts.boat.length + 4, blank_space);
                txt.putString(Display.center_x(columns, username.length()), Texts.boat.length + 4, username);
                txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
            }
            else{
<<<<<<< HEAD
                txt.putString(Display.center_x(columns, Texts.title_board_size.length()), Texts.boat.length + 2, Texts.title_board_size);
                txt.setBackgroundColor(TextColor.ANSI.BLACK);
                txt.putString(Display.center_x(columns, blank_space_board.length()), Texts.boat.length + 4, blank_space_board);
                txt.putString(Display.center_x(columns, 2), Texts.boat.length + 4, Integer.toString(size_board));
                txt.putString(Display.center_x(columns, Texts.warning_two.length()), Texts.boat.length + 6, Texts.warning_two);
=======
                txt.putString(Display.center_x(columns, title_board_size.length()), boat.length + 2, title_board_size);
                txt.putString(Display.center_x(columns, warning_two.length()), boat.length + 6, warning_two);
                txt.setBackgroundColor(TextColor.ANSI.BLACK);
                txt.putString(Display.center_x(columns, blank_space_board.length()), boat.length + 4, blank_space_board);
                txt.putString(Display.center_x(columns, 2), boat.length + 4, Integer.toString(size_board));
                txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                
>>>>>>> d2834d6b8ad751b3379a6ad61ac971682856908e
            }
        }     
        
    }

    private static void draw_menu(TextGraphics txt, int columns, int selected,int menu) {
        draw_ascii(txt, columns, menu);
        int x = Display.center_x(columns, Display.max_line_width(options));
        int y = Texts.boat.length + 4;
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
                if(menu == 1){
                    String username_input;
                    if (username.length() < 20){
                        username_input =Display.get_username_input(screen);
                        if(username_input != null){
                            username += username_input;
                        }
                    }
                    else{
                        username_input = Display.get_username_input(screen);
                    }
                    if (username_input == null){
                        if(username.equals("")){
                            username = "user";
                        }
                        menu = 2;
                    }
                }
                else{
                    KeyStroke key = screen.readInput();
                    switch (key.getKeyType()) {
                        case ArrowUp:
                            if(size_board <= 24){
                                size_board ++;
                            }
                            break;
                        case ArrowDown:
                            if(size_board >= 13){
                                size_board -= 1;
                            }
                            break;
                        case Enter:
                            return selected;
                        default:
                            break;
                    }
                }
            }
        }
    }
}