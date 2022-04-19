package cn.wj.lingoes.v2.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import cn.wj.lingoes.v2.MainFrameV3;
import cn.wj.lingoes.v2.utility.ContainerList;

@SuppressWarnings("serial")
public class PanelLeftCenterSetting extends LingoesPanel {
	public static String key = "PanelLeftCenterSetting";

	public JButton btnAddDict;
	public JButton btnSetDictDisplay;
	public boolean setDictDisplayFrameOpen;
	public boolean setDictSettingFrameOpen;
	public boolean setDictIndexFrameOpen;
	public JButton btnDictSetting;
	public JButton btnSetDictIndex;

	public PanelLeftCenterSetting(ContainerList conList) {
		super(conList);
		setDictSettingFrameOpen = false;
		setDictIndexFrameOpen = false;
		setDictDisplayFrameOpen = false;
	}

	@Override
	protected void initView() {

		// MainFrame mf = (MainFrame) conList.getContainer(MainFrame.key);

		// this.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		btnAddDict = new JButton("Add Dict");
		btnAddDict.setSize(this.getWidth(), 20);
		btnDictSetting = new JButton("Dict Setting");
		btnDictSetting.setSize(this.getWidth(), 20);
		btnSetDictIndex = new JButton("Set Dict Index");
		btnSetDictIndex.setSize(this.getWidth(), 20);
		btnSetDictDisplay = new JButton("Set Dict Display");
		btnSetDictDisplay.setSize(this.getWidth(), 20);
		// this.add(btnAddDict);
		this.add(btnDictSetting);
	    this.add(btnSetDictIndex);
		this.add(btnSetDictDisplay);
	}

	@Override
	protected void register() {

		conList.addContainer(PanelLeftCenterSetting.key, this);

	}

	@Override
	protected void initEvent() {
		MainFrameV3 mf = (MainFrameV3) conList.getContainer(MainFrameV3.key);

		btnDictSetting.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!setDictSettingFrameOpen) {
					setDictSettingFrameOpen = true;
					DictSettingFrame dictSettingFrame = new DictSettingFrame(conList);

					dictSettingFrame.setVisible(true);
					dictSettingFrame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							setDictSettingFrameOpen = false;
						}
					});
//	                } else {
//	                    JOptionPane.showMessageDialog(null, languageApp.getValue("cant_find"));
				}
			}
		});

		btnSetDictIndex.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!setDictIndexFrameOpen) {
					setDictIndexFrameOpen = true;
					DictIndexPanel SetIndexPanel = new DictIndexPanel(conList);

					SetIndexPanel.setVisible(true);
					// loại bỏ tất cả highlight khi tắt findPanel
					SetIndexPanel.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
//							JEditorPane curEpWord = (JEditorPane) scpCenterCenter.getViewport().getView();
//							curEpWord.getHighlighter().removeAllHighlights();
							setDictIndexFrameOpen = false;
						}
					});
//	                } else {
//	                    JOptionPane.showMessageDialog(null, languageApp.getValue("cant_find"));
				}
			}
		});


	}

}
