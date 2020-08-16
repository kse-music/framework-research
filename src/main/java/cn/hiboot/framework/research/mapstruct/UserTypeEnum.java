package cn.hiboot.framework.research.mapstruct;

import lombok.Getter;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2020/8/16 15:10
 */
@Getter
public enum UserTypeEnum {
    Java("000", "Java开发工程师"),
    DB("001", "数据库管理员"),
    LINUX("002", "Linux运维员");

    private String value;
    private String title;

    UserTypeEnum(String value, String title) {
        this.value = value;
        this.title = title;
    }
}
