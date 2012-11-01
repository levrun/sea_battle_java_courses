package sea_battle_java_courses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class SeaBattle extends JFrame {

	/**
	 * 	1.	На основе Swing программы с меню(ваша заготовка) +
	 * 		моего примера из шапки, а также консольной программы,
	 * 		которая расставляет корабли в случайном порядке 
	 * 		создать Swing программу, которая расставляет корабли в случайном порядке. done
	 *	2.	Добавить элемент меню “clear”, который очищает доску.
	 *	3.	Добавить элемент меню “start”, который создаст корабли на доске.
	 */
    private SeaBattleBoard board;

    public SeaBattle() {
        board = new SeaBattleBoard(this);
        add(board);
        setTitle("SeaBattle");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(850, 450);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void drawGameField() {
        Field userFieldMap = new Field();
        userFieldMap.regenerateForGame();
        Field computerFieldMap = new Field();
        computerFieldMap.regenerateForGame();
        board.printBattleField(userFieldMap, computerFieldMap);
        board.drawCoordinateSymbols();
    }
    
    private void addMenuBar() {
    	
    	board.setBackground(java.awt.Color.WHITE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Файл");
        JMenu menuHelp = new JMenu("Справка");
        JMenuItem menuItem = new JMenuItem("");
        menuFile.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuFileItemExit = new JMenuItem("Выход");
        eMenuFileItemExit.setMnemonic(KeyEvent.VK_C);
        eMenuFileItemExit.setToolTipText("Выход из приложения");
        eMenuFileItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
      
        JMenuItem eMenuFileItemRestartGame = new JMenuItem("Новая игра");
        eMenuFileItemRestartGame.setMnemonic(KeyEvent.VK_C);
        eMenuFileItemRestartGame.setToolTipText("Перезапуск игры");
        eMenuFileItemRestartGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                drawGameField();
            }

        });
        
        JMenuItem eMenuHelpItemAbout = new JMenuItem("О программе");
        eMenuHelpItemAbout.setMnemonic(KeyEvent.VK_C);
        eMenuHelpItemAbout.setToolTipText("О программе");
        eMenuHelpItemAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                final AboutProgramWindow about = new AboutProgramWindow();
                about.createAndShowGUI();
            }
        });
        
        menuFile.add(eMenuFileItemRestartGame);
        menuFile.add(eMenuFileItemExit);
        menuHelp.add(eMenuHelpItemAbout);
        
        menuBar.add(menuFile);
        menuBar.add(menuHelp);
        
        setContentPane(board);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SeaBattle seaBattle = new SeaBattle();
        seaBattle.addMenuBar();
        seaBattle.drawGameField();
        
    }

}
