public class CarteChemin extends CarteSaboteur {
	private boolean[] direction; // Tableau qui montre si l'accès vers la droite, gauche, haut, bas est autorisé.
    private boolean inverser; //sert à inverser le sens d'une carte
	public CarteChemin(boolean[] d){
		this.direction = d;
		this.inverser = false;
	}

	public boolean[] getDirection(){
		return direction;
	}

	public String toString(){
	    String s = "";
        boolean[] carrefour = {true, true, true, true};
        boolean[] tridirectiongauche = {true, true, false, true};
        boolean[] tridirectiondroite = {false, true, true, true};
        boolean[] sansissue = {false, false, false, false};
        boolean[] horizontal = {true, false, true, false};
        boolean[] vertical = {false, true, false, true};
        boolean[] tridirectionhaut = {true, true, true, false};
        boolean[] tridirectionbas = {true, false, true, true};
        if(!inverser) {
            if (direction == carrefour) {
                s = " | | /n __   __ /n /n __   __ /n  | |";
            }

            if (direction == sansissue) {
                s = "__\n|\n__";
            }

            if (direction == horizontal) {
                s = "\n____\n____";
            }

            if (direction == vertical) {
                s = "  | |\n  | |";
            }

            if (direction == tridirectionbas) {
                s = "__   __\n__   __\n  | |";
            }

            if (direction == tridirectiondroite) {
                s = "  | |\n     __\n     __\n  | |";
            }

            if (direction == tridirectiongauche) {
                s = "  | |\n__\n__\n  | |";
            }

            if (direction == tridirectionhaut) {
                s = "  | |\n__   __\n__   __";
            }
        }
        else{
            if (direction == carrefour) {
                s = " | | /n __   __ /n /n __   __ /n  | |";
            }

            if (direction == sansissue) {
                s = " X ";
            }

            if (direction == horizontal) {
                s = "\n____\n____";
            }

            if (direction == vertical) {
                s = "  | |\n  | |";
            }

            if (direction == tridirectionbas) {
                s = "  | |\n__   __\n__   __";
            }

            if (direction == tridirectiondroite) {
                s = "  | |\n__\n__\n  | |";
            }

            if (direction == tridirectiongauche) {

                s = "  | |\n     __\n     __\n  | |";
            }

            if (direction == tridirectionhaut) {
                s = "__   __\n__   __\n  | |";

            }
        }

        return s;

    }

    public void setInverser(boolean i){
	    inverser = i;
    }

    public boolean isInverser() {
        return inverser;
    }

    public void inversement(){ //sert à inverser les directions d'une carte
        boolean[] carrefour = {true, true, true, true};
        boolean[] tridirectiongauche = {true, true, false, true};
        boolean[] tridirectiondroite = {false, true, true, true};
        boolean[] sansissue = {false, false, false, false};
        boolean[] horizontal = {true, false, true, false};
        boolean[] vertical = {false, true, false, true};
        boolean[] tridirectionhaut = {true, true, true, false};
        boolean[] tridirectionbas = {true, false, true, true};

	    if(inverser){

            if(direction == tridirectionbas){
                direction = tridirectionhaut;
            }
            if(direction == tridirectiondroite){
                direction = tridirectiongauche;
            }
            if(direction == tridirectiongauche){
                direction = tridirectiondroite;
            }
            if(direction == tridirectionhaut){
                direction = tridirectionbas;
            }
        }
    }
}
