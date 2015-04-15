import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentLinkedQueue;


public class FileReader {
	
	public static ConcurrentLinkedQueue<String> dblReader(ConcurrentLinkedQueue<String> queue, String input, String list) throws IOException{
		FileInputStream fis = new FileInputStream(new File(input));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		String line = null;
		
		while((line = br.readLine()) != null){
			if(list.equalsIgnoreCase("dbl"))
				queue.add(line.substring(1));
			else
				queue.add(line);
		}
		br.close();
		
		return queue;
		
	}
	
	
}
