package cn.hiboot.framework.research.netty.pvt;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/8/28 13:51
 */
public enum ResultType {
    /**
     * 认证成功
     */
    SUCCESS((byte) 0),
    /**
     * 认证失败
     */
    FAIL((byte) -1),


    ;

    private byte value;

    ResultType(byte value) {
        this.value = value;
    }

    public byte value() {
        return this.value;
    }
}