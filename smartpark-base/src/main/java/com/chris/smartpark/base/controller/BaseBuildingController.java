package com.chris.smartpark.base.controller;

import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.smartpark.base.entity.BaseBuildingEntity;
import com.chris.smartpark.base.service.BaseBuildingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 园区楼宇信息表
 *
 * @author chris
 * @email forzamilan0607@gmail.com
 * @since Oct 16.18
 */
@RestController
@RequestMapping("/app/base/building")
@Api("园区楼宇信息接口")
public class BaseBuildingController {
    @Autowired
    private BaseBuildingService baseBuildingService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询列表数据", notes = "查询列表数据")
    public CommonResponse list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<BaseBuildingEntity> baseBuildingList = baseBuildingService.queryList(query);
        int total = baseBuildingService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(baseBuildingList, total, query.getLimit(), query.getPage());

        return CommonResponse.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询子系统", notes = "根据ID查询子系统")
    public CommonResponse info(@PathVariable("id") Integer id) {
        BaseBuildingEntity baseBuilding = baseBuildingService.queryObject(id);

        return CommonResponse.ok().put("baseBuilding", baseBuilding);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存", notes = "保存")
    public CommonResponse save(@RequestBody BaseBuildingEntity baseBuilding) {
        baseBuildingService.save(baseBuilding);

        return CommonResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改", notes = "修改")
    public CommonResponse update(@RequestBody BaseBuildingEntity baseBuilding) {
        baseBuildingService.update(baseBuilding);

        return CommonResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除", notes = "删除")
    public CommonResponse delete(@RequestBody Integer[] ids) {
        baseBuildingService.deleteBatch(ids);

        return CommonResponse.ok();
    }

}
