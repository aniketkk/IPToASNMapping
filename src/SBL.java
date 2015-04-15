import java.util.concurrent.ConcurrentLinkedQueue;


public class SBL implements Runnable{
	Thread SBLGroper;
	ConcurrentLinkedQueue<String> queue;
	String currentDateAndTime;
	RedisClient client;
	
	public SBL(ConcurrentLinkedQueue<String> queue){
		
		SBLGroper = new Thread(this);
		this.queue = queue;
		client = new RedisClient();
		SBLGroper.start();
		
	}
	
	@Override
	public void run(){
		
		while(!queue.isEmpty()){
			String IPAddress = queue.poll();
			
			if(!client.isUpToDate(IPAddress)){
				if(IPAddress.charAt(0) == '#')
				    client.createSBLEntry(IPAddress.substring(1, IPAddress.indexOf('$')-1), ASNMapper.getASNumber(IPAddress));
				else{
					if(IPAddress.indexOf('/') == -1)
						client.createSBLEntry(IPAddress, "");
					else
						client.createSBLEntry(IPAddress.substring(0, IPAddress.indexOf('$')-1), ASNMapper.getASNumber(IPAddress));
				}
			}
		}
	}
}
