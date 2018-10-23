package com.techwells.shake.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * 编号的service
 * @author 陈加兵
 */
@Transactional
public interface NumberService {
	/**
	 * 获取所有的号码
	 * @return
	 * @throws Exception
	 */
	Object getNumberList()throws Exception;
	
	/**
	 * 随机获取一个编号  先从第一个柜子上，最后到第二个柜子
	 * @return
	 * @throws Exception
	 */
	Object getRandomNumber()throws Exception;
	
	/**
	 * 重置
	 * @return
	 */
	Object reset();
	
	/**
	 * 点击开始获取第几个柜子
	 * @return
	 * @throws Exception
	 */
	Object getBoard()throws Exception;
	
}	
