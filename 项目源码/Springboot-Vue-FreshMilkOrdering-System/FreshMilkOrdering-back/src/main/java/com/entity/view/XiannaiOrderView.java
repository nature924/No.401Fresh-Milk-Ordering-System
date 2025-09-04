package com.entity.view;

import com.entity.XiannaiOrderEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品订单
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("xiannai_order")
public class XiannaiOrderView extends XiannaiOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

		/**
		* 订单类型的值
		*/
		private String xiannaiOrderValue;
		/**
		* 支付类型的值
		*/
		private String xiannaiOrderPaymentValue;



		//级联表 address
			/**
			* 送货地址 的 创建用户
			*/
			private Integer addressYonghuId;
			/**
			* 收货人
			*/
			private String addressName;
			/**
			* 电话
			*/
			private String addressPhone;
			/**
			* 地址
			*/
			private String addressDizhi;
			/**
			* 是否默认地址
			*/
			private Integer isdefaultTypes;
				/**
				* 是否默认地址的值
				*/
				private String isdefaultValue;

		//级联表 xiannai
			/**
			* 商品 的 商家
			*/
			private Integer xiannaiShangjiaId;
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
				* 商品类型的值
				*/
				private String xiannaiValue;
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
				* 是否上架的值
				*/
				private String shangxiaValue;
			/**
			* 逻辑删除
			*/
			private Integer xiannaiDelete;
			/**
			* 商品简介
			*/
			private String xiannaiContent;

		//级联表 yonghu
			/**
			* 用户姓名
			*/
			private String yonghuName;
			/**
			* 用户手机号
			*/
			private String yonghuPhone;
			/**
			* 用户身份证号
			*/
			private String yonghuIdNumber;
			/**
			* 用户头像
			*/
			private String yonghuPhoto;
			/**
			* 电子邮箱
			*/
			private String yonghuEmail;
			/**
			* 余额
			*/
			private Double newMoney;

	public XiannaiOrderView() {

	}

	public XiannaiOrderView(XiannaiOrderEntity xiannaiOrderEntity) {
		try {
			BeanUtils.copyProperties(this, xiannaiOrderEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



			/**
			* 获取： 订单类型的值
			*/
			public String getXiannaiOrderValue() {
				return xiannaiOrderValue;
			}
			/**
			* 设置： 订单类型的值
			*/
			public void setXiannaiOrderValue(String xiannaiOrderValue) {
				this.xiannaiOrderValue = xiannaiOrderValue;
			}
			/**
			* 获取： 支付类型的值
			*/
			public String getXiannaiOrderPaymentValue() {
				return xiannaiOrderPaymentValue;
			}
			/**
			* 设置： 支付类型的值
			*/
			public void setXiannaiOrderPaymentValue(String xiannaiOrderPaymentValue) {
				this.xiannaiOrderPaymentValue = xiannaiOrderPaymentValue;
			}




				//级联表的get和set address

					/**
					* 获取：送货地址 的 创建用户
					*/
					public Integer getAddressYonghuId() {
						return addressYonghuId;
					}
					/**
					* 设置：送货地址 的 创建用户
					*/
					public void setAddressYonghuId(Integer addressYonghuId) {
						this.addressYonghuId = addressYonghuId;
					}


					/**
					* 获取： 收货人
					*/
					public String getAddressName() {
						return addressName;
					}
					/**
					* 设置： 收货人
					*/
					public void setAddressName(String addressName) {
						this.addressName = addressName;
					}

					/**
					* 获取： 电话
					*/
					public String getAddressPhone() {
						return addressPhone;
					}
					/**
					* 设置： 电话
					*/
					public void setAddressPhone(String addressPhone) {
						this.addressPhone = addressPhone;
					}

					/**
					* 获取： 地址
					*/
					public String getAddressDizhi() {
						return addressDizhi;
					}
					/**
					* 设置： 地址
					*/
					public void setAddressDizhi(String addressDizhi) {
						this.addressDizhi = addressDizhi;
					}

					/**
					* 获取： 是否默认地址
					*/
					public Integer getIsdefaultTypes() {
						return isdefaultTypes;
					}
					/**
					* 设置： 是否默认地址
					*/
					public void setIsdefaultTypes(Integer isdefaultTypes) {
						this.isdefaultTypes = isdefaultTypes;
					}


						/**
						* 获取： 是否默认地址的值
						*/
						public String getIsdefaultValue() {
							return isdefaultValue;
						}
						/**
						* 设置： 是否默认地址的值
						*/
						public void setIsdefaultValue(String isdefaultValue) {
							this.isdefaultValue = isdefaultValue;
						}





















				//级联表的get和set xiannai

					/**
					* 获取：商品 的 商家
					*/
					public Integer getXiannaiShangjiaId() {
						return xiannaiShangjiaId;
					}
					/**
					* 设置：商品 的 商家
					*/
					public void setXiannaiShangjiaId(Integer xiannaiShangjiaId) {
						this.xiannaiShangjiaId = xiannaiShangjiaId;
					}


					/**
					* 获取： 商品名称
					*/
					public String getXiannaiName() {
						return xiannaiName;
					}
					/**
					* 设置： 商品名称
					*/
					public void setXiannaiName(String xiannaiName) {
						this.xiannaiName = xiannaiName;
					}

					/**
					* 获取： 商品照片
					*/
					public String getXiannaiPhoto() {
						return xiannaiPhoto;
					}
					/**
					* 设置： 商品照片
					*/
					public void setXiannaiPhoto(String xiannaiPhoto) {
						this.xiannaiPhoto = xiannaiPhoto;
					}

					/**
					* 获取： 商品类型
					*/
					public Integer getXiannaiTypes() {
						return xiannaiTypes;
					}
					/**
					* 设置： 商品类型
					*/
					public void setXiannaiTypes(Integer xiannaiTypes) {
						this.xiannaiTypes = xiannaiTypes;
					}


						/**
						* 获取： 商品类型的值
						*/
						public String getXiannaiValue() {
							return xiannaiValue;
						}
						/**
						* 设置： 商品类型的值
						*/
						public void setXiannaiValue(String xiannaiValue) {
							this.xiannaiValue = xiannaiValue;
						}

					/**
					* 获取： 剩余订购数量
					*/
					public Integer getXiannaiKucunNumber() {
						return xiannaiKucunNumber;
					}
					/**
					* 设置： 剩余订购数量
					*/
					public void setXiannaiKucunNumber(Integer xiannaiKucunNumber) {
						this.xiannaiKucunNumber = xiannaiKucunNumber;
					}

					/**
					* 获取： 商品原价
					*/
					public Double getXiannaiOldMoney() {
						return xiannaiOldMoney;
					}
					/**
					* 设置： 商品原价
					*/
					public void setXiannaiOldMoney(Double xiannaiOldMoney) {
						this.xiannaiOldMoney = xiannaiOldMoney;
					}

					/**
					* 获取： 现价/月
					*/
					public Double getXiannaiNewMoney() {
						return xiannaiNewMoney;
					}
					/**
					* 设置： 现价/月
					*/
					public void setXiannaiNewMoney(Double xiannaiNewMoney) {
						this.xiannaiNewMoney = xiannaiNewMoney;
					}

					/**
					* 获取： 保质期
					*/
					public Date getXiannaiTime() {
						return xiannaiTime;
					}
					/**
					* 设置： 保质期
					*/
					public void setXiannaiTime(Date xiannaiTime) {
						this.xiannaiTime = xiannaiTime;
					}

					/**
					* 获取： 点击次数
					*/
					public Integer getXiannaiClicknum() {
						return xiannaiClicknum;
					}
					/**
					* 设置： 点击次数
					*/
					public void setXiannaiClicknum(Integer xiannaiClicknum) {
						this.xiannaiClicknum = xiannaiClicknum;
					}

					/**
					* 获取： 是否上架
					*/
					public Integer getShangxiaTypes() {
						return shangxiaTypes;
					}
					/**
					* 设置： 是否上架
					*/
					public void setShangxiaTypes(Integer shangxiaTypes) {
						this.shangxiaTypes = shangxiaTypes;
					}


						/**
						* 获取： 是否上架的值
						*/
						public String getShangxiaValue() {
							return shangxiaValue;
						}
						/**
						* 设置： 是否上架的值
						*/
						public void setShangxiaValue(String shangxiaValue) {
							this.shangxiaValue = shangxiaValue;
						}

					/**
					* 获取： 逻辑删除
					*/
					public Integer getXiannaiDelete() {
						return xiannaiDelete;
					}
					/**
					* 设置： 逻辑删除
					*/
					public void setXiannaiDelete(Integer xiannaiDelete) {
						this.xiannaiDelete = xiannaiDelete;
					}

					/**
					* 获取： 商品简介
					*/
					public String getXiannaiContent() {
						return xiannaiContent;
					}
					/**
					* 设置： 商品简介
					*/
					public void setXiannaiContent(String xiannaiContent) {
						this.xiannaiContent = xiannaiContent;
					}

















				//级联表的get和set yonghu

					/**
					* 获取： 用户姓名
					*/
					public String getYonghuName() {
						return yonghuName;
					}
					/**
					* 设置： 用户姓名
					*/
					public void setYonghuName(String yonghuName) {
						this.yonghuName = yonghuName;
					}

					/**
					* 获取： 用户手机号
					*/
					public String getYonghuPhone() {
						return yonghuPhone;
					}
					/**
					* 设置： 用户手机号
					*/
					public void setYonghuPhone(String yonghuPhone) {
						this.yonghuPhone = yonghuPhone;
					}

					/**
					* 获取： 用户身份证号
					*/
					public String getYonghuIdNumber() {
						return yonghuIdNumber;
					}
					/**
					* 设置： 用户身份证号
					*/
					public void setYonghuIdNumber(String yonghuIdNumber) {
						this.yonghuIdNumber = yonghuIdNumber;
					}

					/**
					* 获取： 用户头像
					*/
					public String getYonghuPhoto() {
						return yonghuPhoto;
					}
					/**
					* 设置： 用户头像
					*/
					public void setYonghuPhoto(String yonghuPhoto) {
						this.yonghuPhoto = yonghuPhoto;
					}

					/**
					* 获取： 电子邮箱
					*/
					public String getYonghuEmail() {
						return yonghuEmail;
					}
					/**
					* 设置： 电子邮箱
					*/
					public void setYonghuEmail(String yonghuEmail) {
						this.yonghuEmail = yonghuEmail;
					}

					/**
					* 获取： 余额
					*/
					public Double getNewMoney() {
						return newMoney;
					}
					/**
					* 设置： 余额
					*/
					public void setNewMoney(Double newMoney) {
						this.newMoney = newMoney;
					}




}
