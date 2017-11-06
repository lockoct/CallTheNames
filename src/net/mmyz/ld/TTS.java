package net.mmyz.ld;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
/*
 *TTS∑¢“Ù
 */
public class TTS {
	public void speak(String name) {
		ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
		Dispatch sapo = sap.getObject();
				
		try {

			sap.setProperty("Volume", new Variant(100));
			sap.setProperty("Rate", new Variant(0));

			Dispatch.call(sapo, "Speak", new Variant(name));

		} catch (Exception e) {
			e.printStackTrace();		
		} finally { 
			sapo.safeRelease();
			sap.safeRelease();
		}
	}
}
