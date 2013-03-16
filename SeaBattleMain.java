package sea_battle_java_courses;

import sea_battle_java_courses.game_logic.SeaBattle;
import sea_battle_java_courses.swing_logic.SeaBattleSwing;

import javax.swing.*;

/**
 * Учебный пример для изучения языка программирования Java
 */
public class SeaBattleMain {

    public static void main(String[] args) {
        final SeaBattle game = new SeaBattle();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SeaBattleSwing seaBattle = new SeaBattleSwing();
                seaBattle.addMenuBar();
                seaBattle.drawGameField(game.getPlayerField(), game.getComputerField());
                game.seaBattleSwing = seaBattle;
            }
        });

        game.play();

    }

}
