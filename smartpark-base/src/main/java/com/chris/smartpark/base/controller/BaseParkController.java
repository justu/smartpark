package com.chris.smartpark.base.controller;

import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.smartpark.base.entity.BaseParkEntity;
import com.chris.smartpark.base.service.BaseParkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 园区信息表
 *
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
@RestController
@RequestMapping("/app/base/park")
@Api("园区信息接口")
public class BaseParkController {
    @Autowired
    private BaseParkService baseParkService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询列表数据", notes = "查询列表数据")
    public CommonResponse list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<BaseParkEntity> baseParkList = baseParkService.queryList(query);
        int total = baseParkService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(baseParkList, total, query.getLimit(), query.getPage());

        return CommonResponse.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询园区", notes = "根据ID查询园区")
    public CommonResponse info(@PathVariable("id") Integer id) {
        BaseParkEntity basePark = baseParkService.queryObject(id);

        return CommonResponse.ok().put("basePark", basePark);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存", notes = "保存")
    public CommonResponse save(@RequestBody BaseParkEntity basePark) {
        baseParkService.save(basePark);

        return CommonResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改", notes = "修改")
    public CommonResponse update(@RequestBody BaseParkEntity basePark) {
        baseParkService.update(basePark);

        return CommonResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除", notes = "删除")
    public CommonResponse delete(@RequestBody Integer[] ids) {
        baseParkService.deleteBatch(ids);

        return CommonResponse.ok();
    }

}
