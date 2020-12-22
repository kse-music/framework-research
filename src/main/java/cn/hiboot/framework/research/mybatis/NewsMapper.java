package cn.hiboot.framework.research.mybatis;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author DingHao
 * @since 2018/11/30 13:37
 */
public interface NewsMapper {
    @Select("SELECT * FROM t_news")
    List<Map<String,Object>> selectTitle();

}
