package net.mmyz.ld;

import javax.swing.SwingWorker;
/*
 * �̳�SwingWorker�࣬���԰�TTS�ʶ��ı�������
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
