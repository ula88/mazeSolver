package maze;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Ula
 */
public class Tremaux {

    final static int START_I = 1, START_J = 1;
    final static int END_I = 255, END_J = 255;
    private static char[][] maze;

    public char[][] controller() throws IOException {
        generujLabirynt();
        tremaux();
//        print();
        koloruj();
//        zapiszLabirynt();
        return maze;
    }

    boolean tremaux() throws IOException {
        Punkt biezacy = new Punkt(START_I, START_J);
        Punkt poprzedni = new Punkt(START_I, START_J);
        Punkt temp = new Punkt(START_I, START_J);
        Punkt wybrany;

        while (true) {
            if (biezacy.i == END_I && biezacy.j == END_J) {
                return true;
            }
            if (isDeadEnd(biezacy.i, biezacy.j)) {
                maze[biezacy.i][biezacy.j] = '2';
                wybrany = wybierzDroge(biezacy.i, biezacy.j, poprzedni.i, poprzedni.j);
                przypisz(temp, biezacy, poprzedni, wybrany);
            } else if (isJunction(biezacy.i, biezacy.j)) {
                if (maze[biezacy.i][biezacy.j] == 'C') {
                    maze[biezacy.i][biezacy.j] = '2';
                    wybrany = wybierzDroge(biezacy.i, biezacy.j, poprzedni.i, poprzedni.j);
                    przypisz(temp, biezacy, poprzedni, wybrany);

                } else if (maze[poprzedni.i][poprzedni.j] == '1' && maze[biezacy.i][biezacy.j] != 'C') {
                    przypisz(temp, biezacy, poprzedni, poprzedni);

                } else if (maze[poprzedni.i][poprzedni.j] == '2' && maze[biezacy.i][biezacy.j] != 'C') {
                    wybrany = wybierzDroge(biezacy.i, biezacy.j, poprzedni.i, poprzedni.j);
                    przypisz(temp, biezacy, poprzedni, wybrany);
                }
            } else {
                if (maze[biezacy.i][biezacy.j] == 'C') {
                    maze[biezacy.i][biezacy.j] = '1';
                } else if (maze[biezacy.i][biezacy.j] == '1') {
                    maze[biezacy.i][biezacy.j] = '2';
                }
                wybrany = wybierzDroge(biezacy.i, biezacy.j, poprzedni.i, poprzedni.j);
                przypisz(temp, biezacy, poprzedni, wybrany);
            }
        }
    }

    public void print() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
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

    public void generujLabirynt() throws IOException {
        GenerujLabirynt generuj = new GenerujLabirynt();
        char tab[][] = generuj.stworzLabirynt();
        maze = tab;
    }

    public void zapiszLabirynt() throws IOException {
        GenerujLabirynt generuj = new GenerujLabirynt();
        generuj.zapis(maze);
    }

    void koloruj() {
        maze[START_I][START_J] = '#';
        maze[END_I][END_J] = '#';
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                if (maze[i][j] == '1') {
                    maze[i][j] = 'o';
                } else {
                    if (maze[i][j] == '2' && isJunction(i, j) && maSasiadow(i, j)) {
                        maze[i][j] = 'o';
                    } else {
                        if (maze[i][j] == '2') {
                            maze[i][j] = 'a';
                        }
                    }
                }
            }
        }
    }

    boolean maSasiadow(int x, int y) {
        int maSasiadow = 0;
        if (maze[x][y - 1] == '1' || maze[x][y - 1] == 'o') {
            maSasiadow++;
        }
        if (maze[x][y + 1] == '1' || maze[x][y + 1] == 'o') {
            maSasiadow++;
        }
        if (maze[x - 1][y] == '1' || maze[x - 1][y] == 'o') {
            maSasiadow++;
        }
        if (maze[x + 1][y] == '1' || maze[x + 1][y] == 'o') {
            maSasiadow++;
        }
        if (maSasiadow > 0) {
            return true;
        } else {
            return false;
        }
    }

    boolean isJunction(int x, int y) {
        int licznikDrog = 0;

        if (maze[x][y - 1] != 'X') {
            licznikDrog++;
        }
        if (maze[x][y + 1] != 'X') {
            licznikDrog++;
        }
        if (maze[x - 1][y] != 'X') {
            licznikDrog++;
        }
        if (maze[x + 1][y] != 'X') {
            licznikDrog++;
        }
        if (licznikDrog >= 3) {
            return true;
        } else {
            return false;
        }
    }

    boolean isDeadEnd(int x, int y) {
        int licznikScian = 0;

        if (maze[x][y - 1] == 'X') {
            licznikScian++;
        }
        if (maze[x][y + 1] == 'X') {
            licznikScian++;
        }
        if (maze[x - 1][y] == 'X') {
            licznikScian++;
        }
        if (maze[x + 1][y] == 'X') {
            licznikScian++;
        }
        if (licznikScian == 3) {
            return true;
        } else {
            return false;
        }
    }

    Punkt wybierzDroge(int x, int y, int p_x, int p_y) throws IOException {

        ArrayList<Punkt> punkty = new ArrayList<Punkt>();
        if (maze[x][y - 1] == 'C' || maze[x][y - 1] == '#') {
            punkty.add(new Punkt(x, y - 1));
        }
        if (maze[x][y + 1] == 'C' || maze[x][y + 1] == '#') {
            punkty.add(new Punkt(x, y + 1));
        }
        if (maze[x - 1][y] == 'C' || maze[x - 1][y] == '#') {
            punkty.add(new Punkt(x - 1, y));
        }
        if (maze[x + 1][y] == 'C' || maze[x + 1][y] == '#') {
            punkty.add(new Punkt(x + 1, y));
        }

        if (punkty.isEmpty()) {
            if (maze[x][y - 1] == '1') {
                punkty.add(new Punkt(x, y - 1));
            }
            if (maze[x][y + 1] == '1') {
                punkty.add(new Punkt(x, y + 1));
            }
            if (maze[x - 1][y] == '1') {
                punkty.add(new Punkt(x - 1, y));
            }
            if (maze[x + 1][y] == '1') {
                punkty.add(new Punkt(x + 1, y));
            }
        }
        if (punkty.isEmpty()) {
            if (maze[x][y - 1] == '2' && isJunction(x, y - 1)) {
                punkty.add(new Punkt(x, y - 1));
            }
            if (maze[x][y + 1] == '2' && isJunction(x, y + 1)) {
                punkty.add(new Punkt(x, y + 1));
            }
            if (maze[x - 1][y] == '2' && isJunction(x - 1, y)) {
                punkty.add(new Punkt(x - 1, y));
            }
            if (maze[x + 1][y] == '2' && isJunction(x + 1, y)) {
                punkty.add(new Punkt(x + 1, y));
            }
        }

        if (punkty.size() == 1) {
            return punkty.get(0);
        } else {
            Random r = new Random();

            int b = 0;
            int c = punkty.size() - 1;
            int index = r.nextInt(c - b + 1) + b;

            if (punkty.size() == 2) {
                if (maze[punkty.get(0).i][punkty.get(0).j] == '2'
                        && maze[punkty.get(1).i][punkty.get(1).j] == '2') {
                    if (punkty.get(0).i == p_x && punkty.get(0).j == p_y) {
                        return punkty.get(1);
                    } else if (punkty.get(1).i == p_x && punkty.get(1).j == p_y) {
                        return punkty.get(0);
                    }
                }
            }
            return punkty.get(index);
        }
    }

    void przypisz(Punkt temp, Punkt biezacy, Punkt poprzedni, Punkt wybrany) {
        temp.i = biezacy.i;
        temp.j = biezacy.j;
        biezacy.i = wybrany.i;
        biezacy.j = wybrany.j;
        poprzedni.i = temp.i;
        poprzedni.j = temp.j;
    }
    //    public static void main(String[] args) throws IOException {
//        Tremaux tre = new Tremaux();
//        tre.controller();
//    }
}
