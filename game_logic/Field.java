package sea_battle_java_courses.game_logic;

import java.util.Random;

/**
 * Игровое поле одного игрока
 */
public class Field {
    public static final int FIELD_ROW_SIZE = 10;
    public static final int FIELD_COL_SIZE = 10;

    private Cell[][] field = new Cell[FIELD_ROW_SIZE][FIELD_COL_SIZE];
    public static final int BATTLE_SHIPS_COUNT = 1;
    public static final int DESTROYERS_COUNT = 2;
    public static final int CRUISERS_COUNT = 3;
    public static final int BOATS_COUNT = 4;
    public static final int ALL_SHIPS_CELLS_COUNT = BOATS_COUNT * Ship.PATROL_BOAT_SIZE +
            CRUISERS_COUNT * Ship.CRUISER_SIZE +
            DESTROYERS_COUNT * Ship.DESTROYER_SIZE +
            BATTLE_SHIPS_COUNT * Ship.BATTLESHIP_SIZE;

    public Field() {
        regenerateForGame();
    }

    private void regenerateForGame() {

        // заполним поле пустыми ячейками
        for (int i = 0; i < FIELD_COL_SIZE; i++) {
            for (int j = 0; j < FIELD_ROW_SIZE; j++) {
                Cell cell = new Cell(i, j, null);
                field[i][j] = cell;
            }
        }

        addBattleship();
        addDestroyers();
        addCruisers();
        addBoats();
    }

    /**
     * Проверка пространства на наличие кораблей
     * x,y - координаты первой палубы
     * rotation - поворот корабля (располагает корабль по горизонтали или по вертикали)
     * boatSize - размер корабля (количество палуб)
     */
    private boolean shipInPlace(int x, int y, boolean rotation, int boatSize) {
        int cx, cy; // координаты обследуемого пространства
        int i, j;   // переменные размера обследуемого пространства

        // положения корабля всего 2, поэтому проверяем одно условие, по else присваиваются
        // другие значения
        if (rotation) {
            i = boatSize;
            j = 3;
        } else {
            i = 3;
            j = boatSize;
        }

        for (cx = -1; cx <= i; cx++)
            for (cy = -1; cy <= j; cy++) {
                if ((x + cx) >= 0 && (x + cx) <= 9 && (y + cy) >= 0 && (y + cy) <= 9) {
                    if (field[x + cx][y + cy].isShip()) {
                        return true;
                    }
                }
            }

        return false;
    }


    /**
     * Генерирование координат кораблей
     */
    public static int getRandomCoordinate() {
        Random random = new Random();
        return random.nextInt(10);
    }

    /**
     * Генерирование поворота кораблей
     */
    private static boolean rotationShipGenerate() {
        return new Random().nextBoolean();
    }

    /**
     * Добавление однопалубных кораблей
     */
    public void addBoats() {
        int iteration = 0;
        while (iteration < BOATS_COUNT) {
            int x = getRandomCoordinate();
            int y = getRandomCoordinate();
            if (!shipInPlace(x, y, false, Ship.PATROL_BOAT_SIZE)) {
                addPatrolBoat(x, y);
                iteration++;
            }
        }
    }

    /**
     * Добавление двухпалубного корабля кораблей
     */
    public void addCruisers() {
        int iteration = 0;
        while (iteration < CRUISERS_COUNT) {
            int x = getRandomCoordinate();
            int y = getRandomCoordinate();
            boolean rotation = rotationShipGenerate();
            int x1 = calculateXCoordinate(x, rotation);
            int y1 = calculateYCoordinate(y, rotation);
            if ((x1 != 10) && (y1 != 10))
                if (!shipInPlace(x, y, rotationShipGenerate(), Ship.CRUISER_SIZE)) {
                    addCruiser(x, y, x1, y1);
                    iteration++;
                }
        }
    }

    /**
     * Добавление трехпалубного корабля кораблей
     */
    public void addDestroyers() {
        int iteration = 0;
        while (iteration < DESTROYERS_COUNT) {
            int x = getRandomCoordinate();
            int y = getRandomCoordinate();
            boolean rotation = rotationShipGenerate();
            int x1 = calculateXCoordinate(x, rotation);
            int y1 = calculateYCoordinate(y, rotation);
            int x2 = calculateXCoordinate(x1, rotation);
            int y2 = calculateYCoordinate(y1, rotation);
            if ((x1 != 10) && (y1 != 10) & (x2 != 10) && (y2 != 10))
                if (!shipInPlace(x, y, rotation, Ship.DESTROYER_SIZE)) {
                    addDestroyer(x, y, x1, y1, x2, y2);
                    iteration++;
                }
        }
    }

    /**
     * Добавление четырехпалубного корабля кораблей
     */
    public void addBattleship() {
        int iteration = 0;
        while (iteration < BATTLE_SHIPS_COUNT) {
            int x = getRandomCoordinate();
            int y = getRandomCoordinate();
            boolean rotation = rotationShipGenerate();
            int x1 = calculateXCoordinate(x, rotation);
            int y1 = calculateYCoordinate(y, rotation);
            int x2 = calculateXCoordinate(x1, rotation);
            int y2 = calculateYCoordinate(y1, rotation);
            int x3 = calculateXCoordinate(x2, rotation);
            int y3 = calculateYCoordinate(y2, rotation);
            if ((x1 != 10) && (y1 != 10) && (x2 != 10) && (y2 != 10) && (x3 != 10) && (y3 != 10))
                if (!shipInPlace(x, y, rotation, Ship.BATTLESHIP_SIZE)) {
                    addBattleship(x, y, x1, y1, x2, y2, x3, y3);
                    iteration++;
                }
        }
    }

    public void addPatrolBoat(int x, int y) {
        PatrolBoat p1 = new PatrolBoat(x, y);
        field[x][y] = p1.cells[0];
    }

    public void addCruiser(int x1, int y1, int x2, int y2) {
        Cruiser cr1 = new Cruiser(x1, y1, x2, y2);
        field[x1][y1] = cr1.cells[0];
        field[x2][y2] = cr1.cells[1];
    }

    public void addDestroyer(int x1, int y1, int x2, int y2, int x3, int y3) {
        Destroyer dr1 = new Destroyer(x1, y1, x2, y2, x3, y3);
        field[x1][y1] = dr1.cells[0];
        field[x2][y2] = dr1.cells[1];
        field[x3][y3] = dr1.cells[2];
    }

    public void addBattleship(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        Battleship bs1 = new Battleship(x1, y1, x2, y2, x3, y3, x4, y4);
        field[x1][y1] = bs1.cells[0];
        field[x2][y2] = bs1.cells[1];
        field[x3][y3] = bs1.cells[2];
        field[x4][y4] = bs1.cells[3];
    }

    /**
     * Расчет координат палуб
     * x1,y1 - координаты первой палубы
     * rotation - поворот корабля (располагается корабль по горизонтали или по вериткали)
     */
    public int calculateXCoordinate(int x1, boolean rotation) {
        int x2;

        if (!rotation) {
            x2 = x1;
        } else {
            x2 = x1 + 1;
        }

        if ((x2 < 0) || (x2 > 9)) {
            return 10;
        } else {
            return x2;
        }
    }

    public int calculateYCoordinate(int y1, boolean rotation) {
        int y2;

        if (!rotation) {
            y2 = y1 + 1;
        } else {
            y2 = y1;
        }

        if ((y2 < 0) || (y2 > 9)) {

            return 10;
        } else {
            return y2;
        }
    }

    public Cell[][] getFieldMap() {
        return field;
    }

}
