package cn.wj.lingoes.v2.view;

import java.awt.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.wj.lingoes.v2.MainFrameV3;
import cn.wj.lingoes.v2.utility.ContainerList;

@SuppressWarnings("serial")
public class DictIndexPanel extends javax.swing.JFrame {

	public static String key = "DictIndexPanel";
	private ContainerList conList;

	public JButton btnSave;
	public JButton btnCancel;
	public JComboBox<String> comboBox_cn;
	public JComboBox<String> comboBox_en;

	public DictIndexPanel(ContainerList conList) {

		this.conList = conList;
		this.conList.addContainer(key, this);

		initView();
		initEvent();

	}

	private void initView() {
		
		this.setSize(600, 300);
		
		 MainFrameV3 mf = (MainFrameV3)conList.getContainer(MainFrameV3.key);
	        Point p = mf.getLocation();
	        this.setLocation(p.x + 100, p.y + 100);
		
		
		setTitle("Set Index");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel lblNewLabel = new JLabel("Index for English");
		panel.add(lblNewLabel);

		comboBox_en = new JComboBox<String>();
		panel.add(comboBox_en);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		JLabel lblNewLabel_1 = new JLabel("Index for Chinese");
		panel_1.add(lblNewLabel_1);

		comboBox_cn = new JComboBox<String>();
		panel_1.add(comboBox_cn);

		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		btnCancel = new JButton("Cancel");
		panel_2.add(btnCancel);

		btnSave = new JButton("Save");
		panel_2.add(btnSave);


		for (int num = 0; num < MainFrameV3.dictTotleSupported; num++) {
//			if (!dictname.isBlank()) {
//				comboBox_cn.addItem(dictname);
//				comboBox_en.addItem(dictname);
//			}
		}
//		comboBox_cn.setSelectedItem(mf.props.getProperty("dict_index_cn"));
//		comboBox_en.setSelectedItem(mf.props.getProperty("dict_index"));

	}

	private void initEvent() {
		btnSave.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {

			}
		});

		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dispose();
			}
		});

	}

}
