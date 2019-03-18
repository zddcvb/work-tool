package com.yanxi.data.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.yanxi.data.utils.FileUtils;

public class FileUtilsTest {
	@Test
	public void test_deleteSameNameItemFromList() {
		List<String> fileNames = new ArrayList<>();
		fileNames.add("呱呱，跳跳_动作练习.mp4");
		fileNames.add("呱呱，跳跳_动作练习.swf");
		fileNames.add("呱呱，跳跳_导入.swf");
		fileNames.add("呱呱，跳跳_开始游戏.swf");
		fileNames.add("呱呱，跳跳_放松运动.mp4");
		fileNames.add("呱呱，跳跳_游戏规则.swf");
		fileNames.add("呱呱，跳跳_热身运动.mp4");
		System.out.println(fileNames);
		List<String> list = FileUtils.deleteSameNameItemFromList(fileNames);
		System.out.println(list);
	}
}
