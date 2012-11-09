package sea_battle_java_courses;

import java.awt.*;

import javax.swing.*;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class SeaBattleBoard extends JPanel implements MouseListener {
    private JFrame frame;
    public static final int PIXEL_MULTIPLICATOR = 30;
    public static final int X_PIXEL_SHIFT = 415;
    public static final int X_SHIFT = 55;
    public static final int Y_SHIFT = 53;

    public static final int FIELD_SIZE = 10;

    public static final int SHIPS_COUNT = 20;

    public Cell[][] userFieldMap;
    public Cell[][] computerFieldMap;
    
    private Image fieldImage;
    private Image shipImage;
    private Image missImage;
    private Image killImage;
    private Image fireImage;
    private Image cellOfFieldImage;

    private Field user;
    private Field computer;

    private Graphics2D g2d;

    private enum CellImage {
        SHIP, MISS, FIRED, KILLED, EMPTY
    }

    public SeaBattleBoard(JFrame frame) {
        this.frame = frame;
    	
    	ImageIcon fieldIcon = new ImageIcon(this.getClass().getResource("/sea_battle_java_courses/images/sea_battle_2fields.png"));
        fieldImage = fieldIcon.getImage();

        ImageIcon shipIcon = new ImageIcon(this.getClass().getResource("/sea_battle_java_courses/images/ship.png"));
        shipImage = shipIcon.getImage();

        ImageIcon missIcon = new ImageIcon(this.getClass().getResource("/sea_battle_java_courses/images/miss.png"));
        missImage = missIcon.getImage();

        ImageIcon killIcon = new ImageIcon(this.getClass().getResource("/sea_battle_java_courses/images/kill.png"));
        killImage = killIcon.getImage();

        ImageIcon fireIcon = new ImageIcon(this.getClass().getResource("/sea_battle_java_courses/images/fire.png"));
        fireImage = fireIcon.getImage();
        
        ImageIcon cellOfFieldIcon = new ImageIcon(this.getClass().getResource("/sea_battle_java_courses/images/cellOfField.png"));
        cellOfFieldImage = cellOfFieldIcon.getImage();

         
        
        addMouseListener(this);
    }



    @Override
    public void paintComponent(Graphics graphics) {
        if(graphics != null) {
            super.paintComponent(graphics);
            this.g2d = (Graphics2D)graphics;

            if(user != null && computer != null) {
                printBattleField(user, computer);
            }
        }

    }
    
    public void drawCoordinateSymbols() {
    	Graphics2D g2d = (Graphics2D)getGraphics();
    	
    	g2d.drawString("A        B       C       D       E       F        G       H        I        J", 65, 50);
    	g2d.drawString("A        B       C       D       E       F        G       H        I        J", 485, 50);
    	for (int i=1; i < 11 ; i++) {
    		
    		g2d.drawString(String.valueOf(i), 40, (45 + i * 30));
    		g2d.drawString(String.valueOf(i), 455, (45 + i * 30));
    				
    	}

    }

    public void printBattleField(Field user, Field computer) {
        this.user = user;
        this.computer = computer;
        printBattleField(user.getFieldMap(), computer.getFieldMap());
        repaint();
    }

    private void printBattleField(Cell[][] userFieldMap, Cell[][] computerFieldMap) {
        this.userFieldMap = userFieldMap;
        this.computerFieldMap = computerFieldMap;

        for(int i=0; i < Field.FIELD_COL_SIZE; i++) {
            for(int j=0; j < Field.FIELD_ROW_SIZE; j++) {
            	
            	Cell userCell = userFieldMap[i][j];
            	Cell computerCell = computerFieldMap[i][j];
            	
            	if(userCell.isShip()) {
                    if(userCell.isFired()) {
                        paintCell(i, j, CellImage.FIRED, false);
                    } else {
                        paintCell(i, j, CellImage.SHIP, false);
                    }

                } else if(userCell.isFired()) {
                    paintCell(i,j,CellImage.MISS,false);
                } else {
                    paintCell(i, j, CellImage.EMPTY, false);
                }

                if(computerCell.isShip()) {
                    if(computerCell.isFired()) {
                        paintCell(i, j, CellImage.FIRED, true);
                    } else {
                        paintCell(i, j, CellImage.SHIP, true);
                    }

                } else if(computerCell.isFired()) {
                    paintCell(i, j, CellImage.MISS, true);
                } else {
                    paintCell(i, j, CellImage.EMPTY, true);
                }
                // TODO в любом случае для поля противника печатаем пустую ячейку так как "туман войны"
                // myPaintMethod(i, j, CellImage.EMPTY, true);

            }
        }
    }

    public void paintCell(int x, int y, CellImage cell, boolean isComputerField) {
        int shift = 0;
        Image image = cellOfFieldImage;

        if(cell.equals(CellImage.EMPTY)) {
            image = cellOfFieldImage;
        } else if(cell.equals(CellImage.MISS)) {
            image = missImage;
            shift = 5;
        } else if(cell.equals(CellImage.SHIP)) {
            image = shipImage;
        } else if(cell.equals(CellImage.KILLED)) {
            image = killImage;
        } else if(cell.equals(CellImage.FIRED)){
            image = fireImage;
        }

        if(g2d != null) {
            g2d.drawImage(image, convertXtoPixels(x, isComputerField) + shift, convertYtoPixels(y, isComputerField) + shift, null);
        }

    }

    public int convertXtoPixels(int x, boolean isComputerField) {
        if(isComputerField) {
            x = x*PIXEL_MULTIPLICATOR+X_PIXEL_SHIFT;
        } else {
            x = x*PIXEL_MULTIPLICATOR;
        }

        x += X_SHIFT;

        return x;
    }

    public int convertYtoPixels(int y, boolean isComputerField) {
        if(isComputerField) {
            y = y*PIXEL_MULTIPLICATOR;
        } else {
            y = y*PIXEL_MULTIPLICATOR;
        }

        y += Y_SHIFT;

        return y;
    }

    public int convertPixelsToX(int pixels) {
        int x = (pixels - X_SHIFT)/PIXEL_MULTIPLICATOR;

        if(x > FIELD_SIZE) {
            x -= 14;
        }

        return x;
    }

    public boolean isPixelsFromComputerField(int pixels) {
        int x = (pixels - X_SHIFT)/PIXEL_MULTIPLICATOR;

        boolean isComputerField = false;
        if(x > FIELD_SIZE) {
            isComputerField = true;
        }

        return isComputerField;
    }

    public int convertPixelsToY(int pixels) {
        return  (pixels - Y_SHIFT)/PIXEL_MULTIPLICATOR;
    }

    
    
    @Override
    public void mouseClicked(MouseEvent event) {
    	int x = event.getX();
        int y = event.getY();

        System.out.println(convertPixelsToX(x));
        System.out.println(convertPixelsToY(y));

        if(isComputerFieldClicked(x, y)) {
            System.out.println("FIRED");

            // TODO добавить проверку что в это поле уже стреляли

            Cell cell = computerFieldMap[convertPixelsToX(event.getX())][convertPixelsToY(event.getY())];
            cell.setWasFired();
            if(cell.isShip()) {
                System.out.println("ЭТО КОРАБЛЬ!");
                paintCell(convertPixelsToX(event.getX()), convertPixelsToY(event.getY()), CellImage.FIRED, isPixelsFromComputerField(event.getX()));
                // TODO добавить проверку что корабль убит
            } else {
                paintCell(convertPixelsToX(event.getX()), convertPixelsToY(event.getY()), CellImage.MISS, isPixelsFromComputerField(event.getX()));
            }

            if(checkIfUserWin()) {
                 Object[] options = {"Да", "Нет"};
                 int result = JOptionPane.showOptionDialog(frame, "Хотите сыграть еще раз ?", "Вы выиграли",
                         JOptionPane.YES_NO_CANCEL_OPTION,
                         JOptionPane.QUESTION_MESSAGE,
                         null,
                         options,
                         options[1]);

                 if(result == 0) {
                     System.out.println("TODO Перезапуск игры");
                 }
            }


        }

        // TODO goto computer move
        computerMove();

        if(checkIfComputerWin()) {
            Object[] options = {"Да", "Нет"};
            int result = JOptionPane.showOptionDialog(frame, "Хотите сыграть еще раз ?", "Вы проиграли",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);

            if(result == 0) {
                System.out.println("TODO Перезапуск игры");
            }
        }

        // TODO check if computer win

    }

    private void computerMove() {
        int x = Field.getRandomCoordinate();
        int y = Field.getRandomCoordinate();

        Cell cell = userFieldMap[x][y];
        while (cell.isFired()) {
            x = Field.getRandomCoordinate();
            y = Field.getRandomCoordinate();
            cell = userFieldMap[x][y];
        }

        cell.setWasFired();
        if(cell.isShip()) {
            paintCell(x, y, CellImage.FIRED, false);
        } else {
            paintCell(x, y, CellImage.MISS, false);
        }

    }

    public boolean checkIfUserWin() {
        return checkWin(computerFieldMap);
    }

    public boolean checkIfComputerWin() {
        return checkWin(userFieldMap);
    }

    public boolean checkWin(Cell[][] map) {
        int firedShips = 0;
        boolean dontFiredCellsExist = false;
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                Cell cell = map[i][j];
                if(cell.isShip() && cell.isFired()) {
                    firedShips++;
                    if(firedShips == SHIPS_COUNT) {
                        return true;
                    }
                } else if(!cell.isFired()) {
                    dontFiredCellsExist = true;
                }
            }
        }

        return !dontFiredCellsExist;
    }


    public boolean isComputerFieldClicked(int x1, int y1) {
        int x = convertPixelsToX(x1);
        int y = convertPixelsToY(y1);

        if(isPixelsFromComputerField(x1) && x >=0 && x < 10 && y >= 0 && y < 10) {
            return true;
        }

        return false;
    }

    private boolean isCorrectFire(int x, int y) {
        int realX = convertPixelsToX(x);
        int realY = convertPixelsToY(y);

        if(isPixelsFromComputerField(x) && realX >= 0 && realX < 10 && realY >= 0 && realY < 10) {
            return true;
        }

        return false;
    }

    public void mousePressed(MouseEvent event) {
        System.out.println(event.getX() + " " + event.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


	public void setJMenuBar(JMenuBar menubar) {
		// TODO Auto-generated method stub
		
	}
}
