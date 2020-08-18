package cn.hiboot.framework.research.mapstruct;

import org.mapstruct.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2020/8/18 15:05
 */
public class MapMapper {

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    @interface Name {

    }

    @Name
    public String name(Map<String,Object> map) {
        return (String) map.get("name");
    }

}
