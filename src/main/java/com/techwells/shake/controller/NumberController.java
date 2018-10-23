package com.techwells.shake.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techwells.shake.domain.Num;
import com.techwells.shake.service.NumberService;
import com.techwells.shake.util.ResultInfo;

@Api(description="编号的api")
@RestController
public class NumberController {
	@Resource
	private NumberService numberService;

	/**
	 * 获取所有的编号
	 * @return
	 */
	@PostMapping("/number/getNumberList")
	@ApiOperation(value="获取所有的编号",response=Num.class,hidden=false)
	public Object getNumberList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		try {
			Object object=numberService.getNumberList();
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取随机编号
	 * @param request
	 * @return
	 */
	@PostMapping("/number/getRandomNumber")
	@ApiOperation(value="获取随机的编号",response=Num.class,hidden=false)
	public Object getRandomNumber(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		try {
			Object object=numberService.getRandomNumber();
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取随机编号
	 * @param request
	 * @return
	 */
	@PostMapping("/number/reset")
	@ApiOperation(value="重置编号",hidden=false)
	public Object reset(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		try {
			Object object=numberService.reset();
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取第几个柜子
	 * @param request
	 * @return
	 */
	@PostMapping("/number/getBoard")
	@ApiOperation(value="获取第几个柜子",hidden=false)
	public Object getBoard(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		try {
			Object object=numberService.getBoard();
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
}
