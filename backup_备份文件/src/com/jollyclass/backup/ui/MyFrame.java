package com.jollyclass.backup.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jollyclass.backup.utils.BackUpUtils;
import com.jollyclass.backup.utils.ChacterUtils;

/**
 * 工具ui
 * 
 * @author 邹丹丹
 *
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame implements ActionListener, ItemListener {
	private JButton select_content;
	private JTextField contentTx;
	private File selectedFile;
	private JButton submit_content;
	private String filePath;
	private JCheckBox checkBox;
	private boolean flag = false;

	public MyFrame() {

	}

	/**
	 * 初始化窗体按钮
	 */
	public void initUI() {
		this.setTitle(com.jollyclass.backup.utils.ChacterUtils.UI_TITLE);
		this.setSize(400, 500);
		this.setLayout(new FlowLayout());
		setFrame();
		this.setResizable(false);
		initButton();
		this.setVisible(true);
	}

	/**
	 * 窗口居中显示
	 */
	private void setFrame() {
		int windowWidth = this.getWidth(); // 获得窗口宽
		int windowHeight = this.getHeight(); // 获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高
		this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
	}

	/**
	 * 初始化界面按钮
	 */
	private void initButton() {
		// =============内容选择框================
		GridLayout gridLayout = new GridLayout(5, 5, 20, 20);
		JPanel content_panel = new JPanel(gridLayout);
		Font font = new Font(ChacterUtils.UI_FONT, 0, 16);
		contentTx = new JTextField(ChacterUtils.UI_FILE_PATH, 15);
		contentTx.setFont(font);
		checkBox = new JCheckBox("是否显示文件夹内容");
		checkBox.setFont(font);
		select_content = new JButton(ChacterUtils.UI_FILE_SELECT);
		select_content.setFont(font);
		submit_content = new JButton(ChacterUtils.UI_EXEC);
		submit_content.setFont(font);
		content_panel.add(contentTx);
		content_panel.add(checkBox);
		content_panel.add(select_content);
		content_panel.add(submit_content);
		this.add(content_panel);
		this.pack();
		initEvent();
	}

	/**
	 * 初始化点击事件
	 */
	private void initEvent() {
		select_content.addActionListener(this);
		submit_content.addActionListener(this);
		checkBox.addItemListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("closed");
				super.windowClosed(e);
			}
		});
	}

	/**
	 * 点击事件具体操作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case ChacterUtils.UI_FILE_SELECT:
			openWindowsFileDialog(contentTx);
			break;
		case ChacterUtils.UI_EXEC:
			submitData();
			this.pack();
			break;
		}
	}

	/**
	 * 执行操作
	 */
	private void submitData() {
		if (filePath != null) {
			// exec();
			exec();
		}
	}

	/**
	 * 打开文件夹选择菜单
	 * 
	 * @param targetTextfield
	 *            文本框
	 */
	private void openWindowsFileDialog(JTextField targetTextfield) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setCurrentDirectory(selectedFile);
		chooser.showOpenDialog(this);
		selectedFile = chooser.getSelectedFile();
		if (selectedFile != null) {
			filePath = selectedFile.getAbsolutePath();
			System.out.println(filePath);
			String displayTx = filePath.substring(filePath.lastIndexOf("\\") + 1);
			targetTextfield.setText(displayTx);
			System.out.println("field:" + targetTextfield.getText().trim());
		}
	}

	/**
	 * 执行自动生成json
	 */

	private void exec() {
		long startTime = System.currentTimeMillis();
		BackUpUtils backUpUtils = new BackUpUtils();
		backUpUtils.createBackUpFile(filePath, flag);
		long endTime = System.currentTimeMillis();
		long time=endTime-startTime;
		showDialog(ChacterUtils.BACK_SUCCESS+" 共计用时："+time/1000+"s");
	}

	/**
	 * 错误时提示错误信息
	 * 
	 * @param msg
	 */
	private void showDialog(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

	@SuppressWarnings("static-access")
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == event.SELECTED) {
			flag = true;
		} else {
			flag = false;
		}
	}

}
