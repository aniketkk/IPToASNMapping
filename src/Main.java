

import java.io.File;
import java.io.IOException;


public class Main {

	public static void main(String[] args) {
		
		DNSResponseParser parser = new DNSResponseParser();
		System.setProperty("java.net.preferIPv4Stack" , "true");
		//System.setProperty("sun.net.spi.nameservice.nameservers", "8.8.8.8");
		//System.setProperty("sun.net.spi.nameservice.provider.1", "dns,sun");
		File dir = new File(".");
		String path = null;
		try {
			path = dir.getCanonicalPath() + File.separator;
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fdbl = path + "src/dbl.txt";
		String fxbl = path + "src/xbl.txt";
		String fsbl = path + "src/sbl.txt";
		parser.getIPAddresses(fdbl);
		parser.xblAddresses(fxbl);
		parser.sblAddresses(fsbl);
	}

}
