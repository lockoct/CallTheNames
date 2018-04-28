package net.mmyz.ld;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReleaseDll {
	
	public static void start() {
		try {
			File fX86 = new File(System.getProperty("java.home")+"\\bin\\jacob-1.18-M3-x86.dll");
			File fX64 = new File(System.getProperty("java.home")+"\\bin\\jacob-1.18-M3-x64.dll");
			
			if(!fX86.exists()) {
				FileInputStream fisX86 =  new FileInputStream("jacob-1.18-M3-x86.dll");				
				FileOutputStream fosX86 = new FileOutputStream(fX86);
				byte[] b1 = new byte[1024];
				int byteread = 0; 
				while ( (byteread = fisX86.read(b1)) != -1) {  
					fosX86.write(b1, 0, byteread); 
				} 
				fosX86.close();
				fisX86.close();
			}else {
				System.out.println("X86 exist");
			}
			
			if (!fX64.exists()) {
				FileInputStream fisX64 =  new FileInputStream("jacob-1.18-M3-x64.dll");			
				FileOutputStream fosX64 = new FileOutputStream(fX64);
				byte[] b2 = new byte[1024];
				int byteread = 0;
				while ( (byteread = fisX64.read(b2)) != -1) {  
					fosX64.write(b2, 0, byteread); 
				} 
				fosX64.close();
				fisX64.close();				
			}else {
				System.out.println("X64 exist");				
			}
			
			System.out.println("Finished");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}