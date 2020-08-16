package cn.hiboot.framework.research.mapstruct;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/6/24 14:19
 */
public class MapStructDemo {

    @Test
    public void map(){
        UserStruct userStruct = UserStruct.INSTANCE;
        SourceUser sourceUser = new SourceUser();
        sourceUser.setName("name");
        sourceUser.setAge(30);
        sourceUser.setDate(new Date());
        sourceUser.setUserTypeEnum(UserTypeEnum.Java);
        Address address = new Address();
        address.setProvince("anhui");
        address.setCity("tongLing");
        sourceUser.setAddress(address);
        TargetUser targetUser = userStruct.convert(sourceUser);
        System.out.println(targetUser);
    }


}
