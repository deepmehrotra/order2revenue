<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Events</title>
</head>
<body>

	<div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Events</h5>
                            <div class="ibox-tools">
                                <a href="" class="btn btn-primary btn-xs" >Create New Events</a>
                            </div>
                        </div>
                        <div class="ibox-content add-company"> 
                            <form role="form" class="form-horizontal" action="" method="POST" id="">
                        <div class="col-sm-6">
                          <div class="form-group">
                            <label class="col-sm-4 control-label">Event Name</label>
                            <div class="col-sm-8">
                              <input type="number" class="form-control">
                            </div>
                          </div>
                        </div>
                        <div class="col-sm-6">
                          <div class="form-group">
                            <label class="col-sm-4 control-label">Select Channel</label>
                            <div class="col-sm-8">
                              <select class="form-control">
                              <option value="1">
                                  1
                              </option>
                              <option value="1">
                                  2
                              </option>
                              <option value="1">
                                  3
                              </option>
                              </select>
                            </div>
                          </div>
                        </div>
                        <div class="col-lg-12">
                            <h4>Event Period</h4>
                        </div>
                        
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Start Date</label>
                                <div class="col-sm-8" id="data_1">
                                    <div class="input-group date"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control" value="Start Date">
                                    </div>
                                </div>
                            </div> 
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">End Date</label>
                                <div class="col-sm-8" id="data_1">
                                    <div class="input-group date"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control" value="End Date">
                                    </div>
                                </div>
                            </div>  
                        </div>
                        <div class="col-sm-12">
                          <h4>NR Calculator</h4>
                        </div>
                        <div class="col-sm-12">
                          <div class="col-sm-6">
                            <div class="radio">
                              <label>
                                <input type="radio" value="40" id="optionsRadios1" name="toggler">
                                Variable NR</label>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="radio">
                              <label>
                                <input type="radio" value="41" id="optionsRadios1" name="toggler">
                                Fixed TP </label>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-sm-12 radio1 m-b" id="blk-40">
                            <div class="col-sm-12">
								<div class="col-sm-12 radio5" id="nr-switch-sec" style="display: block;">
                                <div class="panel-body">
            <div class="panel-group" id="accordion">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapse8">Commission</a> </h5>
                </div>
                <div id="collapse8" class="panel-collapse collapse">
                  <div class="panel-body">
                    <div class="col-sm-12">
                      <div class="col-sm-6">
                        <div class="radio">
                          <label>
                            <input type="radio" value="1" id="optionsRadios1" name="toggler">
                            Fixed</label>
                        </div>
                      </div>
                      <div class="col-sm-6">
                        <div class="radio">
                          <label>
                            <input type="radio" value="2" id="optionsRadios1" name="toggler">
                            Category Wise </label>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-12 radio1" id="blk-1">
                      <div class="col-sm-12">
                        <div class="form-group">
                          <div class="input-group m-b col-md-4">
                            <input type="text" class="form-control">
                            <span class="input-group-addon">%</span></div>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-12 radio1" id="blk-2">
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label">label</label>
                        <div class="input-group m-b col-md-4">
                          <input type="text" class="form-control">
                          <span class="input-group-addon">%</span></div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label">label</label>
                        <div class="input-group m-b col-md-4">
                          <input type="text" class="form-control">
                          <span class="input-group-addon">%</span></div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label">label</label>
                        <div class="input-group m-b col-md-4">
                          <input type="text" class="form-control">
                          <span class="input-group-addon">%</span></div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapse9">Fixed Fee</a> </h4>
                </div>
                <div id="collapse9" class="panel-collapse collapse">
                  <div class="panel-body">
                    <div class="col-sm-4" style="border-right:1px dotted #ccc;">
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label content-rgt">&lt;250000</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;">
                        </div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-12 control-label">250 &gt;5000000</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;">
                        </div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label">&lt;50000000</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;">
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-4" style="border-right:1px dotted #ccc;">
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label content-rgt">&lt;25000000</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;">
                        </div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-12 control-label">250 &gt; 50000000</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;">
                        </div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label"> &lt; 50999990</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;">
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-4">
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label content-rgt">&lt; 2509999</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;">
                        </div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-12 control-label">250000 &gt; 50000000</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;">
                        </div>
                      </div>
                    </div>
                </div>
              </div>
			   </div>
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapse10">Payment Collection Charges</a> </h4>
                </div>
                <div id="collapse10" class="panel-collapse collapse">
                  <div class="panel-body">
                    <div class="col-sm-12">
                      <div class="col-sm-12">
                        <div class="col-sm-6">
                          <div class="checkbox i-checks">
                            <label>
                              <div class="icheckbox_square-green" style="position: relative;"><input type="checkbox" value="" style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);"></ins></div>
                              <i></i> Which Ever Is Grater </label>
                          </div>
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="col-sm-6">
                          <div class="checkbox i-checks">
                            <label>
                              <div class="icheckbox_square-green" style="position: relative;"><input type="checkbox" value="" style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);"></ins></div>
                              <i></i> Percentage of SP </label>
                          </div>
                        </div>
                        <div class="col-sm-6">
                          <div class="input-group m-b">
                            <input type="text" class="form-control">
                            <span class="input-group-addon">%</span></div>
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="col-sm-6">
                          <div class="checkbox i-checks">
                            <label>
                              <div class="icheckbox_square-green" style="position: relative;"><input type="checkbox" value="" style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);"></ins></div>
                              <i></i> Fixed Amount </label>
                          </div>
                        </div>
                        <div class="col-sm-6">
                          <div class="input-group m-b">
                            <input type="text" class="form-control">
                            <span class="input-group-addon">%</span></div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapse11">Shipping Fee</a> </h4>
                </div>
                <div id="collapse11" class="panel-collapse collapse">
                  <div class="panel-body">
                    <div class="col-sm-12">
                      <div class="col-sm-6">
                        <div class="radio">
                          <label>
                            <input type="radio" value="46" id="optionsRadios1" name="toggler">
                            Variable Shipping Charges</label>
                        </div>
                      </div>
                      <div class="col-sm-6">
                        <div class="radio">
                          <label>
                            <input type="radio" value="47" id="optionsRadios1" name="toggler">
                            Fixed Shipping Charges </label>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-12 radio1" id="blk-46">
                      <div class="col-sm-12 center-align text-center font-bold">
                        <h4 class="text-info">Which Ever is Higher</h4>
                      </div>
                      <div class="col-sm-6">
                        <h4>Volume calculation= (lxbxh)(cm)/5000</h4>
                        <table class="table table-bordered">
                          <thead>
                            <tr>
                              <th>Volume Weight Slab(gms) </th>
                              <th>Local </th>
                              <th>Zonal </th>
                              <th>National </th>
                              <th>Metro </th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td><label>&lt; 500</label></td>
                              <td><div class="form-group ">
                                  <div class=" content-rgt">
                                    <input type="text" placeholder="" class="form-control">
                                  </div>
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>500 &gt; 1000</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>1000 &gt; 1500</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>1500 &gt; 5000</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>add 1 kg</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                      <div class="col-sm-6">
                        <h4>Weight calculation</h4>
                        <table class="table table-bordered">
                          <thead>
                            <tr>
                              <th>Volume Weight Slab(gms) </th>
                              <th>Local </th>
                              <th>Zonal </th>
                              <th>National </th>
                              <th>Metro </th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td><label>&lt; 500</label></td>
                              <td><div class="form-group ">
                                  <div class=" content-rgt">
                                    <input type="text" placeholder="" class="form-control">
                                  </div>
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>additional &gt; 500</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                    <div class="col-sm-12 radio1" id="blk-47">
                      <div class="col-sm-12 center-align text-center font-bold">
                        <h4 class="text-info">Which Ever is Higher</h4>
                      </div>
                      <div class="col-sm-6">
                        <h4>Weight calculation </h4>
                        <table class="table table-bordered">
                          <thead>
                            <tr>
                              <th>Weight Weight Slab(gms) </th>
                              <th>Price </th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td><label>&lt; 500</label></td>
                              <td><div class="form-group ">
                                  <div class=" content-rgt">
                                    <input type="text" placeholder="" class="form-control">
                                  </div>
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>additional &gt; 500</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                      <div class="col-sm-6">
                        <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                        <table class="table table-bordered">
                          <thead>
                            <tr>
                              <th>Volume Weight Slab(gms) </th>
                              <th>Price </th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td><label>&lt; 500</label></td>
                              <td><div class="form-group ">
                                  <div class=" content-rgt">
                                    <input type="text" placeholder="" class="form-control">
                                  </div>
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>500 &gt; 1000</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>1000 &gt; 1500</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>1500 &gt; 5000</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>add 1 kg</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control">
                                </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapse12">Service Tax @ 14.5%</a> </h4>
                </div>
                <div id="collapse12" class="panel-collapse collapse">
                  <div class="panel-body">
                    <div class="form-group col-md-12">
                      <div class="col-md-4 content-rgt">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                  </div>
                </div>
              </div>
          </div>
        </div>
		</div><!-- -----------------------------id-40----------------------------- -->
                            </div>
                          </div>
                          <div class="col-sm-12 radio1" id="blk-41">
                            <div class="form-group col-md-12">
                              <label class="col-sm-2 control-label">label</label>
                              <div class="col-sm-3">
                                <input type="text" placeholder="" class="form-control">
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="col-sm-12">
                          <h4>Return Calculator/Charges</h4>
                        </div>
                        <div class="col-sm-12">
                          <div class="col-sm-6">
                            <div class="radio">
                              <label>
                                <input type="radio" value="42" id="optionsRadios1" name="toggler">
                                Original Terms</label>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="radio">
                              <label>
                                <input type="radio" value="43" id="optionsRadios1" name="toggler">
                                New Terms </label>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-sm-12 radio1 m-b" id="blk-42">
                            <div class="col-sm-12">
                                <div class="col-md-12">
                                  <div class="checkbox i-checks">
                                    <label>
                                      <input type="checkbox" value="">
                                      <i></i> Label </label>
                                  </div>
                                </div>
                            </div>
                          </div>
                          <div class="col-sm-12 radio1" id="blk-43">
                                <div class="ibox-content add-company">
      <div class="panel-body">
        <div class="panel-group" id="accordion">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapse13">Return Charges</a> </h5>
            </div>
            <div id="collapse13" class="panel-collapse collapse">
              <div class="panel-body">
                <h4>Seller Fault </h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="48" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="49" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-48">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Label</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-49">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="50" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="51" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-50">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-51">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <h4>Buyer Return</h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="52" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="53" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-52">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-53">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="54" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="55" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-54">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-55">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapse14">RTO Charges</a> </h4>
            </div>
            <div id="collapse14" class="panel-collapse collapse">
              <div class="panel-body">
                <h4>Seller Fault </h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="56" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="57" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-56">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-57">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="58" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="59" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-58">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-59">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <h4>Buyer Return</h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="60" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="61" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-60">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-61">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="62" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="63" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-62">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-63">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapse15">Replacement Charges</a> </h4>
            </div>
            <div id="collapse15" class="panel-collapse collapse">
              <div class="panel-body">
                <h4>Seller Fault </h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="64" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="65" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-64">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-65">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="66" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="67" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-66">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-67">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <h4>Buyer Return</h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="68" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="69" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-68">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-69">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="70" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="71" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-70">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-71">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapse16">Partial Delivery Charges</a> </h4>
            </div>
            <div id="collapse16" class="panel-collapse collapse">
              <div class="panel-body">
                <h4>Seller Fault </h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="72" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="73" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-72">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-73">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="74" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="75" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-74">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-75">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <h4>Buyer Return</h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="76" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="77" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-76">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-77">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="78" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="79" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-78">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-79">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapse17">Cancellation Charges</a> </h4>
            </div>
            <div id="collapse17" class="panel-collapse collapse">
              <div class="panel-body">
                <h4>Seller Fault </h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <h5>Before RTD</h5>
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="80" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="81" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-80">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-81">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="82" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="83" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-82">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-83">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <h5>After RTD</h5>
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="84" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="85" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-84">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-85">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="86" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="87" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-86">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-87">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <h4>Buyer Cancellation</h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="88" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="89" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-88">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-89">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="90" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="91" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-90">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-91">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
      
      <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapse18">Reverse Shipping Fee</a> </h4>
            </div>
            <div id="collapse18" class="panel-collapse collapse">
              <div class="panel-body">
        <div class="col-sm-12">
          <div class="col-sm-6">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i>( % of Shipping Fee )</label>
                      </div> 
          </div>
          <div class="col-sm-6">
            <div class="input-group m-b">
                            <input type="text" class="form-control">
                            <span class="input-group-addon">%</span>
            </div>
          </div>
                </div>    
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
        <div class="col-sm-12">
          <div class="col-sm-6">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i>No Reverse Fee </label>
                      </div>
                    </div>
          
          </div>
        
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
        
        <div class="col-sm-12">
                      <div class="col-sm-12">
                        <div class="col-sm-6">
                          <div class="checkbox i-checks">
                            <label>
                              <input type="checkbox" value="">
                              <i></i> Which Ever Is Greater </label>
                          </div>
                        </div>
            <div class="col-sm-6">
                          
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="col-sm-6">
                          <div class="checkbox i-checks">
                            <label>
                              <input type="checkbox" value="">
                
                              Flat Amount <div id="sub"><img src="img/about.png" alt="about" style="float: right;position: relative;top: -31px;left: 18px;"></div>

                </label>
               
                
                  <div id="welcome" style="display:none;">
                    
                    <p>Lorem ipsum dolor sit amet, consectetur 
                    quis 
                    </p>
                  </div>
                
                
                          </div>
                        </div>
                        <div class="col-sm-6">
                          <div class="input-group m-b">
                            <input type="text" class="form-control">
                            <span class="input-group-addon">%</span></div>
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="col-sm-6">
                          <div class="checkbox i-checks">
                            <label>
                              <input type="checkbox" value="">
                              <i></i> % of Market Fee(Payment Collection Charges) </label>
                          </div>
                        </div>
                        <div class="col-sm-6">
                          <div class="input-group m-b">
                            <input type="text" class="form-control">
                            <span class="input-group-addon">%</span></div>
                        </div>
                      </div>
                    </div>
          
        
      
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
        <div class="col-sm-12">
          <div class="col-sm-6">
            <div class="checkbox i-checks">
            <label>
                          <input type="checkbox" value="">
                          <i></i>Fix Amount 
              </label>
            </div>
                    </div>
          <div class="col-sm-6">
            <div class="input-group m-b" style="width: 96%;">
                            <input type="text" class="form-control">
                            <span class="input-group-addon">%</span>
            </div>
          </div>          
                </div>
        <div class="col-sm-12">
          <div class="hr-line-dashed">
          </div>    
        </div>
        <div class="col-sm-12">
          <div class="col-sm-6">
            <div class="checkbox i-checks">
              <label>
                <input type="checkbox" value="">
                <i></i>Same as Shipping Fee 
              </label>
            </div>
          </div>
          <div class="col-sm-6">
            
          </div>
        </div>
        <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
          <div class="col-sm-6">
            <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i>Variable Shipping </label>
                    </div>
          </div>
          <div class="col-sm-6">
          </div>
                    
                </div>
          
        <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">           
                      <label>
                        <input type="radio" value="92" id="optionsRadios1" name="toggler">
                        Dead Weight
          </label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="93" id="optionsRadios1" name="toggler">
                        Volume Weight </label>
                    </div>
                  </div>
                </div>
        <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-92">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Amount</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
          <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Per Weight</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
          <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Min Weight</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-93">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Amount</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
          <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Per Weight</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
          <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Min Weight</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
        </div>
            </div>
          </div>   
        </div>
      </div>
                          </div>
                        </div>
                    </form>
                </div>

                </div>
            </div>
            </div>
        </div>
	
</body>
</html>