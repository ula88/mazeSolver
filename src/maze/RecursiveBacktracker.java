package maze;

import java.io.IOException;

public class RecursiveBacktracker {

    final static int START_I = 1, START_J = 1;
    final static int END_I = 255, END_J = 255;
    private static char[][] maze;
    private static char[][] odwiedzone;

    public int size() {
        return maze.length;
    }

    public char[][] controller() throws IOException {
        generujLabirynt();
        Punkt mazePos = new Punkt(START_I, START_J);
        solve(mazePos);
        koloruj();
        return maze;
    }

    public void koloruj() {
        maze[START_I][START_J] = '#';
        maze[END_I][END_J] = '#';

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                if (maze[i][j] != 'X' && maze[i][j] != 'r' && maze[i][j] != '#') {
                    if (odwiedzone[i][j] == 'a') {
                        maze[i][j] = 'a';
                    }
                }
            }
        }
    }

    public void generujLabirynt() throws IOException {
        GenerujLabirynt generuj = new GenerujLabirynt();
        char tab[][] = generuj.stworzLabirynt();
        maze = tab;
        odwiedzone = new char[maze.length][maze.length];
    }

    public void zapiszLabirynt() throws IOException {
        GenerujLabirynt generuj = new GenerujLabirynt();
        generuj.zapis(maze);
    }

    boolean solve(Punkt pos) {

        if (!isInMaze(pos)) {
            return false;
        }
        if (isFinal(pos)) {
            return true;
        }
        if (!isClear(pos)) {
            return false;
        }

        assert (isClear(pos));
        mark(pos, 'r');
        markOdwiedzone(pos, 'a');

        if (solve(pos.south())) {
            return true;
        }
        if (solve(pos.west())) {
            return true;
        }
        if (solve(pos.north())) {
            return true;
        }
        if (solve(pos.east())) {
            return true;
        }
        mark(pos, 'C');
        return false;
    }

    public void print() {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (maze[i][j] == 'X') {
                    System.out.print("X");
                } else {
                    if (maze[i][j] == 'C') {
                        System.out.print("C");
                    } else {
                        System.out.print(maze[i][j]);
                    }
                }
            }
            System.out.println();
        }
    }

    public void printOdwiedzone() {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                System.out.print(odwiedzone[i][j]);
            }
            System.out.println();
        }
    }

    char markOdwiedzone(Punkt pos, char value) {
        return markOdwiedzone(pos.i(), pos.j(), value);
    }

    public char markOdwiedzone(int i, int j, char value) {
        assert (isInMaze(i, j));
        char tmp = odwiedzone[i][j];
        odwiedzone[i][j] = value;
        return tmp;
    }

    public char mark(int i, int j, char value) {
        assert (isInMaze(i, j));
        char tmp = maze[i][j];
        maze[i][j] = value;
        return tmp;
    }

    char mark(Punkt pos, char value) {
        return mark(pos.i(), pos.j(), value);
    }

    public boolean isMarked(int i, int j) {
        assert (isInMaze(i, j));
        return (maze[i][j] == 'r');
    }

    boolean isMarked(Punkt pos) {
        return isMarked(pos.i(), pos.j());
    }

    public boolean isClear(int i, int j) {
        assert (isInMaze(i, j));
        return (maze[i][j] != 'X' && maze[i][j] != 'r');
    }

    boolean isClear(Punkt pos) {
        return isClear(pos.i(), pos.j());
    }

    public boolean isInMaze(int i, int j) {
        if (i >= 0 && i < size() && j >= 0 && j < size()) {
            return true;
        } else {
            return false;
        }
    }

    boolean isInMaze(Punkt pos) {
        return isInMaze(pos.i(), pos.j());
    }

    public boolean isFinal(int i, int j) {
        return (i == RecursiveBacktracker.END_I && j == RecursiveBacktracker.END_J);
    }

    boolean isFinal(Punkt pos) {
        return isFinal(pos.i(), pos.j());
    }
};

class Punkt {

    int i, j;

    public Punkt(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int i() {
        return i;
    }

    public int j() {
        return j;
    }

    public void print() {
        System.out.println("(" + i + "," + j + ")");
    }

    public Punkt north() {
        return new Punkt(i - 1, j);
    }

    public Punkt south() {
        return new Punkt(i + 1, j);
    }

    public Punkt east() {
        return new Punkt(i, j + 1);
    }

    public Punkt west() {
        return new Punkt(i, j - 1);
    }
};
