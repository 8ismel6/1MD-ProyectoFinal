package src.modules;
//Styles and utilities

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Display {
    static void sleep(int sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

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


    //SE NECESITA UNA FUENTE DE TERMINAL ESPECIFICA
    static void draw_board(TextGraphics txt,int x, int y,int size,int[][] board){
        txt.enableModifiers(SGR.BOLD);
        for(int i = 0; i <size;i++){
            for(int j = 0; j<size;j++){
                if (board[i][j] == 2) {
                    txt.setForegroundColor(TextColor.ANSI.RED);
                    txt.setCharacter(x + (i*2),y + j, 'x');
                    txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                    txt.setForegroundColor(TextColor.ANSI.DEFAULT);
                }
                else{
                    if (board[i][j] == 3) {
                        txt.setForegroundColor(TextColor.ANSI.WHITE);
                        txt.setCharacter(x + (i*2),y + j, '0');
                        txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                        txt.setForegroundColor(TextColor.ANSI.DEFAULT);
                    }
                    else{
                        txt.setForegroundColor(TextColor.ANSI.BLUE);
                        txt.setCharacter(x + (i*2),y + j, '■');
                        txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                        txt.setForegroundColor(TextColor.ANSI.DEFAULT);
                }   }     
            }
        }
        
    }

    static void draw_boats(TextGraphics txt,int[][] board,int size, int x, int y){
        for(int i = 0; i <size;i++){
            for(int j = 0; j<size;j++){   
                if(board[i][j] == 1){
                    txt.setForegroundColor(TextColor.ANSI.RED);
                    txt.setCharacter(x + (i*2),y + j, '■');
                    txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                    txt.setForegroundColor(TextColor.ANSI.DEFAULT);
                }             
            }
        }
    }
    
    static void draw_boats_and_pointer(TextGraphics txt,int[][] board,int size, int x, int y, int min_x, int max_x, int min_y, int max_y){
        for(int i = 0; i <size;i++){
            for(int j = 0; j<size;j++){   
                if(board[i][j] == 1){
                    txt.setForegroundColor(TextColor.ANSI.RED);
                    txt.setCharacter(x + (i*2),y + j, '■');
                    txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                    txt.setForegroundColor(TextColor.ANSI.DEFAULT);
                } 
                if(i >= min_x && i <= max_x && j >= min_y && j <= max_y){
                    txt.setForegroundColor(TextColor.ANSI.BLUE);
                    txt.setBackgroundColor(TextColor.ANSI.RED);
                    if(i > min_x && i <= max_x){
                        txt.setCharacter(x + (i*2)- 1,y + j, ' ');    
                    }
                    txt.setCharacter(x + (i*2),y + j, '■');
                    txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
                    txt.setForegroundColor(TextColor.ANSI.DEFAULT);
                }
                
                
            }
        }
    }

    static void draw_pointer(int mid, int top, TextGraphics txt, int x, int y) {
        txt.setForegroundColor(TextColor.ANSI.BLUE);
        txt.setBackgroundColor(TextColor.ANSI.RED);
        txt.setCharacter(mid + x * 2, top + y, '■');
        txt.setForegroundColor(TextColor.ANSI.DEFAULT);
        txt.setBackgroundColor(TextColor.ANSI.DEFAULT);
    }
}