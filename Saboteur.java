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

        for(int i = 0;i<paquet.length;i++){
            paquet[i] = new ArrayList<>();
        }

        //on crée des tableaux de direction

        boolean[] carrefour = {true,true,true,true};
        boolean[] tridirectiongauche = {true,true,false,true};
        boolean[] tridirectiondroite = {false,true,true,true};
        boolean[] sansissue = {false,false,false,false};
        boolean[] horizontal = {true,false,true,false};
        boolean[] vertical = {false,true,false,true};
        boolean[] tridirectionhaut = {true,true,true,false};
        boolean[] tridirectionbas = {true,false,true,true};

        //on insère la carte départ
        CarteChemin depart = new CarteChemin(carrefour);
        Case depart1 = new CaseSaboteur(depart);
        this.plateau.setCase(4,6,depart1);

        //on insère les cartes chemin dans la pioche
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

        //on insère les cartes action dans la pioche
        for(int i=0;i<4;i++){
            paquet[0].add(new CarteAction(true,'o'));
            paquet[0].add(new CarteAction(false,'o'));
            paquet[0].add(new CarteAction(true,'l'));
            paquet[0].add(new CarteAction(false,'l'));
            paquet[0].add(new CarteAction(true,'c'));
            paquet[0].add(new CarteAction(false,'c'));
        }

        //Distribution des cartes
        for(int i = 1;i<paquet.length;i++){
            for(int j = 0;j<5;j++){
                int rand = (int) (Math.random()*71);
                paquet[i].add(paquet[0].get(rand));
                //on supprime la carte distribuée de la pioche
                paquet[0].remove(rand);
            }
        }

        //afficher le plateau
        //afficher les cartes (?)

    }

    public boolean poserCarte(int i,int j,CarteSaboteur c){
        //conditions de sortie de plateau
        //la carte doit être posée après une autre carte
        //cette autre carte doit permettre elle même l'accès à celle ci
        //la carte ne peut pas être posée sur une autre
        //si les outils sont en mauvais état le joueur ne peut pas poser de carte chemin

    }


    @Override
    public void joueUnTour(Joueur j) {

    }

}
