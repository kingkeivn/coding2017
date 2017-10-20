package com.coderising.download;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

import com.coderising.download.api.Connection;
import com.coderising.download.api.ConnectionManager;
import com.coderising.download.impl.ConnectionManagerImpl;

public class DownloadThread extends Thread{

	Connection conn;
	int startPos;
	int endPos;
	File file;
	String path;
	
	public DownloadThread(String path, int startPos, int endPos,File file){
		this.path = path;
		this.startPos = startPos;
		this.endPos = endPos;
		this.file = file;
	}

	@Override
	public void run() {
		
		try {
			FileDownloader downloader = new FileDownloader(path);
			ConnectionManager cm = new ConnectionManagerImpl();
			downloader.setConnectionManager(cm);
			conn = cm.open(this.path);
			byte[] byffer = conn.read(startPos, endPos);
			RandomAccessFile raf = new RandomAccessFile(file, "rws");
			raf.seek(startPos);
			raf.write(byffer);
			raf.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			conn.close();
		}

	}
	
}
