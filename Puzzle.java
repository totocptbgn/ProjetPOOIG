import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Puzzle extends Jeu {
	private VuePuzzle vp; // Interface graphique (Classe interne)

	@Override
	public void lancerPartie() {

		// Création du Joueur
		this.participants = new Joueur[1];
		participants[0] = new Joueur("", 0); // Ici le joueur a peu d'importance.

		// Création du plateau
		this.plateau = new PlateauPuzzle();

		// Création des pièces
		ArrayList<PiecePuzzle> listePiece = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			String image = "./img/Puzzle/PuzzleCut" + i + ".png";
			listePiece.add(new PiecePuzzle(image, i));
		}

		// Remplissage du plateau
		Random rand = new Random();
		int n = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				n = rand.nextInt(listePiece.size());
				plateau.getCase(i, j).poserPiece(listePiece.get(n));
				listePiece.remove(n);
			}
		}

		// Lancement de l'interface graphique
		EventQueue.invokeLater(() -> this.vp = new VuePuzzle());
	}

	public boolean verifCase(){
		// Vérifie pour chaque case, que l'ID de la pièce correspond à celui de la case,
		// si c'est le cas, la partie ce fini, le joueur à gagner
		return false;
	}

	private class VuePuzzle extends JFrame { // Classe interne qui gère l'interface graphique
		VuePuzzle(){
			setPreferredSize(new Dimension(600, 750));
			setTitle("Puzzle");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			Container cont = getContentPane();
			cont.setLayout(new BorderLayout(0, 0));

			JPanel titre = new JPanel();
			JPanel puzzle = new JPanel();
			cont.add(titre, BorderLayout.NORTH);
			cont.add(puzzle, BorderLayout.SOUTH);

			// titre.setBackground(Color.BLACK);
			// puzzle.setBackground(Color.GRAY);

			titre.setPreferredSize(new Dimension(600, 150));
			displayImage(titre, "./img/Puzzle/PuzzleTitle.png");
			puzzle.setPreferredSize(new Dimension(600, 600));

			puzzle.setLayout(new GridLayout(3, 3, 0, 0));

			JPanel case0 = new JPanel();
			puzzle.add(case0);
			displayImage(case0, "./img/Puzzle/PuzzleCut0.png");
			JPanel case1 = new JPanel();
			puzzle.add(case1);
			displayImage(case1, "./img/Puzzle/PuzzleCut1.png");
			JPanel case2 = new JPanel();
			puzzle.add(case2);
			displayImage(case2, "./img/Puzzle/PuzzleCut2.png");
			JPanel case3 = new JPanel();
			puzzle.add(case3);
			displayImage(case3, "./img/Puzzle/PuzzleCut3.png");
			JPanel case4 = new JPanel();
			puzzle.add(case4);
			displayImage(case4, "./img/Puzzle/PuzzleCut4.png");
			JPanel case5 = new JPanel();
			puzzle.add(case5);
			displayImage(case5, "./img/Puzzle/PuzzleCut5.png");
			JPanel case6 = new JPanel();
			puzzle.add(case6);
			displayImage(case6, "./img/Puzzle/PuzzleCut6.png");
			JPanel case7 = new JPanel();
			puzzle.add(case7);
			displayImage(case7, "./img/Puzzle/PuzzleCut7.png");
			JPanel case8 = new JPanel();
			puzzle.add(case8);
			displayImage(case8, "./img/Puzzle/PuzzleCut8.png");

			pack();
			setResizable(false);
			setVisible(true);


		}

		private void displayImage(JPanel jp, String path) { // Affiche dans jp l'image à l'adresse de path
			JLabel jl = new JLabel();
			jl.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
			jp.add(jl);
		}

		private void updateView(){
			// Pour chaque case, afficher dans la JPanel correspondante l'image
			// dont l'adresse est contenue dans la PiecePuzzle posée sur cette Case.
		}
	}

    /*
   	private class BoiteNom extends JFrame {
        BoiteNom(){

        }
    }*/

	@Override
	public void joueUnTour(Joueur j) {
		// Cette fonction est inutile ici car le jeu est entièrement en interface graphique ...
	}

	@Override
	public void setJoueur() {
		// Dans ce jeu le joueur est unique est n'as pas de réel utilité.
	}

	@Override
	public void afficheIntro() {
		// Cette fonction est inutile ici car le jeu est entièrement en interface graphique ...
	}
}
