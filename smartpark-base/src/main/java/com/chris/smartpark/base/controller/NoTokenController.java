package com.chris.smartpark.base.controller;

import com.chris.base.common.utils.CommonResponse;
import com.chris.smartpark.base.entity.BaseParkEntity;
import com.chris.smartpark.base.service.BaseParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class NoTokenController {
    @Autowired
    private BaseParkService baseParkService;

    @RequestMapping("/basepark/list.notoken")
    public CommonResponse list(){
        List<BaseParkEntity> baseParkList = baseParkService.queryList(Collections.EMPTY_MAP);

        return CommonResponse.ok().setData(baseParkList);
    }
}
