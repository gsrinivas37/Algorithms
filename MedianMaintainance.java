import java.io.BufferedReader;
import java.io.FileReader;

public class MedianMaintainance {
	MinHeap minHeap = new MinHeap();
	MaxHeap maxHeap = new MaxHeap();
	
	public static void main(String[] args) {
		MedianMaintainance problem = new MedianMaintainance();
		problem.readInput();
	}

	private void readInput() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("/Users/gsrinivas37/Downloads/Median.txt"));
			int sum = 0;
			
			String line = reader.readLine();
			
			while(line!=null){
				addValue(Integer.parseInt(line));
				sum+= getMedian();
				if(sum>10000)
					sum = sum % 10000;
				
				line = reader.readLine();
			}
			reader.close();
			System.out.println(sum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addValue(int val){
		if(minHeap.getSize()==0){
			minHeap.add(val);
			return;
		}
		
		int top = minHeap.getTop();
		
		if(val>top){
			minHeap.add(val);
			
			if(minHeap.getSize()-maxHeap.getSize()>1){
				maxHeap.add(minHeap.remove());
			}
		}else{
			maxHeap.add(val);
			
			if(maxHeap.getSize()-minHeap.getSize()>1){
				minHeap.add(maxHeap.remove());
			}
		}
	}
	
	private int getMedian(){
		if(minHeap.getSize()>maxHeap.getSize()){
			return minHeap.getTop();
		}
		
		return maxHeap.getTop();
	}
}

class MinHeap {
	int[] val = new int[10000];
	int size =0;
	
	public int getSize(){
		return size;
	}

	public int remove() {
		int ret = getTop();
		
		if(size==1){
			size--;
			return ret;
		}
		
		swap(0, size-1);
		
		int parent = 0;
		int lc = 1;
		int rc = 2;
		while(true){
			int min = -1;
			if(rc<size){
				if(val[lc]<val[rc]){
					min = lc;
				}else{
					min = rc;
				}
			}else if(lc<size){
				min = lc;
			}
			
			if(min==-1||min==size-1)
				break;

			if(val[min]>val[parent])
				break;
			
			swap(parent, min);
			
			parent = min;
			lc = (parent+1)*2-1;
			rc = (parent+1)*2;
		}
		
		size--;
		val[size] =0;
		return ret;
	}

	public int getTop() {
		return val[0];
	}

	public void add(int n) {
		val[size++] = n;
		
		int temp = size-1;
		while(true){
			if(temp<1) break;
			
			int parent = (temp+1)/2-1;
			
			if(val[temp]<val[parent])
			{
				swap(temp, parent);
				temp = parent;
			}else{
				break;
			}
		}
	}

	private void swap(int s, int d) {
		int temp = val[s];
		val[s] = val[d];
		val[d] = temp;
	}
}

class MaxHeap {
	int[] val = new int[10000];
	int size = 0;

	public int getSize(){
		return size;
	}

	public int remove() {
		int ret = getTop();
		if(size==1){
			size--;
			return ret;
		}
		
		swap(0, size-1);
		
		int parent = 0;
		int lc = 1;
		int rc = 2;
		while(true){
			int max = -1;
			if(rc<size){
				if(val[lc]>val[rc]){
					max = lc;
				}else{
					max = rc;
				}
			}else if(lc<size){
				max = lc;
			}
			
			if(max==-1||max==size-1)
				break;
			
			if(val[max]<val[parent])
				break;

			swap(parent, max);
			
			parent = max;
			lc = (parent+1)*2-1;
			rc = (parent+1)*2;
		}
		
		size--;
		val[size] =0;
		return ret;
	}

	public void add(int n) {
		val[size++] = n;
		
		int temp = size -1;
		while(true){
			if(temp<1) break;
			
			int parent = (temp+1)/2-1;
			
			if(val[temp]>val[parent])
			{
				swap(temp, parent);
				temp = parent;
			}else{
				break;
			}
		}
	}

	public int getTop() {
		return val[0];
	}

	private void swap(int s, int d) {
		int temp = val[s];
		val[s] = val[d];
		val[d] = temp;
	}
}
