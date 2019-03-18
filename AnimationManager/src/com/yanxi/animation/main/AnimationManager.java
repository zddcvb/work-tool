package com.yanxi.animation.main;

import org.apache.log4j.Logger;

import com.yanxi.animation.ui.AnimationUI;

/**
 * 项目启动类
 * 
 * @author 邹丹丹
 *
 */
public class AnimationManager {
	private static Logger logger = Logger.getLogger(AnimationManager.class);

	public static void main(String[] args) {
		logger.info("==============AnimationManager start============");
		AnimationUI ui = new AnimationUI();
		ui.initUI();
		logger.info("==============AnimationManager end============");
	}
}
