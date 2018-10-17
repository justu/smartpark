package com.chris.smartpark.base.controller;

import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.smartpark.base.entity.BaseCompanyEntity;
import com.chris.smartpark.base.service.BaseCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 园区公司信息表
 *
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
@RestController
@RequestMapping("/app/base/company")
@Api("园区公司信息接口")
public class BaseCompanyController {
    @Autowired
    private BaseCompanyService baseCompanyService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询列表数据", notes = "查询列表数据")
    public CommonResponse list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<BaseCompanyEntity> baseCompanyList = baseCompanyService.queryList(query);
        int total = baseCompanyService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(baseCompanyList, total, query.getLimit(), query.getPage());

        return CommonResponse.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询公司", notes = "根据ID查询公司")
    public CommonResponse info(@PathVariable("id") Integer id) {
        BaseCompanyEntity baseCompany = baseCompanyService.queryObject(id);

        return CommonResponse.ok().put("baseCompany", baseCompany);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存", notes = "保存")
    public CommonResponse save(@RequestBody BaseCompanyEntity baseCompany) {
        baseCompanyService.save(baseCompany);

        return CommonResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改", notes = "修改")
    public CommonResponse update(@RequestBody BaseCompanyEntity baseCompany) {
        baseCompanyService.update(baseCompany);

        return CommonResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "修改", notes = "修改")
    public CommonResponse delete(@RequestBody Integer[] ids) {
        baseCompanyService.deleteBatch(ids);

        return CommonResponse.ok();
    }

}
