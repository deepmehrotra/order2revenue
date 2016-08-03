package com.o2r.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2r.bean.EmployeeBean;
import com.o2r.bean.OrderBean;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.HelperClass;
import com.o2r.model.ChannelUploadMapping;
import com.o2r.model.Employee;
import com.o2r.service.AdminService;
import com.o2r.service.UploadMappingService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private UploadMappingService uploadMappingService;
	@Autowired
	private HelperClass helperClass;

	static Logger log = Logger.getLogger(AdminController.class.getName());

	@RequestMapping(value = "/seller/save", method = RequestMethod.POST)
	public ModelAndView saveEmployee(
			@ModelAttribute("command") EmployeeBean employeeBean,
			BindingResult result) {
		
		log.info("$$$ saveEmployee() Starts : AdminController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try{
		Employee employee = prepareModel(employeeBean);
		adminService.addEmployee(employee);
		}catch(CustomException ce){
			log.error("saveEmployee exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ saveEmployee() Ends : AdminController $$$");
		return new ModelAndView("redirect:/seller/add.html?page=1");
	}
	
	@RequestMapping(value = "/seller/employees", method = RequestMethod.GET)
	public ModelAndView listEmployees() {

		log.info("$$$ listEmployees Starts : AdminController $$$");
		Map<String, String> model = new HashMap<String, String>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try{
		String jsonArray = gson.toJson(prepareListofBean(adminService.listEmployeess(1)));
		model.put("employees", jsonArray);
		}catch(CustomException ce){
			log.error("listEmployee exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ listEmployees Ends : AdminController $$$");
		return new ModelAndView("employeesList", model);
	}

	@RequestMapping(value = "/admin/listQueries", method = RequestMethod.GET)
	public ModelAndView listQueries() {

		log.info("$$$ listQueries Starts : AdminController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("queries", adminService.listQueries());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
			return new ModelAndView("admin/queryList", model);
		}

		log.info("$$$ listQueries Ends : AdminController $$$");
		return new ModelAndView("admin/queryList", model);
	}
	
	@RequestMapping(value = "/admin/reverseOrderList", method = RequestMethod.GET)
	public ModelAndView returnOrRTOlist() {

		log.info("$$$ reverseOrderList Starts : AdminController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println(" Inside reverse Order list");
		model.put("order", new OrderBean());
		log.info("$$$ reverseOrderList Ends : AdminController $$$");
		return new ModelAndView("/admin/reverseOrderList", model);
	}

	@RequestMapping(value = "/seller/controller", method = RequestMethod.POST)
	public @ResponseBody String listEmployeesJtable(
			@RequestParam(value = "action") String action) {

		log.info("$$$ listEmployeesJtable Starts : AdminController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonArray;
		try{
		model.put("Result", "OK");
		model.put("Records", prepareListofBean(adminService.listEmployeess(1)));

		// Convert Java Object to Json
		jsonArray = gson.toJson(model);
		}catch(CustomException ce){
			log.error("Failed!",ce);
			model.put("error", ce.getMessage());
			String errors=gson.toJson(model);
			return errors;
		}
		log.info("$$$ listEmployeesJtable Ends : AdminController $$$");
		return jsonArray;
	}
	
	@RequestMapping(value = "/seller/savemappingdetails", method = RequestMethod.GET)
	public ModelAndView savemappingdetails(HttpServletRequest request,
			@ModelAttribute("command") ChannelUploadMapping channeluploadmapping, BindingResult result) {

		log.info("$$$ savemappingdetails admin Starts : AdminController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			uploadMappingService.addChannelUploadMapping(channeluploadmapping);
		} catch (CustomException ce) {
			log.error("savemappingdetails exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		
		log.info("$$$ addManualPayment Ends : UploadController $$$");
		return new ModelAndView("redirect:/seller/uploadmappings.html?filename=something", model);
	}

/*	@RequestMapping(value = "/seller/add", method = RequestMethod.GET)
	public ModelAndView addEmployee(@RequestParam(value = "page") int page,
			@ModelAttribute("command") EmployeeBean employeeBean,
			BindingResult result) {

		log.info("$$$ addEmployee Starts : AdminController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try{		
		model.put("employees",prepareListofBean(adminService.listEmployeess(page)));
		}catch(CustomException ce){
			log.error("addEmployee exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ addEmployee Ends : AdminController $$$");
		return new ModelAndView("addEmployee", model);
	}*/

	/*
	 * Method to view Channel Upload Sheets Mapping
	 * 
	 */
	@RequestMapping(value = "/seller/uploadmappings", method = RequestMethod.GET)
		public ModelAndView uploadmappingsDetails(HttpServletRequest request) {

			log.info("$$$ uploadmappingsDetails Starts : UploadController $$$");
			String channelName = request.getParameter("channelName");
			String fileName = request.getParameter("fileName");
			Map<String, Object> model = new HashMap<String, Object>();
			ChannelUploadMapping cum=null;
			try {
				
				if (fileName != null && channelName!=null) {
					cum=uploadMappingService.getChannelUploadMapping(channelName, fileName);
					if(cum!=null)
					model.put("mapping", cum);
					else
					{
						switch(fileName)
						{
						case "payment":
							model.put("o2rheaders", GlobalConstant.paymentHeaderList);
							model.put("mapping", new ChannelUploadMapping());
						}
					}
					model.put("fileName", fileName);
					model.put("channelName",channelName);
				} 
				
				model.put("partnerNames", GlobalConstant.channelMappingList);
				model.put("fileNames", GlobalConstant.filesMappingList);
			} catch (CustomException ce) {
				log.error("uploadmappings exception : "+ ce.toString());
				model.put("errorMessage", ce.getLocalMessage());
				model.put("errorTime", ce.getErrorTime());
				model.put("errorCode", ce.getErrorCode());
				return new ModelAndView("globalErorPage", model);
			} catch (Throwable e) {
				e.printStackTrace();
				log.error("Failed! - Admin "+e);
				model.put("errorMessage", e.getCause());
				return new ModelAndView("globalErorPage", model);
			}
			log.info("$$$ uploadmappingsDetails Ends : UploadController $$$");
			return new ModelAndView("admin/uploadMapping", model);
		}
	
	
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		
		return new ModelAndView("redirect:/landing/home.html");
	}

	@RequestMapping(value = "/seller/delete", method = RequestMethod.GET)
	public ModelAndView editEmployee(@RequestParam(value = "page") int page,
			@ModelAttribute("command") EmployeeBean employeeBean,
			BindingResult result) {
		
		log.info("$$$ editEmployee Starts : AdminController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try{
		adminService.deleteEmployee(prepareModel(employeeBean));		
		model.put("employee", null);
		model.put("employees",prepareListofBean(adminService.listEmployeess(page)));
		}catch(CustomException ce){
			log.error("editEmployee exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ editEmployee Ends : AdminController $$$");
		return new ModelAndView("addEmployee", model);
	}

	@RequestMapping(value = "/seller/edit", method = RequestMethod.GET)
	public ModelAndView deleteEmployee(@RequestParam(value = "page") int page,
			@ModelAttribute("command") EmployeeBean employeeBean,
			BindingResult result) {
		
		log.info("$$$ deleteEmployee Starts : AdminController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try{
		model.put("employee", prepareEmployeeBean(adminService.getEmployee(employeeBean.getId())));
		model.put("employees", prepareListofBean(adminService.listEmployeess(page)));
		}catch(CustomException ce){
			log.error("deleteEmployee exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ deleteEmployee Ends : AdminController $$$");
		return new ModelAndView("addEmployee", model);
	}

	private Employee prepareModel(EmployeeBean employeeBean) {
		Employee employee = new Employee();
		employee.setEmpAddress(employeeBean.getAddress());
		employee.setEmpAge(employeeBean.getAge());
		employee.setEmpName(employeeBean.getName());
		employee.setSalary(employeeBean.getSalary());
		employee.setEmpId(employeeBean.getId());
		employeeBean.setId(null);
		return employee;
	}

	private List<EmployeeBean> prepareListofBean(List<Employee> employees) {
		List<EmployeeBean> beans = null;
		if (employees != null && !employees.isEmpty()) {
			beans = new ArrayList<EmployeeBean>();
			EmployeeBean bean = null;
			for (Employee employee : employees) {
				bean = new EmployeeBean();
				bean.setName(employee.getEmpName());
				bean.setId(employee.getEmpId());
				bean.setAddress(employee.getEmpAddress());
				bean.setSalary(employee.getSalary());
				bean.setAge(employee.getEmpAge());
				beans.add(bean);
			}
		}
		return beans;
	}

	private EmployeeBean prepareEmployeeBean(Employee employee) {
		EmployeeBean bean = new EmployeeBean();
		bean.setAddress(employee.getEmpAddress());
		bean.setAge(employee.getEmpAge());
		bean.setName(employee.getEmpName());
		bean.setSalary(employee.getSalary());
		bean.setId(employee.getEmpId());
		return bean;
	}
}