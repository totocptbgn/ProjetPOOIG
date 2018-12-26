import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Saboteur extends Jeu {
	ArrayList<CarteSaboteur> paquet []; // Même fonctionnement que Domino.paquet, la pioche est à l'index 0 du paquet, ensuite ce sont les paquets des joueurs.
	boolean[][] peutJouer; // Sert à détérminer si les outils, lampe et chariot sont en bon état, si ce n'est pas le cas le joueur ne peut pas poser de carte chemin
                           // peutJouer[p][0] correspond à la lampe, [p][1] correspond au chariot et [p][2] correspond aux outils
    
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
			participants[i] = new Joueur(i);
		}
	}

	@Override
	public void lancerPartie() {

		afficheIntro();

		// On créer les joueurs :
		this.setJoueur();

		// On créer le plateau :
		this.plateau = new PlateauSaboteur(30);

		// On créer (???)
		this.paquet = new ArrayList[participants.length+1];

		for (int i = 0; i < paquet.length; i++){
			paquet[i] = new ArrayList<>();
		}

		// On crée des tableaux de direction
		boolean[] carrefour = {true, true, true, true};
		boolean[] tridirectiongauche = {true, true, false, true};
		boolean[] tridirectiondroite = {false, true, true, true};
		boolean[] sansissue = {false, false, false, false};
		boolean[] horizontal = {true, false, true, false};
		boolean[] vertical = {false, true, false, true};
		boolean[] tridirectionhaut = {true, true, true, false};
		boolean[] tridirectionbas = {true, false, true, true};

		// On insère la carte départ
		CarteChemin depart = new CarteChemin(carrefour);
		Case depart1 = new CaseSaboteur(depart);
		this.plateau.setCase(4, 6, depart1);

		// On insère les cartes chemin dans la pioche
		for(int i = 0; i < 6; i++){
			paquet[0].add(new CarteChemin(tridirectionbas));
			paquet[0].add(new CarteChemin(tridirectiondroite));
			paquet[0].add(new CarteChemin(horizontal));
			paquet[0].add(new CarteChemin(tridirectiongauche));
			paquet[0].add(new CarteChemin(tridirectionhaut));
			paquet[0].add(new CarteChemin(vertical));
			paquet[0].add(new CarteChemin(sansissue));
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

		//on initialise à true peutJouer
  		for(int i = 0;i<peutJouer.length;i++){
		    for(int j = 0;j<peutJouer[i].length;j++){
		        peutJouer[i][j] = true;
            }

		}


		// Afficher le plateau

		// Afficher les cartes (?)

	}

	public boolean poserCarte(int i,int j,CarteSaboteur c,int participant){
		/*
		 * Conditions de sortie de plateau :
		 *  - la carte doit être posée après une autre carte,
		 *  - cette autre carte doit permettre elle même l'accès à celle ci,
		 *  - la carte ne peut pas être posée sur une autre,
		 *  - si les outils sont en mauvais état le joueur ne peut pas poser de carte chemin.
		 */

		if (c instanceof CarteAction){
			return false;
		}

		if (plateau.getCase(i,j) != null){
			return false;
		}

		for(int x = 0;x<3;x++){
		    if(!peutJouer[participant-1][x]){
		        return false;
            }
        }

		if (plateau.getCase(i+1,j) == null && plateau.getCase(i-1,j) == null && plateau.getCase(i,j-1) == null && plateau.getCase(i,j+1) == null){
			return false;
		}

		if (plateau.getCase(i-1,j) != null ){
            boolean[] b = ((CaseSaboteur) plateau.getCase(i-1,j)).getDirection();
		    if(!b[3]){
		        return false;
            }
		}

		if(plateau.getCase(i+1,j) != null){
		    boolean[] b = ((CaseSaboteur) plateau.getCase(i+1,j)).getDirection();
		    if(!b[1]){
		        return false;
            }
        }

        if(plateau.getCase(i,j-1) != null){
            boolean[] b = ((CaseSaboteur) plateau.getCase(i,j-1)).getDirection();
            if(!b[0]){
                return false;
            }
        }

        if(plateau.getCase(i,j+1) != null){
            boolean[] b = ((CaseSaboteur) plateau.getCase(i,j+1)).getDirection();
            if(!b[2]){
                return false;
            }
        }
        //ajouter des conditions s'il y a plusieurs cartes chemin autour
		else return true;
		return false;
	}

    public boolean action(int j,int j2, CarteAction c){

        if(c.getType() == 'c' && c.getSabotage() && peutJouer[j2][1]){
            peutJouer[j2-1][1] = false;
            return true;
        }

        if(c.getType() == 'c' && !c.getSabotage() && !peutJouer[j2][1]){
            peutJouer[j2-1][1] = true;
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

        //sabotage outils

        if(c.getType() == 'o' && c.getSabotage() && peutJouer[j2][2]){
            peutJouer[j2-1][2] = false;
            return true;
        }

        if(c.getType() == 'o' && !c.getSabotage() && !peutJouer[j2][2]){
            peutJouer[j2-1][2] = true;
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

        //sabotage lampe

        if(c.getType() == 'l' && c.getSabotage() && peutJouer[j2][0]){
            peutJouer[j2-1][0] = false;
            return true;
        }

        if(c.getType() == 'l' && !c.getSabotage() && !peutJouer[j2][0]){
            peutJouer[j2-1][0] = true;
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

	@Override
	public void joueUnTour(Joueur j) {

        System.out.println("Quelle carte voulez vous utiliser ? (ID)");
        for(int i = 0;i<paquet[j.getId()].size();i++){
            System.out.print("   "+i+"  "+paquet[j.getId()].get(i).toString());
        }

        Scanner sc = new Scanner(System.in);
        int rep = sc.nextInt();

        try {
        	if(paquet[j.getId()].get(rep) instanceof CarteChemin){

        		System.out.println("Voulez-vous utiliser cette carte ou la jeter définitivement ? [jeter/garder]");

        		String garder = sc.nextLine();

        		if(garder.equals("garder")){
        			System.out.println("Voulez-vous inverser la direction de cette carte ? [oui/non]");
        			String inv = sc.nextLine();
        			if(inv.equals("oui")){
						((CarteChemin) paquet[j.getId()].get(rep)).setInverser(true);
						((CarteChemin) paquet[j.getId()].get(rep)).inversement();
					}
        			System.out.println("Où voulez vous poser cette carte?");
        			int x = sc.nextInt();
        			int y = sc.nextInt();

        			String s = "oui";
        			while(s.equals("oui") && !poserCarte(x,y,paquet[j.getId()].get(rep),j.getId())) {
						if(poserCarte(x, y, paquet[j.getId()].get(rep), j.getId())){
							System.out.println("Carte posée avec succès !");
						}
						else{
							System.out.println("La carte n'a pas pu être posée, voulez vous réessayer ? [oui/non]");
							s = sc.nextLine();
						}
					}

					if(!s.equals("oui")){
						System.out.println("Utiliser une autre carte de votre deck ? [oui/non]");
						s = sc.nextLine();
						if(s.equals("oui")){
							joueUnTour(j);
						}
					}
				}

				if(garder.equals("jeter")){
					System.out.println("Cette carte a été jetée définitivement !");
					paquet[j.getId()].remove(rep);
				}
				else{
					System.err.println("Commande introuvable.. essayez plutôt avec les deux options possibles [jeter/garder]");
				}
			}

			if(paquet[j.getId()].get(rep) instanceof CarteAction){
				System.out.println("Voulez-vous saboter les objets de vos adversaires ou les réparer ? [saboter/réparer]");
				String saboter = sc.nextLine();

				if(saboter.equals("saboter")){
					((CarteAction) paquet[j.getId()].get(rep)).setSabotage(true);
					System.out.println("Sur quel joueur voulez-vous que s'abatte votre couroux ?");
					for(int i = 1;i<paquet.length;i++){
						System.out.print(" "+participants[i].getNom()+" ");
					}
					saboter = sc.nextLine();
						for(int i = 1;i<paquet.length;i++){
							if(participants[i].getNom().equals(saboter)){
								if(!action(j.getId(),i,(CarteAction) paquet[j.getId()].get(rep))){
									System.out.println("Le sabotage n'a pas fonctionné, voulez vous réessayer ? [oui/non]");
									saboter = sc.nextLine();
									if(saboter.equals("oui")){
										joueUnTour(j);
									}
								}
								if(action(j.getId(),i,(CarteAction) paquet[j.getId()].get(rep))){
									action(j.getId(),i,(CarteAction) paquet[j.getId()].get(rep));
									System.out.println("Sabotage réussi !");
								}
							}
						}
				}
				if(saboter.equals("réparer")){
					System.out.println("Quel joueur voulez-vous aider ?");

					for(int i = 1;i<paquet.length;i++){
						System.out.print(" "+participants[i].getNom()+" ");
					}

					saboter = sc.nextLine();

					for(int i = 1;i<paquet.length;i++){
						if(participants[i].getNom().equals(saboter)){
							if(!action(j.getId(),i,(CarteAction) paquet[j.getId()].get(rep))){
								System.out.println("Ça n'a pas fonctionné, voulez vous réessayer ? [oui/non]");
								saboter = sc.nextLine();
								if(saboter.equals("oui")){
									joueUnTour(j);
								}
							}
							if(action(j.getId(),i,(CarteAction) paquet[j.getId()].get(rep))){
								action(j.getId(),i,(CarteAction) paquet[j.getId()].get(rep));
								System.out.println("Réparation réussie !");
							}
						}
					}
				}
			}
		}

		catch (java.util.InputMismatchException e){
        	sc.nextInt();
		}


	}

	public void afficheIntro(){
		System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		System.out.println("|                                                                                                               |");
		System.out.println("|                                                                                                               |");
		System.out.println("|                     ███████╗ █████╗ ██████╗  ██████╗ ████████╗███████╗██╗   ██╗██████╗                        |");
		System.out.println("|                     ██╔════╝██╔══██╗██╔══██╗██╔═══██╗╚══██╔══╝██╔════╝██║   ██║██╔══██╗                       |");
		System.out.println("|                     ███████╗███████║██████╔╝██║   ██║   ██║   █████╗  ██║   ██║██████╔╝                       |");
		System.out.println("|                     ╚════██║██╔══██║██╔══██╗██║   ██║   ██║   ██╔══╝  ██║   ██║██╔══██╗                       |");
		System.out.println("|                     ███████║██║  ██║██████╔╝╚██████╔╝   ██║   ███████╗╚██████╔╝██║  ██║                       |");
		System.out.println("|                     ╚══════╝╚═╝  ╚═╝╚═════╝  ╚═════╝    ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝                       |");
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
