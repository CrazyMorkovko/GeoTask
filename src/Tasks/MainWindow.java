package Tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by OniBoov on 15.03.16.
 */
public class MainWindow extends JFrame {
    private JPanel rootPanel;

    private JButton task1Button;
    private JButton task2Button;
    private JButton task3Button;
    private JButton task4Button;
    private JButton task5Button;
    private JButton task6Button;
    private JButton task7Button;

    private JButton exitButton;

    public MainWindow() throws HeadlessException {
        super("GeoTask");

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(400, 300));

        this.Bind(task1Button, new Task1(this));
        this.Bind(task2Button, new Task2(this));
        this.Bind(task3Button, new Task3(this));
        this.Bind(task4Button, new Task4(this));
        this.Bind(task5Button, new Task5(this));
        this.Bind(task6Button, new Task6(this));
        this.Bind(task7Button, new Task7(this));

        final JFrame mm = this;
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatchEvent(new WindowEvent(mm, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    protected void Bind(JButton btn, final JFrame task) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                task.setVisible(true);
                task.setSize(getSize());
                task.setLocation(getLocation());
                setVisible(false);
            }
        });
    }
}
