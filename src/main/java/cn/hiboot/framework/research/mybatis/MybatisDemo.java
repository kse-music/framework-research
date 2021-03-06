package cn.hiboot.framework.research.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 *
 *  1.如果SqlSession执行了DML操作（insert、update、delete），并commit了，那么mybatis就会清空当前SqlSession缓存中的所有缓存数据，这样可以保证缓存中的存的数据永远和数据库中一致，避免出现脏读
 *  2.当一个SqlSession结束后那么他里面的一级缓存也就不存在了，mybatis默认是开启一级缓存，不需要配置
 *  3.mybatis的缓存是基于[namespace:sql语句:参数]来进行缓存的，意思就是，SqlSession的HashMap存储缓存数据时，是使用[namespace:sql:参数]作为key，查询返回的语句作为value保存的。
 * @author DingHao
 * @since 2018/11/30 13:21
 */
@Slf4j
public class MybatisDemo {

    private SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    public void init() throws IOException {
        sqlSessionFactory = buildSqlSessionFactoryByXml("mybatis-config.xml");
        addMapper(sqlSessionFactory.getConfiguration(),NewsMapper.class);

//        sqlSessionFactory = buildSqlSessionFactoryByConfig();
    }

    private SqlSessionFactory buildSqlSessionFactoryByXml(String resource) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    private void addMapper(Configuration configuration,Class<?> type){
        //此种方式若mapper接口路径下没有xml文件，则需要在mapper接口方法上添加@Select等注解
        // @Select("SELECT * FROM t_news")
        configuration.addMapper(type);//手动添加mapper
    }

    private SqlSessionFactory buildSqlSessionFactoryByConfig(){
        DataSource dataSource = new PooledDataSource("com.mysql.jdbc.Driver","jdbc:mysql://192.168.4.30:3306/meta_boot?characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&useSSL=false","root","root@hiekn");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment.Builder("development").transactionFactory(transactionFactory).dataSource(dataSource).build();
        Configuration configuration = new Configuration(environment);
        addMapper(configuration,NewsMapper.class);
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    /**
     * #{} 预编译
     * ${} 字符串拼接
     *
     * mybatis提供了缓存机制减轻数据库压力，提高数据库性能
     *
     * mybatis的缓存分为两级：一级缓存、二级缓存
     * 一级缓存是SqlSession级别的缓存，缓存的数据只在SqlSession内有效
     * 二级缓存是mapper级别的缓存，同一个namespace公用这一个缓存，所以对SqlSession是共享的
     */
    @Test
    public void testSelect(){
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            //方式一：Good
            NewsMapper mapper = sqlSession.getMapper(NewsMapper.class);
            List<Map<String,Object>> list = mapper.selectTitle();
            log.info("{}",list);

            //方式二
            Map<Object, Object> map2 = sqlSession.selectOne(NewsMapper.class.getName() + ".selectTitle");
            log.info("{}",map2);
        }
    }


}
