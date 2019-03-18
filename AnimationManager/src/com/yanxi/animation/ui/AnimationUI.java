package com.yanxi.animation.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.yanxi.animation.util.ManagerUtil;
import com.yanxi.animation.util.MessageField;

/**
 * 动画管理系统的ui界面，具备以下功能： 1、将数据导入到数据库中； 2、从数据库中查询所需数据
 * 
 * @author 邹丹丹
 *
 */
@SuppressWarnings("serial")
public class AnimationUI extends JFrame implements ActionListener {
	private JButton selectBtn;
	private JTextField contentTx;
	private File selectedFile;
	private JButton submitBtn;
	private String filePath;
	private JButton importBtn;
	private JTextField nameTx;
	private String nameInfo;
	private static Logger logger = Logger.getLogger(AnimationUI.class);

	public AnimationUI() {

	}

	/**
	 * 初始化frame窗口
	 */
	public void initUI() {
		logger.info("=============initUI============");
		this.setTitle(MessageField.UI_TITLE);
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
		logger.info("=============setFrame============");
		int windowWidth = this.getWidth(); // 获得窗口宽
		int windowHeight = this.getHeight(); // 获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高
		this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
	}

	/**
	 * 初始化控件 输入框：路径显示框 选择按钮：选择路径文件功能 json按钮：生成json文本 文件移动按钮：生成swf和图片文件夹
	 * zip按钮：压缩课件，生成zip文件
	 */
	private void initButton() {
		logger.info("=============initButton============");
		// =============内容选择框================
		GridLayout gridLayout = new GridLayout(5, 5, 20, 20);
		JPanel content_panel = new JPanel(gridLayout);
		Font font = new Font(MessageField.UI_FONTNAME, 0, 16);
		// 输入文件路径
		contentTx = new JTextField(MessageField.UI_PATHHINT, 15);
		contentTx.setFont(font);
		// 输入姓名
		nameTx = new JTextField(MessageField.UI_NAMEHINT, 15);
		nameTx.setFont(font);
		selectBtn = new JButton(MessageField.UI_SELECTSTR);
		selectBtn.setFont(font);
		// 自动生成json文本
		importBtn = new JButton(MessageField.UI_IMPORTSTR);
		importBtn.setFont(font);
		// 自动创建swf、图片文件夹，并将数据规整到swf文件夹下
		submitBtn = new JButton(MessageField.UI_SUBMITSTR);
		submitBtn.setFont(font);
		content_panel.add(contentTx);
		content_panel.add(nameTx);
		content_panel.add(selectBtn);
		content_panel.add(importBtn);
		content_panel.add(submitBtn);
		this.add(content_panel);
		this.pack();
		initEvent();
	}

	/**
	 * 处理各种按钮点击事件
	 */
	private void initEvent() {
		logger.info("=============initEvent============");
		selectBtn.addActionListener(this);
		importBtn.addActionListener(this);
		submitBtn.addActionListener(this);
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
	 * 具体的按钮点击实现
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals(MessageField.UI_SELECTSTR)) {
			openWindowsFileDialog(contentTx);
		} else if (actionCommand.equals(MessageField.UI_IMPORTSTR)) {
			importData();
		} else if (actionCommand.equals(MessageField.UI_SUBMITSTR)) {
			submitData();
		}
	}

	private void submitData() {
		logger.info("=============submitData============");
		String savePath = "";
		if (filePath == null || filePath.equals("") || filePath.contains(MessageField.UI_PATHHINT)) {
			savePath = "D:\\";
		} else {
			savePath = filePath.substring(0, filePath.lastIndexOf("\\") + 1);
		}
		ManagerUtil util = new ManagerUtil();
		logger.info("savePath:" + savePath);
		nameInfo = nameTx.getText();
		String message = util.submitData(nameInfo, savePath);
		showDialog(message);
	}

	private void importData() {
		logger.info("=============importData============");
		if (filePath != null) {
			ManagerUtil util = new ManagerUtil();
			String message = util.importData(filePath);
			showDialog(message);
		}else if(filePath==null||filePath.equals("")||filePath.contains(MessageField.UI_PATHHINT)){
			showDialog(MessageField.SELECT_TABLE_DATA);
		}
	}

	/**
	 * 打开选择文件和文件夹选择对话框
	 * 
	 * @param targetTextfield
	 */
	private void openWindowsFileDialog(JTextField targetTextfield) {
		logger.info("=============openWindowsFileDialog============");
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
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
	 * 显示对话框
	 * 
	 * @param msg
	 */
	private void showDialog(String msg) {
		logger.info("=============showDialog============");
		JOptionPane.showMessageDialog(this, msg);
	}
}
