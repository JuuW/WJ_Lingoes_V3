/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.wj.lingoes.v2.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.JTextComponent;

import cn.wj.lingoes.v2.MainFrameV3;
import cn.wj.lingoes.v2.database.SQLiteJDBC;
import cn.wj.lingoes.v2.entity.Dict;
import cn.wj.lingoes.v2.entity.DictItem;
import cn.wj.lingoes.v2.utility.ContainerList;
import cn.wj.lingoes.v2.utility.DownloadUtils;
import cn.wj.lingoes.v2.utility.MusicPlayer;
import cn.wj.lingoes.v2.utility.ScrollUtil;
import javazoom.jl.player.Player;

/**
 *
 * @author USER
 */
@SuppressWarnings({ "serial", "unused" })
public class PanelCenter extends LingoesPanel {

	public static String key = "PanelCenter";

	public static String VOICE_US = "0";
	public static String VOICE_UK = "1";

	public boolean wordFound = false;

	public boolean searchOpenned;
	public boolean setDictDisplayFrameOpen;

	private JButton btnSpeakerUS;
	private JButton btnFind;
	private JButton btnMute;
	private JButton btnSpeakerUK;
	private JButton btnStartPage;
	//private JButton btnDiaplayDict;
	private JPanel pnlTop;
	public javax.swing.JScrollBar vbar;
	public String currentWord = "";
	

	private JTextPane pnDict;

	private JScrollPane scpCenterCenter;

	public PanelCenter(ContainerList conList) {
		super(conList);
		setDictDisplayFrameOpen = false;
		searchOpenned = false;
	}

	protected void initView() {
		this.setBackground(new Color(255, 255, 240));
		pnlTop = new JPanel();
		btnSpeakerUK = new JButton("UK");
		btnSpeakerUS = new JButton("US");
		// btnSave = new JButton();
		btnMute = new JButton("unMute");
		btnFind = new JButton();
		btnStartPage = new JButton();
		pnDict = new JTextPane();
		pnDict.setEditable(false);
		pnDict.setContentType("text/html;charset=utf-8");
		MainFrameV3 mf = (MainFrameV3) this.conList.getContainer(MainFrameV3.key);
		if (mf.dictList.size() == 0) {
			pnDict.setText("No dict found, please add dict file in the 'dicts' folder.");
		}
		currentWord = "";

		scpCenterCenter = new JScrollPane();
		scpCenterCenter.getVerticalScrollBar().setUnitIncrement(16);

		setLayout(new java.awt.BorderLayout());

		pnlTop.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(153, 153, 153)));

		btnSpeakerUK.setIcon(new ImageIcon(getClass().getResource("/resource/img/icon-speaker-16px.png"))); // NOI18N
		btnSpeakerUK.setContentAreaFilled(false);
		btnSpeakerUK.setFocusPainted(false);

		btnSpeakerUS.setIcon(new ImageIcon(getClass().getResource("/resource/img/icon-speaker-16px.png"))); // NOI18N
		btnSpeakerUS.setContentAreaFilled(false);
		btnSpeakerUS.setFocusPainted(false);

		btnMute.setIcon(new ImageIcon(getClass().getResource("/resource/img/icon-speaker-16px.png"))); // NOI18N
		btnMute.setContentAreaFilled(false);
		btnMute.setFocusPainted(false);

		btnFind.setIcon(new ImageIcon(getClass().getResource("/resource/img/icon-find-16px.png"))); // NOI18N
		btnFind.setContentAreaFilled(false);
		btnFind.setFocusPainted(false);


		btnStartPage.setIcon(new ImageIcon(getClass().getResource("/resource/img/icon-homepage2-16px.png"))); // NOI18N
		btnStartPage.setContentAreaFilled(false);
		btnStartPage.setFocusPainted(false);

		GroupLayout pnlTopLayout = new GroupLayout(pnlTop);
		pnlTop.setLayout(pnlTopLayout);
		pnlTopLayout.setHorizontalGroup(pnlTopLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(pnlTopLayout.createSequentialGroup().addGap(20, 20, 20)
						.addComponent(btnSpeakerUS, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addGap(5, 5, 5)
						.addComponent(btnSpeakerUK, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnMute, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addGap(5, 5, 5)
						.addComponent(btnFind, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnStartPage, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)));
		pnlTopLayout.setVerticalGroup(pnlTopLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(pnlTopLayout.createSequentialGroup().addGap(0, 0, 0)
						.addGroup(pnlTopLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(btnSpeakerUS, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSpeakerUK, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnMute, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnFind, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnStartPage, GroupLayout.PREFERRED_SIZE, 24,
										GroupLayout.PREFERRED_SIZE))));

		add(pnlTop, BorderLayout.NORTH);
		pnlTop.setBackground(new Color(255, 255, 240));
		add(scpCenterCenter, BorderLayout.CENTER);
		btnStartPage.doClick();

		scpCenterCenter.add(pnDict);
	}

	@Override
	protected void register() {

		conList.addContainer(PanelCenter.key, this);
	}

	public void displayHTML(String html) throws IOException {

		scpCenterCenter.setViewportView(pnDict);
		pnDict.setText(html);
		pnDict.setCaretPosition(0);

	}

	public void display(String word) throws Exception {
		display(word, false);

	}

	
	public void display(String word, boolean refresh) throws Exception {
		MainFrameV3 mf = (MainFrameV3) conList.getContainer(MainFrameV3.key);
		PanelLeftCenterList plist = (PanelLeftCenterList) conList.getContainer(PanelLeftCenterList.key);
		PanelTop pt = (PanelTop) conList.getContainer(PanelTop.key);
		String dict_name_with_index = "";

		if (!refresh) {
			if (currentWord.equals(word)) {
				return;
			} else {
				mf.LastWord = currentWord;
				currentWord = word;
			}
		}

		pnDict.setText("");

		String dictHtml = "";
		String finalHtml = "";

		if (word.isBlank()) {
			return;
		}

		SQLiteJDBC jdbc = new SQLiteJDBC(mf.appDBPath);
		for (Object item : mf.dictList.toArray()) {
			Dict d = (Dict) item;
			DictItem dictItem = new DictItem(d.getName(), word, "");
			jdbc.selectDictItem(d,dictItem);
			if (!dictItem.getDictValue().isBlank()) {
				if (d.getIsDisplay()==Dict.FLAG_YES) {
					String namewithoutextension = d.getName().split("[.]")[0];
					System.out.println(dictItem.getDictValue());
					dictHtml += mf.dictLayout.replace("@dictContent", dictItem.getDictValue().replace("''", "'"))
							.replaceAll("@dictName", namewithoutextension);
				}
			}
		}
		jdbc.closeConnection();
		
		dict_name_with_index = determineIndexLang(word);

		if (!dict_name_with_index.equals(mf.index_current)) {
			mf.index_current = dict_name_with_index;
			plist.list.removeAll();
			
			ArrayList<Object> index = mf.indexList.get(dict_name_with_index);
			if(index != null) {
				plist.list.setListData(index.toArray());
			}else {
				plist.list.setListData(new String[]{"Index for " +dict_name_with_index+" not found!" });
			}
		}

		if (dictHtml.isBlank()) {

			if (mf.dictList.size() != 0) {
				finalHtml = "<html><h1>Word <font color='red'>" + word + "</font> is not found!</h1></html>";
				wordFound = false;
			}

		} else {
//			pnDict.setText(mf.dictHtml.replace("@word", word).replace("@dicts", dictHtml));
			finalHtml = mf.dictHtml.replace("@word", word).replace("@dicts", dictHtml);
			wordFound = true;
			playVoice(currentWord, VOICE_US, wordFound);
			pt.addItemindroplist(word);
		}

		displayHTML(finalHtml);

//		scpCenterCenter.setViewportView(pnDict);
//		pnDict.setCaretPosition(0);

		// mf.splitPane.setDividerLocation(180);

		plist.list.setSelectedValue(furryMatch(word), true);

	}

	private String determineIndexLang(String word) {
		if(checkcountname(word)) {
			return MainFrameV3.LANG_CN;
		}else {
			return MainFrameV3.default_index_lang;
		}
	}

	private String furryMatch(String word) {

		PanelLeftCenterList plist = (PanelLeftCenterList) conList.getContainer(PanelLeftCenterList.key);
		int size = plist.list.getModel().getSize();

		int leng = word.length();
		String subword = "";
		String keyinthelist = "";
		for (int i = leng; i > 0; i--) {
			subword = word.substring(0, i);
			for (int j = 0; j < size; j++) {
				keyinthelist = plist.list.getModel().getElementAt(j).toString();
				if (keyinthelist.startsWith(subword)) {
					return keyinthelist;
				}
			}
		}
		return keyinthelist;
	}

	@Override
	protected void initEvent() {

		this.pnDict.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				// 直接双击鼠标, 得到系统自动获取的单词
				if (e.getClickCount() == 2) {
					try {

						String selectedText = pnDict.getSelectedText();
						PanelTop pt = (PanelTop) conList.getContainer(PanelTop.key);
						MainFrameV3 mf = (MainFrameV3) conList.getContainer(MainFrameV3.key);

						if (selectedText != null) {
							if (!selectedText.isBlank()) {
								String word = selectedText.toLowerCase();
								mf.LastWord = pt.tfWord.getText();
								pt.addItemindroplist(word);
								pt.tfWord.setText(word);
								pt.cbbWord.setSelectedItem(word);
								pt.doWordSearch(word);
							}
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
// 先选择文本, 然后单击鼠标
				if (e.getClickCount() == 1) {
					try {
						JTextPane textPane = (JTextPane) e.getComponent();

						String selectedText = pnDict.getSelectedText();
						if (selectedText == null) {
							return;
						}

						if (selectedText.isBlank()) {
							return;
						}

						PanelTop pt = (PanelTop) conList.getContainer(PanelTop.key);
						MainFrameV3 mf = (MainFrameV3) conList.getContainer(MainFrameV3.key);

						if (selectedText != null) {
							if (!selectedText.isBlank()) {

								mf.LastWord = pt.tfWord.getText();
								pt.addItemindroplist(selectedText);
								pt.tfWord.setText(selectedText);
								pt.cbbWord.setSelectedItem(selectedText);
								pt.doWordSearch(selectedText);
							}
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			}
		});

		btnFind.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				JTextComponent tc = (JTextComponent) scpCenterCenter.getViewport().getView();
				if (tc == null) {
					return;
				}

				if (!searchOpenned) {
					searchOpenned = true;

					FindPanel findPanel = new FindPanel(conList, tc);

					findPanel.setVisible(true);
					// remove all highlights when findPanel is turned off
					findPanel.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							searchOpenned = false;
							JEditorPane curEpWord = (JEditorPane) scpCenterCenter.getViewport().getView();
							curEpWord.getHighlighter().removeAllHighlights();

						}
					});
				}
			}
		});

		this.btnSpeakerUK.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				playVoice(currentWord, VOICE_UK, wordFound);

			}
		});

		this.btnMute.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				MainFrameV3 mf = (MainFrameV3) conList.getContainer(MainFrameV3.key);

				if (mf.muted) {
					mf.muted = false;
					btnMute.setText("unMute");
					btnMute.setIcon(new ImageIcon(getClass().getResource("/resource/img/icon-speaker-16px.png"))); // NOI18N

				} else {
					mf.muted = true;
					btnMute.setText("Muted");
					btnMute.setIcon(new ImageIcon(getClass().getResource("/resource/img/icon-mute-16px.png"))); // NOI18N

				}

			}
		});
		this.btnSpeakerUS.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				playVoice(currentWord, VOICE_US, wordFound);

			}
		});
		this.btnStartPage.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PanelCenter pc = (PanelCenter) conList.getContainer(PanelCenter.key);
				MainFrameV3 mf = (MainFrameV3) conList.getContainer(MainFrameV3.key);

				try {
					pc.displayHTML(mf.start_page_html);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}

	private void playVoice(String word, String voiceType, boolean wordFound) {
		MainFrameV3 mf = (MainFrameV3) conList.getContainer(MainFrameV3.key);
		if (mf.muted) {
			return;
		}
		if (checkcountname(currentWord)) {
			voiceType = "CN";
		}
		if (!wordFound) {
			return;
		}
		String voiceType1 = voiceType;
		new Thread(new Runnable() {
			@Override
			public void run() {
				String path = mf.appMp3Dir + voiceType1 + "--" + currentWord + ".mp3";
				File file = new File(path);
				if (!file.exists()) {
					if (voiceType1.equals("CN")) {
						downloadMp3Cn(word, voiceType1, mf.appMp3Dir);
					} else {
						downloadMp3En(word, voiceType1, mf.appMp3Dir);
					}
				}
				MusicPlayer.play(path);
			}
		}).start();
	}

	public void downloadMp3En(String word, String voiceType, String path) {

		String baseUrl = "http://dict.youdao.com/dictvoice?type=" + voiceType + "&audio=" + word;
		DownloadUtils downloadUtils = new DownloadUtils(baseUrl, voiceType + "--" + word, "mp3", path);

		try {
			downloadUtils.httpDownload();
			System.out.println(word + " 下载成功");
		} catch (Exception e) {
			System.out.println(word + " 下载失败");
			// e.printStackTrace();
		}

	}

	public void downloadMp3Cn(String word, String voiceType, String path) {

		String baseUrl = "";
		try {
			baseUrl = "https://fanyi.sogou.com/reventondc/synthesis?text=" + URLEncoder.encode(word, "UTF-8")
					+ "&speed=1&lang=zh-CHS&from=translateweb&speaker=6";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		DownloadUtils downloadUtils = new DownloadUtils(baseUrl, voiceType + "--" + word, "mp3", path);

		try {
			downloadUtils.httpDownload();
			System.out.println(word + " 下载成功");
		} catch (Exception e) {
			System.out.println(word + " 下载失败");
		}

	}

	public void displayOnline(String word, String engine) throws IOException {
		String url = "";

		switch (engine) {
		case "有道词典":
			// url = "http://dict.youdao.com/dictvoice?type=0&audio=" + word;
			url = "https://www.youdao.com/w/eng/" + word + "/#keyfrom=dict2.index";
			break;
		case "金山词霸":
			url = "https://www.iciba.com/word?w=" + word;
			break;
		case "百度":
			url = "https://www.baidu.com/s?wd=" + word;
			break;
		case "中文维基百科":
			url = "https://www.wanweibaike.net/wiki-" + word;
			break;
		}
		pnDict.setText("");
		URL myurl = new URL(url);
		pnDict.setPage(myurl);
		scpCenterCenter.setViewportView(pnDict);
		pnDict.repaint();

	}
	public boolean checkcountname(String word) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(word);
		if (m.find()) {
			return true;
		}
		return false;
	}
}
