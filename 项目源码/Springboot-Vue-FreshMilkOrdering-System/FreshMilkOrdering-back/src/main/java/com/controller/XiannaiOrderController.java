
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 商品订单
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/xiannaiOrder")
public class XiannaiOrderController {
    private static final Logger logger = LoggerFactory.getLogger(XiannaiOrderController.class);

    @Autowired
    private XiannaiOrderService xiannaiOrderService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private AddressService addressService;
    @Autowired
    private XiannaiService xiannaiService;
    @Autowired
    private YonghuService yonghuService;
@Autowired
private CartService cartService;
@Autowired
private ShangjiaService shangjiaService;
@Autowired
private XiannaiCommentbackService xiannaiCommentbackService;



    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("商家".equals(role))
            params.put("shangjiaId",request.getSession().getAttribute("userId"));
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = xiannaiOrderService.queryPage(params);

        //字典表数据转换
        List<XiannaiOrderView> list =(List<XiannaiOrderView>)page.getList();
        for(XiannaiOrderView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        XiannaiOrderEntity xiannaiOrder = xiannaiOrderService.selectById(id);
        if(xiannaiOrder !=null){
            //entity转view
            XiannaiOrderView view = new XiannaiOrderView();
            BeanUtils.copyProperties( xiannaiOrder , view );//把实体数据重构到view中

                //级联表
                AddressEntity address = addressService.selectById(xiannaiOrder.getAddressId());
                if(address != null){
                    BeanUtils.copyProperties( address , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setAddressId(address.getId());
                    view.setAddressYonghuId(address.getYonghuId());
                }
                //级联表
                XiannaiEntity xiannai = xiannaiService.selectById(xiannaiOrder.getXiannaiId());
                if(xiannai != null){
                    BeanUtils.copyProperties( xiannai , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setXiannaiId(xiannai.getId());
                }
                //级联表
                YonghuEntity yonghu = yonghuService.selectById(xiannaiOrder.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody XiannaiOrderEntity xiannaiOrder, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,xiannaiOrder:{}",this.getClass().getName(),xiannaiOrder.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("用户".equals(role))
            xiannaiOrder.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        xiannaiOrder.setInsertTime(new Date());
        xiannaiOrder.setCreateTime(new Date());
        xiannaiOrderService.insert(xiannaiOrder);
        return R.ok();
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody XiannaiOrderEntity xiannaiOrder, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,xiannaiOrder:{}",this.getClass().getName(),xiannaiOrder.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("用户".equals(role))
//            xiannaiOrder.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<XiannaiOrderEntity> queryWrapper = new EntityWrapper<XiannaiOrderEntity>()
            .eq("id",0)
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XiannaiOrderEntity xiannaiOrderEntity = xiannaiOrderService.selectOne(queryWrapper);
        if(xiannaiOrderEntity==null){
            xiannaiOrderService.updateById(xiannaiOrder);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        xiannaiOrderService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        try {
            List<XiannaiOrderEntity> xiannaiOrderList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            XiannaiOrderEntity xiannaiOrderEntity = new XiannaiOrderEntity();
//                            xiannaiOrderEntity.setXiannaiOrderUuidNumber(data.get(0));                    //订单号 要改的
//                            xiannaiOrderEntity.setAddressId(Integer.valueOf(data.get(0)));   //送货地址 要改的
//                            xiannaiOrderEntity.setXiannaiId(Integer.valueOf(data.get(0)));   //商品 要改的
//                            xiannaiOrderEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            xiannaiOrderEntity.setBuyNumber(Integer.valueOf(data.get(0)));   //订购数量 要改的
//                            xiannaiOrderEntity.setXiannaiOrderTruePrice(data.get(0));                    //实付价格 要改的
//                            xiannaiOrderEntity.setXiannaiOrderTypes(Integer.valueOf(data.get(0)));   //订单类型 要改的
//                            xiannaiOrderEntity.setXiannaiOrderPaymentTypes(Integer.valueOf(data.get(0)));   //支付类型 要改的
//                            xiannaiOrderEntity.setInsertTime(date);//时间
//                            xiannaiOrderEntity.setCreateTime(date);//时间
                            xiannaiOrderList.add(xiannaiOrderEntity);


                            //把要查询是否重复的字段放入map中
                                //订单号
                                if(seachFields.containsKey("xiannaiOrderUuidNumber")){
                                    List<String> xiannaiOrderUuidNumber = seachFields.get("xiannaiOrderUuidNumber");
                                    xiannaiOrderUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> xiannaiOrderUuidNumber = new ArrayList<>();
                                    xiannaiOrderUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("xiannaiOrderUuidNumber",xiannaiOrderUuidNumber);
                                }
                        }

                        //查询是否重复
                         //订单号
                        List<XiannaiOrderEntity> xiannaiOrderEntities_xiannaiOrderUuidNumber = xiannaiOrderService.selectList(new EntityWrapper<XiannaiOrderEntity>().in("xiannai_order_uuid_number", seachFields.get("xiannaiOrderUuidNumber")));
                        if(xiannaiOrderEntities_xiannaiOrderUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(XiannaiOrderEntity s:xiannaiOrderEntities_xiannaiOrderUuidNumber){
                                repeatFields.add(s.getXiannaiOrderUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [订单号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        xiannaiOrderService.insertBatch(xiannaiOrderList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }





    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        // 没有指定排序字段就默认id倒序
        if(StringUtil.isEmpty(String.valueOf(params.get("orderBy")))){
            params.put("orderBy","id");
        }
        PageUtils page = xiannaiOrderService.queryPage(params);

        //字典表数据转换
        List<XiannaiOrderView> list =(List<XiannaiOrderView>)page.getList();
        for(XiannaiOrderView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        XiannaiOrderEntity xiannaiOrder = xiannaiOrderService.selectById(id);
            if(xiannaiOrder !=null){


                //entity转view
                XiannaiOrderView view = new XiannaiOrderView();
                BeanUtils.copyProperties( xiannaiOrder , view );//把实体数据重构到view中

                //级联表
                    AddressEntity address = addressService.selectById(xiannaiOrder.getAddressId());
                if(address != null){
                    BeanUtils.copyProperties( address , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setAddressId(address.getId());
                }
                //级联表
                    XiannaiEntity xiannai = xiannaiService.selectById(xiannaiOrder.getXiannaiId());
                if(xiannai != null){
                    BeanUtils.copyProperties( xiannai , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setXiannaiId(xiannai.getId());
                }
                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(xiannaiOrder.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody XiannaiOrderEntity xiannaiOrder, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,xiannaiOrder:{}",this.getClass().getName(),xiannaiOrder.toString());
            XiannaiEntity xiannaiEntity = xiannaiService.selectById(xiannaiOrder.getXiannaiId());
            if(xiannaiEntity == null){
                return R.error(511,"查不到该商品");
            }
            // Double xiannaiNewMoney = xiannaiEntity.getXiannaiNewMoney();

            if(false){
            }
            else if((xiannaiEntity.getXiannaiKucunNumber() -xiannaiOrder.getBuyNumber())<0){
                return R.error(511,"购买数量不能大于库存数量");
            }
            else if(xiannaiEntity.getXiannaiNewMoney() == null){
                return R.error(511,"商品价格不能为空");
            }

            //计算所获得积分
            Double buyJifen =0.0;
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            xiannaiOrder.setXiannaiOrderTypes(3); //设置订单状态为已支付
            xiannaiOrder.setXiannaiOrderTruePrice(0.0); //设置实付价格
            xiannaiOrder.setYonghuId(userId); //设置订单支付人id
            xiannaiOrder.setXiannaiOrderUuidNumber(String.valueOf(new Date().getTime()));
            xiannaiOrder.setXiannaiOrderPaymentTypes(1);
            xiannaiOrder.setInsertTime(new Date());
            xiannaiOrder.setCreateTime(new Date());
                xiannaiEntity.setXiannaiKucunNumber( xiannaiEntity.getXiannaiKucunNumber() -xiannaiOrder.getBuyNumber());
                xiannaiService.updateById(xiannaiEntity);
                xiannaiOrderService.insert(xiannaiOrder);//新增订单
            return R.ok();
    }
    /**
     * 添加订单
     */
    @RequestMapping("/order")
    public R add(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("order方法:,,Controller:{},,params:{}",this.getClass().getName(),params.toString());
        String xiannaiOrderUuidNumber = String.valueOf(new Date().getTime());

        //获取当前登录用户的id
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        Integer addressId = Integer.valueOf(String.valueOf(params.get("addressId")));

        Integer xiannaiOrderPaymentTypes = Integer.valueOf(String.valueOf(params.get("xiannaiOrderPaymentTypes")));//支付类型

        String data = String.valueOf(params.get("xiannais"));
        JSONArray jsonArray = JSON.parseArray(data);
        List<Map> xiannais = JSON.parseObject(jsonArray.toString(), List.class);

        //获取当前登录用户的个人信息
        YonghuEntity yonghuEntity = yonghuService.selectById(userId);

        //当前订单表
        List<XiannaiOrderEntity> xiannaiOrderList = new ArrayList<>();
        //商家表
        ArrayList<ShangjiaEntity> shangjiaList = new ArrayList<>();
        //商品表
        List<XiannaiEntity> xiannaiList = new ArrayList<>();
        //购物车ids
        List<Integer> cartIds = new ArrayList<>();

        BigDecimal zhekou = new BigDecimal(1.0);

        //循环取出需要的数据
        for (Map<String, Object> map : xiannais) {
           //取值
            Integer xiannaiId = Integer.valueOf(String.valueOf(map.get("xiannaiId")));//商品id
            Integer buyNumber = Integer.valueOf(String.valueOf(map.get("buyNumber")));//购买数量
            XiannaiEntity xiannaiEntity = xiannaiService.selectById(xiannaiId);//购买的商品
            String id = String.valueOf(map.get("id"));
            if(StringUtil.isNotEmpty(id))
                cartIds.add(Integer.valueOf(id));

            //获取商家信息
            Integer shangjiaId = xiannaiEntity.getShangjiaId();
            ShangjiaEntity shangjiaEntity = shangjiaService.selectById(shangjiaId);//商家

            //判断商品的库存是否足够
            if(xiannaiEntity.getXiannaiKucunNumber() < buyNumber){
                //商品库存不足直接返回
                return R.error(xiannaiEntity.getXiannaiName()+"的库存不足");
            }else{
                //商品库存充足就减库存
                xiannaiEntity.setXiannaiKucunNumber(xiannaiEntity.getXiannaiKucunNumber() - buyNumber);
            }

            //订单信息表增加数据
            XiannaiOrderEntity xiannaiOrderEntity = new XiannaiOrderEntity<>();

            //赋值订单信息
            xiannaiOrderEntity.setXiannaiOrderUuidNumber(xiannaiOrderUuidNumber);//订单号
            xiannaiOrderEntity.setAddressId(addressId);//送货地址
            xiannaiOrderEntity.setXiannaiId(xiannaiId);//商品
            xiannaiOrderEntity.setYonghuId(userId);//用户
            xiannaiOrderEntity.setBuyNumber(buyNumber);//订购数量 ？？？？？？
            xiannaiOrderEntity.setXiannaiOrderTypes(3);//订单类型
            xiannaiOrderEntity.setXiannaiOrderPaymentTypes(xiannaiOrderPaymentTypes);//支付类型
            xiannaiOrderEntity.setInsertTime(new Date());//订购时间
            xiannaiOrderEntity.setCreateTime(new Date());//创建时间

            //判断是什么支付方式 1代表余额 2代表积分
            if(xiannaiOrderPaymentTypes == 1){//余额支付
                //计算金额
                Double money = new BigDecimal(xiannaiEntity.getXiannaiNewMoney()).multiply(new BigDecimal(buyNumber)).multiply(zhekou).doubleValue();

                if(yonghuEntity.getNewMoney() - money <0 ){
                    return R.error("余额不足,请充值！！！");
                }else{
                    //计算所获得积分
                    Double buyJifen =0.0;


                    xiannaiOrderEntity.setXiannaiOrderTruePrice(money);

                    //修改商家余额
                    shangjiaEntity.setNewMoney(shangjiaEntity.getNewMoney()+money);
                }
            }
            xiannaiOrderList.add(xiannaiOrderEntity);
            shangjiaList.add(shangjiaEntity);
            xiannaiList.add(xiannaiEntity);

        }
        xiannaiOrderService.insertBatch(xiannaiOrderList);
        shangjiaService.updateBatchById(shangjiaList);
        xiannaiService.updateBatchById(xiannaiList);
        yonghuService.updateById(yonghuEntity);
        if(cartIds != null && cartIds.size()>0)
            cartService.deleteBatchIds(cartIds);
        return R.ok();
    }











    /**
    * 退款
    */
    @RequestMapping("/refund")
    public R refund(Integer id, HttpServletRequest request){
        logger.debug("refund方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        String role = String.valueOf(request.getSession().getAttribute("role"));

            XiannaiOrderEntity xiannaiOrder = xiannaiOrderService.selectById(id);
            Integer buyNumber = xiannaiOrder.getBuyNumber();
            Integer xiannaiOrderPaymentTypes = xiannaiOrder.getXiannaiOrderPaymentTypes();
            Integer xiannaiId = xiannaiOrder.getXiannaiId();
            if(xiannaiId == null)
                return R.error(511,"查不到该商品");
            XiannaiEntity xiannaiEntity = xiannaiService.selectById(xiannaiId);
            if(xiannaiEntity == null)
                return R.error(511,"查不到该商品");
            //获取商家信息
            Integer shangjiaId = xiannaiEntity.getShangjiaId();
            ShangjiaEntity shangjiaEntity = shangjiaService.selectById(shangjiaId);//商家
            Double xiannaiNewMoney = xiannaiEntity.getXiannaiNewMoney();
            if(xiannaiNewMoney == null)
                return R.error(511,"商品价格不能为空");

            Integer userId = (Integer) request.getSession().getAttribute("userId");
            YonghuEntity yonghuEntity = yonghuService.selectById(userId);
            if(yonghuEntity == null)
                return R.error(511,"用户不能为空");
            if(yonghuEntity.getNewMoney() == null)
                return R.error(511,"用户金额不能为空");

            Double zhekou = 1.0;


            //判断是什么支付方式 1代表余额 2代表积分
            if(xiannaiOrderPaymentTypes == 1){//余额支付
                //计算金额
                Double money = xiannaiEntity.getXiannaiNewMoney() * buyNumber  * zhekou;
                //计算所获得积分
                Double buyJifen = 0.0;


                //修改商家余额
                shangjiaEntity.setNewMoney(shangjiaEntity.getNewMoney() - money);
            }

            xiannaiEntity.setXiannaiKucunNumber(xiannaiEntity.getXiannaiKucunNumber() + buyNumber);



            xiannaiOrder.setXiannaiOrderTypes(2);//设置订单状态为退款
            xiannaiOrderService.updateById(xiannaiOrder);//根据id更新
            shangjiaService.updateById(shangjiaEntity);
            yonghuService.updateById(yonghuEntity);//更新用户信息
            xiannaiService.updateById(xiannaiEntity);//更新订单中商品的信息
            return R.ok();
    }


    /**
     * 发货
     */
    @RequestMapping("/deliver")
    public R deliver(Integer id ){
        logger.debug("refund:,,Controller:{},,ids:{}",this.getClass().getName(),id.toString());
        XiannaiOrderEntity  xiannaiOrderEntity = new  XiannaiOrderEntity();;
        xiannaiOrderEntity.setId(id);
        xiannaiOrderEntity.setXiannaiOrderTypes(4);
        boolean b =  xiannaiOrderService.updateById( xiannaiOrderEntity);
        if(!b){
            return R.error("发货出错");
        }
        return R.ok();
    }









    /**
     * 收货
     */
    @RequestMapping("/receiving")
    public R receiving(Integer id){
        logger.debug("refund:,,Controller:{},,ids:{}",this.getClass().getName(),id.toString());
        XiannaiOrderEntity  xiannaiOrderEntity = new  XiannaiOrderEntity();
        xiannaiOrderEntity.setId(id);
        xiannaiOrderEntity.setXiannaiOrderTypes(5);
        boolean b =  xiannaiOrderService.updateById( xiannaiOrderEntity);
        if(!b){
            return R.error("收货出错");
        }
        return R.ok();
    }



    /**
    * 评价
    */
    @RequestMapping("/commentback")
    public R commentback(Integer id, String commentbackText, Integer xiannaiCommentbackPingfenNumber, HttpServletRequest request){
        logger.debug("commentback方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
            XiannaiOrderEntity xiannaiOrder = xiannaiOrderService.selectById(id);
        if(xiannaiOrder == null)
            return R.error(511,"查不到该订单");
        Integer xiannaiId = xiannaiOrder.getXiannaiId();
        if(xiannaiId == null)
            return R.error(511,"查不到该商品");

        XiannaiCommentbackEntity xiannaiCommentbackEntity = new XiannaiCommentbackEntity();
            xiannaiCommentbackEntity.setId(id);
            xiannaiCommentbackEntity.setXiannaiId(xiannaiId);
            xiannaiCommentbackEntity.setYonghuId((Integer) request.getSession().getAttribute("userId"));
            xiannaiCommentbackEntity.setXiannaiCommentbackText(commentbackText);
            xiannaiCommentbackEntity.setReplyText(null);
            xiannaiCommentbackEntity.setInsertTime(new Date());
            xiannaiCommentbackEntity.setUpdateTime(null);
            xiannaiCommentbackEntity.setCreateTime(new Date());
            xiannaiCommentbackService.insert(xiannaiCommentbackEntity);

            xiannaiOrder.setXiannaiOrderTypes(1);//设置订单状态为已评价
            xiannaiOrderService.updateById(xiannaiOrder);//根据id更新
            return R.ok();
    }







}
