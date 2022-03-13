package com.flink.app.bean;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class OrderDoneLogRecord {

    private String merchandiseId;
    private String Price;
    private String CouponMoney;
    private String RebateAmount;

    public String getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(String merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCouponMoney() {
        return CouponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        CouponMoney = couponMoney;
    }

    public String getRebateAmount() {
        return RebateAmount;
    }

    public void setRebateAmount(String rebateAmount) {
        RebateAmount = rebateAmount;
    }

}
