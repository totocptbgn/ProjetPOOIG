import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DominoGommette extends Domino {

	// Cette version du domino est la même que celle de Domino.java mais
	// les chiffres sont remplacés par des couleurs

	private Color [] colors;
	/* Tableau des couleurs, chaque couleurs fait références au chiffre à son index
	 * dans le tableau :
	 * 0 = Violet,
	 * 1 = Bleu Clair,
	 * 2 = Rouge,
	 * 3 = Orange,
	 * 4 = Jaune,
	 * 5 = Vert,
	 * 6 = Bleu Foncé.
	 */

	@Override
	public void lancerPartie() {
		super.lancerPartie();

		// Création des couleurs à utiliser
		this.colors = new Color[7];
		colors[0] = Color.MAGENTA;
		colors[1] = Color.CYAN;
		colors[2] = Color.RED;
		colors[3] = Color.ORANGE;
		colors[4] = Color.YELLOW;
		colors[5] = Color.GREEN;
		colors[6] = Color.BLUE;

	}

	private class VueDominoGommette extends JFrame { // Classe interne qui gère l'interface graphique
		private Container cont;
		private ArrayList<JPanel> panels;

		VueDominoGommette(){
			// On met en place la fenêtre
			setPreferredSize(new Dimension(600, 780));
			setTitle("Domino Gommette");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			this.cont = getContentPane();
			BorderLayout bl = new BorderLayout(0, 0);
			cont.setLayout(bl);

			// On crée 2
			JPanel puzzle = new JPanel();
			puzzle.setPreferredSize(new Dimension(600, 600));
			puzzle.setLayout(new GridLayout(plateau.hauteur, plateau.longueur, 0, 0));
			cont.add(puzzle, BorderLayout.SOUTH);

			// On crée et ajoute des JPanels pour les cases du puzzle


			pack();
			setResizable(false);
			setLocationRelativeTo(null);
			setVisible(true);
			updateView();
		}

		private void updateView(){
			// Pour chaque case, afficher dans la JPanel correspondante l'image
			// dont l'adresse est contenue dans la PiecePuzzle posée sur cette Case.
			for (JPanel p : panels){
				p.repaint();
			}
		}
	}

	private class JPanelCase extends JPanel {
		// JPanel modifiée pour être utilisé dans le puzzle en tant que CasePuzzle graphique

		private int i;
		private int j;

		JPanelCase(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}

		@Override
		public void paintComponent(Graphics g) { // A REFAIRE !
			// On redefinit paintComponent pour qu'elle affiche l'image du domino pos sur la Case correspondante
			super.paintComponent(g);

			// On va chercher le chemin de l'image dans la pièce
			String source = "";

			// Ici on va juste redeffinir la couleur du background...

			// On créer une image à partir de l'adresse
			Image buffer = null;
			try {
				buffer = ImageIO.read(new File(source));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// On la redessine
			g.drawImage(buffer,0,0,null);
		}
	}
}
