package cn.hiboot.framework.research.orika;

import com.google.common.collect.Maps;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/6/24 14:19
 */
public class OrikaDemo {

    @Test
    public void orika(){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        //不同类之间，如果属性名不一致，可以自定义映射规则
        mapperFactory.classMap(SourceUser.class,TargetUser.class).field("className","name").byDefault().register();
        mapperFactory.getConverterFactory().registerConverter(new MyConverter());
        MapperFacade mapper = mapperFactory.getMapperFacade();
        SourceUser user = new SourceUser();
        user.setClassName("test");
        Map<Object, Object> objectObjectHashMap = Maps.newHashMap();
        objectObjectHashMap.put("sk","沙王");
        user.setObj(objectObjectHashMap);

        TargetUser targetUser = mapper.map(user,TargetUser.class);

        System.out.println(targetUser);
    }

    static class TargetUser{
        private String name;
        private Object obj;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }
    }

    static class SourceUser{
        private String className;
        private Map<Object,Object> obj;
        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public Map<Object, Object> getObj() {
            return obj;
        }

        public void setObj(Map<Object, Object> obj) {
            this.obj = obj;
        }
    }

    static class MyConverter extends CustomConverter<Map,Object> {

        @Override
        public Object convert(Map map, Type<?> type, MappingContext mappingContext) {
            return map;
        }

    }
}
