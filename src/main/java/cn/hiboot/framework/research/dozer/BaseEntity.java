package cn.hiboot.framework.research.dozer;

import java.util.Date;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/6/2 23:21
 */
public abstract class BaseEntity {
    private String id;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
