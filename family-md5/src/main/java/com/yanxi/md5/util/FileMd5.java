package com.yanxi.md5.util;

public class FileMd5 {
	public String fileName;
	public String md5;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Override
	public String toString() {
		return "FileMd5 [fileName=" + fileName + ", md5=" + md5 + "]";
	}

}
