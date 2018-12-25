public class CaseSaboteur extends Case {
    CarteChemin carte;

    public CaseSaboteur(CarteChemin c){
        super();
        this.carte =c;
    }

    @Override
    public int getValeur() {
        return 0;
    }

    public boolean[] getDirection(){
        return carte.getDirection();
    }

    public CarteChemin getPiece(){
        return carte;
    }
}
