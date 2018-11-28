import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Domino extends Jeu {
    private boolean estVide;
    private ArrayList <PieceDomino> pieces;      // Toutes les pièces qui existent
    private ArrayList <PieceDomino> paquet [];   // Tableau comportant la pioche et les Dominos non-posés de chaque joeurs
                                                 //  -> Taille de paquet = Nombre de joueurs + 1 ([0] correspond à la pioche

    @Override
    public void lancerPartie(){
        System.out.println("-- Partie de Domino --" + "\n");

        // Création des Joueurs
        this.setJoueur();

        // Création du plateau
        this.plateau = new PlateauDomino(20);
        this.estVide = true;

        // Création des Dominos
        this.pieces = new ArrayList<>();
        for (int i = 0; i < 7 ; i++ ) {
            for (int j = i; j < 7 ; j++ ) {
                this.pieces.add(new PieceDomino(i, j));
            }
        }

        // Distribution des Dominos
        this.paquet = new ArrayList[participants.length + 1];
        for (int i = 0; i < paquet.length; i++) {
            this.paquet[i] = new ArrayList<>();
        }
        Random rand = new Random();
        int dist;
        if (participants.length == 2){
            dist = 7;
        } else {
            dist = 6;
        }
        for (int i = 1; i < paquet.length; i++) {
            for (int j = 0; j < dist; j++) {
                PieceDomino p = pieces.remove(rand.nextInt(pieces.size()));
                p.setProprio(participants[i-1]);
                this.paquet[i].add(p);
            }
        }
        while(!pieces.isEmpty()){
           this.paquet[0].add(pieces.remove(0));
        }
    }

    public void afficherDominos(){
        for (int i = 1; i < paquet.length; i++) {
            System.out.print( participants[i-1].getNom() + " : ");
            for (int j = 0; j < paquet[i].size(); j++) {
                System.out.print(paquet[i].get(j) + " ");
            }
            System.out.println();
        }
    }
    public boolean placerDomino(int i, int j, int dir, PieceDomino p){

        // i, j : position dans le tableau
        // dir : 0 = vers la droite
        //       1 = vers le bas
        //       2 = vers le gauche
        //       3 = vers le haut
        boolean pose = false; //on retournera pose
        
        //on verifie si les valeurs des cases correspondent bien à celles du tableau
        if(i<0 || j<0 || i>=this.plateau.hauteur || j>=this.plateau.longueur){
            System.err.println("La position donnée n'existe pas sur le plateau");
            return false;
        }
        
        if((j == 0 && dir == 2) || (j == this.plateau.longueur-1 && dir == 0) || (i == 0 && dir == 3) || (i == this.plateau.hauteur-1 && dir == 1)){
            System.err.println("La direction fait sortir la pièce du tableau");
            return false;
        }
        
        CaseDomino caseDomino1 = (CaseDomino) plateau.getCase(i, j);
        CaseDomino caseDomino2;
        switch (dir){
            default: return false;
            case 0: caseDomino2 = (CaseDomino)plateau.getCase(i,j + 1);
                break;
            case 1: caseDomino2 = (CaseDomino)plateau.getCase(i + 1, j);
                break;
            case 2: caseDomino2 = (CaseDomino)plateau.getCase(i, j - 1);
                break;
            case 3: caseDomino2 = (CaseDomino)plateau.getCase(i - 1, j + 1);
        }
        
        if (!(caseDomino1.estOccupee() && caseDomino2.estOccupee()) && estVide){
            int id = p.getProprio().getId();
            caseDomino1.PoserPiece(p, 0);
            caseDomino2.PoserPiece(p, 1);
            paquet[id + 1].remove(p);
            // la condition pour placer la pièce est remplie, on met estVide à false
            this.estVide = false;
            pose = true;
        }
        else if (caseDomino1.estOccupee() || caseDomino2.estOccupee()) {
            System.err.println("Vous ne pouvez pas placer de domino à cette position");
            return false;
        }

         // Placement des pièces
      if (!(caseDomino1.estOccupee() && caseDomino2.estOccupee()) && !this.estVide){
          if (dir == 0){ // Si le domino est posé à l'horizontal vers la droite on vérifie si les cases adjacents possède une pièce de valeur similaire
              if (this.plateau.getCase(i,j-1).getValeur() == p.getValeur(0)){ //si la pièce correspondante est vers la gauche
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }

              if(this.plateau.getCase(i,j-1).getValeur() != p.getValeur(0)){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i-1,j).getValeur() == p.getValeur(0)){ //si la pièce correspondante est vers le bas
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;   
              }
              
              if(this.plateau.getCase(i-1,j).getValeur() != p.getValeur(0) && this.plateau.getCase(i-1,j) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent");
                  return false;
              }
              
              else if(this.plateau.getCase(i+1,j).getValeur() == p.getValeur(0)){ //si la pièce correspondante est vers le haut
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i+1,j).getValeur() != p.getValeur(0) && this.plateau.getCase(i+1,j) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i+1,j+1).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i+1,j+1).getValeur() != p.getValeur(1) && this.plateau.getCase(i+1,j+1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              else if(this.plateau.getCase(i-1,j+1).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i-1,j+1).getValeur() != p.getValeur(1) && this.plateau.getCase(i-1,j+1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if (this.plateau.getCase(i,j+2).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i,j+2).getValeur() != p.getValeur(1) && this.plateau.getCase(i,j+2) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
          }
          
          if(dir == 1){
              
              if(this.plateau.getCase(i,j+1).getValeur() == p.getValeur(0)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i,j+1).getValeur() != p.getValeur(0) && this.plateau.getCase(i,j+1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i,j-1).getValeur() == p.getValeur(0)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i,j-1).getValeur() != p.getValeur(0) && this.plateau.getCase(i,j-1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i+1,j).getValeur() == p.getValeur(0)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i+1,j).getValeur() != p.getValeur(0) && this.plateau.getCase(i+1,j) != null ){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i-1,j+1).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i-1,j+1).getValeur() != p.getValeur(1) && this.plateau.getCase(i-1,j+1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i-1,j-1).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i-1,j-1).getValeur() != p.getValeur(1) && this.plateau.getCase(i-1,j-1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i-2,j).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i-2,j).getValeur() != p.getValeur(1) && this.plateau.getCase(i-2,j) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
          }
          
          if(dir == 2){
              
              if (this.plateau.getCase(i+1,j).getValeur() == p.getValeur(0)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i+1,j).getValeur() != p.getValeur(0) && this.plateau.getCase(i+1,j) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i-1,j).getValeur() == p.getValeur(0)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i-1,j).getValeur() != p.getValeur(0) && this.plateau.getCase(i-1,j) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i,j-1).getValeur() == p.getValeur(0)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i,j-1).getValeur() != p.getValeur(0) && this.plateau.getCase(i,j-1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i+1,j-1).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i+1,j-1).getValeur() != p.getValeur(1) && this.plateau.getCase(i+1,j-1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i-1,j-1).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i-1,j-1).getValeur() != p.getValeur(1) && this.plateau.getCase(i-1,j-1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i,j-2).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i,j-2).getValeur() != p.getValeur(1) && this.plateau.getCase(i,j-2) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
          }
          
          if(dir == 3){
              
              if(this.plateau.getCase(i-1,j).getValeur() == p.getValeur(0)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i-1,j).getValeur() != p.getValeur(0) && this.plateau.getCase(i-1,j) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i,j-1).getValeur() == p.getValeur(0)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i,j-1).getValeur() != p.getValeur(0) && this.plateau.getCase(i,j-1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i,j+1).getValeur() == p.getValeur(0)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i,j+1).getValeur() != p.getValeur(0) && this.plateau.getCase(i,j+1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i+2,j).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i+2,j).getValeur() != p.getValeur(1) && this.plateau.getCase(i+2,j) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if(this.plateau.getCase(i+1,j-1).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i+1,j-1).getValeur() != p.getValeur(1) && this.plateau.getCase(i+1,j-1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
              
              else if (this.plateau.getCase(i+1,j+1).getValeur() == p.getValeur(1)){
                  int id = p.getProprio().getId();
                  caseDomino1.PoserPiece(p, 0);
                  caseDomino2.PoserPiece(p, 1);
                  paquet[id + 1].remove(p);
                  this.estVide = false;
                  pose = true;
              }
              
              if(this.plateau.getCase(i+1,j+1).getValeur() != p.getValeur(1) && this.plateau.getCase(i+1,j+1) != null){
                  System.err.println("Vous ne pouvez pas placer ce domino à cette position car la valeur ne correspond pas à celle du domino adjacent.");
                  return false;
              }
          }
      }
        return pose;
    }

    @Override
    public void joueUnTour(Joueur j) {
        //
    }

    @Override
    public void setJoueur() {
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
}
