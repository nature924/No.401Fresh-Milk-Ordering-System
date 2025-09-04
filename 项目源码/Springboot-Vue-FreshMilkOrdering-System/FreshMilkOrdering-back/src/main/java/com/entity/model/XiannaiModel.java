package com.entity.model;

import com.entity.XiannaiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 商品
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class XiannaiModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 商家
     */
    private Integer shangjiaId;


    /**
     * 商品名称
     */
    private String xiannaiName;


    /**
     * 商品照片
     */
    private String xiannaiPhoto;


    /**
     * 商品类型
     */
    private Integer xiannaiTypes;


    /**
     * 剩余订购数量
     */
    private Integer xiannaiKucunNumber;


    /**
     * 商品原价
     */
    private Double xiannaiOldMoney;


    /**
     * 现价/月
     */
    private Double xiannaiNewMoney;


    /**
     * 保质期
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date xiannaiTime;


    /**
     * 点击次数
     */
    private Integer xiannaiClicknum;


    /**
     * 是否上架
     */
    private Integer shangxiaTypes;


    /**
     * 逻辑删除
     */
    private Integer xiannaiDelete;


    /**
     * 商品简介
     */
    private String xiannaiContent;


    /**
     * 创建时间  show1 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：商家
	 */
    public Integer getShangjiaId() {
        return shangjiaId;
    }


    /**
	 * 设置：商家
	 */
    public void setShangjiaId(Integer shangjiaId) {
        this.shangjiaId = shangjiaId;
    }
    /**
	 * 获取：商品名称
	 */
    public String getXiannaiName() {
        return xiannaiName;
    }


    /**
	 * 设置：商品名称
	 */
    public void setXiannaiName(String xiannaiName) {
        this.xiannaiName = xiannaiName;
    }
    /**
	 * 获取：商品照片
	 */
    public String getXiannaiPhoto() {
        return xiannaiPhoto;
    }


    /**
	 * 设置：商品照片
	 */
    public void setXiannaiPhoto(String xiannaiPhoto) {
        this.xiannaiPhoto = xiannaiPhoto;
    }
    /**
	 * 获取：商品类型
	 */
    public Integer getXiannaiTypes() {
        return xiannaiTypes;
    }


    /**
	 * 设置：商品类型
	 */
    public void setXiannaiTypes(Integer xiannaiTypes) {
        this.xiannaiTypes = xiannaiTypes;
    }
    /**
	 * 获取：剩余订购数量
	 */
    public Integer getXiannaiKucunNumber() {
        return xiannaiKucunNumber;
    }


    /**
	 * 设置：剩余订购数量
	 */
    public void setXiannaiKucunNumber(Integer xiannaiKucunNumber) {
        this.xiannaiKucunNumber = xiannaiKucunNumber;
    }
    /**
	 * 获取：商品原价
	 */
    public Double getXiannaiOldMoney() {
        return xiannaiOldMoney;
    }


    /**
	 * 设置：商品原价
	 */
    public void setXiannaiOldMoney(Double xiannaiOldMoney) {
        this.xiannaiOldMoney = xiannaiOldMoney;
    }
    /**
	 * 获取：现价/月
	 */
    public Double getXiannaiNewMoney() {
        return xiannaiNewMoney;
    }


    /**
	 * 设置：现价/月
	 */
    public void setXiannaiNewMoney(Double xiannaiNewMoney) {
        this.xiannaiNewMoney = xiannaiNewMoney;
    }
    /**
	 * 获取：保质期
	 */
    public Date getXiannaiTime() {
        return xiannaiTime;
    }


    /**
	 * 设置：保质期
	 */
    public void setXiannaiTime(Date xiannaiTime) {
        this.xiannaiTime = xiannaiTime;
    }
    /**
	 * 获取：点击次数
	 */
    public Integer getXiannaiClicknum() {
        return xiannaiClicknum;
    }


    /**
	 * 设置：点击次数
	 */
    public void setXiannaiClicknum(Integer xiannaiClicknum) {
        this.xiannaiClicknum = xiannaiClicknum;
    }
    /**
	 * 获取：是否上架
	 */
    public Integer getShangxiaTypes() {
        return shangxiaTypes;
    }


    /**
	 * 设置：是否上架
	 */
    public void setShangxiaTypes(Integer shangxiaTypes) {
        this.shangxiaTypes = shangxiaTypes;
    }
    /**
	 * 获取：逻辑删除
	 */
    public Integer getXiannaiDelete() {
        return xiannaiDelete;
    }


    /**
	 * 设置：逻辑删除
	 */
    public void setXiannaiDelete(Integer xiannaiDelete) {
        this.xiannaiDelete = xiannaiDelete;
    }
    /**
	 * 获取：商品简介
	 */
    public String getXiannaiContent() {
        return xiannaiContent;
    }


    /**
	 * 设置：商品简介
	 */
    public void setXiannaiContent(String xiannaiContent) {
        this.xiannaiContent = xiannaiContent;
    }
    /**
	 * 获取：创建时间  show1 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间  show1 show2 photoShow
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
