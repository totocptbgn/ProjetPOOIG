import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/*
 * 				@author Thomas (Idée de Hind)
 * 				Nous avons choisi de faire la variante du Taquin,
 * 				qui nous a paru plus interressante,
 * 				voir le README pour le fonctionnement du jeu.
 */

public class Puzzle extends Jeu {

	@Override
	public void lancerPartie() {

		// Création du Joueur
		setJoueur();

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
		int n;
		for (int i = 0; i < plateau.hauteur; i++) {
			for (int j = 0; j < plateau.longueur; j++) {
				n = rand.nextInt(listePiece.size());
				plateau.getCase(i, j).poserPiece(listePiece.get(n));
				listePiece.remove(n);
			}
		}

		// On affiche le message
		afficheIntro();

		// On propose le choix de l'interface au joueur
		boolean textuel = false;
		boolean b = true;
		while (b) {
			System.out.println(" Une interface graphique est disponible ! (recommandée)");
			System.out.print(" Voulez vous l'utiliser ? [y/n] ");
			Scanner sc = new Scanner(System.in);
			String answer = sc.next();
			if (answer.equals("y") || answer.equals("Y")){
				// Lancement de l'interface graphique
				b = false;
				EventQueue.invokeLater(() -> new VuePuzzle());
			} else if (answer.equals("n") || answer.equals("N")){
				textuel = true;
				b = false;
			} else {
				System.err.println("...");
			}
		}

		// Si il choisit l'interface textuel, on la lance
		if (textuel){
			System.out.println();
			int i = 1;
			while (true){
				System.out.println(" Tour " + i + " :");
				joueUnTour(participants[0]);
				i++;
			}
		}
	}

	@Override
	public void joueUnTour(Joueur j) {
		// Afficher le puzzle
		System.out.println();
		plateau.afficher();
		System.out.println();

		// Demander au joueur quelle pièce il veut coulisser
		System.out.println("Quelle pièce voulez-vous coullisser ?");
		int ligne = 0;
		int colonne = 0;
		int input;
		boolean b;
		boolean c = true;

		while (c){
			// On demande la ligne
			b = true;
			while (b) {
				System.out.print(" Donnez la ligne : ");
				Scanner sc = new Scanner(System.in);
				try {
					input = sc.nextInt();
					input--;
					if (input >= 0 && input < plateau.hauteur){
						ligne = input;
						b = false;
					} else {
						System.err.println(" Rentrez un nombre correspondant à une ligne.");
					}
				} catch (InputMismatchException e){
					System.err.println(" Rentrez un nombre valide.");
					input = -1;
				}
			}

			// On demande la colonne
			b = true;
			while (b) {
				System.out.print(" Donnez la colonne : ");
				Scanner sc = new Scanner(System.in);
				try {
					input = sc.nextInt();
					input--;
					if (input >= 0 && input < plateau.hauteur){
						colonne = input;
						b = false;
					} else {
						System.err.println(" Rentrez un nombre correspondant à une ligne.");
					}
				} catch (InputMismatchException e){
					System.err.println(" Rentrez un nombre valide.");
					input = -1;
				}
			}

			// On récupére les case à la position :
			int pieceID = -1;
			if (ligne == 0){
				if (colonne == 0) pieceID = 0;
				if (colonne == 1) pieceID = 1;
				if (colonne == 2) pieceID = 2;
			} else if (ligne == 1){
				if (colonne == 0) pieceID = 3;
				if (colonne == 1) pieceID = 4;
				if (colonne == 2) pieceID = 5;
			} else if (ligne == 2){
				if (colonne == 0) pieceID = 6;
				if (colonne == 1) pieceID = 7;
				if (colonne == 2) pieceID = 8;
			}

			// On récupère la case selectionnée
			CasePuzzle cp = ((PlateauPuzzle) plateau).getAllCases().get(pieceID);

			// On récupère la case vide
			CasePuzzle cv = null;
			ArrayList<CasePuzzle> a = ((PlateauPuzzle) plateau).getAllCases();
			for (CasePuzzle casePuzzle : a) {
				if (((PiecePuzzle) casePuzzle.getPiece()).getId() == 8) {
					cv = casePuzzle;
				}
			}

			// On échange la case
			if (!echangeCase(cp, cv)){
				System.err.println("La cace indiquée n'est pas adjacente à la case vide !");
			} else {
				c = false;
			}
			System.out.println();

			// Si le jouer résout le puzzle, on finit le jeu
			if (verifCase()){
				System.out.println(" Bravo ! Vous avez résolu le puzzle !");
				System.exit(0);
			}
		}
	}

	@Override
	public void setJoueur() {
		// Dans ce jeu le joueur est unique est n'as pas de réel utilité.
		this.participants = new Joueur[1];
		participants[0] = new Joueur("J1", 0);
	}

	private boolean verifCase(){
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

	private boolean echangeCase(CasePuzzle cp, CasePuzzle cv){
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
			return true;
		}
		return false;
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
			JLabel jl = new JLabel();
			jl.setIcon(new javax.swing.ImageIcon(getClass().getResource("./img/Puzzle/PuzzleTitle.png")));
			titre.add(jl);
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
						CasePuzzle cp = ((PlateauPuzzle) plateau).getAllCases().get(p.getId());

						// Récuperer la case qui contient la pièce vide
						ArrayList<CasePuzzle> a = ((PlateauPuzzle) plateau).getAllCases();
						CasePuzzle cv = null;
						for (CasePuzzle casePuzzle : a) {
							if (((PiecePuzzle) casePuzzle.getPiece()).getId() == 8) {
								cv = casePuzzle;
							}
						}

						echangeCase(cp, cv);

						updateView();
						// Si le joueur résout le puzzle
						if (verifCase()){
							// On retire les cases
							puzzle.removeAll();

							// On affiche l'image finale en entière
							Image buffer = null;
							try {
								buffer = ImageIO.read(new File("./img/Puzzle/PuzzleFull1.jpg"));
							} catch (IOException ioe) {
								ioe.printStackTrace();
							}
							Graphics g = puzzle.getGraphics();
							g.drawImage(buffer, 0, 0, null);

							// On affiche une fenêtre pour indiqué au joueur qu'il a gagné
							JFrame bravo = new JFrame();
							bravo.setLayout(new FlowLayout());
							bravo.setTitle("Bravo !");
							bravo.setPreferredSize(new Dimension(300, 65));

							JLabel text = new JLabel("Vous avez complétez le puzzle !");
							bravo.add(text);

							JButton ok = new JButton("Ok");
							bravo.add(ok);
							ok.addActionListener((ActionEvent el) -> System.exit(0));

							bravo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							bravo.pack();
							bravo.setResizable(false);
							bravo.setLocationRelativeTo(null);
							bravo.setVisible(true);
						}
					}
				});
				panels.add(p);
				puzzle.add(p);
			}

			// On fini de metre en place et on affiche les images dans les cases du puzzle
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

		private int id; // ID qui correspond à l'ID de la case qu'elle correspond

		JPanelCase(int id) {
			super();
			this.id = id;
		}

		int getId() {
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
	public void afficheIntro() {
		System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		System.out.println("|                                                                                                               |");
		System.out.println("|                            ██████╗  ██╗   ██╗ ███████╗ ███████╗ ██╗      ███████╗                             |");
		System.out.println("|                            ██╔══██╗ ██║   ██║ ╚══███╔╝ ╚══███╔╝ ██║      ██╔════╝                             |");
		System.out.println("|                            ██████╔╝ ██║   ██║   ███╔╝    ███╔╝  ██║      █████╗                               |");
		System.out.println("|                            ██╔═══╝  ██║   ██║  ███╔╝    ███╔╝   ██║      ██╔══╝                               |");
		System.out.println("|                            ██║      ╚██████╔╝ ███████╗ ███████╗ ███████╗ ███████╗                             |");
		System.out.println("|                            ╚═╝       ╚═════╝  ╚══════╝ ╚══════╝ ╚══════╝ ╚══════╝                             |");
		System.out.println("|                                                                                                               |");
		System.out.println("|              But du jeu :                                                                                     |");
		System.out.println("|                                                                                                               |");
		System.out.println("|                Cette variante du puzzle est appelée Taquin ou \"15-Puzzle\" de son nom                          |");
		System.out.println("|                original. Le but du jeu est bien évidemment de rassembler l'image découpée.                    |");
		System.out.println("|                Pour cela il suffit de cliquer sur une case adjacente à la case vide. Pour                     |");
		System.out.println("|                former l'image, la case vide doit être placée en bas à droite.                                 |");
		System.out.println("|                Le jeu se passe seulement en interface graphique.                                              |");
		System.out.println("|                                                                                                               |");
		System.out.println("|              Conseils :                                                                                       |");
		System.out.println("|                                                                                                               |");
		System.out.println("|                Dans l'hypothèse où la case vide se trouve en bas à droite : remettre le jeu                   |");
		System.out.println("|                dans l'ordre ligne par ligne en commençant par la ligne du haut puis quand il                  |");
		System.out.println("|                ne reste plus que deux lignes mélangées, les réordonner colonne par colonne en                 |");
		System.out.println("|                commençant par celle de gauche. Cette méthode ne garantit pas qu'un nombre                     |");
		System.out.println("|                minimal de mouvements sera effectué, mais est simple à mémoriser et aboutit                    |");
		System.out.println("|                dans tous les cas où une solution est possible.                                                |");
		System.out.println("|                                                                                                               |");
		System.out.println("+---------------------------------------------------------------------------------------------------------------+");
	}
}