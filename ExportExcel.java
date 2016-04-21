
package SOI;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.crypto.Data;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;

import twitter4j.Status;

public class ExportExcel {

	private String path;
	private String fileName = null;
	
	public ExportExcel(String newPath)
	{
		path = newPath;
	}
	
	public void exportExcel(List<Status> tweetsdata) {
	
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Results");

		String[] cellValue = new String[8];

		for (int i = 0; i < tweetsdata.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			Status status = tweetsdata.get(i);

			cellValue[0] = status.getUser().getName();
			cellValue[1] = "" + status.getUser().getId();
			cellValue[2] = status.getText();
			if(status.getGeoLocation() != null)
				cellValue[3] = "" + status.getGeoLocation().getLatitude() + "," + status.getGeoLocation().getLongitude();
			else
//				cellValue[3] = "";
                                cellValue[3] = status.getUser().getLocation();
			cellValue[4] = "" + status.getFavoriteCount();
			cellValue[5] = "" + status.getRetweetCount();
			cellValue[6] = "" + status.getId();
			cellValue[7] = "" + status.getCreatedAt();
					
			for (int j = 0; j < cellValue.length; j++) {
				HSSFCell cell = row.createCell(j);
				HSSFRichTextString text = new HSSFRichTextString(cellValue[j]);
				cell.setCellValue(text);
			}

		}
		  try {
			  Date time = new Date();
			  if(fileName == null)
			  {
				  fileName = path + "/TweetData" + (time.getYear()+1900) +"_" + (time.getMonth() +1) + "_" + time.getDate() +".xls";
				  File f = new File(fileName);
		          FileOutputStream out = new FileOutputStream(f);
		          wb.write(out);
		          out.close();          
			  }
			  else
			  {
				  try {
					    FileInputStream file = new FileInputStream(new File(fileName));

					    HSSFWorkbook workbook = new HSSFWorkbook(file);
					    HSSFSheet workingSheet = workbook.getSheet("Results");
					    HSSFCell cell = null;

					    // Find empty cell     

					    // Update the value of cell

					    file.close();

					    FileOutputStream outFile = new FileOutputStream(new File(fileName));
					    workbook.write(outFile);
					    outFile.close();

					} catch (FileNotFoundException e) {
					    e.printStackTrace();
					} catch (IOException e) {
					    e.printStackTrace();
					}
				  
			  }
	        } catch (FileNotFoundException e) {
	            JOptionPane.showMessageDialog(null, "Fail!");
	            e.printStackTrace();
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "OK!");
	            e.printStackTrace();
	        }

	}
}

