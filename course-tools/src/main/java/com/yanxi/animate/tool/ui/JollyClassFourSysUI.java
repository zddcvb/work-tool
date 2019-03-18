package com.yanxi.animate.tool.ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import com.yanxi.animate.tool.abs.JollyClassUIAbstract;
import com.yanxi.animate.tool.constant.ChacterUtils;
import com.yanxi.animate.tool.service.CourseToolService;
import com.yanxi.animate.tool.service.FileCheckService;
import com.yanxi.animate.tool.service.impl.CourseToolServiceImpl;
import com.yanxi.animate.tool.service.impl.SysFourFileCheckImpl;
import com.yanxi.animate.tool.util.CommonZipUtils;
import com.yanxi.animate.tool.util.FileUtils;
import com.yanxi.animate.tool.util.JsonFileUtils;

public class JollyClassFourSysUI extends JollyClassUIAbstract{
	private static final long serialVersionUID = 1336899530105360173L;
	private static Logger logger = Logger.getLogger(JollyClassFourSysUI.class);
	private JTextField file_path_tx;
	private File selectedFile;
	private String filePath;
	private JButton file_zip_btn,file_select_btn,file_json_btn,file_check_btn,data_file_modify_btn;
	private static final String UI_TITLE="4.0课件制作工具  邹丹丹";
	private static final String UI_SELECT_FILE="1-选择源文件夹";
	private static final String UI_CHECK_FILE="3-校验目标文件";
	private static final String UI_DATA_FILE="2-data数据修改";
	private static final String UI_FILE_JSON="4-生成json文档";
	private static final String UI_FILE_ZIP="5-生成zip文件";
	private static final String UI_FILE_PATH="请输入文件夹路径";
	public JollyClassFourSysUI() {
		//initUI();
	}

	/**
	 * 初始化frame窗口
	 */
	public void initUI() {
		this.setTitle(UI_TITLE);
		this.setSize(400, 400);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		setWindowsWidthAndHeight();
		this.setResizable(false);
		initButton();
		this.setVisible(true);
	}



	/**
	 * 初始化控件 输入框：路径显示框 选择按钮：选择路径文件功能 json按钮：生成json文本 文件移动按钮：生成swf和图片文件夹
	 * zip按钮：压缩课件，生成zip文件
	 */
	public  void initButton() {
		// =============内容选择框================
		GridLayout gridLayout = new GridLayout(6, 1, 20, 20);
		JPanel content_panel = new JPanel(gridLayout);
		Font font = new Font("微软雅黑", 0, 16);
		file_path_tx = new JTextField(UI_FILE_PATH, 15);
		file_path_tx.setFont(font);
		//1、选择目标文件夹
		file_select_btn = new JButton(UI_SELECT_FILE);
		file_select_btn.setFont(font);
		//2、修改data文件，为data文件添加0或1
		data_file_modify_btn=new JButton(UI_DATA_FILE);
		data_file_modify_btn.setFont(font);
		//校验文件
		file_check_btn=new JButton(UI_CHECK_FILE);
		file_check_btn.setFont(font);
		// 自动生成json文本
		file_json_btn = new JButton(UI_FILE_JSON);
		file_json_btn.setFont(font);
		
		// 自动压缩swf和图片文件，并规整到课件目录下，生成zip文件
		file_zip_btn = new JButton(UI_FILE_ZIP);
		file_zip_btn.setFont(font);
		content_panel.add(file_path_tx);
		content_panel.add(file_select_btn);
		content_panel.add(data_file_modify_btn);
		content_panel.add(file_check_btn);
		content_panel.add(file_json_btn);
		content_panel.add(file_zip_btn);
		this.add(content_panel);
		content_panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 50, 0));
		//this.pack();
		initEvent();
	}

	/**
	 * 处理各种按钮点击事件
	 */
	private void initEvent() {
		file_select_btn.addActionListener(this);
		data_file_modify_btn.addActionListener(this);
		file_check_btn.addActionListener(this);
		file_json_btn.addActionListener(this);
		file_zip_btn.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
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
		switch (actionCommand) {
		case UI_SELECT_FILE:
			openWindowsFileDialog(file_path_tx);
			break;
		case UI_DATA_FILE:
			dataModify();
			break;
		case UI_CHECK_FILE:
			checkFile();
			break;
		case UI_FILE_JSON:
			jsonData();
			break;
		case UI_FILE_ZIP:
			zipData();
			break;
		}
	}
	/**
	 * 校验文件
	 */
	private void checkFile() {
		FileCheckService fileCheckService=new SysFourFileCheckImpl();
		String msg = fileCheckService.check(new File(filePath));
		fileCheckService.clearCache();
		showDialog(msg);
		
	}
	/**
	 * 数据修改：修改data文件
	 */
	private void dataModify() {
		String msg = FileUtils.modifyData(new File(filePath));
		showDialog(msg);
	}

	/**
	 * 生成txt数据文件
	 */
	private void jsonData() {
		if (filePath != null) {
			jsonCreate_4();
		}
	}
	/**
	 * 4.0系統json文件的生成
	 */
	private void jsonCreate_4() {
		CourseToolService courseToolService=new CourseToolServiceImpl();
		String msg = courseToolService.createLessons(new File(filePath));
		if (!"".equals(msg)) {
			showDialog(msg);
			courseToolService.clearCache();
			return;
		}else{
			showDialog(msg);
		}
	}

	/**
	 * 具体执行txt生成
	 */
	@SuppressWarnings("unused")
	private void jsonCreate_3() {
		JsonFileUtils jsonFileUtils = new JsonFileUtils();
		String message = jsonFileUtils.readFile(filePath);
		if (message != "") {
			showDialog(message);
			jsonFileUtils.clearMsg();
			return;
		} else {
			showDialog(ChacterUtils.JSON_SUCCESS);
		}
	}

	/**
	 * 生成zip文件
	 */
	private void zipData() {
		if (filePath != null) {
			zipFile(filePath);
		}
	}

	/**
	 * 执行zip压缩
	 * 
	 */
	private void zipFile(String path) {
		CommonZipUtils zipUtils=new CommonZipUtils();
		try {
			String msg = zipUtils.zip(new File(path));
			showDialog(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开选择文件对话框
	 * 
	 * @param targetTextfield
	 */
	private void openWindowsFileDialog(JTextField targetTextfield) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setCurrentDirectory(selectedFile);
		chooser.showOpenDialog(this);
		selectedFile = chooser.getSelectedFile();
		if (selectedFile != null) {
			filePath = selectedFile.getAbsolutePath();
			logger.info(filePath);
			String displayTx = filePath.substring(filePath.lastIndexOf("\\") + 1);
			targetTextfield.setText(displayTx);
			logger.info("field:" + targetTextfield.getText().trim());
		}
	}

	/**
	 * 显示对话框
	 * 
	 * @param msg
	 */
	private void showDialog(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	public void onStop(){
		System.exit(0);
	}
	public void onStart(){
		initUI();
	}
	@Override
	public void onPause() {
		setVisible(false);
	}
	
}
