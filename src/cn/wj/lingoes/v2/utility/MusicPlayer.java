package cn.wj.lingoes.v2.utility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class MusicPlayer {

	// static Player player = null;

//	public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
//		File file = new File("/Users/I329722/Desktop/Lingoes词典3/good.mp3");
//		FileInputStream fis = new FileInputStream(file);
//		BufferedInputStream stream = new BufferedInputStream(fis);
//		Player player = new Player(stream);
//		player.play();
//	}

	/**
	 * 播放 20 秒并结束播放
	 */
	public static void play(String path) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					File file = new File(path);
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream stream = new BufferedInputStream(fis);
					Player player = new Player(stream);
					player.play();
				} catch (Exception e) {

				}
			}
		}).start();

//		try {
//			Thread.sleep(20000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		// player.close();
	}

}