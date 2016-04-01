package Tasks;

import util.Calculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by OniBoov on 15.03.16.
 */
public class Task4 extends JFrame {
    private JButton getBack;
    private JPanel rootPanel;
    private JTextArea data;
    private JTextPane result;
    private JButton calcIt;
    private JTable table1;
    private JTable resultTable;

    public Task4(final MainWindow mainWindow) {

        super("GeoTask : Задача 4");

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
        public Integer number;
        public Double x;
        public Double y;
    }

    public static class DotAngles {
        public Integer number;
        public Double deg1;
        public Double min1;
        public Double deg2;
        public Double min2;
        public Double rad1;
        public Double rad2;
    }

    public static class Side {
        public String number;
        public Double length;

        public Side(String number, Double length) {
            this.number = number;
            this.length = length;
        }

        public Side() {
        }
    }

    String tempResult = "";

    public void calc() {
        this.result.setBackground(Color.WHITE);
        String data = this.data.getText();
        String[] dataArr = data.split("\n");

        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<Double> alpha = new ArrayList<Double>();
        ArrayList<Double> alphag = new ArrayList<Double>();

        Integer currentLine = 0;

        while (currentLine < 5) {

            if (currentLine == 4)
                break;

            String[] values = dataArr[currentLine].split(" ");

            Coordinate coordinate = new Coordinate();
            coordinate.number = Integer.parseInt(values[0]);
            coordinate.x = Double.parseDouble(values[1]);
            coordinate.y = Double.parseDouble(values[2]);
            coordinates.add(coordinate);

            Double dx, dy, r, a, s, ag, am;
            Integer deg, _min, sec;

            // Получаем и вычисляем угол и расстояние
            if ((currentLine + 1) % 2 == 0) {
                dy = coordinate.y - coordinates.get(currentLine - 1).y;
                dx = coordinate.x - coordinates.get(currentLine - 1).x;

                r = Math.atan(Math.abs(dy / dx));

                s = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                a = Calculate.getGzn(r, dx, dy);
                debugToResult("dy", dy);
                debugToResult("dx", dx);
                debugToResult("r", r);
                debugToResult("a", a);
                alpha.add(a);
                ag = 180 * a / Math.PI;
                alphag.add(ag);
                debugToResult("ag", ag);
                deg = ag.intValue();
                am = 60 * (ag - deg);
                debugToResult("am", am);
                _min = am.intValue();
                sec = (int) (60 * (am - _min));
                tempResult += "a=" + deg + " " + _min + " " + sec + "\n" + "s=" + s + "\n" + "\n";
            }

            currentLine++;
        }

        ArrayList<DotAngles> dotAngles = new ArrayList<DotAngles>();
        Double sumb = 0.0;

        currentLine++;

        while (currentLine < dataArr.length) {
            String line = dataArr[currentLine];

            if (line.equals(""))
                break;

            String[] values = dataArr[currentLine].split(",");

            if (values.length == 1)
                break;

            System.out.println(values.length);

            String[] angle = values[1].split(" ");
            DotAngles dAngle = new DotAngles();
            dAngle.deg1 = Double.parseDouble(angle[0]);
            dAngle.min1 = Double.parseDouble(angle[1]);

            sumb += dAngle.deg1 = dAngle.deg1 + dAngle.min1 / 60;
            if (values.length > 2) {
                String[] angle2 = values[2].split(" ");
                dAngle.deg2 = Double.parseDouble(angle2[0]);
                dAngle.min2 = Double.parseDouble(angle2[1]);
                dAngle.deg2 = dAngle.deg2 + dAngle.min2 / 60;
            }
            dotAngles.add(dAngle);

            currentLine++;
        }

        // Вычисляем допустимое несхождение
        double fb = 60 * (sumb - (alpha.get(1) - alpha.get(0)) * 180 / Math.PI - 180 * dotAngles.size());
        double fdop = Math.sqrt(dotAngles.size());
        tempResult += "\n";

        debugToResult("fb", fb);
        debugToResult("fdop", fdop);

        // Проверяем схождение углов
        if (fdop > Math.abs(fb)) {
            fb /= 6;
            Double directAngle = alphag.get(0);
            ArrayList<Double> directAngles = new ArrayList<Double>();

            // Получаем углы
            for (DotAngles dAngle : dotAngles) {
                if (fb > 0)
                    dAngle.deg1 -= fb / 60;
                else
                    dAngle.deg1 += fb / 60;
                debugToResult("dAngle.deg1", dAngle.deg1);
                directAngle += dAngle.deg1 - 180;

                if (directAngle < 0)
                    directAngle += 360;
                else if (directAngle > 360)
                    directAngle -= 360;

                directAngles.add(directAngle);
                debugToResult("directAngle", directAngle);
            }

            //TODO:: Rewrite this check
            // Проверяем на схождение дирекционных углов
            if (alphag.get(1).floatValue() == directAngle.floatValue()) {
                Double sumLength = 0.0;
                ArrayList<Side> sides = new ArrayList<Side>();
                currentLine++;

                while (currentLine < dataArr.length) {
                    String line = dataArr[currentLine];

                    if (line.equals(""))
                        break;

                    String[] sideArr = line.split(",");
                    Side side = new Side(sideArr[0], Double.parseDouble(sideArr[1]));
                    side.number = sideArr[0];
                    sumLength += side.length;
                    sides.add(side);
                    currentLine++;
                }

                // Removing last
                directAngles.remove(directAngles.size() - 1);

                ArrayList<Double> arrdx = new ArrayList<Double>();
                ArrayList<Double> arrdy = new ArrayList<Double>();

                Double fx = 0.0, fy = 0.0, fabc, fotn;

                tempResult += "\n";
                // Расчитываем на сколько нам изменять
                for (int i = 0; i < sides.size(); i++) {
                    arrdx.add(i, sides.get(i).length * Math.cos(directAngles.get(i) * Math.PI / 180));
                    arrdy.add(i, sides.get(i).length * Math.sin(directAngles.get(i) * Math.PI / 180));
                    fx += arrdx.get(i);
                    fy += arrdy.get(i);
                    debugToResult("arrdx[" + i + "]", arrdx.get(i));
                    debugToResult("arrdy[" + i + "]", arrdy.get(i));
                }

                tempResult += "\n";

                fx = fx - (coordinates.get(2).x - coordinates.get(1).x);
                fy = fy - (coordinates.get(2).y - coordinates.get(1).y);

                fabc = Math.sqrt(Math.pow(fx, 2) + Math.pow(fy, 2));
                fotn = fabc / sumLength;

                debugToResult("fabc", fabc);
                debugToResult("fotn", fotn);
                debugToResult("fx", fx);
                debugToResult("fy", fy);

                // Проверям допусимую невязку
                if (fotn < 0.002) {
                    double dX, dY;
                    ArrayList<Double> arrdX = new ArrayList<Double>();
                    ArrayList<Double> arrdY = new ArrayList<Double>();
                    tempResult += "\n";

                    // Вычесляем стороны и значения
                    for (int i = 0; i < arrdx.size(); i++) {
                        dX = fx / sumLength * sides.get(i).length;
                        dY = fy / sumLength * sides.get(i).length;
                        arrdX.add(dX);
                        arrdY.add(dY);
                        debugToResult("dX", dX);
                        debugToResult("dY", dY);
                    }

                    tempResult += "\n";

                    Double X = coordinates.get(1).x;
                    Double Y = coordinates.get(1).y;

                    ArrayList<Coordinate> m_coordinates = new ArrayList<Coordinate>();

                    // Соответственно приводим стороны к верным значениям
                    for (int i = 0; i < arrdx.size(); i++) {
                        arrdx.set(i, arrdx.get(i) - arrdX.get(i));
                        arrdy.set(i, arrdy.get(i) - arrdY.get(i));

                        debugToResult("arrdx.get(" + i + ")", arrdx.get(i));
                        debugToResult("arrdy.get(" + i + ")", arrdy.get(i));
                        X += arrdx.get(i);
                        Y += arrdy.get(i);
                        Coordinate cord = new Coordinate();
                        cord.x = X;
                        cord.y = Y;
                        m_coordinates.add(cord);

                        tempResult += String.format("X=%.2f", X) + "\n";
                        tempResult += String.format("Y=%.2f", Y) + "\n";
                    }

                    // Проверяем правильность
                    if (X.floatValue() == coordinates.get(2).x.floatValue() &&
                            Y.floatValue() == coordinates.get(2).y.floatValue()) {
                        this.result.setText(tempResult);

                        // Обработка оставшихся сторон
                        sides.clear();
                        currentLine++;
                        while (currentLine < dataArr.length) {
                            String line = dataArr[currentLine];

                            if (line.equals(""))
                                break;

                            String[] sideArr = line.split(",");
                            Side side = new Side();
                            side.number = sideArr[0];
                            sumLength += side.length = Double.parseDouble(sideArr[1]);
                            sides.add(side);
                            currentLine++;
                        }

                        tempResult += "\n\n";

                        for (int i = 0; i < directAngles.size() - 1; i++)
                        {
                            double _angle = directAngles.get(i) + dotAngles.get(i + 1).deg2  - 180;
                            if (_angle < 0)
                                _angle += 360;
                            else if (_angle > 360)
                                _angle -= 360;

                            debugToResult("_angle", _angle);

                            dX = sides.get(i).length * Math.cos(_angle * Math.PI / 180);
                            dY = sides.get(i).length * Math.sin(_angle * Math.PI / 180);
                            debugToResult("dX", dX);
                            debugToResult("dY", dY);

                            X = m_coordinates.get(i).x + dX;
                            Y = m_coordinates.get(i).y + dY;
                            debugToResult("X", X);
                            debugToResult("Y", Y);
                        }

                        this.result.setBackground(Color.GREEN);


                    } else
                        Error("Error!");
                } else
                    Error("Invalid apparent difference");
            } else
                Error("Invalid direction angle");
        } else
            Error("Invalid angle difference");
    }

    private void debugToResult(String name, Double value) {
        tempResult += name + ":" + value + "\n";
    }

    public void Error(String error) {
        this.result.setText(error);
        this.result.setBackground(Color.RED);
    }
}
