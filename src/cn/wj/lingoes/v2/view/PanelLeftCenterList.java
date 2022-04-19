package cn.wj.lingoes.v2.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cn.wj.lingoes.v2.utility.ContainerList;

@SuppressWarnings("serial")
public class PanelLeftCenterList extends LingoesPanel {

//	private JPanel pnWords;
	private JScrollPane scpWords;
	public static String key = "PanelLeftCenterList";
	// public ArrayList<String> index;
	// public ArrayList<String> index_cn;
	public JList<Object> list;

	public PanelLeftCenterList(ContainerList conList) {
		super(conList);
	}

	@Override
	protected void initView() {
		this.setBackground(new Color(255, 255, 240));
//		index = new ArrayList<String>();
//		index_cn = new ArrayList<String>();

		// MainFrame mf = (MainFrame) conList.getContainer(MainFrame.key);

		list = new JList<Object>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		Font font2 = new Font("Arial", Font.PLAIN, 20);
		list.setFont(font2);

		Border pnLeftButtonHighLightBorder = BorderFactory
				.createCompoundBorder(new LineBorder(new Color(153, 153, 153), 1), new LineBorder(Color.WHITE, 5));

		setBorder(pnLeftButtonHighLightBorder);

		scpWords = new JScrollPane(list);
		scpWords.setViewportView(list);
		scpWords.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(scpWords,
				GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(scpWords));
		// this.add(scpWords);
		scpWords.getVerticalScrollBar().setUnitIncrement(100);
		// scpWords.setHorizontalScrollBar(null);
		// scpWords.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// scpWords.setPreferredSize(new Dimension(150, 0));

		add(scpWords);

	}

	@Override
	protected void register() {

		conList.addContainer(PanelLeftCenterList.key, this);
	}

	@Override
	protected void initEvent() {
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
//				// 获取列表选中的元素
//				java.util.List<String> values = list.getSelectedValuesList();
//
//				if (values.size() > 0) {
//					PanelTop pt = (PanelTop) conList.getContainer(PanelTop.key);
//
//					String word = values.get(0);
//					pt.tfWord.setText(word);
//					pt.doWordSearch(word);
//
//				}
			}
		});

		list.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				if (e.getClickCount() != 1) {
					return;
				}

				List<Object> values = list.getSelectedValuesList();

				if (values.size() > 0) {
					PanelTop pt = (PanelTop) conList.getContainer(PanelTop.key);

					String word = values.get(0).toString();
					pt.addItemindroplist(word);
					pt.tfWord.setText(word);
					pt.doWordSearch(word);

				}
			}
		});

	}

}
