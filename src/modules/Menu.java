package src.modules;
//Menu utilities

import java.io.IOException;

import javax.swing.text.TextAction;

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
    


    private static void draw_ascii(TextGraphics txt, int columns,  int menu,int dificult) {
        txt.enableModifiers(SGR.BOLD);
        Display.draw_box(txt, Display.center_x(columns, box_width), 0, box_width, box_height, box_style);

        int boat_x = Display.center_x(columns, Display.max_line_width(Texts.boat));
        for (int i = 0; i < Texts.boat.length; i++) {
            txt.putString(boat_x, 2 + i, Texts.boat[i]);
        } 
        draw_options(txt,menu,columns,dificult);
    }
    private static void draw_options(TextGraphics txt,int menu,int columns,int dificult){
        switch ((menu)) {
            case 0:
                txt.putString(Display.center_x(columns, Texts.title.length()), Texts.boat.length + 2, Texts.title);
                break;
            case 1:
                txt.putString(Display.center_x(columns, Texts.title_username.length()), Texts.boat.length + 2, Texts.title_username);
                txt.putString(Display.center_x(columns, Texts.warning.length()), Texts.boat.length + 7, Texts.warning);
                txt.setBackgroundColor(TextColor.ANSI.BLACK);
                txt.putString(Display.center_x(columns, Texts.blank_space.length()) , Texts.boat.length + 4, Texts.blank_space);
                txt.putString(Display.center_x(columns, username.length()), Texts.boat.length + 4, username);
                txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                break;
            case 2:
                txt.putString(Display.center_x(columns, Texts.title_board_size.length()), Texts.boat.length + 2, Texts.title_board_size);
                txt.setBackgroundColor(TextColor.ANSI.BLACK);
                txt.putString(Display.center_x(columns, Texts.blank_space_board.length()), Texts.boat.length + 4, Texts.blank_space_board);
                txt.putString(Display.center_x(columns, 2), Texts.boat.length + 4, Integer.toString(Game.size_board));
                txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                txt.putString(Display.center_x(columns, Texts.title_board_size.length()), Texts.boat.length + 2, Texts.title_board_size);
                txt.putString(Display.center_x(columns, Texts.warning_two.length()), Texts.boat.length + 6, Texts.warning_two);
                break;
            case 3:
                txt.putString(Display.center_x(columns, Texts.title_dificult.length()), Texts.boat.length + 3, Texts.title_dificult); 
                    for(int i = 0; i<= 3; i++){                            
                        if(dificult == i){
                            txt.setBackgroundColor(TextColor.ANSI.WHITE);
                        }
                        txt.putString(Display.center_x(columns, Texts.dificulty[i].length()),12+i, Texts.dificulty[i]);
                        txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                    }
                break;
            case 4:
                txt.putString(Display.center_x(columns, Texts.title_velocity.length()), Texts.boat.length + 3, Texts.title_velocity);
                txt.setBackgroundColor(TextColor.ANSI.BLACK);
                txt.putString(Display.center_x(columns, Texts.blank_space_velocity.length()) , Texts.boat.length + 5, Texts.blank_space_velocity);
                txt.putString(Display.center_x(columns, 2), Texts.boat.length + 5, "x" +Integer.toString(Game.velocity));
                txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                txt.putString(Display.center_x(columns, Texts.subtitle_velocity.length()), Texts.boat.length + 7, Texts.subtitle_velocity);
                break;
            default:
                break;
        }   
    }

    private static void draw_menu(TextGraphics txt, int columns, int selected,int menu,int dificult) {
        draw_ascii(txt, columns, menu,dificult);
        int x = Display.center_x(columns, Display.max_line_width(Texts.options));
        int y = Texts.boat.length + 4;
        if(menu == 0){
            Display.draw_options(txt, Texts.options, selected, x, y, 2);
        }
    }

    public static int show(Screen screen) throws IOException {
        TextGraphics txt = screen.newTextGraphics();
        int selected = 0;
        int menu = 0;

        while (true) {
            TerminalSize size = screen.getTerminalSize();

            screen.clear();
            draw_menu(txt, size.getColumns(), selected,menu,Game.dificult);
            screen.refresh();
            KeyStroke key = screen.readInput();
            switch(menu){
                case 0:
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
                    break;
                case 1:
                    String username_input = "";
                    switch (key.getKeyType()) {
                        case Enter:
                            if (username.equals("")){
                                username = "user";
                            }
                            menu = 2;
                            break;
                        case Character:
                            try {
                                username_input = key.getCharacter().toString();
                                if(username.length() < 20){
                                    username += username_input;
                                }
                                break;
                            } catch (Exception e) {
                                break;
                            }
                        default:
                            break;  
                    }
                    break;
                case 2:
                    switch (key.getKeyType()) {
                        case ArrowUp:
                            if(Game.size_board <= 24){
                                Game.size_board ++;
                            }
                            break;
                        case ArrowDown:
                            if(Game.size_board >= 13){
                                Game.size_board -= 1;
                            }
                            break;
                        case Enter:
                            Game.boat_quantity =(int) Game.size_board / 2;
                            menu = 3;
                            break;
                        default:
                            break;
                        }
                    break;
                case 3:
                        switch (key.getKeyType()) {
                            case ArrowUp:
                                if(Game.dificult ==0){
                                    Game.dificult = 3;
                                }
                                else{
                                    Game.dificult-= 1;
                                }
                                break;
                            case ArrowDown:
                                if(Game.dificult == 3){
                                    Game.dificult = 0;
                                }
                                else{
                                    Game.dificult += 1;
                                }
                                break;
                            case Enter:
                                menu = 4;
                            default:
                                break;
                            }
                    break;
                case 4:
                    switch (key.getKeyType()) {
                        case ArrowUp:
                            if(Game.velocity <= 3){
                                Game.velocity ++;
                            }
                            break;
                        case ArrowDown:
                            if(Game.velocity >= 2){
                                Game.velocity-= 1;
                            }
                            break;
                        case Enter:
                            Game.sleep_time = 750 / Game.velocity;
                            return selected;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }
    
    public static int menu_end(TextGraphics txt,Screen screen,boolean win,int columns) throws IOException{
        int selected = 0;
        String user = "- Hola, " + username + " estas son tus estadisticas";
        String plays = "- Intentos realizados: " +Integer.toString(Game.plays);
        String acerted = "- Intentos acertados: " + Integer.toString(Game.acerted);
        String missed = "- Intentos fallidos: " + Integer.toString(Game.missed);
        while (true) {
            screen.clear();
            Display.draw_box(txt, Display.center_x(columns, box_width), 0, box_width, box_height, box_style);  
            txt.putString(Display.center_x(columns, user.length())  , 2, user);
            txt.putString(Display.center_x(columns, user.length())   , 3, plays);
            txt.putString(Display.center_x(columns, user.length())  , 4, acerted);
            txt.putString(Display.center_x(columns, user.length()) , 5, missed);
            if(win){
                txt.putString(Display.center_x(columns, Texts.title_win.length()), Texts.boat.length + 2, Texts.title_win);
            }
            else{
                txt.putString(Display.center_x(columns, Texts.title_lose.length()), Texts.boat.length + 2, Texts.title_lose);
            }
            Display.draw_options(txt, Texts.final_options, selected, Display.center_x(columns, Display.max_line_width(Texts.options)), Texts.boat.length + 4, 2);
            screen.refresh();
            KeyStroke key = screen.readInput();
            switch (key.getKeyType()) {
                case ArrowUp:
                    if(selected == 0){
                        selected = 2;
                    }
                    else{
                        selected -= 1;
                    }
                    break;
                case ArrowDown:
                    if(selected == 2){
                        selected = 0;
                    }
                    else{
                        selected += 1;
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
