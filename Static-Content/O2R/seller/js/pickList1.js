(function ($) {

   $.fn.pickList = function (options) {

      var opts = $.extend({}, $.fn.pickList.defaults, options);
		
		if($('#hidlocalList').val())
       {
       
                        metroList=$('#hidmetroList').val().split(",");
                       nationalList=$('#hidnationalList').val().split(",");
                       zonalList=$('#hidzonalList').val().split(",");
                       localList=$('#hidlocalList').val().split(",");
                    
           
            this.fill = function () {
                var localoption = '';
                var nationaloption = '';
                var zonaloption = '';
                var metrooption = '';
                
                for(var temp in localList)
                {
                 localoption += '<option id=' +localList[temp] + '>' + localList[temp]+ '</option>';
                } 
                for(var temp in metroList)
                {
                 metrooption += '<option id=' +metroList[temp] + '>' + metroList[temp]+ '</option>';
                } 
                for(var temp in nationalList)
                {
                 nationaloption += '<option id=' +nationalList[temp] + '>' + nationalList[temp]+ '</option>';
                } 
                for(var temp in zonalList)
                {
                 zonaloption += '<option id=' +zonalList[temp] + '>' + zonalList[temp]+ '</option>';
                } 
       
        this.find('#pickData').append(localoption);
         this.find('#pickData1').append(zonaloption);
         this.find('#pickData3').append(nationaloption);
         this.find('#pickData2').append(metrooption);
     };
           
       }
       else
       {
      this.fill = function () {
         var option = '';

         $.each(opts.data, function (key, val) {
            option += '<option id=' + val.id + '>' + val.text + '</option>';
         });
         this.find('#pickData').append(option);
         this.find('#pickData1').append(option);
         this.find('#pickData2').append(option);
         this.find('#pickData3').append(option);

      };
	  }
      this.controll = function () {
        var pickThis = this;

        $("#pAdd").on('click', function () {
            var p = pickThis.find("#pickData option:selected");
            p.clone().appendTo("#pickListResult");
            p.remove();
        });

        $("#pAdd11").on('click', function () {
            var p = pickThis.find("#pickData option:selected");
            p.clone().appendTo("#pickListResult11");
            p.remove();
        });

        $("#pAdd22").on('click', function () {
            var p = pickThis.find("#pickData option:selected");
            p.clone().appendTo("#pickListResult22");
            p.remove();
        });
        $("#pAdd33").on('click', function () {
            var p = pickThis.find("#pickData option:selected");
            p.clone().appendTo("#pickListResult33");
            p.remove();
        });

         $("#pRemove").on('click', function () {
            var p = pickThis.find("#pickListResult option:selected");
            p.clone().appendTo("#pickData");  
            p.remove();

            var options = new Array();
            $(pickThis.find("#pickData option")).each(function(i){options[i] = $(this).text();});
            options.sort();
            $(pickThis.find("#pickData option")).remove();
            var opts = '';
            options.forEach( function(item) {       
                opts += '<option>' + item + '</option>';
            });
                pickThis.find("#pickData").append(opts);
         });

         

         $("#zonal").on('click', function () {
            var p = pickThis.find("#pickListResult11 option:selected");
            p.clone().appendTo("#pickData");
            p.remove();

            var options = new Array();
            $(pickThis.find("#pickData option")).each(function(i){options[i] = $(this).text();});
            options.sort();
            $(pickThis.find("#pickData option")).remove();
            var opts = '';
            options.forEach( function(item) {       
                opts += '<option>' + item + '</option>';
            });
                pickThis.find("#pickData").append(opts);

         });

         $("#national").on('click', function () {
            var p = pickThis.find("#pickListResult22 option:selected");
            p.clone().appendTo("#pickData");
            p.remove();

            var options = new Array();
            $(pickThis.find("#pickData option")).each(function(i){options[i] = $(this).text();});
            options.sort();
            $(pickThis.find("#pickData option")).remove();
            var opts = '';
            options.forEach( function(item) {       
                opts += '<option>' + item + '</option>';
            });
                pickThis.find("#pickData").append(opts);

         });
         $("#metro").on('click', function () {
            var p = pickThis.find("#pickListResult33 option:selected");
            p.clone().appendTo("#pickData");
            p.remove();

            var options = new Array();
            $(pickThis.find("#pickData option")).each(function(i){options[i] = $(this).text();});
            options.sort();
            $(pickThis.find("#pickData option")).remove();
            var opts = '';
            options.forEach( function(item) {       
                opts += '<option>' + item + '</option>';
            });
                pickThis.find("#pickData").append(opts);

         });
      };

      this.getValues = function () {
         var objResult = [];
         this.find("#pickListResult option").each(function () {
            objResult.push({id: this.id, text: this.text});
         });
         return objResult;
      };
      this.init = function () {
         var pickListHtml =

                 "<div class='row'>" +
                 " <div class='col-sm-4'>" +
                 " <label>LOCAL</label>" +
                 " </div>" +
                 " <div class='col-sm-8'>" +
                 "   <select class='form-control pickListSelect' id='pickData' multiple name='account' form='check' style='height:67px;padding: 3px 0px 0px 9px;'></select> " +
                 " </div>" +
                 " <div class='col-sm-4'>" +
                 "  <label>ZONAL</label> " +
                 " </div>" +
                 " <div class='col-sm-8'>" +
                 "  <select class='form-control pickListSelect' id='pickData1' multiple name='account' form='check' style='height:67px;padding: 3px 0px 0px 9px;'></select>" +
                 " </div>" +
                 "  <div class='col-sm-4'>" +
                 "  <label>METRO</label> " +
                 " </div>" +
                 "  <div class='col-sm-8'>" +
                 "   <select class='form-control pickListSelect' id='pickData2' multiple name='account' form='check' style='height:67px;padding: 3px 0px 0px 9px;'></select>" +
                 " </div>" +
                 "  <div class='col-sm-4'>" +
                 "   <label>NATIONAL</label> " +
                 " </div>" +
                 "  <div class='col-sm-8'>" +
                 "   <select class='form-control pickListSelect' id='pickData3' multiple name='account' form='check' style='height:67px;padding: 3px 0px 0px 9px;'></select>" +
                 " </div>" +

                 "</div>" +

                

                 

          
                 "</div>";

         this.append(pickListHtml);

         this.fill();
         this.controll();
      };

      this.init();
      return this;
   };

   $.fn.pickList.defaults = {
      add: '+',
      remove: '-'
   };
  

}(jQuery));

function myFunction(){
 document.getElementById("pickListResult").selected='selected';

  }


$('#select').click(function() {
    $('#pickListResult option').prop('selected', true);
});
$('#select').click(function() {
    $('#pickListResult11 option').prop('selected', true);
});
$('#select').click(function() {
    $('#pickListResult22 option').prop('selected', true);
});
$('#select').click(function() {
    $('#pickListResult33 option').prop('selected', true);
});