import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;


public class DNSResponseParser {
	ConcurrentLinkedQueue<String> dblQueue = new ConcurrentLinkedQueue<>();
	ConcurrentLinkedQueue<String> xblQueue = new ConcurrentLinkedQueue<>();
	ConcurrentLinkedQueue<String> sblQueue = new ConcurrentLinkedQueue<>();
	
	public void getIPAddresses(String input){
		
		try {
			dblQueue = FileReader.dblReader(dblQueue, input, "dbl");
			
			DNSToIP[] digger = new DNSToIP[10];
			
			
			for(int i = 0; i < 10; i++){
				digger[i] = new DNSToIP(dblQueue);
			}
		} 
		
		catch (IOException e) {}

	}
	
	public void xblAddresses(String input){
		
		try {
			xblQueue = FileReader.dblReader(xblQueue, input, "xbl");
		} catch (IOException e) {}
		
		XBL[] xbldigger = new XBL[10];
		for(int i = 0; i < 10; i++){
			xbldigger[i] = new XBL(xblQueue);
		}
	}
	
	public void sblAddresses(String input){
		
		try {
			sblQueue = FileReader.dblReader(sblQueue, input, "sbl");
			System.out.println(sblQueue);
		} catch (IOException e) {}
		
		SBL[] sbldigger = new SBL[10];
		for(int i = 0; i < 10; i++){
			sbldigger[i] = new SBL(sblQueue);
		}
	}
		
}



