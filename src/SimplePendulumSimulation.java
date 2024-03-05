import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** Класс для моделирования простого математического маятника.
 *
 * @author Дмитрий Дробышевский
 * @version 1.0
 */
public class SimplePendulumSimulation extends JPanel {
    private static final double GRAVITY = 9.8; // Ускорение свободного падения (м/с²)

    private double length; // Длина маятника (м)
    private double amplitude; // Начальная амплитуда (в радианах)
    private double time; // Время (с)
    private double angularVelocity; // Угловая скорость (рад/с)
    private double angularAcceleration; // Угловое ускорение (рад/с²)
    private double mass; // Масса груза маятника (кг)

    private List<Double> timeList;
    private List<Double> angleList;
    private List<Double> velocityList;
    private List<Double> accelerationList;

    /** Конструктор класса.
     *
     * @param length    длина маятника (м)
     * @param amplitude начальная амплитуда (в градусах)
     * @param time      время (с)
     * @param mass      масса груза маятника (кг)
     */
    public SimplePendulumSimulation(double length, double amplitude, double time, double mass) {
        this.length = length;
        this.amplitude = Math.toRadians(amplitude);
        this.time = time;
        this.mass = mass;

        this.angularVelocity = 0;
        this.angularAcceleration = -GRAVITY / length * Math.sin(this.amplitude);

        this.timeList = new ArrayList<>();
        this.angleList = new ArrayList<>();
        this.velocityList = new ArrayList<>();
        this.accelerationList = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Обновление положения маятника
        double angle = angleList.get(0);
        double velocity = velocityList.get(0);
        int x = centerX + (int) (length * Math.sin(angle) * 500);
        int y = centerY + (int) (length * Math.cos(angle) * 500);

        // Рисование маятника
        g.drawLine(centerX, centerY, x, y);
        g.fillOval(x - 10, y - 10, 20, 20);

        // Обновление положения и скорости маятника
        angle += velocity * 0.01;
        velocity -= GRAVITY / length * Math.sin(angle) * 0.01;

        // Обновление времени
        angleList.set(0, angle);
        velocityList.set(0, velocity);

        // Задержка
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Перерисовка панели
        revalidate();
        repaint();
    }


    /** Метод для запуска моделирования маятника.
     */
    public void simulate() {
        JFrame frame = new JFrame("Simple Pendulum Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(this);
        frame.setVisible(true);

        double timeStep = 0.01; // Шаг времени (с)
        double period = calculatePeriod(); // Расчет периода колебаний

        for (double t = 0; t <= period * 5; t += timeStep) { // Моделирование нескольких периодов
            double angle = amplitude * Math.cos(2 * Math.PI * t / period);
            double velocity = -amplitude * 2 * Math.PI / period * Math.sin(2 * Math.PI * t / period);
            double acceleration = -amplitude * (2 * Math.PI / period) * (2 * Math.PI / period) * Math.cos(2 * Math.PI * t / period);

            timeList.add(t);
            angleList.add(angle);
            velocityList.add(velocity);
            accelerationList.add(acceleration);

            repaint(); // Обновление отображения
            try {
                Thread.sleep((long) (timeStep * 1000)); // Задержка между кадрами
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        plotGraphs();
    }

    /* Метод для расчета периода колебаний маятника.
     *
     * @return период колебаний маятника
     */
    private double calculatePeriod() {
        return 2 * Math.PI * Math.sqrt(length / GRAVITY);
    }

    /* Метод для построения графиков.
     */
    private void plotGraphs() {
        JFrame graphFrame = new JFrame("Simple Pendulum Graphs");
        graphFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphFrame.setSize(800, 600);

        JPanel graphPanel = new JPanel(new GridLayout(3, 1));

        // График угла от времени
        GraphPanel angleGraphPanel = new GraphPanel(timeList, angleList, "Angle vs. Time", "Time (s)", "Angle (rad)");
        graphPanel.add(angleGraphPanel);

        // График скорости от времени
        GraphPanel velocityGraphPanel = new GraphPanel(timeList, velocityList, "Velocity vs. Time", "Time (s)", "Velocity (rad/s)");
        graphPanel.add(velocityGraphPanel);

        // График ускорения от времени
        GraphPanel accelerationGraphPanel = new GraphPanel(timeList, accelerationList, "Acceleration vs. Time", "Time (s)", "Acceleration (rad/s²)");
        graphPanel.add(accelerationGraphPanel);

        graphFrame.add(graphPanel);
        graphFrame.setVisible(true);
    }

    /* Главный метод для запуска программы.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SimplePendulumSimulation simulation1 = new SimplePendulumSimulation(0.2, 10, 10, 0.01);
        simulation1.simulate();

        SimplePendulumSimulation simulation2 = new SimplePendulumSimulation(0.3, 10, 10, 0.01);
        //simulation2.simulate();
    }
}
