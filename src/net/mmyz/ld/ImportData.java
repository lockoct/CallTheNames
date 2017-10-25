package net.mmyz.ld;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ImportData {
	String path;
	String[] names;
	public ImportData(String path) {
		this.path = path.replace("\\", "\\\\");
	}
	
	private String[] process(){
		try {
			FileInputStream fis = new FileInputStream(this.path);
			
			//获取Excel中的数据
			Workbook workBook = Workbook.getWorkbook(fis);
			Sheet sheet = workBook.getSheet(0);
			//names数组存放名字
			this.names = new String[sheet.getRows()];
			for (int i = 1; i < names.length; i++) {
				//将单元格数据输入数组中
				names[i] = sheet.getCell(0,i).getContents();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return names;
	}
}
