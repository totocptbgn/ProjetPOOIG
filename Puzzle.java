import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

		// Nous avons choisi de faire la variante du Taquin,
		// qui nous a paru plus interressante

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
			setPreferredSize(new Dimension(600, 780));
			setTitle("Puzzle");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			this.cont = getContentPane();
			BorderLayout bl = new BorderLayout(0, 0);
			cont.setLayout(bl);

			// On crée 2 JPanel pour le titre et le puzzle
			JPanel titre = new JPanel();
			titre.setPreferredSize(new Dimension(600, 180));
			displayImage(titre, "./img/Puzzle/PuzzleTitle.png");
			cont.add(titre, BorderLayout.NORTH);


			JPanel puzzle = new JPanel();
			puzzle.setPreferredSize(new Dimension(600, 600));
			puzzle.setLayout(new GridLayout(plateau.hauteur, plateau.longueur, 0, 0));
			cont.add(puzzle, BorderLayout.SOUTH);

			// On crée et ajoute des JPanels pour les cases du puzzle
			this.panels = new ArrayList<>();
			for (int i = 0; i < 9; i++) {
				JPanelCase p = new JPanelCase(i);
				p.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e) {
						// Récupérer la case du plateau cliquée
						CasePuzzle cp = ((PlateauPuzzle) plateau).getAllCases().get(((JPanelCase) p).getId());

						// Récuperer la case qui contient la pièce vide
						ArrayList<CasePuzzle> a = ((PlateauPuzzle) plateau).getAllCases();
						CasePuzzle cv = null;
						for (int j = 0; j < a.size(); j++) {
							if (((PiecePuzzle) a.get(j).getPiece()).getImage().equals("./img/Puzzle/PuzzleCut8.png")){
								cv = a.get(j);
							}
						}

						// Vérifier qu'elle soit en contact avec la pièce vide
						int idClicked = cp.getValeur();
						int idEmpty = cv.getValeur();

						boolean areSided = false;

						switch (idEmpty) {
							case 0:
								int [] isOk0 = {1, 3};
								for (int i1 : isOk0) {
									if (idClicked == i1) {
										areSided = true;
									}
								}
								break;
							case 1:
								int [] isOk1 = {0, 2, 4};
								for (int i1 : isOk1) {
									if (idClicked == i1) {
										areSided = true;
									}
								}
								break;
							case 2:
								int [] isOk2 = {1, 5};
								for (int i1 : isOk2) {
									if (idClicked == i1) {
										areSided = true;
									}
								}
								break;
							case 3:
								int [] isOk3 = {0, 4, 6};
								for (int i1 : isOk3) {
									if (idClicked == i1) {
										areSided = true;
									}
								}
								break;
							case 4:
								int [] isOk4 = {1, 3, 5, 7};
								for (int i5 : isOk4) {
									if (idClicked == i5) {
										areSided = true;
									}
								}
								break;
							case 5:
								int [] isOk5 = {2, 4, 8};
								for (int i4 : isOk5) {
									if (idClicked == i4) {
										areSided = true;
									}
								}
								break;
							case 6:
								int [] isOk6 = {3, 7};
								for (int i3 : isOk6) {
									if (idClicked == i3) {
										areSided = true;
									}
								}
								break;
							case 7:
								int [] isOk7 = {6, 4, 8};
								for (int i2 : isOk7) {
									if (idClicked == i2) {
										areSided = true;
									}
								}
								break;
							case 8:
								int [] isOk8 = {7, 5};
								for (int i1 : isOk8) {
									if (idClicked == i1) {
										areSided = true;
									}
								}
								break;
						}

						// Si c'est le cas échanger les pièces des cases
						if (areSided){
							PiecePuzzle pp = (PiecePuzzle) cp.getPiece();
							PiecePuzzle pv = (PiecePuzzle) cv.getPiece();

							cv.setPiece(pp);
							cp.setPiece(pv);
						}

						updateView();
						if (verifCase()){
							// Afficher un panneau "gagné"
							puzzle.removeAll();
							Image buffer = null;
							try {
								buffer = ImageIO.read(new File("./img/Puzzle/PuzzleFull1.jpg"));
							} catch (IOException ioe) {
								ioe.printStackTrace();
							}
							Graphics g = puzzle.getGraphics();
							g.drawImage(buffer, 0, 0, null);
							setTitle("Félicitations ! Vous avez complétez le puzzle !");
						}
					}
				});
				panels.add(p);
				puzzle.add(p);
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
			for (JPanel p : panels){
				p.repaint();
			}
		}

		private void displayImage(JPanel jp, String path) {
			// Affiche dans jp l'image à l'adresse de path
			// A ne pas utiliser sur les JPanelCase
			jp.removeAll();
			JLabel jl = new JLabel();
			jl.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
			jp.add(jl);
		}
	}

	private class JPanelCase extends JPanel {
		// JPanel modifiée pour être utilisé dans le puzzle en tant que CasePuzzle graphique

		private int id; // ID qui correspond à l'ID de la case qu'elle correspond

		public JPanelCase(int id) {
			super();
			this.id = id;
		}

		public int getId() {
			return id;
		}

		@Override
		public void paintComponent(Graphics g) {
			// On redefinit paintComponent pour qu'elle affiche l'image de la pièce posée
			// sur la Case correspondante
			super.paintComponent(g);

			// On va chercher le chemin de l'image dans la pièce
			String source = "";
			for (CasePuzzle cp : ((PlateauPuzzle) plateau).getAllCases()){
				if (this.id == cp.getValeur()){
					source = ((PiecePuzzle) cp.getPiece()).getImage();
				}
			}

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
