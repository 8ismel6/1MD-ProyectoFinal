package src.modules;
//Styles and utilities

import java.io.IOException;
import java.util.Random;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class Display {
    static int center_x(int total, int width) {
        return (total - width) / 2;
    }

    static int max_line_width(String[] lines) {
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() > max) {
                max = lines[i].length();
            }
        }
        return max;
    }

    static void draw_box(TextGraphics txt, int x, int y, int width, int height, char[] style) {
        char top_left = style[0], top_right = style[1];
        char bottom_left = style[2], bottom_right = style[3];
        char horizontal = style[4], vertical = style[5];

        for (int i = 1; i < width - 1; i++) {
            txt.setCharacter(x + i, y, horizontal);
            txt.setCharacter(x + i, y + height - 1, horizontal);
        }
        for (int i = 1; i < height - 1; i++) {
            txt.setCharacter(x, y + i, vertical);
            txt.setCharacter(x + width - 1, y + i, vertical);
        }
        txt.setCharacter(x, y, top_left);
        txt.setCharacter(x + width - 1, y, top_right);
        txt.setCharacter(x, y + height - 1, bottom_left);
        txt.setCharacter(x + width - 1, y + height - 1, bottom_right);
    }

    static void draw_options(TextGraphics txt, String[] options, int selected, int x, int y, int spacing) {
        for (int i = 0; i < options.length; i++) {
            if (i == selected) {
                txt.setBackgroundColor(TextColor.ANSI.WHITE);
                txt.setForegroundColor(TextColor.ANSI.BLACK);
            } else {
                txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                txt.setForegroundColor(TextColor.ANSI.DEFAULT);
            }
            txt.putString(x, y + i * spacing, options[i]);
        }
        txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
        txt.setForegroundColor(TextColor.ANSI.DEFAULT);
    }

    static String get_username_input(Screen screen)throws IOException{
        KeyStroke key = screen.readInput();
        switch (key.getKeyType()) {
                case Enter:
                    return null;
                default:
                    try {
                        String input = key.getCharacter().toString();
                        return input;
                    } catch (Exception e) {
                        return "";
                    }

                    
            }
    }

    static void draw_board(TextGraphics txt,int x, int y,int size,int x_selected, int y_seleccted){
        Random a = new Random();
        txt.enableModifiers(SGR.BOLD);
        for(int i = 0; i <size;i++){
            for(int j = 0; j<size;j++){
                if(i == x_selected && j == y_seleccted){
                    txt.setBackgroundColor(TextColor.ANSI.BLUE_BRIGHT);
                    txt.setForegroundColor(TextColor.ANSI.WHITE);
                    txt.setCharacter(x + (i*2),y + j, '■');
                }
                else{
                    if(a.nextInt(2)== 0){
                        txt.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT); 
                    }
                    else{    
                        txt.setForegroundColor(TextColor.ANSI.BLUE);
                    }
                    if(a.nextInt(2) == 0){
                        txt.setCharacter(x + (i*2),y + j, '■');
                    }
                    else{
                        txt.setCharacter(x + (i*2),y+j, '◾');
                    }
                }
                txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                
            }
        }
        
    }

}