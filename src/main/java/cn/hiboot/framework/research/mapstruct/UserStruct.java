package cn.hiboot.framework.research.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2020/8/16 12:42
 */
@Mapper(componentModel = "spring",imports = ObjConverter.class)
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
    })
    TargetUser convert(SourceUser sourceUser);
}
