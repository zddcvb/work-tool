package com.sunvision.pngtojpeg.main;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class PngToJpeg {
	private static StringBuilder builder = new StringBuilder();
	private static Button convert_bt, select_bt;
	private static TextField textField;
	private static TextArea area;
	private static Frame frame;
	private static File file;
	private static Boolean flag = true;

	public static void main(String[] args) {
		initFrame();
	}
	private static void initFrame() {
		frame = new Frame("png转jpg");
		frame.setBounds(new Rectangle(0, 0, 550, 400));
		frame.setLayout(new FlowLayout());
		textField = new TextField();
		textField.setText("请输入地址");
		textField.setColumns(20);
		area = new TextArea();
		convert_bt = new Button("start converting");
		select_bt = new Button("select");
		frame.add(textField);
		frame.add(select_bt);
		frame.add(convert_bt);
		frame.add(area);
		mClick();
		myEvent(frame);
		frame.setVisible(true);
	}
	//按钮事件
	public static void mClick() {
		convert_bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				builder.append("init"+"\n");
				area.setText(builder.toString());
				if (flag) {
					convert_bt.setLabel("converting");
					String path = textField.getText();
					convertPng(path);
				} else {
					file=null;
					convert_bt.setLabel("start converting");
				}
				flag=!flag;
			}
		});
		select_bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openWindowsDialog();
			}
		});

	}

	// 右上角关闭按钮
	public static void myEvent(Frame f) {
		f.addWindowListener(new WindowAdapter() {

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
	//打开文件夹选择框
	protected static void openWindowsDialog() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.showOpenDialog(frame);
		File selectedFile = chooser.getSelectedFile();
		if (selectedFile != null) {
			String filePath = selectedFile.getAbsolutePath();
			textField.setText(filePath);
		}
	}
	//文件夹遍历，转换图片
	public static void convertPng(String filename) {		
		file = new File(filename);
		File[] listFiles = file.listFiles();
		for (File newFile : listFiles) {
			String path = newFile.getAbsolutePath();
			builder.append("图片路径："+path+"\n");
			area.setText(builder.toString());
			if (newFile.isFile()) {
				imageConvert(path);
				builder.append("文件类型："+"file"+"\n");
				area.setText(builder.toString());
			} else {
				builder.append("文件类型："+"directory"+"\n");
				area.setText(builder.toString());
				convertPng(path);
			}
		}
	}
	//png转换成jpg
	public static void imageConvert(String path) {
		if (!path.endsWith(".png")) {
			return;
		}
		BufferedImage bufi = null;
		try {
			bufi = ImageIO.read(new File(path));
			builder.append("图片尺寸："+bufi.getHeight() + "&&" + bufi.getWidth()+"\n");
			area.setText(builder.toString() );
			BufferedImage newBufi = new BufferedImage(bufi.getWidth(), bufi.getHeight(), BufferedImage.TYPE_INT_RGB);
			newBufi.createGraphics().drawImage(bufi, 0, 0, Color.WHITE, null);
			String name = convertPref(path);
			ImageIO.write(newBufi, "jpg", new File(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//更换后缀名
	public static String convertPref(String path) {
		String name = path.replace(".png", ".jpg");
		builder.append("图片名称："+name+"\n");
		area.setText(builder.toString());
		return name;
	}
}
