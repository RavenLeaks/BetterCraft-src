package org.newdawn.slick.util.pathfinding;

public interface TileBasedMap {
  int getWidthInTiles();
  
  int getHeightInTiles();
  
  void pathFinderVisited(int paramInt1, int paramInt2);
  
  boolean blocked(PathFindingContext paramPathFindingContext, int paramInt1, int paramInt2);
  
  float getCost(PathFindingContext paramPathFindingContext, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slic\\util\pathfinding\TileBasedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */