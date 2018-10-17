package com.chris.smartpark.ibms.controller;

import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.smartpark.ibms.entity.IBMSDevCollectionRecordEntity;
import com.chris.smartpark.ibms.service.IBMSDevCollectionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 设备采集记录表
 *
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
@RestController
@RequestMapping("/app/ibms/devcollectionrecord")
@Api("设备采集记录接口")
public class IBMSDevCollectionRecordController {
    @Autowired
    private IBMSDevCollectionRecordService ibmsDevCollectionRecordService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询列表数据", notes = "查询列表数据")
    public CommonResponse list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<IBMSDevCollectionRecordEntity> ibmsDevCollectionRecordList = ibmsDevCollectionRecordService.queryList(query);
        int total = ibmsDevCollectionRecordService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(ibmsDevCollectionRecordList, total, query.getLimit(), query.getPage());

        return CommonResponse.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询", notes = "根据ID查询")
    public CommonResponse info(@PathVariable("id") Integer id) {
        IBMSDevCollectionRecordEntity ibmsDevCollectionRecord = ibmsDevCollectionRecordService.queryObject(id);

        return CommonResponse.ok().put("ibmsDevCollectionRecord", ibmsDevCollectionRecord);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存", notes = "保存")
    public CommonResponse save(@RequestBody IBMSDevCollectionRecordEntity ibmsDevCollectionRecord) {
        ibmsDevCollectionRecordService.save(ibmsDevCollectionRecord);

        return CommonResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改", notes = "修改")
    public CommonResponse update(@RequestBody IBMSDevCollectionRecordEntity ibmsDevCollectionRecord) {
        ibmsDevCollectionRecordService.update(ibmsDevCollectionRecord);

        return CommonResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除", notes = "删除")
    public CommonResponse delete(@RequestBody Integer[] ids) {
        ibmsDevCollectionRecordService.deleteBatch(ids);

        return CommonResponse.ok();
    }

}
