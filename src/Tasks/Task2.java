package Tasks;

import util.Calculate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by OniBoov on 15.03.16.
 */
public class Task2 extends JFrame {
    private JButton getBack;
    private JPanel rootPanel;
    private JTextArea data;
    private JTextPane result;
    private JButton calcIt;

    public Task2(final MainWindow mainWindow) {

        super("GeoTask : Задача 2");

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
        public Double x;
        public Double y;
        public Double[] b = new Double[3];
    }

    public void calc() {
        String data = this.data.getText();
        String[] dataArr = data.split("\n");

        ArrayList<Coordinate> coodinates = new ArrayList<Coordinate>();

        for (String line : dataArr) {
            String[] values = line.split(",");


            if (values.length == 4) {
                Coordinate coordinate = new Coordinate();
                coordinate.x = Double.parseDouble(values[0]);
                coordinate.y = Double.parseDouble(values[1]);

                for (int i = 2; i < 4; i++) {
                    String[] angle = values[i].split(" ");
                    if (angle.length == 3) {
                        coordinate.b[i - 1] = Calculate.toRad(
                                Integer.parseInt(angle[0]),
                                Integer.parseInt(angle[1]),
                                Integer.parseInt(angle[2])
                        );
                    }
                }

                coodinates.add(coordinate);
            }
        }

        Integer size = coodinates.size(), i = 0;
        Double xp = 0.0, yp = 0.0;

        // Считаем координаты
        while (size > i + 1) {
            Coordinate coordinate1 = coodinates.get(i);
            Coordinate coordinate2 = coodinates.get(i + 1);
            xp += (coordinate1.x * Calculate.ctg(coordinate2.b[2]) + coordinate2.x * Calculate.ctg(coordinate1.b[1]) - coordinate1.y + coordinate2.y) / (Calculate.ctg(coordinate1.b[1]) + Calculate.ctg(coordinate2.b[2]));
            yp += (coordinate1.y * Calculate.ctg(coordinate2.b[2]) + coordinate2.y * Calculate.ctg(coordinate1.b[1]) + coordinate1.x - coordinate2.x) / (Calculate.ctg(coordinate1.b[1]) + Calculate.ctg(coordinate2.b[2]));
            i++;
        }
        size--;
        xp /= size;
        yp /= size;

        this.result.setText(xp + " " + yp);
    }
}
