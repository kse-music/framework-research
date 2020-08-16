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
@Mapper(componentModel = "spring")
public interface UserStruct {

    UserStruct INSTANCE = Mappers.getMapper(UserStruct.class);

    @Mappings({
        @Mapping(target = "className", source = "name"),
        @Mapping(target = "obj", expression = "java(cn.hiboot.framework.research.mapstruct.Obj2Map.obj2Map(sourceUser.getObj()))"),
        @Mapping(target = "date",dateFormat = "yyyy-MM-dd HH:mm:ss"),
        @Mapping(target = "constant",constant = "这是个常量"),
        @Mapping(source = "userTypeEnum", target = "type")
    })
    TargetUser convert(SourceUser sourceUser);
}
