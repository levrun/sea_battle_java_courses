package sea_battle_java_courses.swing;

import sea_battle_java_courses.game_model.Cell;
import sea_battle_java_courses.game_model.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Sector extends Component implements MouseListener {
    public static int WIDTH = 32;
    public static int HEIGHT = 32;

    private boolean isSelected;
    private boolean isAttacked;
    private boolean isShip;

    private int axisX;
    private int axisY;

    private SwingField field;

    private Sector(SwingField field, int x, int y) {
        isSelected = false;
        isAttacked = false;
        axisX = x;
        axisY = y;
        this.field = field;
        this.field.add(this);
        setSize(WIDTH, HEIGHT);
        setLocation(SwingField.HEADER_ZONE + SwingField.SPACE_BETWEEN * 2 +
                (WIDTH + SwingField.SPACE_BETWEEN) * axisX,
                SwingField.HEADER_ZONE + SwingField.SPACE_BETWEEN * 2 +
                        (HEIGHT + SwingField.SPACE_BETWEEN) * axisY);
        addMouseListener(this);
    }

    public static Sector sectorCreate(SwingField field, int x, int y) {
        if(field != null && 0 <= x && x < SwingField.SECTOR_COUNT && 0 <= y && y < SwingField.SECTOR_COUNT)
            return new Sector(field, x, y);
        else
            return null;
    }

    public void setShip() {
        isShip = true;
    }

    public boolean isShip() {
        return isShip;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        if(field.isOpen()) {
            if(isShip && isAttacked) {
                draw(g2d, Color.red);
            } else if(isShip){
                draw(g2d, Color.black);
            } else if(isAttacked) {
                draw(g2d, Color.blue);
            } else {
                draw(g2d, Color.lightGray);
            }
        } else {
            if(isSelected) {
                draw(g2d, Color.green);
            } else if(isAttacked ){
                if(isShip()) {
                    draw(g2d, Color.red);
                } else {
                    draw(g2d, Color.blue);
                }

            } else {
                draw(g2d, Color.lightGray);
            }
        }
    }

    private void draw(Graphics2D g2d, Color color) {
        int lw = getWidth() - 1;
        int lh = getHeight() - 1;
        int rw = SwingField.SPACE_BETWEEN * 2;
        int rh = SwingField.SPACE_BETWEEN * 2;
        g2d.setPaint(new GradientPaint(lw, lh, Color.white, lw / 4, lh / 4, color, true));
        g2d.fillRoundRect(0, 0, lw, lh, rw, rh);
        g2d.setColor(color);
        g2d.drawRoundRect(0, 0, lw, lh, rw, rh);
    }

    public void mouseEntered(MouseEvent e) {
        if(!field.isOpen()) {
            isSelected = true;
            field.setSelected(this);
            repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
        if(!field.isOpen()) {
            isSelected = false;
            field.setSelected(null);
            repaint();
        }
    }

    public void mouseClicked(MouseEvent e) {
        if(!field.isOpen()) {
            userAttack();
            computerAttack();
        }
    }

    public boolean checkWin(Cell[][] map) {
        int firedShips = 0;
        boolean firedCellsExist = false;
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                Cell cell = map[i][j];
                if(cell.isShip() && cell.isFired()) {
                    firedShips++;
                    if(firedShips == Field.ALL_SHIPS_CELLS_COUNT) {
                        return true;
                    }
                } else if(!cell.isFired()) {
                    firedCellsExist = true;
                }
            }
        }

        return !firedCellsExist;
    }

    private void computerAttack() {
        int x = Field.getRandomCoordinate();
        int y = Field.getRandomCoordinate();

        SwingField playerSwingField = field.getGame().getPlayerField();
        Field playerField = playerSwingField.getField();

        Cell[][] cells = playerField.getFieldMap();
        Cell cell = cells[x][y];

        while (cell.isFired()) {
            x = Field.getRandomCoordinate();
            y = Field.getRandomCoordinate();
            cell = cells[x][y];
        }

        cell.setWasFired();
        playerSwingField.getSectors()[x][y].setAttacked();
        playerSwingField.printField();
        playerSwingField.getSectors()[x][y].repaint();

        showWinMessageBox(cells, "Вы проиграли", false);
    }

    public void setAttacked() {
        this.isAttacked = true;
    }

    private void showWinMessageBox(Cell[][] cells, String message, boolean userWin) {
        if(checkWin(cells)) {
            if(userWin) {
                field.setGameEnd();
                field.repaint();
            } else {
                SwingField playerSwingField = field.getGame().getPlayerField();
                playerSwingField.setGameEnd();
                playerSwingField.repaint();
            }

            Object[] options = {"Да", "Нет"};
            int result = JOptionPane.showOptionDialog(field.getParent(), "Хотите сыграть еще раз ?", message,
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);

            if(result == 0) {
                // TODO Перезапуск игры
                System.out.println("TODO Перезапуск игры");
            }
        }
    }

    private void userAttack() {
        if(!isAttacked) {
            Cell[][] cells = field.getField().getFieldMap();
            Cell cell = cells[axisX][axisY];
            cell.setWasFired();

            isAttacked = true;
            field.setSelected(null);
            if(cell.isShip()) {
                setShip();
            }

            showWinMessageBox(cells, "Вы выиграли", true);

            // TODO проверить если корабль убит то выделять его серым

            // TODO вынести логику игры в другой пакет

            // TODO выделить шаблон проектирования Observer + MVC
        }
    }

    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }


}
