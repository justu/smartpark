package com.chris.smartpark.busi.controller;

import java.util.List;
import java.util.Map;

import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.annotation.Login;
import com.chris.smartpark.busi.common.VisitorConstants;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.chris.smartpark.busi.entity.WorkOrderEntity;
import com.chris.smartpark.busi.service.WorkOrderService;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.base.common.utils.CommonResponse;




/**
 * 工单controller
 * @author chris
 * @email 258321511@qq.com
 * @since Apr 29.19
 */
@RestController
@RequestMapping("/app/workorder")
public class WorkOrderController {
	@Autowired
	private WorkOrderService workOrderService;
	
	/**
	 * 工单查询
	 */
	@PostMapping("/list")
//	@Login
	public CommonResponse list(@RequestBody Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<WorkOrderEntity> workOrderList = workOrderService.queryList(query);
		int total = workOrderService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(workOrderList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	//	@Login
	public CommonResponse save(@RequestBody WorkOrderEntity workOrder){
		this.validateWorkOrder(workOrder, VisitorConstants.Action.CREATE);
		this.workOrderService.save(workOrder);
		return CommonResponse.ok();
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	//	@Login
	public CommonResponse update(@RequestBody WorkOrderEntity workOrder){
		this.validateWorkOrder(workOrder, VisitorConstants.Action.MODIFY);
		this.workOrderService.update(workOrder);
		return CommonResponse.ok();
	}

	private void validateWorkOrder(WorkOrderEntity workOrder, int action) {
		if (action == VisitorConstants.Action.MODIFY) {
			if (ValidateUtils.isEmpty(workOrder.getId())) {
				throw new CommonException("工单ID为空");
			}
		}
		if (ValidateUtils.isEmptyString(workOrder.getTitle())) {
			throw new CommonException("标题为空");
		}
		if (ValidateUtils.isEmptyString(workOrder.getContent())) {
			throw new CommonException("内容为空");
		}
		if (ValidateUtils.isEmpty(workOrder.getCreateUserId())) {
			throw new CommonException("创建人为空");
		}
	}


}
