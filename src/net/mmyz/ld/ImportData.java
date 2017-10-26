package net.mmyz.ld;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ImportData {
	String path;
	String[][] names;
	public ImportData(String path) {
		this.path = path.replace("\\", "\\\\");
	}
	
	public String[][] process(){
		try {
			FileInputStream fis = new FileInputStream(this.path);
			
			//��ȡExcel�е�����
			Workbook workBook = Workbook.getWorkbook(fis);
			Sheet sheet = workBook.getSheet(0);
			//names����������
			this.names = new String[72][2];
			//��ʼ������Ϣ
			for (int i = 0; i < 2; i++) {
				for(int j = 0; j < 72; j++) {
					//����Ԫ����������������
					names[j][i] = sheet.getCell(i,j).getContents();
				}
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
