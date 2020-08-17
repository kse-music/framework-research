package cn.hiboot.framework.research.mapstruct;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2020/8/16 12:43
 */
@Setter
@Getter
public class TargetUser {
    private Integer age;
    private String className;
    private Map<Object,Object> obj;
    private String date;
    private String constant;
    private String type;
    private Address address;
    private List<Address> addresses;


}
