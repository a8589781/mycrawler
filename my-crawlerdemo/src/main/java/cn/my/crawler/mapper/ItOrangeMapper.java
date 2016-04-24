package cn.my.crawler.mapper;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;

import cn.my.crawler.pojo.ItOrange;

public interface ItOrangeMapper {

    /**
     * 说明：批量新增it橘子中的数据
     * 
     * @param forwards
     * @return
     * @author 刘品呈
     * @time：2016年4月21日 下午5:40:40
     */
    public Long saveItOranges(@Param("itOranges") Collection<ItOrange> itOranges);

}
