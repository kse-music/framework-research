package cn.hiboot.framework.research.netty.order;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/8/22 10:46
 */
public class Order {

    private long orderNumber;
    private Customer customer;
    private Address billTo;
    private Shipping shipping;
    private Address shipTo;
    private Float total;

    public long getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(long orderId) {
        this.orderNumber = orderId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Address getBillTo() {
        return billTo;
    }
    public void setBillTo(Address billTo) {
        this.billTo = billTo;
    }
    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }
    public Address getShipTo() {
        return shipTo;
    }
    public void setShipTo(Address shipTo) {
        this.shipTo = shipTo;
    }

    public Float getTotal() {
        return total;
    }
    public void setTotal(Float total) {
        this.total = total;
    }
}