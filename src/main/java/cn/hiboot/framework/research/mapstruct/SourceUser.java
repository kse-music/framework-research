package cn.hiboot.framework.research.mapstruct;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2020/8/16 12:43
 */
@Setter
@Getter
public class SourceUser {
    private int age;
    private String name;
    private Object obj;
    private Date date;
    private UserTypeEnum userTypeEnum;
    private Address address;
    private List<Address> addresses;

}
