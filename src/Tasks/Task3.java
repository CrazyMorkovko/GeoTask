package Tasks;

import util.Calculate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by OniBoov on 15.03.16.
 */
public class Task3 extends JFrame {
    private JButton getBack;
    private JPanel rootPanel;
    private JTextArea data;
    private JTextPane result;
    private JButton calcIt;

    public Task3(final MainWindow mainWindow) {

        super("GeoTask : Задача 3");

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

    public static class Coordinate {
        public String way;
        public Double x;
        public Double y;
        public Double a = 0.0;
        public Double b;
    }

    public void calc() {
        String data = this.data.getText();
        String[] dataArr = data.split("\n");

        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();

        for (String line : dataArr) {
            String[] values = line.split(",");

            if (values.length == 4) {
                Coordinate coordinate = new Coordinate();
                coordinate.way = values[0];
                coordinate.x = Double.parseDouble(values[2]);
                coordinate.y = Double.parseDouble(values[3]);

                String[] angle = values[1].split(" ");
                if (angle.length == 3) {
                    // Сразу конвертим наш угол в радианы
                    coordinate.a = Calculate.toRad(
                            Integer.parseInt(angle[0]),
                            Integer.parseInt(angle[1]),
                            Integer.parseInt(angle[2])
                    );
                }

                if (coordinates.size() != 0) {
                    coordinates.get(coordinates.size() - 1).b = coordinate.a - coordinates.get(coordinates.size() - 1).a;
                }

                coordinates.add(coordinate);
            }
        }

        int size = coordinates.size(), i = 0;
        Double xp = 0.0, yp = 0.0, _xp, m, n, u, g;

        // Сразу определяем координаты вставляемой точки
        while (size > i + 2)
        {
            Coordinate coordinate1 = coordinates.get(i);
            Coordinate coordinate2 = coordinates.get(i + 1);
            Coordinate coordinate3 = coordinates.get(i + 2);
            m = coordinate1.y * Calculate.ctg(coordinate1.b) + coordinate2.y * (-Calculate.ctg(coordinate1.b) - Calculate.ctg(coordinate2.b))
                    + coordinate3.y * Calculate.ctg(coordinate2.b) + coordinate1.x - coordinate3.x;
            n = coordinate1.x * Calculate.ctg(coordinate1.b) + coordinate2.x * (-Calculate.ctg(coordinate1.b) - Calculate.ctg(coordinate2.b))
                    + coordinate3.x * Calculate.ctg(coordinate2.b) - coordinate1.y + coordinate3.y;
            u = Math.atan(m / n);
            g = u - coordinate1.b;
            xp += _xp = (coordinate1.x * Math.tan(g) - coordinate2.x * Math.tan(u) + coordinate2.y - coordinate1.y) / (Math.tan(g) - Math.tan(u));
            yp += (_xp - coordinate2.x) * Math.tan(u) + coordinate2.y;
            i++;
        }
        size -= 2;
        xp /= size;
        yp /= size;

        this.result.setText(xp + " " + yp);
    }
}
