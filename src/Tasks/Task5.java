package Tasks;

import util.Calculate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by OniBoov on 15.03.16.
 */
public class Task5 extends JFrame {
    private JButton getBack;
    private JPanel rootPanel;
    private JTextArea data;
    private JTextPane result;
    private JButton calcIt;

    public Task5(final MainWindow mainWindow) {

        super("GeoTask : Задача 5");

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

        ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> sortedMatrix = new ArrayList<ArrayList<Double>>();
        Double eps = 0.001;

        for (String line : dataArr) {
            String[] values = line.split(" ");

            if (values.length == 4) {
                ArrayList<Double> coef = new ArrayList<Double>();
                for (String num : values)
                    coef.add(Double.parseDouble(num));
                matrix.add(coef);
            } else
                eps = Double.parseDouble(values[0]);
        }

        int coefIndex = 0;

        // Сортируем
        while (matrix.size() != 0)
        {
            double maxElem = 0;
            int maxElemIndex = 0;

            for (int i = 0; i < matrix.size(); i++)
            {

                if (matrix.get(i).get(coefIndex) > maxElem)
                {
                    maxElem = matrix.get(i).get(coefIndex);
                    maxElemIndex = i;
                }
            }

            coefIndex++;
            sortedMatrix.add(matrix.get(maxElemIndex));
            //Удаление элемента по индексу.
            matrix.remove(maxElemIndex);
        }

        int size = sortedMatrix.size();
        ArrayList<Double> previousVariableValues = new ArrayList<Double>();
        for (int i = 0; i < size + 2; i++)
            previousVariableValues.add(0.0);

        int iter = 0;

        // Алгоритм Зейделя
        while (true)
        {
            iter++;
            ArrayList<Double> currentVariableValues = new ArrayList<Double>();

            for (int i = 0; i < size; i++)
            {
                currentVariableValues.add(i, sortedMatrix.get(i).get(size));

                for (int j = 0; j < size; j++)
                {
                    if (j < i)
                    {
                        currentVariableValues.set(i,  currentVariableValues.get(i) - sortedMatrix.get(i).get(j) * currentVariableValues.get(j));
                    }

                    if (j > i)
                    {
                        currentVariableValues.set(i,  currentVariableValues.get(i)  - sortedMatrix.get(i).get(j) * previousVariableValues.get(j));
                    }
                }

                currentVariableValues.set(i, currentVariableValues.get(i) / sortedMatrix.get(i).get(i));
            }

            double error = 0.0;

            for (int i = 0; i < size; i++)
            {
                double _error = Math.abs (currentVariableValues.get(i) - previousVariableValues.get(i));
                error += error < _error ? _error : error;
            }

            previousVariableValues = currentVariableValues;

            for (int i = 0; i < size; i++)
            {
                result += String.format("%.5f ", previousVariableValues.get(i));
            }

            result +=  String.format("%.5f ", error);
            result += "\n";

            if (error < eps)
                break;
        }

        this.result.setText(result + "Итераций: " + iter);
    }
}
