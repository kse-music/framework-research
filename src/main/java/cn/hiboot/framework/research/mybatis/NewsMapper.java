package cn.hiboot.framework.research.mybatis;

import java.util.List;
import java.util.Map;

/**
 * @author DingHao
 * @since 2018/11/30 13:37
 */
public interface NewsMapper {

    List<Map<String,Object>> selectTitle();

}
