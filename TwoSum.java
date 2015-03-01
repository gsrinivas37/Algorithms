import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


public class TwoSum {
	private Map<Long, Integer> map = new HashMap<Long, Integer>();
	
	public static void main(String[] args) {
		TwoSum problem = new TwoSum();
		problem.readInput();
		System.out.println(problem.compute());
	}
	
	private long compute(){
		long cnt = 0;
		
		for(long i=-10000; i<=10000; i++)
		{
			for(long val : map.keySet()){
				long rem = i-val;
				if(rem==val)
					continue;
				
				if(map.containsKey(rem)){
					cnt++;
					break;
				}
				
			}
		}
		return cnt;
	}
	
	private void readInput(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("/Users/gsrinivas37/Downloads/algo1-programming_prob-2sum.txt"));

			String line = reader.readLine();
			
			while(line!=null){
				long val = Long.parseLong(line.trim());
				
				if(map.containsKey(val)){
					int cnt = map.get(val);
					map.put(val,++cnt);
				}else{
					map.put(val, 1);					
				}
				line = reader.readLine();
			}
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
