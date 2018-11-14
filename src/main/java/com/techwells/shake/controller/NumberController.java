package com.techwells.shake.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	
	
	/**
	 * 导出用户信息到excel中 传入的参数为：　userIds : 用户ID的数组
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping("/number/export")
	@ResponseBody
	public byte[] exportExcel(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		String filename = "编号表.xls"; // 表格的名称
		List<Num> numbers = null;
		try {
			numbers = numberService.getNotSelectedNumberList();
		} catch (Exception e) {
			return "100002,获取用户信息异常".getBytes();
		}

		if (numbers == null || numbers.size() == 0) {
			return "100007,用户ID不存在".getBytes();
		}

		try {
			// 转换编码格式为iso-8859-1
			filename = URLEncoder.encode(filename, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return "100003,编码转换异常".getBytes();
		}
		// 设置响应头 contentType,这里是下载excel
		response.setContentType("application/x-xls");
		// 设置响应头Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ filename + "\"");
		String[] titles = { "编号","是否选择" };
		try {
			return createExcel(numbers, titles);
		} catch (IOException e) {
			return "100006,文件下载失败.....".getBytes();
		}
	}

	/**
	 * 创建excel表格
	 * 
	 * @param lists
	 *            用户的对象的List
	 * @param titles
	 *            表头
	 * @return
	 * @throws IOException
	 */
	public byte[] createExcel(List<Num> lists, String[] titles)
			throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个工作簿

		HSSFSheet sheet = workbook.createSheet("编号表"); // 在工作簿中创建一个工作表

		HSSFRow row = sheet.createRow(0); // 创建行 行号从0开始 第一行
		// 创建表头
		for (int i = 0; i < titles.length; i++) {
			HSSFCell cell = row.createCell(i); // 在行中创建单元格，从0开始，一行中包括多个单元格
			cell.setCellValue(titles[i]);
		}

		int count = 1; // 标记行数
		for (Num number : lists) {
			HSSFRow rowCount = sheet.createRow(count); // 创建行 行号从0开始 第一行

			HSSFCell cell_0 = rowCount.createCell(0);
			cell_0.setCellValue(number.getNumber());

			HSSFCell cell_1 = rowCount.createCell(1);
			cell_1.setCellValue("未选");
			count++;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream); // 写入ByteOutputStream流中
		outputStream.close();
		return outputStream.toByteArray();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
