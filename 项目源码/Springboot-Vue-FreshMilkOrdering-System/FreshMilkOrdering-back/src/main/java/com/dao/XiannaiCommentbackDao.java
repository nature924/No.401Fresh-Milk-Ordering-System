package com.dao;

import com.entity.XiannaiCommentbackEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.XiannaiCommentbackView;

/**
 * 商品评价 Dao 接口
 *
 * @author 
 */
public interface XiannaiCommentbackDao extends BaseMapper<XiannaiCommentbackEntity> {

   List<XiannaiCommentbackView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
