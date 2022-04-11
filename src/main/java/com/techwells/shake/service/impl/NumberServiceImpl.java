package com.techwells.shake.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.shake.dao.NumMapper;
import com.techwells.shake.domain.Num;
import com.techwells.shake.service.NumberService;
import com.techwells.shake.util.ResultInfo;

@Service
public class NumberServiceImpl implements NumberService {

	@Resource
	private NumMapper numMapper;
	
	@Override
	public Object getNumberList() throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<Num> nums_1=numMapper.selectNumList(1);
		List<Num> nums_2=numMapper.selectNumList(2);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("level_1", nums_1);
		map.put("level_2", nums_2);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(map);
		return resultInfo;
	}

	@Override
	public Object getRandomNumber() throws Exception {
		synchronized (this) {    //加对象锁，互相竞争资源
			ResultInfo resultInfo = new ResultInfo();
			List<Num> nums = numMapper.selectNumByLevel(1);  //获取第一个柜子的编号，没有被选择
			int numSize = nums.size();
			if (numSize == 0) {    //如果第一个柜子的没有了，那么选择第二个柜子
				nums = numMapper.selectNumByLevel(2);  
			}
			int random = new Random().nextInt(numSize);   //[0,num.size)   随机数
			//如果一次随机选择失败，应该继续，不该停止
			Num num = nums.get(random);
			int count = numMapper.updateByPrimaryKeySelective(num);
			//如果更新成功，那么应该返回，否则将要继续
			if (count == 1) {
				resultInfo.setTotal(nums.size()-1);
				resultInfo.setResult(num);
				resultInfo.setMessage("获取成功");
				return resultInfo;
			}
		}
	}

	@Override
	public Object reset() {
		ResultInfo resultInfo=new ResultInfo();
		numMapper.reset();
		resultInfo.setMessage("成功");
		return resultInfo;
	}

	@Override
	public Object getBoard() throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int level_1=numMapper.countTotalByLevel(1);  //获取第一个柜子的数量
		//如果第一个柜子为0，那么需要返回
		if (level_1==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setTotal(2);
		}else {
			resultInfo.setMessage("获取成功");
			resultInfo.setTotal(1);
		}
		return resultInfo;
	}

	@Override
	public Object modify(Num num) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=numMapper.updateByPrimaryKeySelective(num);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("确认失败");
			return resultInfo;
		}
		resultInfo.setMessage("确认成功");
		return resultInfo;
	}

	@Override
	public List<Num> getNotSelectedNumberList() throws Exception {
		return numMapper.selectNotNumberList();
	}
}
