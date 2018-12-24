public abstract class Piece {
    private Joueur proprio; // Joueur à qui appartient la pièce.

    public Joueur getProprio() { // Getter du proprio.
        return proprio;
    }

    public void setProprio(Joueur proprio) { // Setter du proprio
        this.proprio = proprio;
    }
}
