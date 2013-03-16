package sea_battle_java_courses.game_logic;

/**
 * Прототип корабля
 */
public class Ship {
    public static final int PATROL_BOAT_SIZE = 1;
    public static final int CRUISER_SIZE = 2;
    public static final int DESTROYER_SIZE = 3;
    public static final int BATTLESHIP_SIZE = 4;
    public static boolean isLife = true;

    protected Cell[] cells;

    public boolean shipIsDead() {
        for(Cell cell : cells) {
            if(!cell.isFired()) {
                return false;
            }
        }

        return true;
    }

}
