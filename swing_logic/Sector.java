package sea_battle_java_courses.swing_logic;

import sea_battle_java_courses.game_logic.Cell;
import sea_battle_java_courses.game_logic.SeaBattle;

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
        setLocation(SwingField.HEADER_ZONE + SwingField.SPACE_BETWEEN * 2 +(WIDTH + SwingField.SPACE_BETWEEN) * axisX,
                SwingField.HEADER_ZONE + SwingField.SPACE_BETWEEN * 2 + (HEIGHT + SwingField.SPACE_BETWEEN) * axisY);

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

            if(!isAttacked) {
                setAttacked();
            }

            // TODO предположительно этот код нужно вынести в основную логику игры
            Cell[][] cells = field.getField().getFieldMap();
            Cell cell = cells[axisX][axisY];
            cell.setWasFired();

            field.setSelected(null);
            if (cell.isShip()) {
                setShip();
                SeaBattle.userKilledSomeone = true;
            }

            repaint();
            SeaBattle.userMakeShooting = true;
        }
    }

    public void setAttacked() {
        this.isAttacked = true;
    }

    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }


}
