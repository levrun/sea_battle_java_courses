package sea_battle_java_courses;

/**
 * Четырехпалубный корабль
 */
// TODO вплотную коммент и класс
public class Battleship extends Ship {

	Battleship(int x1, int y1,int x2, int y2,int x3, int y3,int x4, int y4) {
        cells = new Cell[BATTLESHIP_SIZE];
        cells[0] = new Cell(x1, y1, this);
        cells[1] = new Cell(x2, y2, this);
        cells[2] = new Cell(x3, y3, this);
        cells[3] = new Cell(x4, y4, this);
        
    }
	
}
