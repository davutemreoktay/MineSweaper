import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    int rows, cols, size;
    int[][] map;
    int[][] board;
    boolean game = true;

    Random rand = new Random();
    Scanner scan = new Scanner(System.in);

    MineSweeper(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.map = new int[rows][cols];
        this.board = new int[rows][cols];
        this.size = rows * cols;
    }

    public void run() {
        int row, col, victory = 0;
        prepareGame();
        System.out.println("Oyun Başladı");
        while (game) {
            print(map);
            System.out.println();
            print(board);
            System.out.print("Satır: ");
            row = scan.nextInt();
            System.out.print("Sütun: ");
            col = scan.nextInt();


            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                System.out.println("Geçersiz Giriş");
                continue;
            }

            if (map[row][col] != -1) {
                check(row, col);
                victory++;
                if (victory == (size - (size / 4))) {
                    System.out.println("Kazandınız.");
                    break;
                }
            } else {
                game = false;
                System.out.println("Mayına Bastınız. Oyun Bitti!");
            }

        }

    }

    private void prepareGame() {

        int totalMines = size / 4;
        for (int i = 0; i < totalMines; i++) {
            int randRow, randCol;
            do {
                randRow = rand.nextInt(rows);
                randCol = rand.nextInt(cols);
            } while (map[randRow][randCol] == -1);
            map[randRow][randCol] = -1;
        }


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == -1) {
                    continue;
                }
                int count = 0;
                for (int r = -1; r <= 1; r++) {
                    for (int c = -1; c <= 1; c++) {
                        int newRow = i + r;
                        int newCol = j + c;
                        if (isValid(newRow, newCol) && map[newRow][newCol] == -1) {
                            count++;
                        }
                    }
                }
                map[i][j] = count;
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private void check(int row, int col) {
        if (board[row][col] != 0) {
            return;
        }

        board[row][col] = map[row][col];

        if (map[row][col] == 0) {

            for (int r = -1; r <= 1; r++) {
                for (int c = -1; c <= 1; c++) {
                    int newRow = row + r;
                    int newCol = col + c;
                    if (isValid(newRow, newCol)) {
                        check(newRow, newCol);
                    }
                }
            }
        }
    }

    private void print(int[][] grid) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == -1) {
                    System.out.print("* ");
                } else if (grid[i][j] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(grid[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
