package com.chris.smartpark.ibms.controller;

import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.smartpark.ibms.entity.IBMSSubsystemEntity;
import com.chris.smartpark.ibms.service.IBMSSubsystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * ibms子系统表
 *
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 06.18
 */
@RestController
@RequestMapping("/app/ibms/ibmssubsystem")
@Api("子系统接口")
public class IBMSSubsystemController {
    @Autowired
    private IBMSSubsystemService ibmsSubsystemService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询列表", notes = "查询列表")
    public CommonResponse list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<IBMSSubsystemEntity> ibmsSubsystemList = ibmsSubsystemService.queryList(query);
        int total = ibmsSubsystemService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(ibmsSubsystemList, total, query.getLimit(), query.getPage());

        return CommonResponse.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询子系统", notes = "根据ID查询子系统")
    public CommonResponse info(@PathVariable("id") Integer id) {
        IBMSSubsystemEntity ibmsSubsystem = ibmsSubsystemService.queryObject(id);
        return CommonResponse.ok().setData(ibmsSubsystem);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存", notes = "保存")
    public CommonResponse save(@RequestBody IBMSSubsystemEntity ibmsSubsystem) {
        ibmsSubsystemService.save(ibmsSubsystem);

        return CommonResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @ApiOperation(value = "修改", notes = "修改")
    public CommonResponse update(@RequestBody IBMSSubsystemEntity ibmsSubsystem) {
        ibmsSubsystemService.update(ibmsSubsystem);

        return CommonResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除", notes = "删除")
    public CommonResponse delete(@RequestBody Integer[] ids) {
        ibmsSubsystemService.deleteBatch(ids);

        return CommonResponse.ok();
    }

}
