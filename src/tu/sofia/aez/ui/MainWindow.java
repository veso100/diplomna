package tu.sofia.aez.ui;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tu.sofia.aez.util.ChartingService;
import tu.sofia.aez.util.DataSetService;
import net.miginfocom.swing.MigLayout;

public class MainWindow {

	private JFrame frame = new JFrame("ТУ София - динамично спиране на асинхронен двигател");
	private JPanel panel = new JPanel();
	private JLabel title = new JLabel("Технически унивеситет - София");

	public MainWindow() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(new MigLayout("center"));
		panel.add(title, "wrap");
		DataSetService dataSetService = new DataSetService();
		panel.add(new ChartingService().prepareChartPanel(dataSetService.createSampleDataset()), "wrap");
		title.setFont(new Font("Arial", Font.PLAIN, 24));

		frame.add(panel);
		frame.setResizable(false);
		frame.setSize(UIConstants.WINDOW_WIDTH, UIConstants.WINDOW_HEIGHT);
		frame.setVisible(true);
	}

	public Runnable getRunnable() {
		return new Runnable() {
			public void run() {
				draw();
			}
		};
	}

	public void draw() {

		frame.setVisible(true);
	}

}
