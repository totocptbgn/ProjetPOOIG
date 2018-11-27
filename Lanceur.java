import javax.swing.*;
import java.awt.*;

public class Lanceur extends JFrame {

    public Lanceur() {
        this.setTitle("Projet POOIG");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(300, 150));

        Container cont = this.getContentPane();

        JPanel panel = new JPanel();
        cont.add(panel);

        panel.add(new JLabel("Launcher"));

        JButton domino = new JButton();
        panel.add(domino);
        domino.setText("Domino");
        domino.addActionListener((e) -> {
            this.setVisible(false);
            Domino d = new Domino();
            d.lancerPartie();
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            Lanceur l = new Lanceur();
            // l.pack();
            l.setVisible(true);
        });
    }
}
