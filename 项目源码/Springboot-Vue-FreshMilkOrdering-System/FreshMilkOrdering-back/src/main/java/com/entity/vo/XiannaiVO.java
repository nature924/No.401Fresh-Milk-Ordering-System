package com.entity.vo;

import com.entity.XiannaiEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 商品
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("xiannai")
public class XiannaiVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 商家
     */

    @TableField(value = "shangjia_id")
    private Integer shangjiaId;


    /**
     * 商品名称
     */

    @TableField(value = "xiannai_name")
    private String xiannaiName;


    /**
     * 商品照片
     */

    @TableField(value = "xiannai_photo")
    private String xiannaiPhoto;


    /**
     * 商品类型
     */

    @TableField(value = "xiannai_types")
    private Integer xiannaiTypes;


    /**
     * 剩余订购数量
     */

    @TableField(value = "xiannai_kucun_number")
    private Integer xiannaiKucunNumber;


    /**
     * 商品原价
     */

    @TableField(value = "xiannai_old_money")
    private Double xiannaiOldMoney;


    /**
     * 现价/月
     */

    @TableField(value = "xiannai_new_money")
    private Double xiannaiNewMoney;


    /**
     * 保质期
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "xiannai_time")
    private Date xiannaiTime;


    /**
     * 点击次数
     */

    @TableField(value = "xiannai_clicknum")
    private Integer xiannaiClicknum;


    /**
     * 是否上架
     */

    @TableField(value = "shangxia_types")
    private Integer shangxiaTypes;


    /**
     * 逻辑删除
     */

    @TableField(value = "xiannai_delete")
    private Integer xiannaiDelete;


    /**
     * 商品简介
     */

    @TableField(value = "xiannai_content")
    private String xiannaiContent;


    /**
     * 创建时间  show1 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：商家
	 */
    public Integer getShangjiaId() {
        return shangjiaId;
    }


    /**
	 * 获取：商家
	 */

    public void setShangjiaId(Integer shangjiaId) {
        this.shangjiaId = shangjiaId;
    }
    /**
	 * 设置：商品名称
	 */
    public String getXiannaiName() {
        return xiannaiName;
    }


    /**
	 * 获取：商品名称
	 */

    public void setXiannaiName(String xiannaiName) {
        this.xiannaiName = xiannaiName;
    }
    /**
	 * 设置：商品照片
	 */
    public String getXiannaiPhoto() {
        return xiannaiPhoto;
    }


    /**
	 * 获取：商品照片
	 */

    public void setXiannaiPhoto(String xiannaiPhoto) {
        this.xiannaiPhoto = xiannaiPhoto;
    }
    /**
	 * 设置：商品类型
	 */
    public Integer getXiannaiTypes() {
        return xiannaiTypes;
    }


    /**
	 * 获取：商品类型
	 */

    public void setXiannaiTypes(Integer xiannaiTypes) {
        this.xiannaiTypes = xiannaiTypes;
    }
    /**
	 * 设置：剩余订购数量
	 */
    public Integer getXiannaiKucunNumber() {
        return xiannaiKucunNumber;
    }


    /**
	 * 获取：剩余订购数量
	 */

    public void setXiannaiKucunNumber(Integer xiannaiKucunNumber) {
        this.xiannaiKucunNumber = xiannaiKucunNumber;
    }
    /**
	 * 设置：商品原价
	 */
    public Double getXiannaiOldMoney() {
        return xiannaiOldMoney;
    }


    /**
	 * 获取：商品原价
	 */

    public void setXiannaiOldMoney(Double xiannaiOldMoney) {
        this.xiannaiOldMoney = xiannaiOldMoney;
    }
    /**
	 * 设置：现价/月
	 */
    public Double getXiannaiNewMoney() {
        return xiannaiNewMoney;
    }


    /**
	 * 获取：现价/月
	 */

    public void setXiannaiNewMoney(Double xiannaiNewMoney) {
        this.xiannaiNewMoney = xiannaiNewMoney;
    }
    /**
	 * 设置：保质期
	 */
    public Date getXiannaiTime() {
        return xiannaiTime;
    }


    /**
	 * 获取：保质期
	 */

    public void setXiannaiTime(Date xiannaiTime) {
        this.xiannaiTime = xiannaiTime;
    }
    /**
	 * 设置：点击次数
	 */
    public Integer getXiannaiClicknum() {
        return xiannaiClicknum;
    }


    /**
	 * 获取：点击次数
	 */

    public void setXiannaiClicknum(Integer xiannaiClicknum) {
        this.xiannaiClicknum = xiannaiClicknum;
    }
    /**
	 * 设置：是否上架
	 */
    public Integer getShangxiaTypes() {
        return shangxiaTypes;
    }


    /**
	 * 获取：是否上架
	 */

    public void setShangxiaTypes(Integer shangxiaTypes) {
        this.shangxiaTypes = shangxiaTypes;
    }
    /**
	 * 设置：逻辑删除
	 */
    public Integer getXiannaiDelete() {
        return xiannaiDelete;
    }


    /**
	 * 获取：逻辑删除
	 */

    public void setXiannaiDelete(Integer xiannaiDelete) {
        this.xiannaiDelete = xiannaiDelete;
    }
    /**
	 * 设置：商品简介
	 */
    public String getXiannaiContent() {
        return xiannaiContent;
    }


    /**
	 * 获取：商品简介
	 */

    public void setXiannaiContent(String xiannaiContent) {
        this.xiannaiContent = xiannaiContent;
    }
    /**
	 * 设置：创建时间  show1 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间  show1 show2 photoShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
