public class PiecePuzzle extends Piece {
  /*
   * Si on fait l'idée plus simple où il y a un plateau solution il suffit juste de comparer la pièce
   * que l'on pose à la pièce dans le plateau solution  à la même position, ainsi si elle sont identiques,
   * pas besoin de s'embeter !
   *
   * Il faudrait ducoup un int id, et aussi une image correspondante si on le fait à l'interface graphique...
   */

  private int valeur;

  private class key{
    int id; // Forme du bout ou de l'excroissance qui est unique avec l'autre bout correspond,
    boolean bout;
    boolean sens;
  }
}
