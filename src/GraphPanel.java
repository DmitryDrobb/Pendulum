import javax.swing.*;
import java.awt.*;
import java.util.List;

/** Панель для отображения графика.
 * @author Дмитрий Дробышевский
 * @version 1.0
 */
class GraphPanel extends JPanel {
    private List<Double> xData;
    private List<Double> yData;
    private String title;
    private String xAxisLabel;
    private String yAxisLabel;

    /* Конструктор класса.
     *
     * @param xData      данные для оси x
     * @param yData      данные для оси y
     * @param title      заголовок графика
     * @param xAxisLabel метка оси x
     * @param yAxisLabel метка оси y
     */
    public GraphPanel(List<Double> xData, List<Double> yData, String title, String xAxisLabel, String yAxisLabel) {
        this.xData = xData;
        this.yData = yData;
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        // Draw title
        g.drawString(title, width / 2 - title.length() * 3, 20);

        // Draw x-axis label
        g.drawString(xAxisLabel, width - xAxisLabel.length() * 6, height - 10);

        // Draw y-axis label
        g.drawString(yAxisLabel, 10, height / 2 + yAxisLabel.length() * 3);

        // Draw x-axis ticks
        for (int i = 0; i < width; i += width / 10) {
            g.drawLine(i, height - 30, i, height - 20);
            g.drawString(String.valueOf(i), i - 5, height - 10);
        }

        // Draw y-axis ticks
        for (int i = height; i > 0; i -= height / 10) {
            g.drawLine(30, i, 20, i);
            g.drawString(String.valueOf(i), 5, i + 5);
        }

        // Draw data points
        for (int i = 0; i < xData.size(); i++) {
            int x = (int) (xData.get(i) / xData.get(xData.size() - 1) * width);
            int y = height - (int) (yData.get(i) / yData.get(yData.size() - 1) * height);
            g.fillOval(x - 3, y - 3, 6, 6);
        }

        // Draw lines connecting data points
        for (int i = 0; i < xData.size() - 1; i++) {
            int x1 = (int) (xData.get(i) / xData.get(xData.size() - 1) * width);
            int y1 = height - (int) (yData.get(i) / yData.get(yData.size() - 1) * height);
            int x2 = (int) (xData.get(i + 1) / xData.get(xData.size() - 1) * width);
            int y2 = height - (int) (yData.get(i + 1) / yData.get(yData.size() - 1) * height);
            g.drawLine(x1, y1, x2, y2);
        }
    }
}