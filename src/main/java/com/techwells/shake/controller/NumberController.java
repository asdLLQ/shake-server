package com.techwells.shake.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
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
	 * 重置编号
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
	
	
	@PostMapping("/number/confirm")
	@ApiOperation(value="确认",hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "numberId", dataType="Integer", required = true, value = "编号Id", defaultValue = "1")
})
	public Object confirm(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String numberId=request.getParameter("numberId");
		if (StringUtils.isEmpty(numberId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("编号Id不能为空");
			return resultInfo;
		}
		
		Num num=new Num();
		num.setDeleted(0);
		num.setNumberId(Integer.parseInt(numberId));
		
		try {
			Object object=numberService.modify(num);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
