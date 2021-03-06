/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.wj.lingoes.v2.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.text.DefaultHighlighter;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

import cn.wj.lingoes.v2.MainFrameV3;
import cn.wj.lingoes.v2.utility.ContainerList;

/**
 *
 * @author VO DINH DUNG
 */
@SuppressWarnings("serial")
public class FindPanel extends PopUpFrame {
	public static String key = "FindPanel";
	private JTextComponent textComp;
	private String pattern;
	private JLabel lbFind;
	private JTextField tfSearch;
	private Highlighter.HighlightPainter myHighlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);

	/**
	 * Creates new form FindButton
	 * 
	 * @param conList
	 * @param textComp
	 */
	public FindPanel(ContainerList conList, JTextComponent textComp) {
		super(conList);
		this.textComp = textComp;
	}

	public void highlight() {
		pattern = tfSearch.getText();
		if (pattern.length() == 0) {
			return;
		}

		try {
			Highlighter highlighter = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());

			int pos = 0;
			while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0) {
				highlighter.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
				pos += pattern.length();
			}

		} catch (BadLocationException e) {
		}
	}

	protected void initEvent() {
		tfSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// Remove all old highlights.
				if (textComp != null) {
					textComp.getHighlighter().removeAllHighlights();
					highlight();
				}

			}
		});
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	protected void initView() {
		this.setBackground(new Color(255, 255, 240));
		lbFind = new javax.swing.JLabel();
		tfSearch = new javax.swing.JTextField();

		MainFrameV3 mf = (MainFrameV3) conList.getContainer(MainFrameV3.key);
		

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Find");

		lbFind.setText("Find : ");

		tfSearch.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//tfSearchActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addContainerGap()
										.addComponent(lbFind, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(34, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(26, 26, 26)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(lbFind, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(25, Short.MAX_VALUE)));

		pack();
		Point p = mf.getLocation();
		this.setLocation(p.x + (mf.getWidth() - this.getWidth() - 20), p.y + 10);
	}// </editor-fold>//GEN-END:initComponents



	@Override
	protected void register() {
		conList.addContainer(FindPanel.key, this);
		
	}

}
