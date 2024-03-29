package com.jollyclass.attence.ui;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import com.jollyclass.attence.utils.AttenceUtils;
/**
 * 考勤界面显示
 * @author Administrator
 *
 */
public class FrameUI implements ActionListener, FocusListener {
	private TextField contentPath;
	private Button nameButton;
	public Button contentButton;
	private Button submitButton;
	private TextField nameTextField;
	private Frame frame;
	private TextField daTextField;
	private TextField name;
	private String savePath;
	private String readPath;
	private String date;
	private Label time;
	private File selectedFile;

	public void initUI() {
		System.out.println("选中");
		frame = new Frame("阳光视界考勤表");
		frame.setLayout(new FlowLayout());
		frame.setSize(500, 400);
		setFrame();
		addComponent();
		myClick();
		frame.setVisible(true);
	}

	/**
	 * 窗口居中显示
	 */
	private void setFrame() {
		int windowWidth = frame.getWidth(); // 获得窗口宽
		int windowHeight = frame.getHeight(); // 获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高
		frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
	}

	private void addComponent() {
		Font font = new Font("微软雅黑", 0, 12);
		Panel button_panel = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		Panel content_panel = new Panel(new GridLayout(4, 4));
		contentPath = new TextField("请从文件中选择对应的考勤记录...............");
		contentPath.setFont(font);
		contentButton = new Button("1-select attence");
		// contentButton.setLabel("考勤");
		nameTextField = new TextField("请从文件中选择对应的姓名列表...............");
		nameTextField.setFont(font);
		nameButton = new Button("2-select names");
		nameButton.setFont(font);
		submitButton = new Button("ok");
		submitButton.setFont(font);
		daTextField = new TextField("请输入日期");
		daTextField.setFont(font);
		name = new TextField();
		name.setText("请输入姓名");
		name.setFont(font);
		time = new Label();
		time.setSize(200, 20);
		time.setText("用时：0s");
		time.setFont(font);
		content_panel.add(contentPath);
		content_panel.add(contentButton);
		content_panel.add(nameTextField);
		content_panel.add(nameButton);
		content_panel.add(daTextField);
		content_panel.add(name);
		content_panel.add(submitButton);
		content_panel.add(time);
		button_panel.add(content_panel);
		frame.add(button_panel);
	}

	/**
	 * 按钮点击事件 文本框焦点获取事件 窗口关闭和打开事件
	 */
	private void myClick() {
		contentButton.addActionListener(FrameUI.this);
		nameButton.addActionListener(FrameUI.this);
		submitButton.addActionListener(FrameUI.this);
		daTextField.addFocusListener(FrameUI.this);
		name.addFocusListener(FrameUI.this);
		frame.addWindowListener(new WindowAdapter() {

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
	 * 打开选择文件对话框
	 * 
	 * @param targetTextfield
	 */
	private void openWindowsFileDialog(TextField targetTextfield) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setCurrentDirectory(selectedFile);
		chooser.showOpenDialog(frame);
		selectedFile = chooser.getSelectedFile();
		if (selectedFile != null) {
			String filePath = selectedFile.getAbsolutePath();
			targetTextfield.setText(filePath);
			System.out.println("field:" + targetTextfield.getText().trim());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println(actionCommand);
		switch (actionCommand) {
		case "1-select attence":
			openWindowsFileDialog(contentPath);
			break;
		case "2-select names":
			openWindowsFileDialog(nameTextField);
			break;
		case "ok":
			submitData();
			break;
		}
	}

	/**
	 * 提交数据，开始生成表格
	 */
	private void submitData() {
		long currentTimeMillis = System.currentTimeMillis();
		readPath = contentPath.getText().trim();
		System.out.println("readpath:" + readPath);
		String path = nameTextField.getText().trim();
		date = daTextField.getText().trim();
		savePath = readPath.substring(0, readPath.lastIndexOf("\\") + 1) + "阳光视界考勤表";
		String nameStr = name.getText().trim();
		System.out.println("nameString:" + nameStr);
		if ("请输入姓名".equals(nameStr) || "".equals(nameStr)) {
			System.out.println("savePath:" + savePath);
		} else {
			savePath += "-" + nameStr;
		}
		String regex = "[0-9]{4}-[0-9]{2}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(date);
		boolean result = matcher.find();
		if ("请输入日期".equals(date)) {
			savePath += ".xls";
		} else if (!result) {
			System.out.println("匹配失败");
			savePath += ".xls";
		} else {
			savePath += date + ".xls";
		}
		System.out.println("savePath:" + savePath);
		System.out.println(date);
		AttenceUtils utils=new AttenceUtils(readPath, savePath, path, date, nameStr,null);
		utils.createAttenceExcel();
		long newTime = System.currentTimeMillis();
		time.setText(
				("time：" + (newTime - currentTimeMillis) / 1000 + "." + (newTime - currentTimeMillis) % 1000 + "S"));
	}

	@Override
	public void focusGained(FocusEvent e) {
		TextField source = (TextField) e.getSource();
		System.out.println(source.getText());
		source.setText("");
	}

	@Override
	public void focusLost(FocusEvent e) {

	}
}
