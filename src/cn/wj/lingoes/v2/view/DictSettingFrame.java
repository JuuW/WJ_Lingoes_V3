package cn.wj.lingoes.v2.view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import cn.wj.lingoes.v2.MainFrameV3;
import cn.wj.lingoes.v2.utility.ContainerList;

import java.awt.BorderLayout;
import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.AbstractListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class DictSettingFrame extends JFrame {
	public static String key = "DictSettingFrame";
	private ContainerList conList;

	private JButton btnAddDict;

	public DictSettingFrame(ContainerList conList) {

		this.conList = conList;
		this.conList.addContainer(key, this);

		this.setSize(600, 300);
		MainFrameV3 mf = (MainFrameV3) conList.getContainer(MainFrameV3.key);
		Point p = mf.getLocation();
		this.setLocation(p.x + 100, p.y + 100);

		setTitle("修改词典顺序");

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);
		setVisible(true);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel panel_dicts = new JPanel();
		scrollPane.setColumnHeaderView(panel_dicts);
		panel_dicts.setLayout(new BoxLayout(panel_dicts, BoxLayout.X_AXIS));

		JList list_display = new JList();
		list_display.setModel(new AbstractListModel() {
			String[] values = new String[] { "display", "1", "2", "3" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel_dicts.add(list_display);

		JList list_dict = new JList();
		list_dict.setModel(new AbstractListModel() {
			String[] values = new String[] { "dict", "1", "2", "3" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel_dicts.add(list_dict);

		JList list_index = new JList();
		list_index.setModel(new AbstractListModel() {
			String[] values = new String[] { "index", "1", "2", "3" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel_dicts.add(list_index);

		JList list_sequence = new JList();
		list_sequence.setModel(new AbstractListModel() {
			String[] values = new String[] { "sequence", "1", "2", "3" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel_dicts.add(list_sequence);
		
		

		JPanel panel_north = new JPanel();
		getContentPane().add(panel_north, BorderLayout.NORTH);
		panel_north.setLayout(new BoxLayout(panel_north, BoxLayout.X_AXIS));

		btnAddDict = new JButton("+");
		panel_north.add(btnAddDict);

		JButton btnDeleteDict = new JButton("-");
		panel_north.add(btnDeleteDict);

		JButton btnMoveTop = new JButton("Very Up");
		panel_north.add(btnMoveTop);

		JButton btnMoveUp = new JButton("UP");
		panel_north.add(btnMoveUp);

		JButton btnMoveDown = new JButton("DOWN");
		panel_north.add(btnMoveDown);

		JButton btnMoveBottom = new JButton("Very Down");
		panel_north.add(btnMoveBottom);

		JButton btnDictDetail = new JButton("DIct Detail");
		panel_north.add(btnDictDetail);

		JPanel panel_south = new JPanel();
		getContentPane().add(panel_south, BorderLayout.SOUTH);

		JButton btnSave = new JButton("Save");
		panel_south.add(btnSave);

		JButton btnCancel = new JButton("Cancel");
		panel_south.add(btnCancel);

		initEvent();
	}

	private void initEvent() {

		MainFrameV3 mf = (MainFrameV3) conList.getContainer(MainFrameV3.key);
		btnAddDict.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				String fromPath = "";
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.isDirectory() || (f.isFile() && f.getName().toLowerCase().endsWith(".ld2"));
					}

					@Override
					public String getDescription() {
						return "Export File (*.ld2)";
					}
				});

				int rVal = fc.showSaveDialog(mf);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					if (!f.getName().toLowerCase().endsWith(".ld2")) {
						f = new File(f.getAbsolutePath() + ".ld2");
					}

					fromPath = f.getParent() + File.separator;
					try {
						mf.addDict(fromPath, f.getName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		});

	}

}
