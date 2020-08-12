package cn.hiboot.framework.research.dozer;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/6/2 23:21
 */
public class ContacterDO extends BaseEntity {
    private String name;

    private String sex;
    private Integer age ;
    private  String phone;

    private String location;

    private String dflag ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDflag() {
        return dflag;
    }

    public void setDflag(String dflag) {
        this.dflag = dflag;
    }
}
