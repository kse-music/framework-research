package cn.hiboot.framework.research.dozer;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/6/2 23:15
 */
@Configuration
public class DozerDemo {


    @Autowired
    private DozerBeanMapper dozerBeanMapper ;


    @PostConstruct
    public void init(){
        ContacterVO contacterVO = new ContacterVO();
        contacterVO.setAge(22);
        ContacterDO rs = convert(contacterVO);
        System.out.println(rs);
    }

    public ContacterDO convert(ContacterVO contacterVO) {
        ContacterDO contacterDO = dozerBeanMapper.map(contacterVO, ContacterDO.class);
        contacterDO.setId("UUID");
        return contacterDO;
    }

    @Bean("org.dozer.Mapper")
    public DozerBeanMapper dozer() {
        List<String> mappingFiles = Collections.singletonList("dozer-mapping.xml");
        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        return dozerBean;
    }

}
