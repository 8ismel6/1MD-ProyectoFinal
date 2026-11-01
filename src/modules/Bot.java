package src.modules;
import java.util.Random;

public class Bot {
    public static Random random = new Random();
    public static int acuracity = 2;

    public static void Play(int[][] board, int size) {
        int target = 0;
        if (random.nextInt(acuracity) == 0) {
            target = 1;
        }

        int count = count_cells(board, size, target);

        if (count == 0) {
            target = 1 - target;
            count = count_cells(board, size, target);
        }

        if (count > 0) {
            int n = random.nextInt(count);
            int k = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] == target) {
                        if (k == n) {
                            shoot(board, i, j);
                        }
                        k++;
                    }
                }
            }
        }
    }

    private static int count_cells(int[][] board, int size, int value) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == value) {
                    count++;
                }
            }
        }
        return count;
    }

    private static void shoot(int[][] board, int x, int y) {
        if (board[x][y] == 1) {
            board[x][y] = 2;
        } else {
            board[x][y] = 3; 
        }
    }
}