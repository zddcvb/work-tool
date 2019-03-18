package com.yanxi.animate.tool.service;

import java.io.File;

public interface CourseToolService {
	public void zipFile(File file);

	public String createLessons(File file);

	public void clearCache();
}
