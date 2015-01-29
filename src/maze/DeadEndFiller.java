
package maze;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Ula
 */
public class DeadEndFiller {

    final static int START_I = 1, START_J = 1;
    final static int END_I = 255, END_J = 255;
    private static char[][] maze;
    ArrayList<Punkt> slepe = new ArrayList<Punkt>();

    public void generujLabirynt() throws IOException {
        GenerujLabirynt generuj = new GenerujLabirynt();
        char tab[][] = generuj.stworzLabirynt();
        tab[START_I][START_J] = '#';
        tab[END_I][END_J] = '#';
        maze = tab;
    }

    public char[][] controller() throws IOException {
        generujLabirynt();
        znajdzSlepe();
        wypelnijSlepe();
        koloruj();
        return maze;
    }

    public void znajdzSlepe() {
        int sciany;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                sciany = 0;
                if (maze[i][j] == 'C') {
                    if (maze[i - 1][j] == 'X') {
                        sciany++;
                    }
                    if (maze[i + 1][j] == 'X') {
                        sciany++;
                    }
                    if (maze[i][j - 1] == 'X') {
                        sciany++;
                    }
                    if (maze[i][j + 1] == 'X') {
                        sciany++;
                    }
                }
                if (sciany == 3) {
                    if (maze[i][j] != '#') {
                        Punkt a = new Punkt(i, j);
                        slepe.add(a);
                    }
                }
            }
        }
    }

    public void wypelnijSlepe() throws IOException {
        int rozgalezienie;
        int temp_x;
        int temp_y;
        int x, y;

        for (int i = 0; i < slepe.size(); i++) {
            x = slepe.get(i).x;
            y = slepe.get(i).y;

            do {
                temp_x = 0;
                temp_y = 0;
                rozgalezienie = 0;
                if (maze[x - 1][y] != 'X' && maze[x - 1][y] != 'a') {
                    temp_x = x - 1;
                    temp_y = y;
                    rozgalezienie++;
                }
                if (maze[x + 1][y] != 'X' && maze[x + 1][y] != 'a') {
                    temp_x = x + 1;
                    temp_y = y;
                    rozgalezienie++;
                }
                if (maze[x][y - 1] != 'X' && maze[x][y - 1] != 'a') {
                    temp_y = y - 1;
                    temp_x = x;
                    rozgalezienie++;
                }
                if (maze[x][y + 1] != 'X' && maze[x][y + 1] != 'a') {
                    temp_y = y + 1;
                    temp_x = x;
                    rozgalezienie++;
                }
                if (rozgalezienie == 1) {
                    if (maze[x][y] != '#') {
                        maze[x][y] = 'a';
                    } else {
                        rozgalezienie = 2;
                    }
                    x = temp_x;
                    y = temp_y;

                }
            } while (rozgalezienie < 2);
        }
    }

    public void koloruj() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                if (maze[i][j] == 'C') {
                    maze[i][j] = 'd';
                }
            }
        }
    }

    public void wyswietl() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                if (maze[i][j] == 'C') {
                    System.out.print(' ');
                } else {
                    if (maze[i][j] == 'X') {
                        System.out.print('#');
                    } else {
                        System.out.print(maze[i][j]);
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void zapisz() throws IOException {
        GenerujLabirynt generuj = new GenerujLabirynt();
        generuj.zapis(maze);
    }

//    public static void main(String[] args) throws IOException {
//        DeadEndFiller deadEnd = new DeadEndFiller();
//        deadEnd.generujLabirynt();
//        deadEnd.znajdzSlepe();
//        deadEnd.wypelnijSlepe();
//        deadEnd.zapisz();
//    }
    class Punkt {

        int x;
        int y;

        public Punkt(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
