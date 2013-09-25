package tu.sofia.aez.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;
import tu.sofia.aez.om.CondenzatornoSpirane;
import tu.sofia.aez.om.DS;
import tu.sofia.aez.om.RDSPOT;
import tu.sofia.aez.om.RDSS;
import tu.sofia.aez.om.RejimNaSpirane;
import tu.sofia.aez.ui.VariantsPanel.Variants;

public class MainWindow {

	private JFrame frame = new JFrame("ТУ София - динамично спиране на асинхронен двигател");
	private JPanel mainPanel = new JPanel();
	private VariantsPanel variantsPanel = new VariantsPanel();
	private JLabel title = new JLabel("Технически унивеситет - София");
	private RejimNaSpirane rejimNaSpirane;
	public static final String DS_RADIO = "DS";
	public static final String RDSS_RADIO = "RDSS";
	public static final String RDSPOT_RADIO = "RDSPOT";
	public static final String CONDENZATORNO = "CONDENZATORNO";
	private ButtonGroup rejimButtonGroup = new ButtonGroup();
	private JRadioButton dsRadio = new JRadioButton(DS_RADIO);
	private JRadioButton rdssRadio = new JRadioButton(RDSS_RADIO);
	private JRadioButton rdspotRadio = new JRadioButton(RDSPOT_RADIO);
	private JRadioButton condenzatornoRadio = new JRadioButton(CONDENZATORNO);
	private RejimChangedListener rejimChangedListener = new RejimChangedListener();

	public MainWindow() {
		/*
		 * 
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * panel.setLayout(new MigLayout("center")); panel.add(title, "wrap");
		 * DataSetService dataSetService = new DataSetService(); panel.add(new
		 * ChartingService
		 * ().prepareChartPanel(dataSetService.createSampleDataset()), "wrap");
		 * panel.add(new UIDvigatel().getReadOnlyPanel(),"wrap");
		 */

		// FRAME PROPERTIES
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new MigLayout("center"));
		title.setFont(new Font("Arial", Font.PLAIN, 24));
		mainPanel.setLayout(new MigLayout("center"));
		frame.setResizable(false);
		frame.setSize(UIConstants.WINDOW_WIDTH, UIConstants.WINDOW_HEIGHT);
		frame.setVisible(true);

		// RADIO BUTONI ZA IZBOR NA REJIM
		rejimButtonGroup.add(dsRadio);
		rejimButtonGroup.add(rdssRadio);
		rejimButtonGroup.add(rdspotRadio);
		rejimButtonGroup.add(condenzatornoRadio);
		dsRadio.addActionListener(rejimChangedListener);
		rdssRadio.addActionListener(rejimChangedListener);
		rdspotRadio.addActionListener(rejimChangedListener);
		condenzatornoRadio.addActionListener(rejimChangedListener);
		mainPanel.add(title, "span,align center, wrap");
		mainPanel.add(dsRadio);
		mainPanel.add(rdssRadio);
		mainPanel.add(rdspotRadio);
		mainPanel.add(condenzatornoRadio);
		frame.add(mainPanel, "wrap");

		// VARIANTI ZA REJIMA
		frame.add(variantsPanel);

	}

	public class RejimChangedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == DS_RADIO) {
				if (rejimNaSpirane == null || !(rejimNaSpirane instanceof DS)) {
					switchToDSRejim();
					System.out.println(DS_RADIO + " selected.");
				}
			} else if (e.getActionCommand() == RDSS_RADIO) {
				if (rejimNaSpirane == null || !(rejimNaSpirane instanceof RDSS)) {
					switchToRDSSRejim();
					System.out.println(RDSS_RADIO + " selected.");
				}
			} else if (e.getActionCommand() == CONDENZATORNO) {
				if (rejimNaSpirane == null || !(rejimNaSpirane instanceof CondenzatornoSpirane)) {
					switchToConednzatornoSpirane();
					System.out.println(RDSS_RADIO + " selected.");
				}
			} else {
				if (rejimNaSpirane == null || !(rejimNaSpirane instanceof RDSPOT)) {
					switchToRDSPOTRejim();
					System.out.println(RDSPOT_RADIO + " selected.");
				}
			}

		}

	}

	private void redrawVariantsPanel() {
		if (rejimNaSpirane instanceof DS) {
			if (Variants.RDSPOT.equals(variantsPanel.getVariant())) {
				variantsPanel.setVariant(Variants.DS);
				variantsPanel.refreshPanel();
			} else
				variantsPanel.setVariant(Variants.DS);
		} else if (rejimNaSpirane instanceof RDSS) {

			if (Variants.RDSPOT.equals(variantsPanel.getVariant())) {
				variantsPanel.setVariant(Variants.RDSS);
				variantsPanel.refreshPanel();
			} else
				variantsPanel.setVariant(Variants.RDSS);
		} else if (rejimNaSpirane instanceof RDSPOT) {
			if (!Variants.RDSPOT.equals(variantsPanel.getVariant())) {
				variantsPanel.setVariant(Variants.RDSPOT);
				variantsPanel.refreshPanel();
			} else
				variantsPanel.setVariant(Variants.RDSPOT);
		}
	}

	private void switchToDSRejim() {
		rejimNaSpirane = new DS();
		redrawVariantsPanel();
	}

	private void switchToConednzatornoSpirane() {
		rejimNaSpirane = new CondenzatornoSpirane();
		redrawVariantsPanel();
	}

	private void switchToRDSSRejim() {
		rejimNaSpirane = new RDSS();
		redrawVariantsPanel();
	}

	private void switchToRDSPOTRejim() {
		rejimNaSpirane = new RDSPOT();
		redrawVariantsPanel();
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
