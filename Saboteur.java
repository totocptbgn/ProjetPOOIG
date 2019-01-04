import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


	/*
	 * 								@author Hind
	 * 				Jeu du saboteur, entièrment textuel, sur le terminal.
	 * 				 Pour voir le fonctionnement du jeu, lire le README.
	 */

public class Saboteur extends Jeu {
	ArrayList<CarteSaboteur> paquet []; // Même fonctionnement que Domino.paquet, la pioche est à l'index 0 du paquet, ensuite ce sont les paquets des joueurs.
	boolean[][] peutJouer; // Sert à détérminer si les outils, lampe et chariot sont en bon état, si ce n'est pas le cas le joueur ne peut pas poser de carte chemin
                           // peutJouer[p][0] correspond à la lampe, [p][1] correspond au chariot et [p][2] correspond aux outils
    CaseSaboteur[] arrivees; // Un tableau contenant les coordonnées des cases d'arrivée
	CaseSaboteur depart;

	int manche;
	boolean[] mancheSaboteur; //nombre de manches gagnées par le groupe des saboteurs
	boolean[] mancheChercheur; //nombre de manches gagnéespar le grouoe des chercheurs
	boolean estGagnant;  //dit si le joueur est gagnant

	int xArrivee; //coordonnée de ligne de la case d'arrivée contenant le trésor
	int yArrivee; //coordonnée de colonne de la case d'arrivée


	public void setJoueur() { // Met en place la création des joueurs.
		boolean b = true;
		while (b){
			System.out.println("Combien de joueurs participent ?");
			Scanner sc = new Scanner(System.in);
			int nbrJ = 1;
			try {
				nbrJ = sc.nextInt();
				if (nbrJ < 2 || nbrJ > 4) {
					System.err.println("Rentrez un chiffre entre 2 et 4 inclus.");
					b = true;
				} else {
					this.participants = new Joueur[nbrJ];
					b = false;
				}
			} catch (InputMismatchException e){
				System.err.println("Rentrez un nombre valide.");
				b = true;
			}
		}
		for (int i = 0; i < participants.length; i++) {
			System.out.println("Quel est le nom du joueur n°" + (i+1) + " ?");
			participants[i] = new Joueur(i+1);
		}
	}

	@Override
	public void lancerPartie() {

		afficheIntro();

		// On crée les joueurs :
		this.setJoueur();



		mancheChercheur = new boolean[3];
  		mancheSaboteur = new boolean[3];

  		manche = 0;
		// Afficher le plateau

		// Afficher les cartes (?)
		while (manche < 3) {
			lancerManche();
		}
		int gagneS = 0;
		int gagneC = 0;
		for(int i = 0;i<3;i++){
			if(mancheChercheur[i]){
				gagneC++;
			}
			if(mancheSaboteur[i]){
				gagneS++;
			}
		}
		if(gagneC>gagneS){
			System.out.println("  Les Chercheurs ont gagné la partie !!");
		}
		if(gagneS>gagneC){
			System.out.println("      Les Saboteurs ont remporté la victoire !!");
		}
		if(gagneC == gagneS){
			System.out.println("   Les deux équipes sont ex-aequo");
		}


	}

	public void lancerManche(){ //on réinitialise la pioche , le plateau et les paquets des joueurs lors de la nouvelle manche

		this.plateau = new PlateauSaboteur(18);

		// On crée les paquets des joueurs et la pioche
		this.paquet = new ArrayList[participants.length+1];
		for (int i = 0; i < paquet.length; i++){
			paquet[i] = new ArrayList<>();
		}

		// On crée des tableaux de direction
		boolean[] carrefour = {true, true, true, true};
		boolean[] tridirectiongauche = {true, true, false, true};
		boolean[] tridirectiondroite = {false, true, true, true};
		boolean[] horizontal = {true, false, true, false};
		boolean[] vertical = {false, true, false, true};
		boolean[] tridirectionhaut = {true, true, true, false};
		boolean[] tridirectionbas = {true, false, true, true};
		boolean[] viragedroite = {false,false,true,true};
		boolean[] viragegauche = {true,false,false,true};
		boolean[] sansissuegauche = {true,false,false,false};
		boolean[] sansissuedroite = {false,false,true,false};
		boolean[] sansissuehaut = {false,true,false,false};
		boolean[] sansissuebas = {false,false,false,true};


		// On insère les cartes chemin dans la pioche
		for(int i = 0; i < 4; i++){
			paquet[0].add(new CarteChemin(tridirectionbas));
			paquet[0].add(new CarteChemin(tridirectiondroite));
			paquet[0].add(new CarteChemin(horizontal));
			paquet[0].add(new CarteChemin(tridirectiongauche));
			paquet[0].add(new CarteChemin(tridirectionhaut));
			paquet[0].add(new CarteChemin(vertical));
			paquet[0].add(new CarteChemin(sansissuegauche));
			paquet[0].add(new CarteChemin(sansissuedroite));
			paquet[0].add(new CarteChemin(sansissuehaut));
			paquet[0].add(new CarteChemin(sansissuebas));
			paquet[0].add(new CarteChemin(viragedroite));
			paquet[0].add(new CarteChemin(viragegauche));
			// Mélanger les cartes de la pioche
		}

		// On insère les cartes action dans la pioche :
		for (int i = 0; i < 4; i++){
			paquet[0].add(new CarteAction(true,'o'));
			paquet[0].add(new CarteAction(false,'o'));
			paquet[0].add(new CarteAction(true,'l'));
			paquet[0].add(new CarteAction(false,'l'));
			paquet[0].add(new CarteAction(true,'c'));
			paquet[0].add(new CarteAction(false,'c'));
		}

		// Distribution des cartes :
		for (int i = 1; i<paquet.length; i++){
			for (int j = 0; j < 5; j++){
				int rand = (int) (Math.random()*paquet[0].size()-1);
				paquet[i].add(paquet[0].get(rand));
				// On supprime la carte distribuée de la pioche
				paquet[0].remove(rand);
			}
		}

		// On initialise à true peutJouer
		peutJouer = new boolean[participants.length][3];
		for (int i = 0; i < peutJouer.length; i++){
			for (int j = 0; j < peutJouer[i].length; j++){
				peutJouer[i][j] = true;
			}
		}


		// Initialisation des cartes de départ et d'arrivées
		if(manche == 0){
			xArrivee = plateau.hauteur-4;
			yArrivee = 3;
		}

		if(manche == 1){
			xArrivee = plateau.hauteur/2-plateau.hauteur/3;
			yArrivee = plateau.hauteur/3;
		}

		if(manche == 2){
			xArrivee = plateau.hauteur-4;
			yArrivee = 3;
		}
		//plateau.getCase(plateau.longueur/2,plateau.longueur/2).PoserPiece(new CarteChemin(carrefour));


		// Afficher le plateau
		plateau.afficher();
		// Afficher les cartes (?)
		System.out.println("                         MANCHE N°" + (manche+1));
			for (int i = 0; i < participants.length; i++) {
				joueUnTour(participants[i]);

				for(int j = 1;j < paquet.length;j++){
					if(paquet[0].isEmpty() && paquet[j].isEmpty()){
						manche++;
						System.out.println("Il n'y a plus de cartes dans la pioche, cette manche est perdue par les deux équipes");
						System.exit(0);
					}
				}
			}

	}


	public void ouPoser(){
		System.out.println("Les emplacements vous permettant de poser une carte sont : ");
		boolean[] carrefour = {true,true,true,true};
		CarteChemin c = new CarteChemin(carrefour);

		for(int i = 0;i < plateau.longueur;i++){
			for(int j = 0;j < plateau.longueur; j++){
				boolean b = poserCarte(i,j,c);
				if(b){
					System.out.print( " ("+ i + "," + j + ")");
					retirerCarte(i,j);
				}
			}
		}


	}

	public boolean poserCarte(int i, int j, CarteSaboteur c){

		/*
		 * Conditions de sortie de plateau :
		 *  - la carte doit être posée après une autre carte,
		 *  - cette autre carte doit permettre elle même l'accès à celle ci,
		 *  - la carte ne peut pas être posée sur une autre,
		 *  - si les outils sont en mauvais état le joueur ne peut pas poser de carte chemin.
		 */


		if (i > plateau.hauteur || j > plateau.longueur || i < 0 || j < 0){
			System.err.println("Ces coordonnées sortent des limites du plateau");
			return false;
		}

		if(plateau.getCase(i, j).estOccupee()){
			System.err.println("Cette case est déja occupée !");
			return false;
		}

		//premier cas : si i et j ne se trouvent pas en bordure du plateau ( et que l'une des cartes adjacentes n'est pas une carte d'arrivée

		if (i > 0 && i < plateau.hauteur-1 && j > 0 && j < plateau.longueur - 1 && !(estArrivee(i+1,j) && estArrivee(i-1,j) && estArrivee(i,j+1) && estArrivee(i,j-1))) {
				if (!(plateau.getCase(i + 1, j).estOccupee() || plateau.getCase(i - 1, j).estOccupee() || plateau.getCase(i, j + 1).estOccupee() || plateau.getCase(i, j - 1).estOccupee())) {
					System.err.println("Il faut poser votre carte après une autre sur le plateau !");
					return false;
				}

				if(plateau.plateau[i+1][j].estOccupee()){
					boolean[] b = ((CaseSaboteur) plateau.plateau[i+1][j]).getDirection();
					if(b[3]){
						System.out.println("Carte posée avec succès !");
						plateau.plateau[i][j].piece = c;
						return true;
					}
				}

				if(plateau.plateau[i-1][j].estOccupee()){
					boolean[] b = ((CaseSaboteur) plateau.plateau[i-1][j]).getDirection();
					if( b[1]){
						System.out.println("Carte posée avec succès !");
						plateau.plateau[i][j].piece = c;
						return true;
					}
				}

				if(plateau.plateau[i][j+1].estOccupee()){
					boolean[] b = ((CaseSaboteur) plateau.plateau[i][j+1]).getDirection();
					if( b[0]){
						System.out.println("Carte posée avec succès !");
						plateau.plateau[i][j].piece = c;
						return true;
					}
				}

				if(plateau.plateau[i][j-1] != null){
					boolean[] b = ((CaseSaboteur) plateau.plateau[i][j-1]).getDirection();
					//if(b[2]){
						System.out.println("Carte posée avec succès !");
						plateau.plateau[i][j].piece = c;
						return true;
					//}
				}

		}



		//deuxième cas : si i est en bordure de plateau
		if(i == 0 && j > 0 && j < plateau.longueur-1){
			if(plateau.getCase(i+1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i+1,j)).getDirection();
				if( b[3]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}


			if(plateau.getCase(i,j+1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j+1)).getDirection();
				if( b[0]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}

			if(plateau.getCase(i,j-1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j-1)).getDirection();
				if( b[2]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}
		}

		if(i == plateau.hauteur-1 && j < plateau.longueur-1 && j > 0){


			if(plateau.getCase(i-1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i-1,j)).getDirection();
				if( b[1]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}

			if(plateau.getCase(i,j+1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j+1)).getDirection();
				if( b[0]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}

			if(plateau.getCase(i,j-1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j-1)).getDirection();
				if( b[2]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}
		}

		//troisième cas : si j est en bordure de plateau
		if(j == 0 && i > 0 && i < plateau.longueur-1){

			if(plateau.getCase(i+1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i+1,j)).getDirection();
				if( b[3]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}

			if(plateau.getCase(i-1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i-1,j)).getDirection();
				if( b[1]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}

			if(plateau.getCase(i,j+1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j+1)).getDirection();
				if( b[0]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}


		}

		if(j == plateau.hauteur-1 && i < plateau.longueur-1 && i > 0){
			if(plateau.getCase(i+1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i+1,j)).getDirection();
				if(b[3]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}

			if(plateau.getCase(i-1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i-1,j)).getDirection();
				if( b[1]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}


			if(plateau.getCase(i,j-1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j-1)).getDirection();
				if( b[2]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}
		}

		//quatrième cas : si i et j sont en bordure du plateau
		if(i == 0 && j == 0){
			if(plateau.getCase(i+1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i+1,j)).getDirection();
				if( b[3]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}


			if(plateau.getCase(i,j+1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j+1)).getDirection();
				if( b[0]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}
		}

		if(i == plateau.hauteur-1 && j == plateau.longueur-1){

			if(plateau.getCase(i-1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i-1,j)).getDirection();
				if( b[1]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}


			if(plateau.getCase(i,j-1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j-1)).getDirection();
				if( b[2]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}
		}
		if(estArrivee(i+1,j)){
			if(plateau.getCase(i-1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i-1,j)).getDirection();
				if( b[1]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					if(i-1 == xArrivee && j == yArrivee){
						estGagnant = true;
					}
					return true;
				}
			}

			if(plateau.getCase(i,j+1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j+1)).getDirection();
				if( b[0]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}

			if(plateau.getCase(i,j-1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j-1)).getDirection();
				if( b[2]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}
			else {
				System.err.println("Vous ne pouvez pas placer une carte après une carte d'arrivée sans qu'il y ait déja une route les reliant !");
				return false;
			}
		}

		if (estArrivee(i - 1, j)) {
			if(plateau.getCase(i+1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i+1,j)).getDirection();
				if( b[3]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					estGagnant = true;
					return true;
				}
			}

			if(plateau.getCase(i,j+1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j+1)).getDirection();
				if( b[0]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					estGagnant = true;
					return true;
				}
			}

			if(plateau.getCase(i,j-1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j-1)).getDirection();
				if( b[2]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					estGagnant = true;
					return true;
				}
			}
			else {
				System.err.println("Vous ne pouvez pas placer une carte après une carte d'arrivée sans qu'il y ait déja une route les reliant !");
				return false;
			}

		}

		if(estArrivee(i,j-1)){
			if(plateau.getCase(i+1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i+1,j)).getDirection();
				if( b[3]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					estGagnant = true;
					return true;
				}
			}

			if(plateau.getCase(i-1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i-1,j)).getDirection();
				if( b[1]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					estGagnant = true;
					return true;
				}
			}

			if(plateau.getCase(i,j+1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j+1)).getDirection();
				if( b[0]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					estGagnant = true;
					return true;
				}
			}

			else {
				System.err.println("Vous ne pouvez pas placer une carte après une carte d'arrivée sans qu'il y ait déja une route les reliant !");
				return false;
			}

		}

		if(estArrivee(i,j+1)){

			if(plateau.getCase(i+1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i+1,j)).getDirection();
				if( b[3]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}

			if(plateau.getCase(i-1,j).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i-1,j)).getDirection();
				if( b[1]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}


			if(plateau.getCase(i,j-1).estOccupee()){
				boolean[] b = ((CaseSaboteur) plateau.getCase(i,j-1)).getDirection();
				if( b[2]){
					System.out.println("Carte posée avec succès !");
					plateau.plateau[i][j].piece = c;
					return true;
				}
			}

			else {
				System.err.println("Vous ne pouvez pas placer une carte après une carte d'arrivée sans qu'il y ait déja une route les reliant !");
				return false;
			}
		}
		System.err.println("peut être une erreur..");
		return false;
	}

	public void retirerCarte(int i,int j){
		if(plateau.getCase(i,j).estOccupee()){
			plateau.plateau[i][j] = null;
		}
	}

    public boolean action(int j,int j2, CarteAction c){

        if(c.getType() == 'c' && c.getSabotage() && peutJouer[j2][1]){
            peutJouer[j2][1] = false;
            System.out.println("Chariot saboté");
            return true;
        }

        if(c.getType() == 'c' && !c.getSabotage() && !peutJouer[j2][1]){
            peutJouer[j2][1] = true;
            System.out.println("Chariot réparé");
            return true;
        }

        if(c.getType() == 'c' && c.getSabotage() && !peutJouer[j2][1]){
            System.err.println("Le chariot de ce joueur est déjà saboté");
            return false;
        }

        if(c.getType() == 'c' && !c.getSabotage() && peutJouer[j2][1]){
            System.err.println("Le chariot de ce joueur est déja en bon état");
            return false;
        }

        // Sabotage outils

        if(c.getType() == 'o' && c.getSabotage() && peutJouer[j2][2]){
            peutJouer[j2][2] = false;
            System.out.println("Outils sabotés");
            return true;
        }

        if(c.getType() == 'o' && !c.getSabotage() && !peutJouer[j2][2]){
            peutJouer[j2][2] = true;
            System.out.println("Outils réparés");
            return true;
        }

        if(c.getType() == 'o' && c.getSabotage() && !peutJouer[j2][2]){
            System.err.println("Les outils de ce joueur sont déjà sabotés");
            return false;
        }

        if(c.getType() == 'o' && !c.getSabotage() && peutJouer[j2][2]){
            System.err.println("Les outils de ce joueur sont déja en bon état");
            return false;
        }

        // Sabotage lampe

        if(c.getType() == 'l' && c.getSabotage() && peutJouer[j2][0]){
            peutJouer[j2][0] = false;
            System.out.println("Lampe sabotée");
            return true;
        }

        if(c.getType() == 'l' && !c.getSabotage() && !peutJouer[j2][0]){
            peutJouer[j2][0] = true;
            System.out.println("Lampe réparée");
            return true;
        }

        if(c.getType() == 'l' && c.getSabotage() && !peutJouer[j2][0]){
            System.err.println("La lampe de ce joueur est déjà sabotée");
            return false;
        }

        if(c.getType() == 'l' && !c.getSabotage() && peutJouer[j2][0]){
            System.err.println("La lampe de ce joueur est déja en bon état");
            return false;
        }
        return false;
    }

    //Montre si la case en paramètre est une case d'arrivée
    public boolean estArrivee(int i,int j){
		if(i == plateau.hauteur/2-plateau.hauteur/3 && j == plateau.hauteur/3){
			return true;
		}

		if(i == plateau.hauteur-4 && j == 3){
			return true;
		}

		if(i == plateau.hauteur-plateau.hauteur/2-1 && j == plateau.hauteur/4){
			return true;
		}

		return false;
	}

	@Override
	public void joueUnTour(Joueur j){
		System.out.println("C'est à vous de jouer, "+j.getNom()+" !");
		if(j.getId() % 2 == 0){
			System.out.println("Nous vous rappelons que vous êtes un Saboteur.");
		}
		if(j.getId() % 2 != 0){
			System.out.println("Nous vous rappelons que vous êtes un Chercheur. ");
		}
		System.out.println("Quelle carte voulez vous utiliser ? (ID)");
		for(int i = 0;i<paquet[j.getId()].size();i++){
			if(paquet[j.getId()].get(i) instanceof CarteAction){
				CarteAction c = (CarteAction)paquet[j.getId()].get(i);
				System.out.println("   "+ i +"\n "+ c.toString());
			}
			else {
				CarteChemin cc = (CarteChemin)paquet[j.getId()].get(i);
				System.out.println("   " + i + " \n" + cc.toString());
			}
		}

		Scanner sc = new Scanner(System.in);
		int rep = sc.nextInt();

		try {
			Scanner sc2 = new Scanner(System.in);

			if (paquet[j.getId()].get(rep) instanceof CarteChemin) {

				System.out.println("Voulez vous garder ou jeter définitivement cette carte ? [garder/jeter]");

				if (sc2.nextLine().equals("jeter")) {
					paquet[j.getId()].remove(rep);
				} else if (sc2.nextLine().equals("garder")) {
					System.out.println("Voulez-vous inverser le sens de la carte ? [y/n]");

					sc2 = new Scanner(System.in);

					if (sc2.nextLine().equals("y")) {
						((CarteChemin) paquet[j.getId()].get(rep)).setInverser(true);
						((CarteChemin) paquet[j.getId()].get(rep)).inversement();
					}

					int x = 0;
					int y = 0;
					String s = "oui";
					Scanner ouinon = new Scanner(System.in);
					while (s.equals("oui")) {
						System.out.println("Où voulez-vous poser cette carte ?");
						System.out.println("Choisissez la ligne : ");
						Scanner coorX = new Scanner(System.in);
						Scanner coorY = new Scanner(System.in);
						x = coorX.nextInt();
						y = coorY.nextInt();
						if (poserCarte(x, y, paquet[j.getId()].get(rep))) {
							paquet[j.getId()].remove(rep);
							System.out.println("Carte posée avec succès !!");
							break;
						} else {
							System.out.println("La carte n'a pas pu être posée, voulez-vous réessayer ? [oui/non]");
							s = ouinon.nextLine();
						}
					}

				} else {
					System.err.println("Vous devez choisir entre garder et jeter");
					joueUnTour(j);
				}

			} else {

				System.out.println("Voulez-vous garder ou jeter cette carte définitivement ? [garder/jeter");
				Scanner garder = new Scanner(System.in);
				if (garder.nextLine().equals("jeter")) {
					paquet[j.getId()].remove(rep);
				} else if (garder.nextLine().equals("garder")) {
					if (((CarteAction) paquet[j.getId()].get(rep)).sabotage) {
						System.out.println("Sur quel joueur voulez vous que s'abatte votre couroux ? [nomJoueur]");
						Scanner saboter = new Scanner(System.in);

						for (int i = 0; i < participants.length; i++) {

							System.out.print(" " + participants[i].getNom() + " ");

						}
						String joueur = saboter.nextLine();

						for (int i = 0; i < participants.length; i++) {

							if (participants[i].getNom().equals(joueur)) {
								if (action(j.getId(), i, (CarteAction) paquet[j.getId()].get(rep))) {
									System.out.println("Sabotage réussi !!");
									break;
								} else {
									System.out.println("Le sabotage n'a pas fonctionné,voulez-vous réessayer ? [o/n]");
									Scanner ouinon = new Scanner(System.in);
									if (ouinon.nextLine().equals("o")) {
										joueUnTour(j);
									}
								}
							}
						}
					} else {
						System.out.println("Quel joueur voulez-vous aider ? [nomJoueur]");
						Scanner saboter = new Scanner(System.in);

						for (int i = 0; i < participants.length; i++) {

							System.out.print(" " + participants[i].getNom() + " ");

						}
						String joueur = saboter.nextLine();

						for (int i = 0; i < participants.length; i++) {

							if (participants[i].getNom().equals(joueur)) {
								if (action(j.getId(), i, (CarteAction) paquet[j.getId()].get(rep))) {
									System.out.println("Réparation réussie !!");
									break;
								} else {
									System.out.println("La réparation n'a pas fonctionné,voulez-vous réessayer ? [o/n]");
									Scanner ouinon = new Scanner(System.in);
									if (ouinon.nextLine().equals("o")) {
										joueUnTour(j);
									}
								}
							}
						}

					}
				}
			}
		}
		catch (java.util.InputMismatchException e){
				sc.nextInt();
			}

			// Le joueur pioche
			if(!paquet[0].isEmpty()) {
				paquet[j.getId()].add(paquet[0].get(paquet[0].size() - 1));
				paquet[0].remove(paquet[0].size() - 1);
				System.out.println(j.getNom() + " a pioché une carte..");
			}

			if(paquet[0].isEmpty()){
				System.err.println("Vous ne pouvez plus piocher !");
			}

	}
	/*public void joueUnTour(Joueur j) {

		System.out.println("C'est à vous de jouer, "+j.getNom()+" !");
		if(j.getId() % 2 == 0){
			System.out.println("Nous vous rappelons que vous êtes un Saboteur.");
		}
		if(j.getId() % 2 != 0){
			System.out.println("Nous vous rappelons que vous êtes un Chercheur. ");
		}
        System.out.println("Quelle carte voulez vous utiliser ? (ID)");
        for(int i = 0;i<paquet[j.getId()].size();i++){
        	if(paquet[j.getId()].get(i) instanceof CarteAction){
        		CarteAction c = (CarteAction)paquet[j.getId()].get(i);
				System.out.println("   "+ i +"\n "+ c.toString());
			}
			else {
				CarteChemin cc = (CarteChemin)paquet[j.getId()].get(i);
				System.out.println("   " + i + " \n" + cc.toString());
			}
        }

        Scanner sc = new Scanner(System.in);
        int rep = sc.nextInt();

        try {
        	Scanner sc2 = new Scanner(System.in);
        	if(paquet[j.getId()].get(rep) instanceof CarteChemin){

        		System.out.println("Voulez-vous utiliser cette carte ou la jeter définitivement ? [jeter/garder]");

        		String garder = sc2.nextLine();

        		if(garder.equals("garder")){
        			System.out.println("Voulez-vous inverser la direction de cette carte ? [oui/non]");
        			String inv = sc2.nextLine();
        			if(inv.equals("oui")){
						((CarteChemin) paquet[j.getId()].get(rep)).setInverser(true);
						((CarteChemin) paquet[j.getId()].get(rep)).inversement();
					}
					int x = 0;
					int y = 0;
        			String s = "oui";


        			while (s.equals("oui") ) {
						//ouPoser();
						System.out.println("Où voulez vous poser cette carte?");
						System.out.print("Donnez la ligne : ");
						 x = sc2.nextInt()-1;
						sc2 = new Scanner(System.in);
						System.out.print("Donnez la colonne : ");
						 y = sc2.nextInt()-1;
						if(poserCarte(x, y, paquet[j.getId()].get(rep))){
							paquet[j.getId()].remove(rep);
							if(estGagnant){
								estGagnant = false;
								System.out.println(j.getNom() + " a remporté le trésor ! ");
								if(j.getId() % 2 == 0){

									mancheSaboteur[manche] = true;
								}
								if(j.getId() % 2 != 0){
									mancheChercheur[manche] = true;
								}
								System.out.println("La nouvelle manche débute ! \n Distribution des cartes..");
								manche++;
								break;
							}
							//le joueur pioche et finis sa manche s'il gagne

							/*paquet[j.getId()].add(paquet[0].get(paquet[0].size()-1));
							paquet[0].remove(paquet[0].size()-1);
							System.out.println(j.getNom() + " a pioché une carte..");*/
						/*	break;
						}
						else{
							System.out.println("La carte n'a pas pu être posée, voulez vous réessayer ? [oui/non]");
							sc2 = new Scanner(System.in);
							s = sc2.nextLine();
						}
					}

					if(!s.equals("oui")){
						System.out.println("Utiliser une autre carte de votre deck ? [oui/non]");
						sc2 = new Scanner(System.in);
						s = sc2.nextLine();
						if(s.equals("oui")){
							joueUnTour(j);
						}
					}
					sc2 = new Scanner(System.in);
				}

				if(garder.equals("jeter")){
					System.out.println("Cette carte a été jetée définitivement !");
					paquet[j.getId()].remove(rep);
				}
				else{
					System.err.println("Commande introuvable.. essayez plutôt avec les deux options possibles [jeter/garder]");
				}
				if(!garder.equals("jeter") && !garder.equals("garder")){
					System.err.println("Vous devez choisir entre jeter et garder");
					joueUnTour(j);
				}
			}

			if (paquet[j.getId()].get(rep) instanceof CarteAction) {

				System.out.println("Voulez-vous utiliser cette carte ou la jeter définitivement ? [jeter/garder]");
				Scanner sc3 = new Scanner(System.in);

				if (sc3.nextLine().equals("jeter")) {
					paquet[j.getId()].remove(rep);
				}

				if (sc3.nextLine().equals("garder")) {
					String saboter = "";

					if (((CarteAction) paquet[j.getId()].get(rep)).sabotage) {

						System.out.println("Sur quel joueur voulez-vous que s'abatte votre couroux ?");
						for (int i = 0; i < participants.length; i++) {

							System.out.print(" " + participants[i].getNom() + " ");

						}
						saboter = sc2.nextLine();

						for (int i = 1; i < participants.length; i++) {

							if (participants[i].getNom().equals(saboter)) {

								if (!action(j.getId(), i, (CarteAction) paquet[j.getId()].get(rep))) {

									System.out.println("Le sabotage n'a pas fonctionné, voulez vous réessayer ? [oui/non]");

									saboter = sc2.nextLine();

									if (saboter.equals("oui")) {

										joueUnTour(j);

									}
								}
								if (action(j.getId(), i, (CarteAction) paquet[j.getId()].get(rep))) {
									paquet[j.getId()].remove(rep);
									System.out.println("Sabotage réussi !");
								}
							}
						}
					}
					if (!((CarteAction) paquet[j.getId()].get(rep)).sabotage) {

						System.out.println("Quel joueur voulez-vous aider ?");

						for (int i = 0; i < participants.length; i++) {

							System.out.print(" " + participants[i].getNom() + " ");

						}

						saboter = sc2.nextLine();

						for (int i = 0; i < participants.length; i++) {

							if (participants[i].getNom().equals(saboter)) {

								if (!action(j.getId(), i, (CarteAction) paquet[j.getId()].get(rep))) {

									System.out.println("Ça n'a pas fonctionné, voulez vous réessayer ? [oui/non]");
									saboter = sc2.nextLine();
									if (saboter.equals("oui")) {
										joueUnTour(j);
									}
								}
								if (action(j.getId(), i, (CarteAction) paquet[j.getId()].get(rep))) {

									System.out.println("Réparation réussie !");
								}
							}
						}
					}
				}
			}
		}

		catch (java.util.InputMismatchException e){
        	sc.nextInt();
		}

		// Le joueur pioche
		if(!paquet[0].isEmpty()) {
			paquet[j.getId()].add(paquet[0].get(paquet[0].size() - 1));
			paquet[0].remove(paquet[0].size() - 1);
			System.out.println(j.getNom() + " a pioché une carte..");
		}

		if(paquet[0].isEmpty()){
			System.err.println("Vous ne pouvez plus piocher !");
		}
	}*/

	public void afficheIntro(){
		System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		System.out.println("|                                                                                                               |");
		System.out.println("|                                                                                                               |");
		System.out.println("|                   ███████╗  █████╗  ██████╗   ██████╗  ████████╗ ███████╗ ██╗   ██╗ ██████╗                   |");
		System.out.println("|                   ██╔════╝ ██╔══██╗ ██╔══██╗ ██╔═══██╗ ╚══██╔══╝ ██╔════╝ ██║   ██║ ██╔══██╗                  |");
		System.out.println("|                   ███████╗ ███████║ ██████╔╝ ██║   ██║    ██║    █████╗   ██║   ██║ ██████╔╝                  |");
		System.out.println("|                   ╚════██║ ██╔══██║ ██╔══██╗ ██║   ██║    ██║    ██╔══╝   ██║   ██║ ██╔══██╗                  |");
		System.out.println("|                   ███████║ ██║  ██║ ██████╔╝ ╚██████╔╝    ██║    ███████╗ ╚██████╔╝ ██║  ██║                  |");
		System.out.println("|                   ╚══════╝ ╚═╝  ╚═╝ ╚═════╝   ╚═════╝     ╚═╝    ╚══════╝  ╚═════╝  ╚═╝  ╚═╝                  |");
		System.out.println("|                                                                                                               |");
		System.out.println("|               Règles :                                                                                        |");
		System.out.println("|                                                                                                               |");
		System.out.println("|                 Dans ce jeu, les joueurs sont ou bien des chercheurs d’or, creusant des                       |");
		System.out.println("|                 galeries profondes dans la montagne, ou bien des saboteurs qui essaient                       |");
		System.out.println("|                 d’entraver le creusement. Les deux groupes peuvent s’aider mutuellement, même                 |");
		System.out.println("|                 s’ils ont un soupçon du rôle joué par l’autre.  Si les chercheurs d’or                        |");
		System.out.println("|                 réussissent à atteindre le trésor, ils sont alors récompensés par des pépites                 |");
		System.out.println("|                 d’or et les saboteurs restent les mains vides. Dans le cas contraire, la                      |");
		System.out.println("|                 récompense sera pour les saboteurs. C’est seulement lorsque le trésor sera                    |");
		System.out.println("|                 partagé, que les rôles seront dévoilés. Après trois parties, le joueur avec le                |");
		System.out.println("|                 plus grand nombre de pépites sera le vainqueur.                                               |");
		System.out.println("|                                                                                                               |");
		System.out.println("+---------------------------------------------------------------------------------------------------------------+");
	}
}
