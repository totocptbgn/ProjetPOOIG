import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Saboteur extends Jeu {
    ArrayList<CarteSaboteur> paquet []; // Même fonctionnement que Domino.paquet, la pioche est à l'index 0 du paquet, ensuite ce sont les paquets des joueurs.
    
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

        this.setJoueur();

        this.plateau = new PlateauSaboteur(30);

        this.paquet = new ArrayList[participants.length+1];

        boolean[] carrefour = {true,true,true,true};
        boolean[] tridirectiongauche = {true,true,false,true};
        boolean[] tridirectiondroite = {false,true,true,true};
        boolean[] sansissue = {false,false,false,false};
        boolean[] horizontal = {true,false,true,false};
        boolean[] vertical = {false,true,false,true};
        boolean[] tridirectionhaut = {true,true,true,false};
        boolean[] tridirectionbas = {true,false,true,true};

        CarteChemin depart = new CarteChemin(carrefour);

        for(int i = 0;i<6;i++){
            paquet[0].add(new CarteChemin(tridirectionbas));
            paquet[0].add(new CarteChemin(tridirectiondroite));
            paquet[0].add(new CarteChemin(horizontal));
            paquet[0].add(new CarteChemin(tridirectiongauche));
            paquet[0].add(new CarteChemin(tridirectionhaut));
            paquet[0].add(new CarteChemin(vertical));
            paquet[0].add(new CarteChemin(sansissue));
            //mélanger les cartes de la pioche
        }


    }

    public void inverserDirections(CarteChemin c){
        //on inverse les positions de la carte chemin si le joueur décide de la poser dans l'autre sens
    }

    @Override
    public void joueUnTour (Joueur j){

    }

    public void afficher(){

    }

}
