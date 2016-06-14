(function ($) {

   $.fn.pickList = function (options) {

      var opts = $.extend({}, $.fn.pickList.defaults, options);

      this.fill = function () {
         var option = '';

         $.each(opts.data, function (key, val) {
            option += '<option id=' + val.id + '>' + val.text + '</option>';
         });
         this.find('#pickData').append(option);
      };
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
                 "  <div class='col-sm-2'>" +
                 "	 <select class='form-control pickListSelect' id='pickData' multiple name='pickData' form='addpartnerform'></select>" +
                 " </div>" +
                 " <div class='col-sm-2'>" +
                 "    <select class='form-control pickListSelect' id='pickListResult' form='addpartnerform' multiple name='local' onclick=\"myFunction()\"></select>" +
                 " </div>" +
                 " <div class='col-sm-2'>" +
                 "    <select class='form-control pickListSelect' id='pickListResult11' form='addpartnerform' multiple name='zonal' onclick=\"myFunction()\"></select>" +
                 " </div>" +
                 " <div class='col-sm-2'>" +
                 "    <select class='form-control pickListSelect' id='pickListResult22' form='addpartnerform' multiple name='national' onclick=\"myFunction()\"></select>" +
                 " </div>" +
                 " <div class='col-sm-2'>" +
                 "    <select class='form-control pickListSelect' id='pickListResult33' form='addpartnerform' multiple name='metro' onclick=\"myFunction()\"></select>" +
                 " </div>" +

                 "</div>" +

                

                 "<div  class='row'>"+
                 " <div class='col-sm-2'>" +
                  " </div><div class='col-sm-2'>" +

                 "  <p id='pAdd' class='btn btn-primary btn-sm'>" + opts.add + "</p>" +
                 "  <p id='pRemove' class='btn btn-primary btn-sm'>" + opts.remove + "</p>" +
                 "  </div><div class='col-sm-2'>" +

                 "  <p id='pAdd11' class='btn btn-primary btn-sm'>" + opts.add + "</p>" +
                 "  <p id='zonal' class='btn btn-primary btn-sm'>" + opts.remove + "</p>" +
                 " </div>" +
                 "<div class='col-sm-2'>" +

                 "  <p id='pAdd22' class='btn btn-primary btn-sm'>" + opts.add + "</p>" +
                 "  <p id='national' class='btn btn-primary btn-sm'>" + opts.remove + "</p>" +
                 " </div>" +
                 "<div class='col-sm-2'>" +

                 "  <p id='pAdd33' class='btn btn-primary btn-sm'>" + opts.add + "</p>" +
                 "  <p id='metro' class='btn btn-primary btn-sm'>" + opts.remove + "</p>" +
                 " </div>" +


          
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


$('#submitButton').click(function() {
    $('#pickListResult option').prop('selected', true);
});
$('#submitButton').click(function() {
    $('#pickListResult11 option').prop('selected', true);
});
$('#submitButton').click(function() {
    $('#pickListResult22 option').prop('selected', true);
});
$('#submitButton').click(function() {
    $('#pickListResult33 option').prop('selected', true);
});