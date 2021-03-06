import java.util.Arrays;

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

	public boolean getDirection(int n){
		return direction[n];
	}

	public String toString(){
		String s = "";
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
		if(!inverser) {
			if (Arrays.equals(direction,carrefour)) {
				s = "  | |  \n__   __\n__   __\n  | |  ";
			}



			if (Arrays.equals(direction,horizontal)) {
				s = "\n_______\n_______\n       \n       ";
			}

			if (Arrays.equals(direction,vertical)) {
				s = "  | |  \n  | |  \n  | |  \n  | |  \n  | |  ";
			}

			if (Arrays.equals(direction,tridirectionbas)) {
				s = "_______\n__   __\n  | |  \n       \n       ";
			}

			if (Arrays.equals(direction,tridirectiondroite)) {
				s = "  | |  \n     __\n  |  __\n  | |  \n       ";
			}

			if (Arrays.equals(direction,tridirectiongauche)) {
				s = "  | |  \n__     \n__  |  \n  | |  \n       ";
			}

			if (Arrays.equals(direction,tridirectionhaut)) {
				s = "  | |  \n__   __\n_______\n       \n       ";
			}

			if(Arrays.equals(direction,viragedroite)){
				s = "  _____\n  |  __\n  | |  \n       \n       ";
			}

			if(Arrays.equals(direction,viragegauche)){
				s = "_____\n__  | \n  | |  \n       \n       ";
			}

			if(Arrays.equals(direction,sansissuebas)){
				s = "\n  ___  \n  | |  \n  | |  \n       ";
			}

			if(Arrays.equals(direction,sansissuedroite)){
				s = "\n   ____\n  |____\n       \n       ";
			}

			if(Arrays.equals(direction,sansissuegauche)){
				s = "\n_____  \n_____| \n       \n       ";
			}

			if(Arrays.equals(direction,sansissuehaut)){
				s = "  | |  \n  |_|  \n       \n       \n       ";
			}
		}
		else{
			if (Arrays.equals(direction,carrefour)) {
				s = "  | |  \n__   __\n__   __\n  | |  \n       ";
			}


			if (Arrays.equals(direction,horizontal)) {
				s = "\n_______\n_______\n       \n       ";
			}

			if (Arrays.equals(direction,vertical)) {
				s = "  | |  \n  | |  \n  | |  \n  | |  \n  | |  ";
			}

			if (Arrays.equals(direction,tridirectionbas)) {
				s = "  | |  \n__   __\n_______\n       \n       ";
			}

			if (Arrays.equals(direction,tridirectiondroite)) {
				s = "  | |  \n__     \n__  |  \n  | |  \n       ";
			}

			if (Arrays.equals(direction,tridirectiongauche)) {

				s = "  | |  \n     __\n  |  __\n  | |  \n       ";
			}

			if (Arrays.equals(direction,tridirectionhaut)) {
				s = "_______\n__   __\n  | |  \n       \n       ";

			}

			if(Arrays.equals(direction,viragegauche)){
				s = "  _____\n  |  __\n  | |  \n       \n       ";
			}

			if(Arrays.equals(direction,viragedroite)){
				s = "_____\n__  | \n  | |  \n       \n       ";
			}

			if(Arrays.equals(direction,sansissuehaut)){
				s = "\n  ___  \n  | |  \n  | |  \n       ";
			}

			if(Arrays.equals(direction,sansissuegauche)){
				s = "\n   ____\n  |____\n       \n       ";
			}

			if(Arrays.equals(direction,sansissuedroite)){
				s = "\n_____  \n_____| \n       \n       ";
			}

			if(Arrays.equals(direction,sansissuebas)){
				s = "  | |  \n  |_|  \n       \n       \n       ";
			}
		}

		return s;

	}

	public void setInverser(boolean i){
		inverser = i;
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

			if(Arrays.equals(direction,tridirectionbas)){
				direction = tridirectionhaut;
			}
			if(Arrays.equals(direction,tridirectiondroite)){
				direction = tridirectiongauche;
			}
			if(Arrays.equals(direction,tridirectiongauche)){
				direction = tridirectiondroite;
			}
			if(Arrays.equals(direction,tridirectionhaut)){
				direction = tridirectionbas;
			}
		}
	}
}
