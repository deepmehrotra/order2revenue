package com.o2r.helper;



import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * Writes the report to the output stream
 * 
 * @author Deep Mehrotra
 */
public class Writer {

	private static Logger logger = Logger.getLogger("service");
	/**
	 * Writes the report to the output stream
	 */
	public static void write(HttpServletResponse response, HSSFSheet worksheet,String reportName) {
 		
		logger.debug("Writing report to the stream");
		try {
				if(!reportName.endsWith(".xls"))
					reportName = reportName+".xls";
		        response.setContentType("application/vnd.openxml");
		        response.setHeader("Content-disposition", "attachment; filename=\"" + reportName + "\"" );
		        ServletOutputStream out = response.getOutputStream();
		        worksheet.getWorkbook().write(out);		//response.setContentType("application/msexcel");
			    response.flushBuffer();

		} catch (Exception e) {
			logger.error("Unable to write report to the output stream");
		}
	}
}
