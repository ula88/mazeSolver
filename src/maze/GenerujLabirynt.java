package maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Ula
 */
public class GenerujLabirynt {

    int rozmiarLabiryntu = 128;
    ArrayList<Labirynt> labirynt = new ArrayList<Labirynt>();
    int rozmiar = 0;
    String fileName = "C:\\Users\\Ula\\Documents\\NetBeansProjects\\MazeSolverProject\\mazeData.txt";

    public char[][] stworzLabirynt() throws IOException {
        odczytDanychZPliku();
        char tab[][] = tworzenieLabiryntu();
        return tab;
    }

    void odczytDanychZPliku() throws IOException {
        BufferedReader plik = null;
        String tab[];
        int maxValue = 0;
        try {
            plik = new BufferedReader(new FileReader(fileName));

            String l = plik.readLine();

            while (l != null) {
                tab = l.split(" ");
                Labirynt a = new Labirynt(Integer.parseInt(tab[0]), Integer.parseInt(tab[1]), Integer.parseInt(tab[2]));
                labirynt.add(a);
                if (maxValue < Integer.parseInt(tab[0])) {
                    maxValue = Integer.parseInt(tab[0]);
                }
                l = plik.readLine();
            }
            maxValue++;
            rozmiar = maxValue;
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku " + fileName);
        } finally {
            if (plik != null) {
                plik.close();
            }
        }
    }

    char[][] tworzenieLabiryntu() throws IOException {
        char tab[][] = new char[rozmiarLabiryntu * 2 + 1][rozmiarLabiryntu * 2 + 1];

        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                if (i == 0 || i == tab.length - 1 || j == 0 || j == tab.length - 1) {
                    tab[i][j] = 'X';
                }
            }
        }

        for (int i = 0; i < labirynt.size(); i++) {
            if (labirynt.get(i).decyzja == 0) {
                //w dol
                tab[labirynt.get(i).y * 2][labirynt.get(i).x * 2] = 'X';
                tab[labirynt.get(i).y * 2 + 1][labirynt.get(i).x * 2] = 'X';
                tab[labirynt.get(i).y * 2 + 2][labirynt.get(i).x * 2] = 'X';

                //w bok
                tab[labirynt.get(i).y * 2][labirynt.get(i).x * 2] = 'X';
                tab[labirynt.get(i).y * 2][labirynt.get(i).x * 2 + 1] = 'X';
                tab[labirynt.get(i).y * 2][labirynt.get(i).x * 2 + 2] = 'X';
            }
            if (labirynt.get(i).decyzja == 1) {
                //w dol
                tab[labirynt.get(i).y * 2][labirynt.get(i).x * 2] = 'X';
                tab[labirynt.get(i).y * 2 + 1][labirynt.get(i).x * 2] = 'X';
                tab[labirynt.get(i).y * 2 + 2][labirynt.get(i).x * 2] = 'X';
            }
            if (labirynt.get(i).decyzja == 2) {
                //w prawo
                tab[labirynt.get(i).y * 2][labirynt.get(i).x * 2] = 'X';
                tab[labirynt.get(i).y * 2][labirynt.get(i).x * 2 + 1] = 'X';
                tab[labirynt.get(i).y * 2][labirynt.get(i).x * 2 + 2] = 'X';
            }
        }

        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                if (tab[i][j] != 'X' && tab[i][j] != '#') {
                    tab[i][j] = 'C';
                }
            }
        }
        return tab;
    }

    public void zapis(char tab[][]) throws IOException {
        String nazwa = "C:\\Users\\Ula\\Documents\\NetBeansProjects\\MazeSolverProject\\wynik.txt";
        PrintWriter plik = null;
        try {
            plik = new PrintWriter(new FileWriter(nazwa, true));
            for (int i = 0; i < tab.length; i++) {
                for (int j = 0; j < tab[0].length; j++) {
                    if (tab[i][j] == 'X') {
                        plik.print('█');
                    } else {
                        if (tab[i][j] == 'C') {
                            plik.print(' ');
                        } else {
                            plik.print(tab[i][j]);
                        }
                    }

                }
                plik.println();
            }
            plik.println();
        } finally {
            if (plik != null) {
                plik.close();
            }
        }
    }

    class Labirynt {

        int x;
        int y;
        int decyzja;

        public Labirynt(int x, int y, int decyzja) {
            this.x = x;
            this.y = y;
            this.decyzja = decyzja;
        }
    }
}
