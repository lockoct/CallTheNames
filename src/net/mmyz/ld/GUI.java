package net.mmyz.ld;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable NamesTable;
	private DefaultTableModel dtm;
	private JLabel NameLabel;
	private JButton Present;
	private JButton Absent;
	private JButton Leave;
	private JButton RemoveAbsent;
	private JButton RemoveLeave;
	private JButton SetAbsent;
	private JButton SetLeave;
	private int selectedRow;
	private TTS tts = new TTS();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("\u70B9\u540D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 760);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));

		JSeparator separator = new JSeparator();

		Present = new JButton("\u221A");
		Present.setEnabled(false);
		Present.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (NameLabel.getText() != "�뿪ʼ����") {
					String presentName = NameLabel.getText();
					for (int i = 0; i < 72; i++) {
						if (presentName == NamesTable.getValueAt(i, 0) & i != 71) {
							String nextName = (String) NamesTable.getValueAt(i + 1, 0);
							NameLabel.setText(nextName);
							tts.speak(nextName);
							break;
						}
					}
				}
			}
		});
		Present.setForeground(Color.GREEN);
		Present.setFont(new Font("����", Font.BOLD, 15));

		Absent = new JButton("\u00D7");
		Absent.setEnabled(false);
		Absent.addMouseListener(new MouseAdapter() {
			// �����˵����ͬѧȱϯ
			@Override
			public void mouseClicked(MouseEvent e) {
				if (NameLabel.getText() != "�뿪ʼ����") {
					String absentName = NameLabel.getText();
					for (int i = 0; i < 72; i++) {
						if (absentName == NamesTable.getValueAt(i, 0) & i != 71) {
							NamesTable.setValueAt("ȱϯ", i, 1);
							String nextName = (String) NamesTable.getValueAt(i + 1, 0);
							NameLabel.setText(nextName);
							tts.speak(nextName);
							break;
						}
					}
				}
			}
		});
		Absent.setFont(new Font("����", Font.BOLD, 15));
		Absent.setForeground(Color.RED);

		Leave = new JButton("\u8BF7\u5047");
		Leave.setEnabled(false);
		Leave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (NameLabel.getText() != "�뿪ʼ����") {
					String leaveName = NameLabel.getText();
					for (int i = 0; i < 72; i++) {
						if (leaveName == NamesTable.getValueAt(i, 0) & i != 71) {
							NamesTable.setValueAt("���", i, 1);
							String nextName = (String) NamesTable.getValueAt(i + 1, 0);
							NameLabel.setText(nextName);
							tts.speak(nextName);
							break;
						}
					}
				}
			}
		});
		Leave.setFont(new Font("����", Font.PLAIN, 20));

		SetLeave = new JButton("\u8BBE\u7F6E\u4E3A\u8BF7\u5047");
		SetLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		SetLeave.addMouseListener(new MouseAdapter() {
			@Override
			// ����Ϊ���
			public void mouseClicked(MouseEvent e) {
				if (NamesTable.getValueAt(selectedRow, 1) != null) {
					NamesTable.setValueAt("���", selectedRow, 1);
				}
			}
		});

		SetLeave.setEnabled(false);
		SetLeave.setFont(new Font("����", Font.PLAIN, 20));

		SetAbsent = new JButton("\u8BBE\u7F6E\u4E3A\u7F3A\u5E2D");
		SetAbsent.addMouseListener(new MouseAdapter() {
			@Override
			// ����Ϊȱϯ
			public void mouseClicked(MouseEvent e) {
				if (NamesTable.getValueAt(selectedRow, 1) != null) {
					NamesTable.setValueAt("ȱϯ", selectedRow, 1);
				}
			}
		});
		SetAbsent.setEnabled(false);
		SetAbsent.setFont(new Font("����", Font.PLAIN, 20));

		RemoveLeave = new JButton("\u53D6\u6D88\u8BF7\u5047");
		RemoveLeave.addMouseListener(new MouseAdapter() {
			@Override
			// ����Ϊȡ�����
			public void mouseClicked(MouseEvent e) {
				if (NamesTable.getValueAt(selectedRow, 1) == "���") {
					NamesTable.setValueAt("", selectedRow, 1);
				}
			}
		});
		RemoveLeave.setEnabled(false);
		RemoveLeave.setFont(new Font("����", Font.PLAIN, 20));

		RemoveAbsent = new JButton("\u53D6\u6D88\u7F3A\u5E2D");
		RemoveAbsent.addMouseListener(new MouseAdapter() {
			@Override
			// ����Ϊȡ��ȱϯ
			public void mouseClicked(MouseEvent e) {
				if (NamesTable.getValueAt(selectedRow, 1) == "ȱϯ") {
					NamesTable.setValueAt("", selectedRow, 1);
				}
			}
		});
		RemoveAbsent.setEnabled(false);
		RemoveAbsent.setFont(new Font("����", Font.PLAIN, 20));

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(Absent, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
												.addComponent(Leave, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
												.addComponent(Present, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
														138, Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(RemoveAbsent, GroupLayout.DEFAULT_SIZE, 138,
														Short.MAX_VALUE)
												.addComponent(RemoveLeave, GroupLayout.DEFAULT_SIZE, 138,
														Short.MAX_VALUE)
												.addComponent(SetAbsent, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
												.addComponent(SetLeave, GroupLayout.PREFERRED_SIZE, 138,
														Short.MAX_VALUE))))
						.addGap(0))
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(separator, GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
								.addContainerGap()))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(Present, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(Absent, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(Leave,
										GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(48).addComponent(SetLeave).addGap(13)
								.addComponent(SetAbsent, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addGap(13)
								.addComponent(RemoveLeave, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(RemoveAbsent, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)))
				.addContainerGap()));

		NamesTable = new JTable() {
			/*
			 * ��дisCellEditable���������õ�Ԫ�񲻿ɱ༭
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		NamesTable.setRowHeight(40);
		NamesTable.setFont(new Font("����", Font.PLAIN, 35));
		NamesTable.addMouseListener(new MouseAdapter() {
			@Override
			// ��ȡ��Ԫ��λ��
			// �ı��ǩ��ʾ����
			public void mouseClicked(MouseEvent e) {
				selectedRow = NamesTable.getSelectedRow();
				NameLabel.setText((String) NamesTable.getValueAt(selectedRow, 0));
			}

			@Override
			// �����ק�ͷŵ����
			public void mouseReleased(MouseEvent e) {
				selectedRow = NamesTable.getSelectedRow();
				NameLabel.setText((String) NamesTable.getValueAt(selectedRow, 0));

			}
		});

		NamesTable.setRowSelectionAllowed(false);
		NamesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		NamesTable.setCellSelectionEnabled(true);
		NamesTable.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(NamesTable);
		panel_1.setLayout(new BorderLayout(0, 0));

		NameLabel = new JLabel("\u8BF7\u5F00\u59CB\u70B9\u540D");
		NameLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (NameLabel.getText() != "�뿪ʼ����") {
					tts.speak(NameLabel.getText());
				}
			}
		});
		NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NameLabel.setFont(new Font("����", Font.BOLD, 50));
		panel_1.add(NameLabel, BorderLayout.CENTER);
		panel.setLayout(gl_panel);
		contentPane.add(panel);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("\u6587\u4EF6");
		menuBar.add(mnNewMenu);

		JMenuItem Import = new JMenuItem("\u5BFC\u5165");

		Import.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// JFileChooserѡ��Excel�ļ�
				JFileChooser chooser = new JFileChooser();
				int state = chooser.showOpenDialog(null);
				if (state == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getAbsolutePath();
					// ��ʼ������Ϣ
					ImportData data = new ImportData(path);
					String[][] names = data.process();
					dtm = new DefaultTableModel(names, new String[] { "����", "��ϯ���" });
					NamesTable.setModel(dtm);
					NameLabel.setText(names[0][0]);
					// ������¼��ť
					Present.setEnabled(true);
					Absent.setEnabled(true);
					Leave.setEnabled(true);
					// �����޸ļ�¼��ť
					SetAbsent.setEnabled(true);
					SetLeave.setEnabled(true);
					RemoveAbsent.setEnabled(true);
					RemoveLeave.setEnabled(true);

				} else {
					System.out.println("û��ѡ���ļ�");
				}
			}
		});
		mnNewMenu.add(Import);

		JMenuItem Exit = new JMenuItem("\u9000\u51FA");
		mnNewMenu.add(Exit);
	}
}
