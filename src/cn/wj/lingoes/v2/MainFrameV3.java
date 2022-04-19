package cn.wj.lingoes.v2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cn.wj.lingoes.v2.database.SQLiteJDBC;
import cn.wj.lingoes.v2.entity.Dict;
import cn.wj.lingoes.v2.utility.ContainerList;
import cn.wj.lingoes.v2.view.PanelBottom;
import cn.wj.lingoes.v2.view.PanelCenter;
import cn.wj.lingoes.v2.view.PanelLeft;
import cn.wj.lingoes.v2.view.PanelLeftCenterList;
import cn.wj.lingoes.v2.view.PanelLeftCenterSetting;
import cn.wj.lingoes.v2.view.PanelTop;

public class MainFrameV3 extends JFrame {

	public final static String key = "MainFrame";
	public final static int DICT_HEADER_EXIST = -1;
	public final static int DICT_HEADER_ADDED_FAILD = -2;
	public final static String appName = "Lingoes for MacOS V3";
	public final static String FILE_DICT_LAYOUT = "/resource/dictLayout.html";
	public final static String FILE_DICT_HTML = "/resource/dictHtml.html";
	public final static String FILE_START_PAGE = "/resource/index.html";

	public final static String LANG_EN = "en";
	public final static String LANG_CN = "cn";

	public final static int dictTotleSupported = 30;

	public static String default_index_lang = LANG_EN;

	public String appRootDir;
	public String appMp3Dir;
	public String appDBPath;
	public String LastWord;
	public boolean muted;

	public String dictLayout;
	public String dictHtml;
	public ArrayList<Dict> dictList = new ArrayList<Dict>();
	public HashMap<String, ArrayList<Object>> indexList;

	public String index_current;
	public String start_page_html;

	private PanelTop pnlTop;
	private JPanel pnlBottom;
	private JSplitPane splitPane;
	private JPanel pnlCenter;
	private JPanel pnlLeft;
	private ContainerList conList;

	private static final long serialVersionUID = 8688253556545208400L;

	public MainFrameV3() throws Exception {

		conList = new ContainerList();
		conList.addContainer(MainFrameV3.key, this);
		muted = false;
		indexList = new HashMap<String, ArrayList<Object>>();
		index_current = "";
		initApp();
		initView();
		initDict();
		initEvent();
		refreshView();
	}

	private void initEvent() {
		this.addComponentListener((ComponentListener) new ComponentAdapter() {

			public void componentResized(ComponentEvent e) {
				splitPane.setDividerLocation(180);

			}

		});
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

//				if(e.isMetaDown()&&e.getKeyCode()==KeyEvent.VK_V) {
//					PanelTop pt = (PanelTop)conList.getContainer(PanelTop.key);
//					String clipstr = getSysClipboardText();
//					
//					System.out.println("command+v");
//					System.out.println("clipstr=" + clipstr);
//					pt.tfWord.setText(clipstr);
//					pt.cbbWord.setSelectedItem(clipstr);
//					pt.btnSearch.doClick();
//				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

		});

	}

	private void refreshView() {

		pnlTop.cbbWord.requestFocus();
		PanelLeftCenterList plist = (PanelLeftCenterList) conList.getContainer(PanelLeftCenterList.key);
		PanelCenter pc = (PanelCenter) conList.getContainer(PanelCenter.key);

		for (Object o : this.dictList.toArray()) {
			if (((Dict) o).getIsDefaultIndex() == Dict.FLAG_YES) {
				plist.list.setListData(((Dict) o).getDictindex().toArray());
			}
		}

		try {
			pc.displayHTML(this.start_page_html);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void initApp() throws Exception {

		// initial files and dir
		appRootDir = System.getProperty("user.home") + File.separator + appName;
		appMp3Dir = appRootDir + File.separator + "mp3" + File.separator;
		appDBPath = appRootDir + File.separator + "lingoes.db";

		File rootdir = new File(appMp3Dir);
		if (!rootdir.exists()) {
			try {
				rootdir.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// initial DB
		if (!new File(appDBPath).exists()) {
			SQLiteJDBC jdbc = new SQLiteJDBC(appDBPath);
			jdbc.createTable();
			jdbc.closeConnection();
		}

		// initial html templates
		InputStream ips = MainFrameV3.class.getResourceAsStream(MainFrameV3.FILE_DICT_LAYOUT);
		BufferedReader bReader = new BufferedReader(new InputStreamReader(ips));
		StringBuilder sb = new StringBuilder();
		String s = "";
		while ((s = bReader.readLine()) != null) {
			sb.append(s);
		}
		bReader.close();
		ips.close();
		dictLayout = sb.toString();

		InputStream ips2 = MainFrameV3.class.getResourceAsStream(MainFrameV3.FILE_DICT_HTML);
		BufferedReader bReader2 = new BufferedReader(new InputStreamReader(ips2));
		StringBuilder sb2 = new StringBuilder();
		String s2 = "";
		while ((s2 = bReader2.readLine()) != null) {
			sb2.append(s2);
		}
		bReader2.close();
		ips2.close();
		dictHtml = sb2.toString();

		InputStream ips4 = MainFrameV3.class.getResourceAsStream("/resource/index.html");
		BufferedReader bReader4 = new BufferedReader(new InputStreamReader(ips4));
		StringBuilder sb4 = new StringBuilder();
		String s4 = "";
		while ((s4 = bReader4.readLine()) != null) {
			sb4.append(s4);
		}
		bReader4.close();
		ips4.close();
		start_page_html = sb4.toString();

	}

	public void initDict() {

		HashMap<String, String> indexlist = new HashMap<String, String>();
		SQLiteJDBC jdbc = new SQLiteJDBC(appDBPath);
		try {
			jdbc.c.setAutoCommit(false);

			Statement stmt_select_index = jdbc.c.createStatement();
			String sql_select_dict_index = "SELECT * FROM dict_index;";

			ResultSet rs_select_dict_index = stmt_select_index.executeQuery(sql_select_dict_index);

			while (rs_select_dict_index.next()) {
				indexlist.put(String.valueOf(rs_select_dict_index.getInt("dict_id")),
						rs_select_dict_index.getString("lang"));
			}
			rs_select_dict_index.close();
			stmt_select_index.close();

			Statement stmt_select = jdbc.c.createStatement();
			String sql_select_dict_header = "SELECT * FROM dict_header order by dict_sequence;";

			ResultSet rs = stmt_select.executeQuery(sql_select_dict_header);

			while (rs.next()) {
				Dict dict = new Dict();
				dict.setId(rs.getInt("id"));
				dict.setName(rs.getString("name"));
				dict.setSequence(rs.getInt("dict_sequence"));
				dict.setType(rs.getString("dict_type"));
				dict.setIsDisplay(rs.getString("is_display").charAt(0));
				dictList.add(dict);
				String lang = indexlist.get(String.valueOf(dict.getId()));
				if (lang != null) {
				///	buildDictIndex(dict);
					this.indexList.put(lang, dict.getDictindex());
				}
			}
			rs.close();
			stmt_select.close();
			jdbc.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			jdbc.closeConnection();
		}

		for (Object o : dictList.toArray()) {
			String lang = indexlist.get(String.valueOf(((Dict) o).getId()));
			if (lang != null) {
				buildDictIndex((Dict) o);
				this.indexList.put(lang, ((Dict) o).getDictindex());
			}

		}

	}

	private void buildDictIndex(Dict dict) {
		SQLiteJDBC jdbc = new SQLiteJDBC(appDBPath);
		try {
			jdbc.c.setAutoCommit(false);
			Statement stmt_select = jdbc.c.createStatement();
			String sql_select_dict_header = "SELECT word FROM dict where dict_id = " + dict.getId() + " ;";

			ResultSet rs = stmt_select.executeQuery(sql_select_dict_header);
			while (rs.next()) {
				dict.getDictindex().add(rs.getString("word"));
			}
			rs.close();
			stmt_select.close();
			jdbc.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			jdbc.closeConnection();
		}
	}

//	public void loadDict() throws Exception {
//		String dir = appDictDir;
//		String filename = "";
//
//		for (int dictindex = 0; dictindex < MainFrame.dictTotleSupported; dictindex++) {
//			filename = props.getProperty("dict_" + dictindex);
//			if (filename != null) {
//				if (!filename.isBlank()) {
//					File file = new File(dir + filename.trim());
//					if (file.exists()) {
//						int id = addDict(dir, filename.trim());
//						if(id == MainFrame.DICT_HEADER_EXIST) {
//							
//						}else if(id == MainFrame.DICT_HEADER_EXIST) {
//							
//						}
//					} else {
//						System.out.println(filename + "在词典列表中存在, 但是未找到词典文件");
//					}
//
//				}
//			}
//
//		}

//		ArrayList<Dict> dictList = loadDictHeader();

//		SQLiteJDBC jdbc = new SQLiteJDBC(appDBPath);
//		try {
//			jdbc.c.setAutoCommit(false);
//			Statement stmt_select1 = jdbc.c.createStatement();
//			String sql_select_dict_header1 = "SELECT * FROM dict_header WHERE name = '" + dictName + "';";
//
//			ResultSet rs1 = stmt_select1.executeQuery(sql_select_dict_header1);
//
//			while (rs1.next()) {
//				rs1.close();
//				stmt_select1.close();
//				jdbc.closeConnection();
//				return MainFrame.DICT_HEADER_EXIST;
//			}
//			rs1.close();
//			stmt_select1.close();
//
//			Statement stmt_insert = jdbc.c.createStatement();
//
//			String sql_insert_dict_header = "INSERT INTO dict_header (NAME,dict_type,is_display,dict_sequence) VALUES ('"
//					+ dictName + "', '" + dictType + "', '" + isDisplay + "','" + dict_sequence + "' );";
//
//			stmt_insert.execute(sql_insert_dict_header);
//			stmt_insert.close();
//			jdbc.c.commit();
//
//			Statement stmt_select2 = jdbc.c.createStatement();
//			String sql_select_dict_header2 = "SELECT * FROM dict_header WHERE name = '" + dictName + "';";
//
//			ResultSet rs2 = stmt_select2.executeQuery(sql_select_dict_header2);
//
//			while (rs2.next()) {
//				int id = rs2.getInt("id");
//				rs2.close();
//				stmt_select2.close();
//				jdbc.closeConnection();
//				return id;
//			}
//			rs2.close();
//			stmt_select2.close();
//			jdbc.closeConnection();
//		} catch (Exception e) {
//			e.printStackTrace();
//			jdbc.closeConnection();
//		}

//		if (dir.isEmpty()) {
//			System.out.println("配置文件找到词典目录");
//		}

//		String[] filelist = new File(dir).list();
//		int i = 0;
//		if (filelist.length == 0) {
//			return;
//		}
//		for (String filename : filelist) {
//			if (filename.endsWith(".ld2")) {
//				i += 1;
//				addDict(dir, filename);
//			}
//		}

//		System.out.println("=====解析词典完成");
//
//	}

	public int addDict(String dir, String filename) throws Exception {
		Dict dict = new Dict(filename);

		dict.setFilePath(dir + filename);
		dictList.add(dict);
		System.out.println(".....开始解析词典: " + dict.getName());

		int id = addDictHeader(dict);

		if (id > 0) {
			dict.setId(id);
			addDictItems(dict);
			if (dict.getIsIndex() == Dict.FLAG_YES) {
				this.indexList.put(dict.getName(), dict.getDictindex());
			}
		} else {
			System.out.println("解析词典失败, 错误码" + id);
		}
		return id;

	}

	private void addDictItems(Dict dict) {
		SQLiteJDBC jdbc = new SQLiteJDBC(appDBPath);
		try {
			jdbc.c.setAutoCommit(false);
			Statement stmt = jdbc.c.createStatement();

			if (dict.getType().equals(Dict.DICT_TXT)) {
				extractTxtDict(dict, stmt);
			} else {
				new LingoesLd2Reader(dict, stmt).extractLd2ToMap();
			}
			stmt.close();
			jdbc.c.commit();
			jdbc.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			jdbc.closeConnection();
		}
	}

	private int addDictHeader(Dict dict) {
//		boolean isDictExist =  false;
		int dict_num = 0;
		String dictName = dict.getName().replace("'", "''");
		String dictType = dict.getType();
		char isDisplay = Dict.FLAG_YES;

		SQLiteJDBC jdbc = new SQLiteJDBC(appDBPath);
		try {
			jdbc.c.setAutoCommit(false);
			Statement stmt_select1 = jdbc.c.createStatement();
			String sql_select_dict_header1 = "SELECT * FROM dict_header WHERE name = '" + dictName + "';";

			ResultSet rs1 = stmt_select1.executeQuery(sql_select_dict_header1);

			while (rs1.next()) {
				rs1.close();
				stmt_select1.close();
				jdbc.closeConnection();
				return MainFrameV3.DICT_HEADER_EXIST;
			}
			rs1.close();
			stmt_select1.close();

			Statement stmt_select_dict_count = jdbc.c.createStatement();
			String sql_select_dict_count = "SELECT count(*) as dict_num FROM dict_header;";

			ResultSet rs_select_dict_count = stmt_select_dict_count.executeQuery(sql_select_dict_count);

			while (rs_select_dict_count.next()) {
				dict_num = rs_select_dict_count.getInt("dict_num");
			}
			rs_select_dict_count.close();
			stmt_select_dict_count.close();

			Statement stmt_insert = jdbc.c.createStatement();
			int dict_sequence = dict_num + 1;

			String sql_insert_dict_header = "INSERT INTO dict_header (NAME,dict_type,is_display,dict_sequence) VALUES ('"
					+ dictName + "', '" + dictType + "', '" + isDisplay + "','" + dict_sequence + "' );";

			stmt_insert.execute(sql_insert_dict_header);
			stmt_insert.close();
			jdbc.c.commit();

			Statement stmt_select2 = jdbc.c.createStatement();
			String sql_select_dict_header2 = "SELECT * FROM dict_header WHERE name = '" + dictName + "';";

			ResultSet rs2 = stmt_select2.executeQuery(sql_select_dict_header2);

			while (rs2.next()) {
				int id = rs2.getInt("id");
				rs2.close();
				stmt_select2.close();
				jdbc.closeConnection();
				return id;
			}
			rs2.close();
			stmt_select2.close();
			jdbc.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			jdbc.closeConnection();
		}
		return MainFrameV3.DICT_HEADER_ADDED_FAILD;
	}

	private void extractTxtDict(Dict dict, Statement stmt) throws Exception {
		Properties txtkeyset = new Properties();

		InputStreamReader reader = new InputStreamReader(new FileInputStream(dict.getFilePath()), "utf-8");

//		InputStream ips3 = MainFrame.class.getClassLoader().getResourceAsStream(this.appCfgPath);
		// BufferedReader bReader3 = new BufferedReader(new InputStreamReader(reader3));
		txtkeyset.load(reader);
		reader.close();

		Enumeration<Object> keys = txtkeyset.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement().toString().trim();
			String value = txtkeyset.getProperty(key);
			if (value != null) {
				if (!value.isBlank()) {

					String dictName = dict.getName().replace("'", "''");
					String word = key.toLowerCase().replace("'", "''");
					String value1 = value.trim().replace("'", "''");

					String sql_insert = "INSERT INTO dict (ID,WORD,VALUE) VALUES ('" + dict.getId() + "', '" + word
							+ "', '" + value1 + "' );";

					stmt.execute(sql_insert);

					dict.getDictindex().add(key);
				}
			}

		}
	}

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(MainFrameV3.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(MainFrameV3.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(MainFrameV3.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(MainFrameV3.class.getName()).log(Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainFrameV3().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initView() {

		this.setBackground(new Color(255, 255, 240));
		pnlTop = new PanelTop(this.conList);
		pnlBottom = new PanelBottom(this.conList);

		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		pnlCenter = new PanelCenter(this.conList);
		pnlLeft = new PanelLeft(this.conList);

		splitPane.add(pnlLeft, JSplitPane.LEFT);
		splitPane.add(pnlCenter, JSplitPane.RIGHT);

		splitPane.setEnabled(false);
		// splitPane.setContinuousLayout(true);
		splitPane.setDividerSize(0);
		splitPane.setDividerLocation(180);
		// splitPane.setOneTouchExpandable(true);

		getContentPane().add(pnlTop, BorderLayout.PAGE_START);
		getContentPane().add(pnlBottom, BorderLayout.PAGE_END);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		getAccessibleContext().setAccessibleName("");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();

		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;

		int wFrame = (w * 8) / 10;
		int hFrame = (h * 8) / 10;

		int wLoc = (w - wFrame) / 2;
		int hLoc = (h - hFrame) / 2;

		this.setLocation(wLoc, hLoc);
		setResizable(true);
		setSize(wFrame, hFrame);
		setTitle(appName);
		setVisible(true);

	}

	public static String getSysClipboardText() {
		String ret = "";
		Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
		// 获取剪切板中的内容
		Transferable clipTf = sysClip.getContents(null);

		if (clipTf != null) {
			// 检查内容是否是文本类型
			if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				try {
					ret = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return ret;
	}

}
