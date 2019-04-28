package com.chris.smartpark.busi.controller;

import com.chris.base.common.annotation.SysLog;
import com.chris.base.common.tree.TreeNode;
import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.annotation.Login;
import com.chris.smartpark.busi.common.VisitorConstants;
import com.chris.smartpark.busi.dto.DoorControllerDTO;
import com.chris.smartpark.busi.entity.AccessRecordEntity;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.service.AccessRecordService;
import com.chris.smartpark.busi.service.DoorService;
import com.chris.smartpark.busi.service.EntranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 出入管理
 */
@RestController
@RequestMapping("/app/entrance")
public class EntranceController {

    @Autowired
    private EntranceService entranceService;

    @Autowired
    private DoorService doorService;

    @Autowired
    private AccessRecordService accessRecordService;

    /**
     * 用户门禁权限列表
     */
    @RequestMapping(value = "/userDoors")
    //@RequiresPermissions("busi:entrance:userDoors")
    public CommonResponse userDoors(@RequestParam String openId) {
        //查询列表数据
        //Query query = new Query(params);
        List<DoorEntity> userDoorList = this.entranceService.queryUserDoors(openId);
        if (ValidateUtils.isNotEmptyCollection(userDoorList)) {
            return CommonResponse.ok().setData(userDoorList);
        } else {
            return CommonResponse.error("未查询到数据!");
        }
    }

    /**
     * 根据openId查询用户有权限门节点信息
     *
     * @param openId
     * @return
     */
    @GetMapping("/queryDoorNodes")
    @Login
    public CommonResponse queryDoorNodes(String openId) {
        List<TreeNode> doorNodes = this.entranceService.queryHasPermissionDoorLevelNodesByOpenId(openId);
        return CommonResponse.ok().setData(doorNodes);
    }

    @GetMapping("/queryDoorControllers")
    @Login
    public CommonResponse queryDoorControllers(String openId) {
        List<DoorControllerDTO> doorControllers = this.doorService.queryDoorControllersByOpenId(openId);
        if (ValidateUtils.isNotEmptyCollection(doorControllers)) {
            List<DoorControllerDTO> publicDoorControllers = doorControllers.stream().filter(item -> item.getDoorType() == VisitorConstants.DoorType.PUBLIC).collect(Collectors.toList());
            List<DoorControllerDTO> privateDoorControllers = doorControllers.stream().filter(item -> item.getDoorType() == VisitorConstants.DoorType.PRIVATE).collect(Collectors.toList());
            return CommonResponse.ok().put("publicDoorControllers", publicDoorControllers).put("privateDoorControllers", privateDoorControllers);
        }
        return CommonResponse.ok();
    }

    /**
     * 远程开门
     */
    @PostMapping("/remoteOpenDoor")
    @SysLog("远程开门")
    public CommonResponse remoteOpenDoor(@RequestBody Map<String, Object> params) {
        this.entranceService.remoteOpenDoor(params);
        return CommonResponse.ok();
    }

    /**
     * 出入记录查询
     */
    @PostMapping("/accessrecord/list")
//    @Login
    public CommonResponse list(@RequestBody Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<AccessRecordEntity> accessRecordList = this.accessRecordService.queryList(query);
        int total = this.accessRecordService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(accessRecordList, total, query.getLimit(), query.getPage());

        return CommonResponse.ok().put("page", pageUtil);
    }
}
