
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
 * 商品
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/xiannai")
public class XiannaiController {
    private static final Logger logger = LoggerFactory.getLogger(XiannaiController.class);

    @Autowired
    private XiannaiService xiannaiService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private ShangjiaService shangjiaService;

    @Autowired
    private YonghuService yonghuService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("商家".equals(role))
            params.put("shangjiaId",request.getSession().getAttribute("userId"));
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        params.put("xiannaiDeleteStart",1);params.put("xiannaiDeleteEnd",1);
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = xiannaiService.queryPage(params);

        //字典表数据转换
        List<XiannaiView> list =(List<XiannaiView>)page.getList();
        for(XiannaiView c:list){
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
        XiannaiEntity xiannai = xiannaiService.selectById(id);
        if(xiannai !=null){
            //entity转view
            XiannaiView view = new XiannaiView();
            BeanUtils.copyProperties( xiannai , view );//把实体数据重构到view中

                //级联表
                ShangjiaEntity shangjia = shangjiaService.selectById(xiannai.getShangjiaId());
                if(shangjia != null){
                    BeanUtils.copyProperties( shangjia , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setShangjiaId(shangjia.getId());
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
    public R save(@RequestBody XiannaiEntity xiannai, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,xiannai:{}",this.getClass().getName(),xiannai.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("商家".equals(role))
            xiannai.setShangjiaId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<XiannaiEntity> queryWrapper = new EntityWrapper<XiannaiEntity>()
            .eq("shangjia_id", xiannai.getShangjiaId())
            .eq("xiannai_name", xiannai.getXiannaiName())
            .eq("xiannai_types", xiannai.getXiannaiTypes())
            .eq("xiannai_kucun_number", xiannai.getXiannaiKucunNumber())
            .eq("xiannai_clicknum", xiannai.getXiannaiClicknum())
            .eq("shangxia_types", xiannai.getShangxiaTypes())
            .eq("xiannai_delete", xiannai.getXiannaiDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XiannaiEntity xiannaiEntity = xiannaiService.selectOne(queryWrapper);
        if(xiannaiEntity==null){
            xiannai.setXiannaiClicknum(1);
            xiannai.setShangxiaTypes(1);
            xiannai.setXiannaiDelete(1);
            xiannai.setCreateTime(new Date());
            xiannaiService.insert(xiannai);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody XiannaiEntity xiannai, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,xiannai:{}",this.getClass().getName(),xiannai.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("商家".equals(role))
//            xiannai.setShangjiaId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<XiannaiEntity> queryWrapper = new EntityWrapper<XiannaiEntity>()
            .notIn("id",xiannai.getId())
            .andNew()
            .eq("shangjia_id", xiannai.getShangjiaId())
            .eq("xiannai_name", xiannai.getXiannaiName())
            .eq("xiannai_types", xiannai.getXiannaiTypes())
            .eq("xiannai_kucun_number", xiannai.getXiannaiKucunNumber())
            .eq("xiannai_clicknum", xiannai.getXiannaiClicknum())
            .eq("shangxia_types", xiannai.getShangxiaTypes())
            .eq("xiannai_delete", xiannai.getXiannaiDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XiannaiEntity xiannaiEntity = xiannaiService.selectOne(queryWrapper);
        if("".equals(xiannai.getXiannaiPhoto()) || "null".equals(xiannai.getXiannaiPhoto())){
                xiannai.setXiannaiPhoto(null);
        }
        if(xiannaiEntity==null){
            xiannaiService.updateById(xiannai);//根据id更新
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
        ArrayList<XiannaiEntity> list = new ArrayList<>();
        for(Integer id:ids){
            XiannaiEntity xiannaiEntity = new XiannaiEntity();
            xiannaiEntity.setId(id);
            xiannaiEntity.setXiannaiDelete(2);
            list.add(xiannaiEntity);
        }
        if(list != null && list.size() >0){
            xiannaiService.updateBatchById(list);
        }
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<XiannaiEntity> xiannaiList = new ArrayList<>();//上传的东西
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
                            XiannaiEntity xiannaiEntity = new XiannaiEntity();
//                            xiannaiEntity.setShangjiaId(Integer.valueOf(data.get(0)));   //商家 要改的
//                            xiannaiEntity.setXiannaiName(data.get(0));                    //商品名称 要改的
//                            xiannaiEntity.setXiannaiPhoto("");//详情和图片
//                            xiannaiEntity.setXiannaiTypes(Integer.valueOf(data.get(0)));   //商品类型 要改的
//                            xiannaiEntity.setXiannaiKucunNumber(Integer.valueOf(data.get(0)));   //剩余订购数量 要改的
//                            xiannaiEntity.setXiannaiOldMoney(data.get(0));                    //商品原价 要改的
//                            xiannaiEntity.setXiannaiNewMoney(data.get(0));                    //现价/月 要改的
//                            xiannaiEntity.setXiannaiTime(sdf.parse(data.get(0)));          //保质期 要改的
//                            xiannaiEntity.setXiannaiClicknum(Integer.valueOf(data.get(0)));   //点击次数 要改的
//                            xiannaiEntity.setShangxiaTypes(Integer.valueOf(data.get(0)));   //是否上架 要改的
//                            xiannaiEntity.setXiannaiDelete(1);//逻辑删除字段
//                            xiannaiEntity.setXiannaiContent("");//详情和图片
//                            xiannaiEntity.setCreateTime(date);//时间
                            xiannaiList.add(xiannaiEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        xiannaiService.insertBatch(xiannaiList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
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
        PageUtils page = xiannaiService.queryPage(params);

        //字典表数据转换
        List<XiannaiView> list =(List<XiannaiView>)page.getList();
        for(XiannaiView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        XiannaiEntity xiannai = xiannaiService.selectById(id);
            if(xiannai !=null){

                //点击数量加1
                xiannai.setXiannaiClicknum(xiannai.getXiannaiClicknum()+1);
                xiannaiService.updateById(xiannai);

                //entity转view
                XiannaiView view = new XiannaiView();
                BeanUtils.copyProperties( xiannai , view );//把实体数据重构到view中

                //级联表
                    ShangjiaEntity shangjia = shangjiaService.selectById(xiannai.getShangjiaId());
                if(shangjia != null){
                    BeanUtils.copyProperties( shangjia , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setShangjiaId(shangjia.getId());
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
    public R add(@RequestBody XiannaiEntity xiannai, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,xiannai:{}",this.getClass().getName(),xiannai.toString());
        Wrapper<XiannaiEntity> queryWrapper = new EntityWrapper<XiannaiEntity>()
            .eq("shangjia_id", xiannai.getShangjiaId())
            .eq("xiannai_name", xiannai.getXiannaiName())
            .eq("xiannai_types", xiannai.getXiannaiTypes())
            .eq("xiannai_kucun_number", xiannai.getXiannaiKucunNumber())
            .eq("xiannai_clicknum", xiannai.getXiannaiClicknum())
            .eq("shangxia_types", xiannai.getShangxiaTypes())
            .eq("xiannai_delete", xiannai.getXiannaiDelete())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XiannaiEntity xiannaiEntity = xiannaiService.selectOne(queryWrapper);
        if(xiannaiEntity==null){
            xiannai.setXiannaiDelete(1);
            xiannai.setCreateTime(new Date());
        xiannaiService.insert(xiannai);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


}
