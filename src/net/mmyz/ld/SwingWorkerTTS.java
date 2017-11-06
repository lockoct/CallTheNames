package net.mmyz.ld;

import javax.swing.SwingWorker;
/*
 * 继承SwingWorker类，可以把TTS朗读文本传进来
 */
public class SwingWorkerTTS extends SwingWorker<Void, Void> {
	public String text;
	
	public SwingWorkerTTS(String nextName) {
		this.text = nextName;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		return null;
	}

}
