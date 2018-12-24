public class CarteSaboteur extends Piece {
    private int ID;

    public CarteSaboteur(){
        super();
        //mettre l'id en autoincrement?
    }

    public class CarteChemin {
        private boolean[] direction; //tableau qui montre si l'accès vers la droite,gauche,haut,bas est autorisé

        public CarteChemin(boolean[] d){
            this.direction = d;
        }

    }

    public class CarteAction {
        boolean sabotage; //si la carte est dédiée au sabotage ou à l'aide
        char type; // chariot,lampe,outil (c,l,o)

        public CarteAction(boolean s,char t){
            this.sabotage = s;
            this.type = t;
        }
    }
}
