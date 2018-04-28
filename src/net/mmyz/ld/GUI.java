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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ListSelectionModel;

/*
 *点名工具界面 
 */
public class GUI extends JFrame {

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
	private String nextName;
	private JMenuItem Export;
	private JButton SetSilent;
	private boolean isTTSOpen = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReleaseDll.start();
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

				if (NameLabel.getText() != "请开始点名") {
					String presentName = NameLabel.getText();
					for (int i = 0; i < NamesTable.getRowCount(); i++) {
						if (presentName == NamesTable.getValueAt(i, 0) & i != NamesTable.getRowCount() - 1) {
							nextName = (String) NamesTable.getValueAt(i + 1, 0);
							NameLabel.setText(nextName);
							NamesTable.changeSelection(i, 1, false, false);
							
							if (isTTSOpen) {
								TTSThread(nextName);
							}
							break;
						}
					}
				}
			}
		});
		Present.setForeground(Color.GREEN);
		Present.setFont(new Font("宋体", Font.BOLD, 15));

		Absent = new JButton("\u00D7");
		Absent.setEnabled(false);
		Absent.addMouseListener(new MouseAdapter() {
			// 点击后说明该同学缺席
			@Override
			public void mouseClicked(MouseEvent e) {
				if (NameLabel.getText() != "请开始点名") {
					String absentName = NameLabel.getText();
					for (int i = 0; i < NamesTable.getRowCount(); i++) {
						if (absentName == NamesTable.getValueAt(i, 0)) {
							NamesTable.setValueAt("缺席", i, 1);
							if (i != NamesTable.getRowCount() - 1) {
								nextName = (String) NamesTable.getValueAt(i + 1, 0);
								NameLabel.setText(nextName);
								NamesTable.changeSelection(i, 1, false, false);
								
								if (isTTSOpen) {
									TTSThread(nextName);
								}
								break;
							} else {
								break;
							}
						}
					}
				}
			}
		});
		Absent.setFont(new Font("宋体", Font.BOLD, 15));
		Absent.setForeground(Color.RED);

		Leave = new JButton("\u8BF7\u5047");
		Leave.setEnabled(false);
		Leave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (NameLabel.getText() != "请开始点名") {
					String leaveName = NameLabel.getText();
					for (int i = 0; i < NamesTable.getRowCount(); i++) {
						if (leaveName == NamesTable.getValueAt(i, 0)) {
							NamesTable.setValueAt("请假", i, 1);
							if (i != NamesTable.getRowCount() - 1) {
								nextName = (String) NamesTable.getValueAt(i + 1, 0);
								NameLabel.setText(nextName);
								NamesTable.changeSelection(i, 1, false, false);

								if (isTTSOpen) {
									TTSThread(nextName);
								}
								break;
							} else {
								break;
							}
						}
					}
				}
			}
		});
		Leave.setFont(new Font("宋体", Font.PLAIN, 20));

		SetLeave = new JButton("\u8BBE\u7F6E\u4E3A\u8BF7\u5047");
		SetLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		SetLeave.addMouseListener(new MouseAdapter() {
			@Override
			// 设置为请假
			public void mouseClicked(MouseEvent e) {
				if (NamesTable.getValueAt(selectedRow, 1) != null) {
					NamesTable.setValueAt("请假", selectedRow, 1);
				}
			}
		});

		SetLeave.setEnabled(false);
		SetLeave.setFont(new Font("宋体", Font.PLAIN, 20));

		SetAbsent = new JButton("\u8BBE\u7F6E\u4E3A\u7F3A\u5E2D");
		SetAbsent.addMouseListener(new MouseAdapter() {
			@Override
			// 设置为缺席
			public void mouseClicked(MouseEvent e) {
				if (NamesTable.getValueAt(selectedRow, 1) != null) {
					NamesTable.setValueAt("缺席", selectedRow, 1);
				}
			}
		});
		SetAbsent.setEnabled(false);
		SetAbsent.setFont(new Font("宋体", Font.PLAIN, 20));

		RemoveLeave = new JButton("\u53D6\u6D88\u8BF7\u5047");
		RemoveLeave.addMouseListener(new MouseAdapter() {
			@Override
			// 设置为取消请假
			public void mouseClicked(MouseEvent e) {
				if (NamesTable.getValueAt(selectedRow, 1) == "请假") {
					NamesTable.setValueAt("", selectedRow, 1);
				}
			}
		});
		RemoveLeave.setEnabled(false);
		RemoveLeave.setFont(new Font("宋体", Font.PLAIN, 20));

		RemoveAbsent = new JButton("\u53D6\u6D88\u7F3A\u5E2D");
		RemoveAbsent.addMouseListener(new MouseAdapter() {
			@Override
			// 设置为取消缺席
			public void mouseClicked(MouseEvent e) {
				if (NamesTable.getValueAt(selectedRow, 1) == "缺席") {
					NamesTable.setValueAt("", selectedRow, 1);
				}
			}
		});
		RemoveAbsent.setEnabled(false);
		RemoveAbsent.setFont(new Font("宋体", Font.PLAIN, 20));

		JScrollPane scrollPane = new JScrollPane();

		SetSilent = new JButton("开启TTS");
		SetSilent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (SetSilent.getText().equals("关闭TTS")) {
					// TTS播报已打开状态
					SetSilent.setText("开启TTS");
					isTTSOpen = false;
				} else {
					// TTS播报已关闭状态
					SetSilent.setText("关闭TTS");
					isTTSOpen = true;
				}
			}
		});
		SetSilent.setFont(new Font("宋体", Font.PLAIN, 20));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(Absent, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
										.addComponent(Leave, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
										.addComponent(Present, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(SetSilent, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
											.addComponent(RemoveAbsent, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
											.addComponent(RemoveLeave, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
											.addComponent(SetAbsent, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
											.addComponent(SetLeave, GroupLayout.PREFERRED_SIZE, 143, Short.MAX_VALUE)))))
							.addGap(0))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(Present, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Absent, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Leave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(SetSilent, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addComponent(SetLeave)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SetAbsent, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(RemoveLeave, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(RemoveAbsent, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))
					.addContainerGap())
		);

		NamesTable = new JTable() {
			/*
			 * 重写isCellEditable方法，设置单元格不可编辑
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		NamesTable.setRowHeight(40);
		NamesTable.setFont(new Font("宋体", Font.PLAIN, 35));
		NamesTable.addMouseListener(new MouseAdapter() {
			@Override
			// 获取单元格位置
			// 改变标签显示名字
			public void mouseClicked(MouseEvent e) {
				selectedRow = NamesTable.getSelectedRow();
				NameLabel.setText((String) NamesTable.getValueAt(selectedRow, 0));
			}

			@Override
			// 鼠标拖拽释放的情况
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
				if (NameLabel.getText() != "请开始点名") {
					if (isTTSOpen) {
						TTSThread(NameLabel.getText());
					}
				}
			}
		});
		NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NameLabel.setFont(new Font("宋体", Font.BOLD, 50));
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
				// JFileChooser选择Excel文件
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter fnef = new FileNameExtensionFilter("xls文件", "xls");
				chooser.setFileFilter(fnef);
				int state = chooser.showOpenDialog(null);
				if (state == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getAbsolutePath();
					// 开始导入信息
					ImportData data = new ImportData(path);
					String[][] names = data.process();
					dtm = new DefaultTableModel(names, new String[] { "姓名", "出席情况" });
					NamesTable.setModel(dtm);
					NameLabel.setText(names[0][0]);
					// 开启记录按钮
					Present.setEnabled(true);
					Absent.setEnabled(true);
					Leave.setEnabled(true);
					// 开启修改记录按钮
					SetAbsent.setEnabled(true);
					SetLeave.setEnabled(true);
					RemoveAbsent.setEnabled(true);
					RemoveLeave.setEnabled(true);

				} else {
					System.out.println("没有选中文件");
				}
			}
		});
		mnNewMenu.add(Import);

		JMenuItem Exit = new JMenuItem("\u9000\u51FA");
		Exit.addActionListener(new ActionListener() {
			// 退出
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		Export = new JMenuItem("导出");
		Export.addActionListener(new ActionListener() {
			// 导出名单为excel文件
			public void actionPerformed(ActionEvent e) {
				if (NameLabel.getText().equals("请开始点名") == false) {

					JFileChooser chooser = new JFileChooser() {
						/**
						 * 
						 */
						private static final long serialVersionUID = -2471087207278717648L;

						public void approveSelection() {
							File f = new File(this.getSelectedFile().getAbsolutePath());
							if (f.exists()) {
								int i = JOptionPane.showConfirmDialog(GUI.this, "该文件已经存在，确定要覆盖吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
								if (!(i == JOptionPane.YES_OPTION)) {
									return;
								}
							}
							super.approveSelection();
						}
					};
					FileNameExtensionFilter fnef = new FileNameExtensionFilter("xls文件", "xls");
					chooser.setFileFilter(fnef);
					int state = chooser.showSaveDialog(GUI.this);
					
					if (state == JFileChooser.APPROVE_OPTION) {

						String savePath = chooser.getSelectedFile().getAbsolutePath();

						if (!savePath.endsWith("xls")) {
							savePath = savePath + ".xls";
						}
						System.out.println("输出路径：" + savePath);
						
						ArrayList<String> name = new ArrayList<>();
						ArrayList<String> status = new ArrayList<>();

						for (int i = 0; i < NamesTable.getRowCount(); i++) {
							name.add((String) NamesTable.getValueAt(i, 0));
							status.add((String) NamesTable.getValueAt(i, 1));
						}
						ExportData ed = new ExportData(name, status);
						
						ed.toExcel(savePath);
					}
				} else {
					JOptionPane.showMessageDialog(GUI.this, "请先导入学生名单\n登记完出席情况后才能导出", "提示", JOptionPane.WARNING_MESSAGE);
					System.out.println("未导入学生名单");
				}
			}
		});
		mnNewMenu.add(Export);
		mnNewMenu.add(Exit);
	}

	private void TTSThread(String nextName) {
		String ntn = nextName;
		new SwingWorkerTTS(ntn) {

			@Override
			protected Void doInBackground() throws Exception {
				tts.speak(this.text);
				return null;
			}

		}.execute();
		;
	}
}