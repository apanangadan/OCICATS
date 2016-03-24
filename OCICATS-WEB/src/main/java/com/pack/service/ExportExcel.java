package com.pack.service;

import java.awt.image.BufferedImage;
import java.io.File;
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

import twitter4j.Status;

public class ExportExcel {

	public void exportExcel(List<Status> tweetsdata) {
		// 创建�?个工作薄
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("new sheet");

		String[] cellValue = new String[2];

		for (int i = 0; i < tweetsdata.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			Status status = tweetsdata.get(i);

			cellValue[0] = status.getUser().getName();
			cellValue[1] = status.getText();
			for (int j = 0; j < cellValue.length; j++) {
				HSSFCell cell = row.createCell(j);
				HSSFRichTextString text = new HSSFRichTextString(cellValue[j]);
				cell.setCellValue(text);
			}

		}
		  try {
	            //默认导出到E盘下
			  Date time = new Date();
			  	String path = "D://"+time.getTime()+".xls";
			  	File f = new File(path);
	            FileOutputStream out = new FileOutputStream(f);
	            wb.write(out);
	            out.close();        
	        } catch (FileNotFoundException e) {
	            JOptionPane.showMessageDialog(null, "Fail!");
	            e.printStackTrace();
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "OK!");
	            e.printStackTrace();
	        }

	}
}
