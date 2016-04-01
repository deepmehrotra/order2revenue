package com.o2r.bean;
import java.util.Date;
import java.util.List;
import java.util.Map;
public class DashboardBean {
	private double profitThisYear;
       private double profitLastYear;
            private double percentChangeInProfit;
        private long saleQuantityThisYear;
                private long saleQuantityLastYear;
                private double percentChangeInSQ;
                private long totalCustomers;
                private long totalStock;
                private Map<Date,Long> last30daysOrderCount;
                private Map<Date,Long> last30DaysPaymentCount;
                private  Map<Date,Long> last12MonthsOrderCount;
                private  Map<Date,Long> last12MonthsPaymentCount;
                private long todaysOrderCount;
                private long thisMonthOrderCount;
                private long thisYearOrderCount;
                private long todaysPaymentCount;
                private long thisMonthPaymentCount;
                private long thisYearPaymentCount;
                private double todaysGrossProfit;
                private double thisMonthGrossProfit;
                private double thisYearGrossProfit;
                private Map<Date,Double> totalUpcomingPayments;
                private Map<String,Double>totalOutstandingPayments;
                private Map<String,Long> topSellingSKU;
                private Map<String,Long> topSellingRegion;
                private Map<String,Double> expenditurePercentage;
                private Map<String,Double> expenditureThisMonth;
                private Map<String,Double> grossProfitMonthly;
                private Map<String,Double> expenditureMonthly;
                private Map<String,Double> profitLastSixMonth;
                private List<TaxDetailBean> taxAlerts;
                private List<TaxDetailBean> tdsAlerts;
                private Map<String,Long> saleQuantity;
                private Map<String,Long> returnQuantity;
                private Map<String,Double> saleAmount;
                private Map<String,Double> returnAmount;
                public double getProfitThisYear() {
                                return profitThisYear;
                }
                public void setProfitThisYear(double profitThisYear) {
                                this.profitThisYear = profitThisYear;
                }
                public double getProfitLastYear() {
                                return profitLastYear;
                }
                public void setProfitLastYear(double profitLastYear) {
                                this.profitLastYear = profitLastYear;
                }
                public double getPercentChangeInProfit() {
                                return percentChangeInProfit;
                }
                public void setPercentChangeInProfit(double percentChangeInProfit) {
                                this.percentChangeInProfit = percentChangeInProfit;
                }
                public long getSaleQuantityThisYear() {
                                return saleQuantityThisYear;
                }
                public void setSaleQuantityThisYear(long saleQuantityThisYear) {
                                this.saleQuantityThisYear = saleQuantityThisYear;
                }
                public double getPercentChangeInSQ() {
                                return percentChangeInSQ;
                }
                public void setPercentChangeInSQ(double percentChangeInSQ) {
                                this.percentChangeInSQ = percentChangeInSQ;
                }
                public long getTotalCustomers() {
                                return totalCustomers;
                }
                public void setTotalCustomers(long totalCustomers) {
                                this.totalCustomers = totalCustomers;
                }
                public long getTotalStock() {
                                return totalStock;
                }
                public void setTotalStock(long totalStock) {
                                this.totalStock = totalStock;
                }
                public long getTodaysOrderCount() {
                                return todaysOrderCount;
                }
                public void setTodaysOrderCount(long todaysOrderCount) {
                                this.todaysOrderCount = todaysOrderCount;
                }
                public long getThisMonthOrderCount() {
                                return thisMonthOrderCount;
                }
                public void setThisMonthOrderCount(long thisMonthOrderCount) {
                                this.thisMonthOrderCount = thisMonthOrderCount;
                }
                public long getThisYearOrderCount() {
                                return thisYearOrderCount;
                }
                public void setThisYearOrderCount(long thisYearOrderCount) {
                                this.thisYearOrderCount = thisYearOrderCount;
                }
                public long getTodaysPaymentCount() {
                                return todaysPaymentCount;
                }
                public void setTodaysPaymentCount(long todaysPaymentCount) {
                                this.todaysPaymentCount = todaysPaymentCount;
                }
                public long getThisMonthPaymentCount() {
                                return thisMonthPaymentCount;
                }
                public void setThisMonthPaymentCount(long thisMonthPaymentCount) {
                                this.thisMonthPaymentCount = thisMonthPaymentCount;
                }
                public long getThisYearPaymentCount() {
                                return thisYearPaymentCount;
                }
                public void setThisYearPaymentCount(long thisYearPaymentCount) {
                                this.thisYearPaymentCount = thisYearPaymentCount;
                }
                public void setThisYearGrossProfit(long thisYearGrossProfit) {
                                this.thisYearGrossProfit = thisYearGrossProfit;
                }
                public Map<String, Long> getTopSellingSKU() {
                                return topSellingSKU;
                }
                public void setTopSellingSKU(Map<String, Long> topSellingSKU) {
                                this.topSellingSKU = topSellingSKU;
                }
                public Map<String, Long> getTopSellingRegion() {
                                return topSellingRegion;
                }
                public void setTopSellingRegion(Map<String, Long> topSellingRegion) {
                                this.topSellingRegion = topSellingRegion;
                }
                public Map<String, Double> getExpenditurePercentage() {
                                return expenditurePercentage;
                }
                public void setExpenditurePercentage(Map<String, Double> expenditurePercentage) {
                                this.expenditurePercentage = expenditurePercentage;
                }
                public Map<String, Double> getExpenditureThisMonth() {
                                return expenditureThisMonth;
                }
                public void setExpenditureThisMonth(Map<String, Double> expenditureThisMonth) {
                                this.expenditureThisMonth = expenditureThisMonth;
                }
                public Map<String, Double> getProfitLastSixMonth() {
                                return profitLastSixMonth;
                }
                public void setProfitLastSixMonth(Map<String, Double> profitLastSixMonth) {
                                this.profitLastSixMonth = profitLastSixMonth;
                }
                public List<TaxDetailBean> getTaxAlerts() {
                                return taxAlerts;
                }
                public void setTaxAlerts(List<TaxDetailBean> taxAlerts) {
                                this.taxAlerts = taxAlerts;
                }
                public List<TaxDetailBean> getTdsAlerts() {
                                return tdsAlerts;
                }
                public void setTdsAlerts(List<TaxDetailBean> tdsAlerts) {
                                this.tdsAlerts = tdsAlerts;
                }
                public long getSaleQuantityLastYear() {
                                return saleQuantityLastYear;
                }
                public void setSaleQuantityLastYear(long saleQuantityLastYear) {
                                this.saleQuantityLastYear = saleQuantityLastYear;
                }
                @Override
                public String toString() {
                                return "DashboardBean [profitThisYear=" + profitThisYear
                                                                + ", profitLastYear=" + profitLastYear
                                                                + ", percentChangeInProfit=" + percentChangeInProfit
                                                                + ", saleQuantityThisYear=" + saleQuantityThisYear
                                                                + ", saleQuantityLastYear=" + saleQuantityLastYear
                                                                + ", percentChangeInSQ=" + percentChangeInSQ
                                                                + ", totalCustomers=" + totalCustomers + ", totalStock="
                                                                + totalStock + ", last30daysOrderCount=" + last30daysOrderCount
                                                                + ", last30DaysPaymentCount=" + last30DaysPaymentCount
                                                                + ", last12MonthsOrderCount=" + last12MonthsOrderCount
                                                                + ", last12MonthsPaymentCount=" + last12MonthsPaymentCount
                                                                + ", todaysOrderCount=" + todaysOrderCount
                                                                + ", thisMonthOrderCount=" + thisMonthOrderCount
                                                                + ", thisYearOrderCount=" + thisYearOrderCount
                                                                + ", todaysPaymentCount=" + todaysPaymentCount
                                                                + ", thisMonthPaymentCount=" + thisMonthPaymentCount
                                                                + ", thisYearPaymentCount=" + thisYearPaymentCount
                                                                + ", todaysGrossProfit=" + todaysGrossProfit
                                                                + ", thisMonthGrossProfit=" + thisMonthGrossProfit
                                                                + ", thisYearGrossProfit=" + thisYearGrossProfit
                                                                + ", totalUpcomingPayments=" + totalUpcomingPayments
                                                                + ", totalOutstandingPayments=" + totalOutstandingPayments
                                                                + ", topSellingSKU=" + topSellingSKU + ", topSellingRegion="
                                                                + topSellingRegion + ", expenditurePercentage="
                                                                + expenditurePercentage + ", expenditureThisMonth="
                                                                + expenditureThisMonth + ", grossProfitMonthly="
                                                                + grossProfitMonthly + ", expenditureMonthly="
                                                                + expenditureMonthly + ", profitLastSixMonth="
                                                                + profitLastSixMonth + ", taxAlerts=" + taxAlerts
                                                                + ", tdsAlerts=" + tdsAlerts + ", saleQuantity=" + saleQuantity
                                                                + ", returnQuantity=" + returnQuantity + ", saleAmount="
                                                                + saleAmount + ", returnAmount=" + returnAmount + "]";
                }
                public Map<Date, Long> getLast30daysOrderCount() {
                                return last30daysOrderCount;
                }
                public void setLast30daysOrderCount(Map<Date, Long> last30daysOrderCount) {
                                this.last30daysOrderCount = last30daysOrderCount;
                }
                public Map<Date, Long> getLast30DaysPaymentCount() {
                                return last30DaysPaymentCount;
                }
                public void setLast30DaysPaymentCount(Map<Date, Long> last30DaysPaymentCount) {
                                this.last30DaysPaymentCount = last30DaysPaymentCount;
                }
                public Map<Date, Long> getLast12MonthsOrderCount() {
                                return last12MonthsOrderCount;
                }
                public void setLast12MonthsOrderCount(Map<Date, Long> last12MonthsOrderCount) {
                                this.last12MonthsOrderCount = last12MonthsOrderCount;
                }
                public Map<Date, Long> getLast12MonthsPaymentCount() {
                                return last12MonthsPaymentCount;
                }
                public void setLast12MonthsPaymentCount(Map<Date, Long> last12MonthsPaymentCount) {
                                this.last12MonthsPaymentCount = last12MonthsPaymentCount;
                }
                public double getTodaysGrossProfit() {
                                return todaysGrossProfit;
                }
                public void setTodaysGrossProfit(double todaysGrossProfit) {
                                this.todaysGrossProfit = todaysGrossProfit;
                }
                public double getThisMonthGrossProfit() {
                                return thisMonthGrossProfit;
                }
                public void setThisMonthGrossProfit(double thisMonthGrossProfit) {
                                this.thisMonthGrossProfit = thisMonthGrossProfit;
                }
                public double getThisYearGrossProfit() {
                                return thisYearGrossProfit;
                }
                public void setThisYearGrossProfit(double thisYearGrossProfit) {
                                this.thisYearGrossProfit = thisYearGrossProfit;
                }
                public Map<Date, Double> getTotalUpcomingPayments() {
                                return totalUpcomingPayments;
                }
                public void setTotalUpcomingPayments(Map<Date, Double> totalUpcomingPayments) {
                                this.totalUpcomingPayments = totalUpcomingPayments;
                }
                public Map<String, Double> getTotalOutstandingPayments() {
                                return totalOutstandingPayments;
                }
                public void setTotalOutstandingPayments(
                                                Map<String, Double> totalOutstandingPayments) {
                                this.totalOutstandingPayments = totalOutstandingPayments;
                }
                           public Map<String, Double> getGrossProfitMonthly() {
                                  return grossProfitMonthly;
                           }
                           public void setGrossProfitMonthly(Map<String, Double> grossProfitMonthly) {
                                  this.grossProfitMonthly = grossProfitMonthly;
                           }
                           public Map<String, Double> getExpenditureMonthly() {
                                  return expenditureMonthly;
                           }
                           public void setExpenditureMonthly(Map<String, Double> expenditureMonthly) {
                                  this.expenditureMonthly = expenditureMonthly;
                           }
                           public Map<String, Long> getSaleQuantity() {
                                  return saleQuantity;
                           }
                           public void setSaleQuantity(Map<String, Long> saleQuantity) {
                                  this.saleQuantity = saleQuantity;
                           }
                           public Map<String, Long> getReturnQuantity() {
                                  return returnQuantity;
                           }
                           public void setReturnQuantity(Map<String, Long> returnQuantity) {
                                  this.returnQuantity = returnQuantity;
                           }
                           public Map<String, Double> getSaleAmount() {
                                  return saleAmount;
                           }
                           public void setSaleAmount(Map<String, Double> saleAmount) {
                                  this.saleAmount = saleAmount;
                           }
                           public Map<String, Double> getReturnAmount() {
                                  return returnAmount;
                           }
                           public void setReturnAmount(Map<String, Double> returnAmount) {
                                  this.returnAmount = returnAmount;
                           }
}
