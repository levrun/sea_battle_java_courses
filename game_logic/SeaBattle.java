package sea_battle_java_courses.game_logic;

import sea_battle_java_courses.swing_logic.SeaBattleSwing;
import sea_battle_java_courses.swing_logic.Sector;
import sea_battle_java_courses.swing_logic.SwingField;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class SeaBattle {

    private Field playerFieldMap;
    private Field computerFieldMap;

    public SeaBattleSwing seaBattle;

    public static boolean userShoot;
    public static boolean userKilledSomeone;

    public SeaBattle() {
        playerFieldMap = new Field();
        computerFieldMap = new Field();
    }

    public Field getPlayerField() {
        return this.playerFieldMap;
    }

    public Field getComputerField() {
        return this.computerFieldMap;
    }

    public void play() {
        boolean userWin = false;
        boolean computerWin = false;

        while (!userWin && !computerWin) {
            userShoot = false;
            waitUserAttack();

            userWin = checkWin(computerFieldMap.getFieldMap());
            if (userWin) {
                continue;
            }

            if (userKilledSomeone) {
                userKilledSomeone = false;
                continue;
            }

            while (computerAttack()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            computerWin = checkWin(playerFieldMap.getFieldMap());

        }

        if (computerWin) {
            showWinMessageBox("Вы проиграли", false);
        } else {
            showWinMessageBox("Вы выиграли", true);
        }

    }

    private boolean computerAttack() {
        int x = Field.getRandomCoordinate();
        int y = Field.getRandomCoordinate();

        SwingField playerSwingField = seaBattle.getPlayerField();
        Sector sector = playerSwingField.getSectors()[x][y];

        Cell[][] cells = playerFieldMap.getFieldMap();
        Cell cell = cells[x][y];

        while (cell.isFired()) {
            x = Field.getRandomCoordinate();
            y = Field.getRandomCoordinate();
            cell = cells[x][y];
        }

        cell.setWasFired();

        sector.setAttacked();
        if (cell.isShip()) {
            sector.setShip();
        }

        sector.repaint();

        return cell.isShip();
    }

    private void showWinMessageBox(String message, boolean userWin) {
        if (userWin) {
            seaBattle.getComputerField().setGameEnd();
            seaBattle.getComputerField().repaint();
        } else {
            SwingField playerSwingField = seaBattle.getPlayerField();
            playerSwingField.setGameEnd();
            playerSwingField.repaint();
        }

        Object[] options = {"Да", "Нет"};
        int result = JOptionPane.showOptionDialog(seaBattle.getComputerField().getParent(), "Хотите сыграть еще раз ?", message,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (result == 0) {
            // TODO Перезапуск игры
            System.out.println("TODO Перезапуск игры");
        }

    }

    public static void waitUserAttack() {
        do {
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!userShoot);
    }

    public boolean checkWin(Cell[][] map) {
        int firedShips = 0;
        boolean firedCellsExist = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cell cell = map[i][j];
                if (cell.isShip() && cell.isFired()) {
                    firedShips++;
                    if (firedShips == Field.ALL_SHIPS_CELLS_COUNT) {
                        return true;
                    }
                } else if (!cell.isFired()) {
                    firedCellsExist = true;
                }
            }
        }

        return !firedCellsExist;
    }


}
