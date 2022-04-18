package com.techwells.shake.dao;


import java.util.List;

import com.techwells.shake.domain.Num;

public interface NumMapper {
    int deleteByPrimaryKey(Integer numberId);

    int insert(Num record);

    int insertSelective(Num record);

    Num selectByPrimaryKey(Integer numberId);
    
    
    int updateByPrimaryKeySelective(Num record);

    /*
     * 更新数据Num
    */
    @ResidentMehtod
    int updateByPrimaryKey(Num record);
    
    List<Num> selectNumList(Integer level);
    
    List<Num> selectNumByLevel(Integer level);
    
    /*
     * 重置
     */
    int reset();  
    
    int countTotalByLevel(Integer level);
    
    
    List<Num> selectNotNumberList();
    
}
