/*$(function() {
Morris.Donut({
        element: 'morris-donut-chart',
        data: [{ label: "Download Sales", value: 12 },
            { label: "In-Store Sales", value: 30 },
            { label: "Mail-Order Sales", value: 20 } ],
        resize: true,
        colors: ['#87d6c6', '#54cdb4','#1ab394'],
    });
});*/
function plotdonut(expensedata ,expenseColor) {
Morris.Donut({
        element: 'morris-donut-chart',
        data: expensedata,
        resize: true,
         colors:expenseColor
    });
 
}