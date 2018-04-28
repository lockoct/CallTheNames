package net.mmyz.ld;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportData {
	private ArrayList<String> name;
	private ArrayList<String> status;
	
	public ExportData(ArrayList<String> name,ArrayList<String> status) {
		this.name = name;
		this.status = status;
	}
	public void toExcel(String path) {
		String[] title = {"姓名","出席情况"};
		WritableWorkbook wwb;
		try {
			FileOutputStream fos = new FileOutputStream(path);
			wwb = Workbook.createWorkbook(fos);
			WritableSheet sheet = wwb.createSheet("Sheet1", 0);
			Label label = new Label(0, 0, title[0]);
			sheet.addCell(label);
			
			label = new Label(1, 0, title[1]);
			sheet.addCell(label);
			
			//导入姓名和出席情况到单元格中
			for (int i = 0; i < name.size(); i++) {
				label = new Label(0, i+1, name.get(i));
				sheet.addCell(label);
				
				label = new Label(1, i+1, status.get(i));
				sheet.addCell(label);
			}
			wwb.write();
			wwb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}
}
