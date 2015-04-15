import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;



public class DNSToIP implements Runnable {
	Thread DNSToIPConverter;
	ConcurrentLinkedQueue<String> queue;
	String currentDateAndTime;
	RedisClient client;
	
	public DNSToIP(ConcurrentLinkedQueue<String> queue){
		
		DNSToIPConverter = new Thread(this);
		this.queue = queue;
		client = new RedisClient();
		DNSToIPConverter.start();
		
	}
	
	@Override
	public void run(){
		
		while(!queue.isEmpty()){
			
			String hostName = queue.poll();
			try {
				
					InetAddress[] ipaddresses = InetAddress.getAllByName(hostName);
				    for(InetAddress i:ipaddresses){
				    	if(!client.isUpToDate(hostName)){
				    		String IPAddress = i.getHostAddress();
				    		client.createEntry(hostName, IPAddress, ASNMapper.getASNumber(IPAddress));
				    		}
				    	
				    }
			} 
			
			catch ( UnknownHostException e ) {
				
			} 
			
		}
	}

}


