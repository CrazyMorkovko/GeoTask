package Tasks;

import util.Calculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by OniBoov on 15.03.16.
 */
public class Task1 extends JFrame {
    private JButton getBack;
    private JPanel rootPanel;
    private JTextArea data;
    private JTextPane result;
    private JButton calcIt;

    public Task1(final MainWindow mainWindow) {

        super("GeoTask : Задач 1");

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(mainWindow.getSize());

        getBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                mainWindow.setVisible(true);
                mainWindow.setSize(getSize());
                mainWindow.setLocation(getLocation());
            }
        });
        calcIt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calc();
            }
        });
    }

    public void calc() {
        String data = this.data.getText();
        String[] dataArr = data.split("\n");
        String result = "";
        for(String line : dataArr) {
            String[] values = line.split(" ");
            Double x1, x2, y1, y2, dx, dy, r, a, s, ag, am;
            Integer deg, _min, sec;

            if (values.length == 4) {
                y2 = Double.parseDouble(values[0]);
                y1 = Double.parseDouble(values[1]);
                x2 = Double.parseDouble(values[2]);
                x1 = Double.parseDouble(values[3]);
                dy = y2 - y1;
                dx = x2 - x1;

                // Считаем растояние
                r = Math.atan(Math.abs(dy / dx));
                s = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                // Вычисляем дирекционный угол
                a = Calculate.getGzn(r, dx, dy);
                ag = 180 * a / Math.PI;
                deg = ag.intValue();
                am = 60 * (ag - deg);
                _min = am.intValue();
                sec = ((Double) (60 * (am - _min))).intValue();

                result += deg + " " + _min + " " + sec + "\n" + s + "\n";
            }
        }

        this.result.setText(result);
    }
}
