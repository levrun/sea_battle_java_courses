package sea_battle_java_courses.swing_logic;


import sea_battle_java_courses.game_logic.Cell;
import sea_battle_java_courses.game_logic.Field;

import javax.swing.*;
import java.awt.*;

public class SwingField extends JComponent {
    public static int SECTOR_COUNT = 10;
    public static int SPACE_BETWEEN = 2;
    public static int HEADER_ZONE = 16;

    public static int HEIGHT = HEADER_ZONE + SPACE_BETWEEN * 2 + (Sector.HEIGHT + SPACE_BETWEEN) * SECTOR_COUNT;;
    public static int WIDTH = HEADER_ZONE + SPACE_BETWEEN * 2 + (Sector.WIDTH + SPACE_BETWEEN) * SECTOR_COUNT;

    private Sector[][] sectors;
    private Sector selectedSector;

    private Field field;
    private SeaBattleSwing game;

    private boolean gameEnd = false;
    private boolean isOpen;

    public SwingField(boolean isOpen, SeaBattleSwing game) {
        this.isOpen = isOpen;
        this.game = game;

        sectors = new Sector[SECTOR_COUNT][SECTOR_COUNT];
        for(int i = 0; i < SECTOR_COUNT; i++)
            for(int j = 0; j < SECTOR_COUNT; j++)
                sectors[i][j] = Sector.sectorCreate(this, i,j);

        selectedSector = null;

        setSize(WIDTH, HEIGHT);
        printLettersAndNumbers();
    }

    public SeaBattleSwing getGame() {
        return this.game;
    }

    public Sector[][] getSectors() {
        return this.sectors;
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
        // almostFinishTheGame(field);
    }

    private void almostFinishTheGame(Field field) {
        Cell[][] cells = field.getFieldMap();
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 9; j++) {
                cells[i][j].setWasFired();
                sectors[i][j].setAttacked();
            }
        }
        repaint();
    }

    public void setSelected(Sector sector) {
        selectedSector = sector;
        repaint();
    }

    public boolean isOpen() {
        return isOpen;
    }

    private void printLettersAndNumbers() {
        char[] letters = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'К'};
        for(int i = 0; i < SECTOR_COUNT; i++) {
            JLabel horizontal = new JLabel(String.valueOf(i + 1));
            if(i < letters.length)
                horizontal.setText(String.valueOf(letters[i]));
            JLabel vertical = new JLabel(String.valueOf(i + 1));
            horizontal.setHorizontalAlignment(JLabel.CENTER);
            horizontal.setVerticalAlignment(JLabel.CENTER);
            vertical.setHorizontalAlignment(JLabel.CENTER);
            vertical.setVerticalAlignment(JLabel.CENTER);
            horizontal.setSize(Sector.WIDTH, HEADER_ZONE);
            vertical.setSize(HEADER_ZONE, Sector.HEIGHT);
            horizontal.setLocation(HEADER_ZONE + SPACE_BETWEEN * 2 +
                    (Sector.WIDTH + SPACE_BETWEEN) * i, SPACE_BETWEEN);
            vertical.setLocation(SPACE_BETWEEN, HEADER_ZONE + SPACE_BETWEEN * 2 +
                    (Sector.HEIGHT + SPACE_BETWEEN) * i);
            add(horizontal);
            add(vertical);
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        if(gameEnd) {
            paintGradientBoxForFieldBottom(g2d);
            paintWinOrLoseMessage(g2d);
        } else {
            paintGradientBoxForFieldBottom(g2d);
            paintCurrentSelectedElement(g2d);
            super.paint(g);
        }
    }

    private void paintWinOrLoseMessage(Graphics2D g2d) {
        if(isOpen) {
            g2d.setColor(Color.red);
            g2d.scale(10, 10);
            g2d.drawString("LOSE", 1, 21);
        }
        else {
            g2d.setColor(Color.green);
            g2d.scale(10, 10);
            g2d.drawString("WIN", 7, 21);
        }
    }

    public void setGameEnd() {
        this.gameEnd = true;
    }

    public void printField() {
        Cell[][] cells = this.field.getFieldMap();
        for(int i = 0; i < SECTOR_COUNT; i++) {
            for(int j = 0; j < SECTOR_COUNT; j++) {
                if(cells[i][j].isShip()) {
                    sectors[i][j].setShip();
                    sectors[i][j].repaint();
                } else if(cells[i][j].isFired()) {
                    sectors[i][j].setAttacked();
                }
            }
        }
    }

    private void paintCurrentSelectedElement(Graphics2D g2d) {
        if(selectedSector != null) {
            int spx = selectedSector.getLocation().x;
            int spy = selectedSector.getLocation().y;
            int slw = selectedSector.getWidth() - 1;
            int slh = selectedSector.getHeight() - 1;
            int srw = SPACE_BETWEEN * 2;
            int srh = SPACE_BETWEEN * 2;
            g2d.setPaint(new GradientPaint(spx + slw, SPACE_BETWEEN + HEADER_ZONE, Color.white,
                    spx + slw / 4, SPACE_BETWEEN + HEADER_ZONE / 4, Color.green, true));
            g2d.fillRoundRect(spx, SPACE_BETWEEN, slw, HEADER_ZONE, srw, srh);
            g2d.setPaint(new GradientPaint(SPACE_BETWEEN + HEADER_ZONE, spy + slh, Color.white,
                    SPACE_BETWEEN + HEADER_ZONE / 4, spy + slh / 4, Color.green, true));
            g2d.fillRoundRect(SPACE_BETWEEN, spy, HEADER_ZONE, slh, srw, srh);
            g2d.setColor(Color.green);
            g2d.drawRoundRect(spx, SPACE_BETWEEN, slw, HEADER_ZONE, srw, srh);
            g2d.drawRoundRect(SPACE_BETWEEN, spy, HEADER_ZONE, slh, srw, srh);
        }
    }

    private void paintGradientBoxForFieldBottom(Graphics2D g) {
        int lw = getWidth() - 1;
        int lh = getHeight() - 1;
        int rw = SPACE_BETWEEN * 2;
        int rh = SPACE_BETWEEN * 2;
        g.setPaint(new GradientPaint(lw, lh, Color.white,
                lw / 4, lh / 4, Color.lightGray, true));
        g.fillRoundRect(0, 0, lw, lh, rw, rh);
        g.setColor(Color.lightGray);
        g.drawRoundRect(0, 0, lw, lh, rw, rh);
    }


}
