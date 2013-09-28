package tu.sofia.aez.ui;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tu.sofia.aez.om.DSVarianti;
import tu.sofia.aez.om.RDSPOTVarianti;
import tu.sofia.aez.om.RejimNaSpirane;
import tu.sofia.aez.ui.VariantsPanel.Variants;
import tu.sofia.aez.util.ChartingService;
import tu.sofia.aez.util.DataSetService;
import net.miginfocom.swing.MigLayout;

public class ResultsWindow extends JFrame {

	private static final long serialVersionUID = 7683619577604904507L;
	private UIDvigatel dvigatel;
	private RejimNaSpirane rejim;
	private boolean isDs = false;

	public ResultsWindow(UIDvigatel uiDvigatel, RejimNaSpirane rejim, VariantsPanel variants) {
		dvigatel = new UIDvigatel(uiDvigatel.getDvigatel());
		setLayout(new MigLayout("center"));
		setResizable(false);
		setSize(UIConstants.RESULT_WIDTH, UIConstants.RESULT_HEIGHT);
		setTitle("Резултати");
		setVisible(true);

		JLabel title = new JLabel("Резултати от изчисленията");
		title.setForeground(Color.blue);
		title.setFont(new Font("Arial", Font.BOLD, 22));
		add(title, "span,align center, wrap, gapy 20");

		JPanel selectedDvigatelPanel = dvigatel.getPanel(false, true);
		JLabel labelDvigatel = new JLabel("Избран двигател:");
		labelDvigatel.setForeground(Color.blue);
		labelDvigatel.setFont(new Font("Arial", Font.BOLD, 14));
		selectedDvigatelPanel.add(labelDvigatel, "pos 37 5");

		JLabel labelRejim = new JLabel("Избран режим:");
		labelRejim.setFont(new Font("Arial", Font.BOLD, 14));
		selectedDvigatelPanel.add(labelRejim, "wrap");
		labelRejim.setForeground(Color.blue);

		JLabel labelRejimCurrent = new JLabel(rejim.getName());
		selectedDvigatelPanel.add(labelRejimCurrent, "wrap");

		URL variantResource = null;
		if (Variants.DS.equals(variants.getVariant()) || Variants.RDSS.equals(variants.getVariant())) {
			isDs = true;
			variantResource = variants.getDSVariant().getResource();
		} else {
			isDs = false;
			variantResource = variants.getRDSPOTVariant().getResource();
		}
		selectedDvigatelPanel.add(new JLabel(new ImageIcon(variantResource)), "wrap");
		add(selectedDvigatelPanel, "span,align center, wrap, gapy 20");

		DataSetService dataSetService = new DataSetService();
		add(new ChartingService().prepareChartPanel(dataSetService.createSampleDataset()),
				"span,align center, wrap, gapy 20");
	}

}
