import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ASNMapper {

	public static String getASNumber(String IPAddress){
		StringBuilder reverseIP = new StringBuilder(IPAddress);//
		reverseIP = reverse(IPAddress);
		
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		Process p;
 
		try {
			p = Runtime.getRuntime().exec("dig +short "+ reverseIP +".origin.asn.cymru.com TXT");
			p.waitFor();
			reader = new BufferedReader(
				new InputStreamReader(p.getInputStream()));
 
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
 
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(IPAddress + "   "+reverseIP);
		return sb.substring(1, sb.indexOf("|") -1);
	}
	
	private static StringBuilder reverse(String IPAddress){
		int start  = IPAddress.length();
		int dotTracker = start - 1;
		StringBuilder reverseIP = new StringBuilder();
		
		for(int i = 0; i <= IPAddress.length() - 1; i++){
			if(IPAddress.charAt(dotTracker) == '.'){
				reverseIP.append(IPAddress.substring(dotTracker+1, start));
				reverseIP.append(".");
				start = dotTracker;
			}
			dotTracker--;
		}
		reverseIP.append(IPAddress.substring(dotTracker+1, start));
		return reverseIP;
	}
}
