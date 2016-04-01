package Tasks;

import util.Calculate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by OniBoov on 15.03.16.
 */
public class Task6 extends JFrame {
    private JButton getBack;
    private JPanel rootPanel;
    private JTextArea data;
    private JTextPane result;
    private JButton calcIt;

    public Task6(final MainWindow mainWindow) {

        super("GeoTask : Задача 6");

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

        Double[][] matrix = new Double[dataArr.length][dataArr[0].split(" ").length];

        for (int i = 0; i < dataArr.length; i++) {
            String line = dataArr[i];
            String[] values = line.split(" ");

            if (values.length == matrix[0].length) {
                for (int j = 0; j < values.length; j++) {
                    matrix[i][j] = Double.parseDouble(values[j]);
                }
            }
        }

        // Стандартный алгоритм гаусса ничего нового тут нет.
        Double calculated[] = new Double[matrix.length];
        for (int i = 0; i < calculated.length; i++) {
            calculated[i] = matrix[i][matrix[i].length - 1];
        }
        Double min;
        for (int k = 1; k < matrix.length; k++) {
            for (int j = k; j < matrix.length; j++) {
                min = matrix[j][k - 1] / matrix[k - 1][k - 1];
                for (int i = 0; i < matrix[j].length; i++) {
                    matrix[j][i] = matrix[j][i] - min * matrix[k - 1][i];
                }
                calculated[j] = calculated[j] - min * calculated[k - 1];
            }
        }

        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = i + 1; j < matrix.length; j++) calculated[i] -= matrix[i][j] * calculated[j];
                calculated[i] = calculated[i] / matrix[i][i];
        }

        for (Double aX : calculated) {
            result += aX.floatValue() + "\n";
        }

        this.result.setText(result);
    }
}
