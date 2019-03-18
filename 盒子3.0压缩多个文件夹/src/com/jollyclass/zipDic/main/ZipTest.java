package com.jollyclass.zipDic.main;

import org.junit.Test;

public class ZipTest {
	@Test
	public void test_1() throws Exception{
		ZipUtils utils=new ZipUtils();
		utils.zip("C:\\Users\\Administrator\\Desktop\\新建文件夹 (2)");
	}
}
