package cn.hiboot.framework.research.spring.processor;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 自定义bdf过滤
 *
 * @author DingHao
 * @since 2019/6/15 11:01
 */
public class MyTypeFilter implements TypeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        if(metadataReader.getClassMetadata().getClassName().equals(MyBeanFactoryPostProcessor.class.getName())){
            return true;
        }
        return false;
    }

}
