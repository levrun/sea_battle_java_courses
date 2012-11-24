package sea_battle_java_courses.swing;

import sea_battle_java_courses.game_model.Field;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class SeaBattleSwing extends JFrame {

    private SwingField playerField;
    private SwingField computerField;

    private Field playerFieldMap;
    private Field computerFieldMap;

    public SeaBattleSwing() {
        setTitle("Sea Battle, Swing version");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(SwingField.WIDTH * 2 + 37, SwingField.HEIGHT + 68);

        playerField =  new SwingField(true, this);
        computerField = new SwingField(false, this);

        add(playerField);
        add(computerField);

        playerField.setLocation(10, 10);
        computerField.setLocation(SwingField.WIDTH + 20, 10);

        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public Field getPlayerFieldMap() {
        return playerFieldMap;
    }

    public SwingField getPlayerField() {
        return playerField;
    }

    public void drawGameField() {
        playerFieldMap = new Field();
        playerFieldMap.regenerateForGame();

        computerFieldMap = new Field();
        computerFieldMap.regenerateForGame();

        playerField.setField(playerFieldMap);
        computerField.setField(computerFieldMap);

        playerField.printField();
    }

    public void addMenuBar() {
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
        
        setJMenuBar(menuBar);
    }

}
