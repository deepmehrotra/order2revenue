package com.o2r.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.o2r.model.Employee;
import com.o2r.service.AdminService;

/**
 * @author Deep Mehrotra
 *
 *
 *
 */
//Comment for comiting git changes in testing for first time-31st march -2016
@Controller
public class AdminController {

 @Autowired
 private AdminService adminService;

/* @RequestMapping
 public ModelAndView saveEmployeeeee() {
 	System.out.println("Inside save");
   return new ModelAndView("index");
  }
*/


@RequestMapping(value = "/seller/save", method = RequestMethod.POST)
public ModelAndView saveEmployee(@ModelAttribute("command")EmployeeBean employeeBean,
   BindingResult result) {
	System.out.println("Inside save");
  Employee employee = prepareModel(employeeBean);
  adminService.addEmployee(employee);
  return new ModelAndView("redirect:/seller/add.html?page=1");
 }

 /*@RequestMapping(value="/seller/employees", method = RequestMethod.GET)
 public ModelAndView listEmployees(@RequestParam(value = "page") int page) {
  Map<String, Object> model = new HashMap<String, Object>();
  model.put("employees",  prepareListofBean(adminService.listEmployeess(page)));
  return new ModelAndView("employeesList", model);
 }
*/
@RequestMapping(value="/seller/employees", method = RequestMethod.GET)

public ModelAndView listEmployees() {
 Map<String, String> model = new HashMap<String, String>();
 Gson gson = new GsonBuilder().setPrettyPrinting().create();
 String jsonArray = gson.toJson( prepareListofBean(adminService.listEmployeess(1)));
 model.put("employees", jsonArray);
  return new ModelAndView("employeesList", model);
}

@RequestMapping(value="/admin/listQueries", method = RequestMethod.GET)

public ModelAndView listQueries() {
 Map<String, Object> model = new HashMap<String, Object>();
try
{
 model.put("queries", adminService.listQueries());
}
catch(Exception e)
{
	e.printStackTrace();
	return new ModelAndView("admin/queryList", model);

}
  return new ModelAndView("admin/queryList", model);
}

@RequestMapping(value="/seller/controller", method = RequestMethod.POST)

public @ResponseBody String listEmployeesJtable(@RequestParam(value="action") String action) {
	System.out.println(" Inside Emplyee Jtable");

 Map<String, Object> model = new HashMap<String, Object>();
 Gson gson = new GsonBuilder().setPrettyPrinting().create();


 model.put("Result", "OK");
 model.put("Records", prepareListofBean(adminService.listEmployeess(1)));

	// Convert Java Object to Json
String jsonArray = gson.toJson(model);
 //model.put("employees", jsonArray);
  return jsonArray;
}

 @RequestMapping(value = "/seller/add", method = RequestMethod.GET)
 public ModelAndView addEmployee(@RequestParam(value = "page") int page,@ModelAttribute("command")EmployeeBean employeeBean,
   BindingResult result) {
  Map<String, Object> model = new HashMap<String, Object>();
  model.put("employees",  prepareListofBean(adminService.listEmployeess(page)));
  return new ModelAndView("addEmployee", model);
 }

@RequestMapping(value = "/index", method = RequestMethod.GET)
public ModelAndView welcome() {
  return new ModelAndView("index");
 }

@RequestMapping(value = "/seller/delete", method = RequestMethod.GET)
public ModelAndView editEmployee(@RequestParam(value = "page") int page,@ModelAttribute("command")EmployeeBean employeeBean,
   BindingResult result) {
	System.out.println(" Inside deleteemployee id"+employeeBean.getId());
	System.out.println(" Inside deleteemployee name"+employeeBean.getName());
  adminService.deleteEmployee(prepareModel(employeeBean));
  Map<String, Object> model = new HashMap<String, Object>();
  model.put("employee", null);
  model.put("employees",  prepareListofBean(adminService.listEmployeess(page)));
  return new ModelAndView("addEmployee", model);
 }

@RequestMapping(value = "/seller/edit", method = RequestMethod.GET)
public ModelAndView deleteEmployee(@RequestParam(value = "page") int page,@ModelAttribute("command")EmployeeBean employeeBean,
   BindingResult result) {
  Map<String, Object> model = new HashMap<String, Object>();
  model.put("employee", prepareEmployeeBean(adminService.getEmployee(employeeBean.getId())));
  model.put("employees",  prepareListofBean(adminService.listEmployeess(page)));
  return new ModelAndView("addEmployee", model);
 }


 private Employee prepareModel(EmployeeBean employeeBean){
  Employee employee = new Employee();
  employee.setEmpAddress(employeeBean.getAddress());
  employee.setEmpAge(employeeBean.getAge());
  employee.setEmpName(employeeBean.getName());
  employee.setSalary(employeeBean.getSalary());
  employee.setEmpId(employeeBean.getId());
  employeeBean.setId(null);
  return employee;
 }

 private List<EmployeeBean> prepareListofBean(List<Employee> employees){
  List<EmployeeBean> beans = null;
  if(employees != null && !employees.isEmpty()){
   beans = new ArrayList<EmployeeBean>();
   EmployeeBean bean = null;
   for(Employee employee : employees){
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

 private EmployeeBean prepareEmployeeBean(Employee employee){
  EmployeeBean bean = new EmployeeBean();
  bean.setAddress(employee.getEmpAddress());
  bean.setAge(employee.getEmpAge());
  bean.setName(employee.getEmpName());
  bean.setSalary(employee.getSalary());
  bean.setId(employee.getEmpId());
  return bean;
 }
}