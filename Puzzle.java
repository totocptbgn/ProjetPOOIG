import javax.swing.*;
import java.awt.*;

public class Puzzle extends Jeu {
    private Plateau courant;  // Plateau à remplir par le joueur
    private Plateau solution; // Plateau déjà rempli qui est solution
    private Joueur joueur;
    private VuePuzzle vp;

    @Override
    public void lancerPartie() {
        System.out.println("-- Partie de Puzzle --" + "\n");

        // Création du Joueur
        this.setJoueur();

        // Création du plateau solution

        // Remplissage de solution

        // Création du plateau courant


        // Lancement de l'interface graphique
        EventQueue.invokeLater(() -> {
            this.vp = new VuePuzzle();
        });
    }

    @Override
    public void joueUnTour(Joueur j) {

    }

    @Override
    public void setJoueur() {
        System.out.println("Quel est le nom du joueur ?");
        this.joueur = new Joueur(0);
    }

    @Override
    public void afficheIntro() {

    }

    private class VuePuzzle extends JFrame {
        VuePuzzle(){
            setPreferredSize(new Dimension(500, 600));
            setTitle("Puzzle");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Container cont = getContentPane();
            cont.setLayout(new BorderLayout());

            JPanel titre = new JPanel();
            JPanel puzzle = new JPanel();
            cont.add(titre, BorderLayout.NORTH);
            cont.add(puzzle, BorderLayout.SOUTH);

            titre.setBackground(Color.BLACK);
            puzzle.setBackground(Color.GRAY);

            titre.setPreferredSize(new Dimension(500, 100));
            puzzle.setPreferredSize(new Dimension(500, 500));

            puzzle.setLayout(new GridLayout(3, 3));

            JPanel case1 = new JPanel();
            case1.setBackground(Color.GRAY);
            puzzle.add(case1);
            JPanel case2 = new JPanel();
            case2.setBackground(Color.WHITE);
            puzzle.add(case2);
            JPanel case3 = new JPanel();
            case3.setBackground(Color.GRAY);
            puzzle.add(case3);
            JPanel case4 = new JPanel();
            case4.setBackground(Color.WHITE);
            puzzle.add(case4);
            JPanel case5 = new JPanel();
            case5.setBackground(Color.GRAY);
            puzzle.add(case5);
            JPanel case6 = new JPanel();
            case6.setBackground(Color.WHITE);
            puzzle.add(case6);
            JPanel case7 = new JPanel();
            case7.setBackground(Color.GRAY);
            puzzle.add(case7);
            JPanel case8 = new JPanel();
            case8.setBackground(Color.WHITE);
            puzzle.add(case8);
            JPanel case9 = new JPanel();
            case9.setBackground(Color.GRAY);
            puzzle.add(case9);
            
            pack();
            setResizable(false);
            setVisible(true);
        }
    }
}
