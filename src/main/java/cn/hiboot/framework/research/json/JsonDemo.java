package cn.hiboot.framework.research.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonDemo {
	
    @DisplayName("FastJson与Gson区别")
    @ParameterizedTest
    @CsvSource({"20,json","30,gson"})
	public void json(int age, String name) throws Exception{
		StudentBean a = new StudentBean();
		a.setAge(age);
		a.setName(name);

		String fastRs = JSON.toJSONString(a);//根据get后面的属性
		System.out.println(fastRs);

		String gsonRs = new Gson().toJson(a);//根据字段属性
		System.out.println(gsonRs);
		assertNotNull(fastRs);

		ObjectMapper mapper = new ObjectMapper();//根据get后面的属性
		String jsonString = mapper.writeValueAsString(a);
		System.out.println(jsonString);
    }

    @DisplayName("FastJson与Gson区别")
    @ParameterizedTest
    @CsvSource(value = {"{\"name\":\"json\",\"age2\":20}"},delimiter = ';')
	public void deJson(String str) throws Exception{
		//反序列化用字段名

		StudentBean fastRs = JSON.parseObject(str,StudentBean.class);
		System.out.println(fastRs);

		StudentBean gsonRs = new Gson().fromJson(str,StudentBean.class);
		System.out.println(gsonRs);

		ObjectMapper mapper = new ObjectMapper();
		//在反序列化时忽略在 JSON 中存在但 Java 对象不存在的属性
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		//在序列化时日期格式默认为 yyyy-MM-dd'T'HH:mm:ss.SSSZ
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
		//在序列化时忽略值为 null 的属性
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		//忽略值为默认值的属性
		mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
		StudentBean studentBean = mapper.readValue(str, StudentBean.class);
		System.out.println(studentBean);
    }

}