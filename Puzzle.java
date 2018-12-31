import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Puzzle extends Jeu {

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
		for (int i = 0; i < plateau.hauteur; i++) {
			for (int j = 0; j < plateau.longueur; j++) {
				n = rand.nextInt(listePiece.size());
				plateau.getCase(i, j).poserPiece(listePiece.get(n));
				listePiece.remove(n);
			}
		}

		// Lancement de l'interface graphique
		EventQueue.invokeLater(() -> new VuePuzzle());
	}

	public boolean verifCase(){
		// Vérifie pour chaque case, que l'ID de la pièce correspond à celui de la case,
		// si c'est le cas, la partie se fini, le joueur à gagner

		for (int i = 0; i < plateau.hauteur; i++) {
			for (int j = 0; j < plateau.longueur; j++) {
				CasePuzzle cp = (CasePuzzle) plateau.getCase(i, j);
				if (cp.getValeur() != ((PiecePuzzle) cp.getPiece()).getId()){
					return false;
				}
			}
		}
		return true;
	}

	private class VuePuzzle extends JFrame { // Classe interne qui gère l'interface graphique
		private Container cont;
		private ArrayList<JPanel> panels;

		VuePuzzle(){
			// On met en place la fenêtre
			setPreferredSize(new Dimension(600, 750));
			setTitle("Puzzle");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			this.cont = getContentPane();
			BorderLayout bl = new BorderLayout(0, 0);

			cont.setLayout(bl);

			// On crée 2 JPanel pour le titre et le puzzle
			JPanel titre = new JPanel();
			titre.setPreferredSize(new Dimension(600, 150));
			displayImage(titre, "./img/Puzzle/PuzzleTitle.png");
			cont.add(titre, BorderLayout.NORTH);

			JPanel puzzle = new JPanel();
			puzzle.setPreferredSize(new Dimension(600, 600));
			puzzle.setLayout(new GridLayout(plateau.hauteur, plateau.longueur, 0, 0));
			cont.add(puzzle, BorderLayout.SOUTH);

			// On crée et ajoute des JPanels pour les cases du puzzle
			this.panels = new ArrayList<>();
			for (int i = 0; i < 9; i++) {
				JPanel p = new JPanel();
				panels.add(p);
				puzzle.add(p);
				p.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e) {
						// Comportement à adopter lorsqu'on clique sur une case.
					}
				});
			}

			// On fini de metre en place et on affiche les images dans les cases du puzzle
			pack();
			setResizable(false);
			setVisible(true);
			updateView();
		}

		private void updateView(){
			// Pour chaque case, afficher dans la JPanel correspondante l'image
			// dont l'adresse est contenue dans la PiecePuzzle posée sur cette Case.
			int n = 0;
			for (int i = 0; i < plateau.hauteur; i++) {
				for (int j = 0; j < plateau.longueur; j++) {
					displayImage(panels.get(n), ((PiecePuzzle) plateau.getCase(i, j).getPiece()).getImage());
					n++;
				}
			}
		}

		private void displayImage(JPanel jp, String path) { // Affiche dans jp l'image à l'adresse de path
			JLabel jl = new JLabel();
			jl.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
			jp.add(jl);
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
