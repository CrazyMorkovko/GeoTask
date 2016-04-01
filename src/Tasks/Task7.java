package Tasks;

import util.Calculate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by OniBoov on 15.03.16.
 */
public class Task7 extends JFrame {
    private JButton getBack;
    private JPanel rootPanel;
    private JTextArea data;
    private JTextPane result;
    private JButton calcIt;

    public static class Point {
        public Double x;
        public Double y;

        public Point(Double x, Double y) {
            this.x = x;
            this.y = y;
        }
    }

    public Task7(final MainWindow mainWindow) {

        super("GeoTask : Задача 7");

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

        ArrayList<Point> points = new ArrayList<Point>();

        for (String line : dataArr) {
            String[] values = line.split(" ");

            if (values.length == 2)
                points.add(new Point(Double.parseDouble(values[0]), Double.parseDouble(values[1])));
        }

        Double integral = 0.0;

        // Вычисляем интеграл
        for (int i = 1; i < points.size(); i++) {
            Point point = points.get(i);
            Point pointPrevious = points.get(i - 1);
            integral += (point.x - pointPrevious.x) * (point.y + pointPrevious.y);
        }

        integral /= 2.0;

        this.result.setText("Площадь: " + String.format("%.3f", integral));
    }
}
