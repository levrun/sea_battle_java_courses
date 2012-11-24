package sea_battle_java_courses;

import sea_battle_java_courses.swing.SeaBattleSwing;

import javax.swing.*;

/**
 * Учебный пример для изучения языка программирования Java
 */
public class SeaBattleMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SeaBattleSwing seaBattle = new SeaBattleSwing();
                seaBattle.addMenuBar();
                seaBattle.drawGameField();
            }
        });
    }

}
