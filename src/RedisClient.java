import java.util.HashMap;
import java.util.Map;
import redis.clients.jedis.Jedis;


public class RedisClient {
	private Jedis redisServerConnection;
	
	public RedisClient(){
		redisServerConnection = new Jedis("localhost");
		System.out.println("Server is running: "+ redisServerConnection.ping());
		redisServerConnection.sadd("DBL", "CreateDBLSet");
		redisServerConnection.sadd("XBL", "CreateXBLSet");
		redisServerConnection.sadd("SBL", "CreateSBLSet");
	}
	
	public void createEntry(String DNSDomainName, String IPAddress, String ASNumber){
		String time = redisServerConnection.time().get(0);
		redisServerConnection.hmset(DNSDomainName, addValue(IPAddress, time, ASNumber));
		redisServerConnection.expire(DNSDomainName, 300);
		redisServerConnection.sadd("DBL", DNSDomainName);
		
	}
	
	public void createXBLEntry(String IPAddress, String ASNumber){
		String time = redisServerConnection.time().get(0);
		redisServerConnection.hmset(IPAddress, addXBLValue(time, ASNumber));
		redisServerConnection.expire(IPAddress, 300);
		redisServerConnection.sadd("XBL", IPAddress);
		
	}
	public void createSBLEntry(String IPAddress, String ASNumber){
		String time = redisServerConnection.time().get(0);
		redisServerConnection.hmset(IPAddress, addXBLValue(time, ASNumber));
		redisServerConnection.expire(IPAddress, 300);
		redisServerConnection.sadd("SBL", IPAddress);
		
	}
	
	public String getTimeOfEntry(String DNSDomain){
		return redisServerConnection.hget(DNSDomain, "Time");
	}
	
	public void deleteEntry(String DNSDomain, String IPAddress, String timeOfIteration, String ASNumber){
		redisServerConnection.srem("DBL", DNSDomain);
		remove(DNSDomain);
		
	}
	
	public boolean isUpToDate(String DNSDomain){
		return redisServerConnection.exists(DNSDomain) && redisServerConnection.ttl(DNSDomain) > 120 ;
	}
	
	private Map<String, String> addValue(String IPAddress, String timeOfIteration, String ASNumber){
		Map<String, String> hash = new HashMap<>();
		hash.put("IPAddress", IPAddress);
		hash.put("Time", timeOfIteration);
		hash.put("ASNumber", ASNumber);
		
		return hash;
	}
	
	private Map<String, String> addXBLValue(String timeOfIteration, String ASNumber){
		Map<String, String> hash = new HashMap<>();
		hash.put("Time", timeOfIteration);
		hash.put("ASNumber", ASNumber);
		
		return hash;
	}
	
	private void remove(String DNSDomain){
		redisServerConnection.hdel(DNSDomain, "IPAddress");
		redisServerConnection.hdel(DNSDomain, "Time");
		redisServerConnection.hdel(DNSDomain, "ASNumber");
	}
	
	
}




   

