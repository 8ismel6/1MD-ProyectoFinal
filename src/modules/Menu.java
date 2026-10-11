package src.modules;
//Menu utilities

import java.io.IOException;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
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

    private static void draw_ascii(TextGraphics txt, int columns) {
        txt.enableModifiers(SGR.BOLD);
        Display.draw_box(txt, Display.center_x(columns, box_width), 0, box_width, box_height, box_style);

        int boat_x = Display.center_x(columns, Display.max_line_width(boat));
        for (int i = 0; i < boat.length; i++) {
            txt.putString(boat_x, 2 + i, boat[i]);
        }

        txt.putString(Display.center_x(columns, title.length()), boat.length + 2, title);
    }

    private static void draw_menu(TextGraphics txt, int columns, int selected) {
        draw_ascii(txt, columns);
        int x = Display.center_x(columns, Display.max_line_width(options));
        int y = boat.length + 4;
        Display.draw_options(txt, options, selected, x, y, 2);
    }

    public static int show(Screen screen) throws IOException {
        TextGraphics txt = screen.newTextGraphics();
        int selected = 0;

        while (true) {
            TerminalSize size = screen.getTerminalSize();

            screen.clear();
            draw_menu(txt, size.getColumns(), selected);
            screen.refresh();

            KeyStroke key = screen.readInput();
            switch (key.getKeyType()) {
                case ArrowUp:
                    selected = 0;
                    break;
                case ArrowDown:
                    selected = 1;
                    break;
                case Enter:
                    return selected;
                default:
                    break;
            }
        }
    }
}