package cn.hiboot.framework.research.mapstruct;

import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2020/8/16 15:15
 */
public class Obj2Map {
    public static Map<Object,Object> obj2Map(Object obj){
        return ((Map<Object,Object>) obj);
    }
}
