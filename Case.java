public abstract class Case {
  private Piece piece;

  public Case(){
    this.piece = null;
  }

  public boolean estOccupee() {
    return this.piece != null;
  }

  public void PoserPiece(Piece p){
    this.piece = p;
  }

  public Piece getPiece() {
    return piece;
  }

  public abstract int getValeur();
}
