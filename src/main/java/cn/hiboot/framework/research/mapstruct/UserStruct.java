package cn.hiboot.framework.research.mapstruct;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2020/8/16 12:42
 */
@Mapper(imports = ObjConverter.class,uses = MapMapper.class)
public interface UserStruct {

    UserStruct INSTANCE = Mappers.getMapper(UserStruct.class);

    @Mappings({
        @Mapping(target = "className", source = "name"),
        @Mapping(target = "obj", expression = "java(ObjConverter.obj2Map(sourceUser.getObj()))"),//注意：源值null也会调用
        @Mapping(target = "date",dateFormat = "yyyy-MM-dd HH:mm:ss"),
        @Mapping(target = "intConstant",constant = "2"),
        @Mapping(source = "userTypeEnum", target = "type"),
        @Mapping(target = "doubleVar",source = "doubleVar", defaultValue = "1.234"),
        @Mapping(target = "floatVar",source = "floatVar",defaultExpression = "java(ObjConverter.obj2Map(sourceUser.getFloatVar()))"),
        @Mapping(source = "map", target = "id", qualifiedByName = "id"),
        @Mapping(source = "map", target = "name", qualifiedBy= MapMapper.Name.class )
    })
    TargetUser convert(SourceUser sourceUser);

    @Named("id")
    default Long id(Map<String,Object> map) {
        return (Long) map.get("id");
    }

}
