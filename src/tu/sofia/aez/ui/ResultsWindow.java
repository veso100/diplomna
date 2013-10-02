package tu.sofia.aez.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import tu.sofia.aez.om.RejimNaSpirane;
import tu.sofia.aez.util.ChartingService;
import tu.sofia.aez.util.DataSetService;
import tu.sofia.aez.util.PrinterService;
import tu.sofia.aez.util.ResultsCalculationService;

public class ResultsWindow extends JFrame {

	private static final long serialVersionUID = 7683619577604904507L;
	private UIDvigatel dvigatel;
	private JPanel printablePart = new JPanel(new MigLayout("center"));
	private JButton printButton = new JButton("Отпечатай резултатите");

	public ResultsWindow(UIDvigatel uiDvigatel, RejimNaSpirane rejim, VariantsPanel variants) {
		dvigatel = new UIDvigatel(uiDvigatel.getDvigatel(), uiDvigatel.getVeriga());

		setLayout(new MigLayout("center"));
		setResizable(false);
		setSize(UIConstants.RESULT_WIDTH, UIConstants.RESULT_HEIGHT);
		add(printablePart, "wrap");
		setTitle("Резултати");
		setVisible(true);

		JLabel title = new JLabel("Резултати от изчисленията");
		title.setForeground(Color.blue);
		title.setFont(new Font("Arial", Font.BOLD, 22));
		printablePart.add(title, "span,align center, wrap, gapy 20");

		JPanel selectedDvigatelPanel = dvigatel.getPanel(false, true);
		JLabel labelDvigatel = new JLabel("Избрани двигател");
		labelDvigatel.setForeground(Color.blue);
		labelDvigatel.setFont(new Font("Arial", Font.BOLD, 14));
		selectedDvigatelPanel.add(labelDvigatel, "pos 37 5");

		JLabel labelRejim = new JLabel("Избран режим");
		labelRejim.setFont(new Font("Arial", Font.BOLD, 14));
		selectedDvigatelPanel.add(labelRejim);
		labelRejim.setForeground(Color.blue);
		JLabel labelVeriga = new JLabel("Избрани параметри на веригата");
		labelVeriga.setFont(new Font("Arial", Font.BOLD, 14));
		selectedDvigatelPanel.add(labelVeriga, "span,wrap");
		labelVeriga.setForeground(Color.blue);

		JPanel rejimPanel = new JPanel(new MigLayout());
		JLabel labelRejimCurrent = new JLabel(rejim.getName());
		rejimPanel.add(labelRejimCurrent, "wrap");

		URL variantResource = null;
		variantResource = variants.getDSVariant().getResource();
		rejimPanel.add(new JLabel(new ImageIcon(variantResource)));
		selectedDvigatelPanel.add(rejimPanel);
		JPanel verigaPanel = new JPanel(new MigLayout());

		verigaPanel.add(new JLabel("Ипн"));
		JTextField ipnTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		ipnTextField.setText(dvigatel.getVeriga().getIpN() + "");
		ipnTextField.setEditable(false);
		verigaPanel.add(ipnTextField, "wrap, gapx 20");

		verigaPanel.add(new JLabel("Rш"));
		JTextField rshTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		rshTextField.setText(dvigatel.getVeriga().getRsh() + "");
		rshTextField.setEditable(false);
		verigaPanel.add(rshTextField, "wrap, gapx 20");

		verigaPanel.add(new JLabel("Rд"));
		JTextField rdTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		rdTextField.setText(dvigatel.getVeriga().getRsh() + "");
		rdTextField.setEditable(false);
		verigaPanel.add(rdTextField, "wrap, gapx 20");

		verigaPanel.add(new JLabel("Iμ min"));
		JTextField iMiuMinTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		iMiuMinTextField.setText(dvigatel.getVeriga().getImiuMin() + "");
		iMiuMinTextField.setEditable(false);
		verigaPanel.add(iMiuMinTextField, "wrap, gapx 20");

		verigaPanel.add(new JLabel("Iμ max"));
		JTextField iMiuMaxTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		iMiuMaxTextField.setText(dvigatel.getVeriga().getImiuMax() + "");
		iMiuMaxTextField.setEditable(false);
		verigaPanel.add(iMiuMaxTextField, "wrap");
		selectedDvigatelPanel.add(verigaPanel, "wrap, gapx 20");

		JLabel labelResults = new JLabel("Получени резултати от изчисленията");
		labelResults.setFont(new Font("Arial", Font.BOLD, 14));
		selectedDvigatelPanel.add(labelResults, "span,align center, wrap, gapy 20");
		labelResults.setForeground(Color.blue);

		printablePart.add(selectedDvigatelPanel, "span,align center, wrap, gapy 20");

		ResultsCalculationService resultService = new ResultsCalculationService(uiDvigatel.getDvigatel(),
				uiDvigatel.getVeriga(), variants.getDSVariant());

		DataSetService dataSetService = new DataSetService(resultService);

		printablePart.add(new ChartingService().prepareChartPanel(dataSetService.createResultsDataset()),
				"span,align center, wrap, gapy 20");
		add(printButton, "span,align center, wrap, gapy 20");
		printButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PrinterService(ResultsWindow.this.printablePart).printFrame();

			}
		});

	}

}