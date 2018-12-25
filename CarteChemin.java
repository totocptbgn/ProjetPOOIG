public class CarteChemin extends CarteSaboteur {
	private boolean[] direction; // Tableau qui montre si l'accès vers la droite, gauche, haut, bas est autorisé.

	public CarteChemin(boolean[] d){
		this.direction = d;
	}

	public boolean[] getDirection(){
		return direction;
	}

	public String toString(){
	    String s = "";
	    if(direction[1]){
            s+="  | |";
        }

	    if(direction[0] && !direction[2]){
	        s+="\n__ \n __";
        }

        if(!direction[0] && direction[2]){
            s+="\n     __\n     __";
        }

        if(direction[0] && direction[2]){
            s+="\n__   __\n__   __";
        }
        
        if(direction[3]){
            s+="\n  | |";
        }

        return s;

	    //String s = " | | /n __   __ /n /n __   __ /n  | |";
    }
}
