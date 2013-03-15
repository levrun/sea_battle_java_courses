package sea_battle_java_courses.game_logic;

/**
 * Однопалубный корабль
 */
public class PatrolBoat extends Ship {

    PatrolBoat(int x, int y) {
        cells = new Cell[PATROL_BOAT_SIZE];
        cells[0] = new Cell(x, y, this);
    }
}
