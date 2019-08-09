package com.hwl.im.server;

public class IMServerLauncher {

	public static void main(String[] args) throws Exception {
		if (args == null || args.length <= 0){
			args = new String[]{"127.0.0.1", "8088"};
		}
	    IMServer im=new IMServer(args[0],Integer.parseInt(args[1]));
		im.start();
	}   
}