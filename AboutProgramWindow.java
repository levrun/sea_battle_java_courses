package sea_battle_java_courses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AboutProgramWindow implements ActionListener, MouseListener {  // TODO
    private static String aboutGameText = "Игра Морской бой версия 0.7";
    final JLabel label = new JLabel(aboutGameText);

    private JFrame frame;

    public void actionPerformed(ActionEvent e) {
	//ЗАКРЫТИЕ ОКНА   // TODO ? это не доделано ?
        frame.dispose();  // TODO - закрыть можно через этот метод, только передавать его нужно через "ж"
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	//ЗАКРЫТИЕ ОКНА // TODO ? это не доделано ?
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public Component createComponents(JFrame frame) {
        this.frame = frame;
        JButton button = new JButton("ОК");
        button.setMnemonic(KeyEvent.VK_I);
        button.addActionListener(this);
        label.setLabelFor(button);

        //JPanel pane = new JPanel(new GridLayout(0, 1));  // что обозначает GridLayout(0, 1)
        JPanel pane = new JPanel();
        
        pane.setPreferredSize(new Dimension(300, 200));
        pane.setLocation(100,100);    // почему-то местоположение не срабатывает
        pane.add(label);
        pane.add(button);
        pane.setBorder(BorderFactory.createEmptyBorder(
                30, //top
                30, //left
                10, //bottom
                30) //right
        );
        pane.addMouseListener(this);

        return pane;
    }

	public static void createAndShowGUI() {
        JFrame frame = new JFrame(aboutGameText);

        AboutProgramWindow swingExample = new AboutProgramWindow();
        Component contents = swingExample.createComponents(frame);
        frame.getContentPane().add(contents, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
	
   public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
