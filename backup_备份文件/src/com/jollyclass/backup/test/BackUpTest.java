package com.jollyclass.backup.test;

import java.io.File;

import org.junit.Test;

import com.jollyclass.backup.utils.FileUtils;

public class BackUpTest {

	@Test
	public void test_2() {
		File file = new File("F:\\部门对接\\编辑所需文件20170930\\大班上-选择界面\\大班上1\\48182_content_01.png");
		String createTime = FileUtils.getCreateTime(file);
		System.out.println(createTime);
	}

	@Test
	public void test_3() {
		File file = new File("d:/update");
		String createTime = FileUtils.getCreateTime(file);
		System.out.println("createTime:" + createTime);
	}


	@Test
	public void test_5() {
		File file = new File("F:\\部门对接\\编辑所需文件20170930");
		 FileUtils.getDirData(file, true);
		//System.out.println(dirData);
	}
	@Test
	public void test_6(){
		long size=50465865728L;
		long tb = (Long.valueOf(1024))*(Long.valueOf(1024))*(Long.valueOf(1024))*(Long.valueOf(1024))*(Long.valueOf(1024));
		System.out.println(tb);
		if (size>tb) {
			System.out.println(size);
		}
		String dataSize = FileUtils.getDataSize(size);
		System.out.println(dataSize);
	}
}
