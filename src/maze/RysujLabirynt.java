package maze;

/**
 *
 * @author Ula
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class Surface extends JPanel {

    char[][] tab;

    public Surface(char[][] tab) {
        this.tab = tab;
    }

    private void doDrawing(Graphics g) throws IOException {

        Graphics2D g2d = (Graphics2D) g;
        BasicStroke bs2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2d.setStroke(bs2);
        int x, y;
        y = 1;
        for (int i = 0; i < tab.length; i++) {
            x = 1;
            for (int j = 0; j < tab.length; j++) {
                x += 5;
                if (tab[i][j] == 'X') {
                    g.setColor(Color.black);
                    g2d.fillRect(x, y, 5, 5);
                } else if (tab[i][j] == 'o') {
                    g.setColor(Color.orange);
                    g2d.fillRect(x, y, 5, 5);
                } else if (tab[i][j] == '#') {
                    g.setColor(Color.red);
                    g2d.fillRect(x, y, 5, 5);
                } else if (tab[i][j] == 'a') {
                    g.setColor(new Color(252, 249, 168));
                    g2d.fillRect(x, y, 5, 5);
                } else if (tab[i][j] == 'd') {
                    g.setColor(Color.GREEN);
                    g2d.fillRect(x, y, 5, 5);
                } else if (tab[i][j] == 'r') {
                    g.setColor(Color.PINK);
                    g2d.fillRect(x, y, 5, 5);
                } else {
                    g.setColor(Color.white);
                    g2d.fillRect(x, y, 5, 5);
                }
            }
            y += 5;
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        try {
            doDrawing(g);


        } catch (IOException ex) {
            Logger.getLogger(Surface.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}

public class RysujLabirynt extends JFrame {

    static void zapiszDoPliku(Surface dPanel, String fileName) throws IOException {
        BufferedImage bImg = new BufferedImage(dPanel.getWidth(), dPanel.getWidth(), BufferedImage.TYPE_INT_RGB);
        Graphics2D cg = bImg.createGraphics();
        dPanel.paintAll(cg);

        ImageIO.write(bImg, "png", new File(fileName));
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        int algorytm = 3;

        char[][] tab;
        if (algorytm == 1) {
            RecursiveBacktracker labirynt = new RecursiveBacktracker();
            tab = labirynt.controller();
        } else if (algorytm == 2) {
            DeadEndFiller labirynt = new DeadEndFiller();
            tab = labirynt.controller();
        } else {
            Tremaux labirynt = new Tremaux();
            tab = labirynt.controller();
        }

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane vertical;
        Surface surface = new Surface(tab);
        surface.setPreferredSize(new Dimension(1300, 1300));
        vertical = new JScrollPane(surface);
        vertical.setPreferredSize(new Dimension(1325, 700));
        vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        vertical.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        window.getContentPane().add(vertical);
        window.pack();
        window.setVisible(true);

        String file1 = "C:\\Users\\Ula\\Documents\\NetBeansProjects\\MazeSolverProject\\backtracker.png";
        String file2 = "C:\\Users\\Ula\\Documents\\NetBeansProjects\\MazeSolverProject\\deadend.png";
        String file3 = "C:\\Users\\Ula\\Documents\\NetBeansProjects\\MazeSolverProject\\tremaux.png";

        if (algorytm == 1) {
            zapiszDoPliku(surface, file1);
        } else if (algorytm == 2) {
            zapiszDoPliku(surface, file2);
        } else {
            zapiszDoPliku(surface, file3);
        }
    }
}