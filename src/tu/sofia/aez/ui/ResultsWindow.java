package tu.sofia.aez.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;
import tu.sofia.aez.om.RejimNaSpirane;
import tu.sofia.aez.util.ChartingService;
import tu.sofia.aez.util.DataSetService;
import tu.sofia.aez.util.ExcelService;
import tu.sofia.aez.util.PrinterService;
import tu.sofia.aez.util.ResultsCalculationService;

public class ResultsWindow {

	private static final String NAN_STR = "N/A";
	private UIDvigatel dvigatel;
	private JPanel printablePart = new JPanel(new MigLayout("center"));
	private JButton printButton = new JButton("Отпечатай резултатите");
	private JButton excelButton = new JButton("Запази резултатите в Excel");
	private JTextField excelTextField = new JTextField(14);
	ResultsCalculationService resultService = null;

	public ResultsWindow(UIDvigatel uiDvigatel, RejimNaSpirane rejim, VariantsPanel variants) {
		dvigatel = new UIDvigatel(uiDvigatel.getDvigatel(), uiDvigatel.getVeriga());
		DecimalFormat rf = new DecimalFormat("#.##");
		JFrame frameHolder = new JFrame();
		frameHolder.setLayout(new MigLayout("center"));
		frameHolder.setResizable(false);
		frameHolder.setSize(UIConstants.RESULT_WIDTH, UIConstants.RESULT_HEIGHT);

		frameHolder.setTitle("Резултати");
		frameHolder.setVisible(true);
		JPanel container = new JPanel(new MigLayout("center"));
		JScrollPane jsp = new JScrollPane(container);
		jsp.setPreferredSize(new Dimension(UIConstants.RESULT_WIDTH, UIConstants.RESULT_HEIGHT));
		frameHolder.add(jsp, "wrap");
		container.add(printablePart, "wrap");
		JLabel title = new JLabel("Резултати от изчисленията");
		title.setForeground(Color.blue);
		title.setFont(new Font("Arial", Font.BOLD, 22));
		printablePart.add(title, "span,align center, wrap, gapy 20");

		JPanel dvigatelVerigaResultatiPanel = dvigatel.getPanel(false, true);
		JLabel labelDvigatel = new JLabel("Избран двигател");
		labelDvigatel.setForeground(Color.blue);
		labelDvigatel.setFont(new Font("Arial", Font.BOLD, 14));
		dvigatelVerigaResultatiPanel.add(labelDvigatel, "pos 280 5");

		JPanel verigaIRejimLabel = new JPanel(new MigLayout("center"));

		JLabel labelRejim = new JLabel("Избран режим и параметри");
		labelRejim.setFont(new Font("Arial", Font.BOLD, 14));
		verigaIRejimLabel.add(labelRejim);
		labelRejim.setForeground(Color.blue);
		dvigatelVerigaResultatiPanel.add(labelRejim, "span,align center, wrap, gapy 10");

		JPanel rejimPanel = new JPanel(new MigLayout());
		JLabel labelRejimCurrent = new JLabel(rejim.getName());
		rejimPanel.add(labelRejimCurrent, "wrap");

		resultService = new ResultsCalculationService(uiDvigatel.getDvigatel(), uiDvigatel.getVeriga(),
				variants.getDSVariant(), rejim.getType());

		URL variantResource = null;
		variantResource = variants.getDSVariant().getResource();
		rejimPanel.add(new JLabel(new ImageIcon(variantResource)));
		verigaIRejimLabel.add(rejimPanel);
		JPanel verigaPanel = new JPanel(new MigLayout());
		verigaPanel.add(new JLabel("Iпн"));
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
		rdTextField.setText(dvigatel.getVeriga().getRd() + "");
		rdTextField.setEditable(false);
		verigaPanel.add(rdTextField, "wrap, gapx 20");

		verigaPanel.add(new JLabel("Iμ min"));
		JTextField iMiuMinTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		iMiuMinTextField.setText(rf.format(resultService.getCalculatedIMiuMin()) + "");
		iMiuMinTextField.setEditable(false);
		verigaPanel.add(iMiuMinTextField, "wrap, gapx 20");

		verigaPanel.add(new JLabel("Iμ max"));
		JTextField iMiuMaxTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		iMiuMaxTextField.setText(rf.format(resultService.getCalculatedIMiuMax()) + "");
		iMiuMaxTextField.setEditable(false);
		verigaPanel.add(iMiuMaxTextField, "wrap, gapx 20");

		verigaIRejimLabel.add(verigaPanel);
		verigaIRejimLabel.add(new JLabel(new ImageIcon(dvigatel.getVeriga().getVariantResource())), "wrap, gapx 20");

		dvigatelVerigaResultatiPanel.add(verigaIRejimLabel, "span,align center, wrap");

		JLabel labelResults = new JLabel("Пресметнати стойности");
		labelResults.setFont(new Font("Arial", Font.BOLD, 14));
		dvigatelVerigaResultatiPanel.add(labelResults, "span,align center, wrap");
		labelResults.setForeground(Color.blue);

		JPanel calculatedValues = new JPanel(new MigLayout("center"));
		calculatedValues.add(new JLabel("ωn"));
		JTextField wnTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		wnTextField.setText(Double.isNaN(resultService.getWn()) ? NAN_STR : rf.format(resultService.getWn()));
		wnTextField.setEditable(false);
		calculatedValues.add(wnTextField);

		calculatedValues.add(new JLabel("ωo"));
		JTextField woTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		woTextField.setText(Double.isNaN(resultService.getWo()) ? NAN_STR : rf.format(resultService.getWo()));
		woTextField.setEditable(false);
		calculatedValues.add(woTextField);

		calculatedValues.add(new JLabel("Mn"));
		JTextField mnTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		mnTextField.setText(Double.isNaN(resultService.getMn()) ? NAN_STR : rf.format(resultService.getMn()));
		mnTextField.setEditable(false);
		calculatedValues.add(mnTextField);

		calculatedValues.add(new JLabel("Ke"));
		JTextField keTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		keTextField.setText(Double.isNaN(resultService.getKe()) ? NAN_STR : rf.format(resultService.getKe()));
		keTextField.setEditable(false);
		calculatedValues.add(keTextField);

		calculatedValues.add(new JLabel("R1/r1"));
		JTextField r1r1TextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		r1r1TextField.setText(Double.isNaN(resultService.getVariant().getR1()) ? NAN_STR : rf.format(resultService
				.getVariant().getR1()));
		r1r1TextField.setEditable(false);
		calculatedValues.add(r1r1TextField);

		calculatedValues.add(new JLabel("Kc"));
		JTextField kc1TextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		kc1TextField.setText(Double.isNaN(resultService.getVariant().getKc()) ? NAN_STR : rf.format(resultService
				.getVariant().getKc()));
		kc1TextField.setEditable(false);
		calculatedValues.add(kc1TextField, "wrap");

		calculatedValues.add(new JLabel("Kов"));
		JTextField kovTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		kovTextField.setText(Double.isNaN(resultService.getKov()) ? NAN_STR : rf.format(resultService.getKov()));
		kovTextField.setEditable(false);
		calculatedValues.add(kovTextField);

		calculatedValues.add(new JLabel("Xμn"));
		JTextField xmnTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		xmnTextField.setText(Double.isNaN(resultService.getXmiuN()) ? NAN_STR : rf.format(resultService.getXmiuN()));
		xmnTextField.setEditable(false);
		calculatedValues.add(xmnTextField);

		calculatedValues.add(new JLabel("r2'"));
		JTextField r2primTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		r2primTextField.setText(Double.isNaN(resultService.getR2Prim()) ? NAN_STR
				: rf.format(resultService.getR2Prim()));
		r2primTextField.setEditable(false);
		calculatedValues.add(r2primTextField);

		calculatedValues.add(new JLabel("r2'*"));
		JTextField r2primZTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		r2primZTextField.setText(Double.isNaN(resultService.getR2PrimZvezda()) ? NAN_STR : rf.format(resultService
				.getR2PrimZvezda()));
		r2primZTextField.setEditable(false);
		calculatedValues.add(r2primZTextField);

		calculatedValues.add(new JLabel("x2'"));
		JTextField x2primTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		x2primTextField.setText(Double.isNaN(resultService.getX2Prim()) ? NAN_STR
				: rf.format(resultService.getX2Prim()));
		x2primTextField.setEditable(false);
		calculatedValues.add(x2primTextField);

		calculatedValues.add(new JLabel("x2'*"));
		JTextField x2primZTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		x2primZTextField.setText(Double.isNaN(resultService.getX2PrimZvezda()) ? NAN_STR : rf.format(resultService
				.getX2PrimZvezda()));
		x2primZTextField.setEditable(false);
		calculatedValues.add(x2primZTextField, "wrap");

		calculatedValues.add(new JLabel("KΣ"));
		JTextField kSumTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		kSumTextField.setText(Double.isNaN(resultService.getKSuma()) ? NAN_STR : rf.format(resultService.getKSuma()));
		kSumTextField.setEditable(false);
		calculatedValues.add(kSumTextField);

		calculatedValues.add(new JLabel("KΣ²"));
		JTextField kSum2TextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		kSum2TextField
				.setText(Double.isNaN(resultService.getKSuma2()) ? NAN_STR : rf.format(resultService.getKSuma2()));
		kSum2TextField.setEditable(false);
		calculatedValues.add(kSum2TextField);

		calculatedValues.add(new JLabel("R2'*"));
		JTextField R2primZTextField = new JTextField(UIDvigatel.INPUT_COLUMNS);
		R2primZTextField.setText(Double.isNaN(resultService.getGlavnoRPrimZvezda()) ? NAN_STR : rf.format(resultService
				.getGlavnoRPrimZvezda()));
		R2primZTextField.setEditable(false);
		calculatedValues.add(R2primZTextField, "wrap");

		dvigatelVerigaResultatiPanel.add(calculatedValues, "span,align center, wrap");
		printablePart.add(dvigatelVerigaResultatiPanel, "span,align center, wrap, gapy 20");

		DataSetService dataSetService = new DataSetService(resultService);

		printablePart.add(new ChartingService().prepareChartPanel(dataSetService.createResultsDataset()),
				"span,align center, wrap, gapy 20");
		JPanel buttonBar = new JPanel(new MigLayout("center"));
		container.add(buttonBar, "span,align center, wrap, gapy 20");
		buttonBar.add(printButton);
		printButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PrinterService(ResultsWindow.this.printablePart).printFrame();

			}
		});
		buttonBar.add(excelButton);
		buttonBar.add(new JLabel("име на файл"));
		buttonBar.add(excelTextField);
		excelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (excelTextField.getText().isEmpty()) {
					Border border = BorderFactory.createLineBorder(Color.red);
					excelTextField.setBorder(border);
					return;
				}
				try {
					new ExcelService().exportResultsToExcel(ResultsWindow.this.resultService, excelTextField.getText()
							+ ".xlsx");
				} catch (Exception e) {
					Border border = BorderFactory.createLineBorder(Color.red);
					excelTextField.setBorder(border);
					return;
				}
				excelTextField.setBorder(UIManager.getBorder("TextField.border"));
			}
		});

	}

}