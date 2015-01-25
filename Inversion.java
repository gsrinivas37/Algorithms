import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Inversion {
	public static void main(String[] args) {
		List<Integer> values = new ArrayList<>();

		//		values.add(12);
		//		values.add(5);
		//		values.add(3);
		//		values.add(15);
		//		values.add(7);
		//		values.add(6);
		//		values.add(4);

		File file = new File("/Users/gsrinivas37/Downloads/IntegerArray.txt");

		if(file.exists()){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));

				while(true){
					String readLine = reader.readLine();
					if(readLine==null) break;
					values.add(Integer.parseInt(readLine.trim()));
				}
			} catch (Exception e) {
			}
		}

		System.out.println(values.get(values.size()-1));
		
		Inversion inv = new Inversion();
		System.out.println(inv.sortAndComputeInversion(values));
	}

	public long sortAndComputeInversion(List<Integer> values) {
		if(values.size()<2) return 0;

		if(values.size()==2) {
			if(values.get(0)>values.get(1)){
				Integer first = values.get(0);
				values.set(0, values.get(1));
				values.set(1, first);
				return 1;
			}
			else return 0;
		}

		long num=  sortAndComputeInversion(values.subList(0, values.size()/2)) + sortAndComputeInversion(values.subList(values.size()/2, values.size()));

		List<Integer> sorted = new ArrayList<>();


		int first = 0;
		int second = 0;
		for(int i =0; i< values.size();){
			if(values.get(first) < values.get(values.size()/2 +second)){
				sorted.add(values.get(first++));
				i++;
				if(first==values.size()/2){
					for(; second+values.size()/2 < values.size();){
						sorted.add(values.get(values.size()/2+second++));
						i++;
					}
				}
			}else{
				sorted.add(values.get(values.size()/2 + second++));
				i++;
				num+= values.size()/2-first;

				if(second+values.size()/2 == values.size()){
					for(;first<values.size()/2;){
						sorted.add(values.get(first++));
						i++;
					}
				}

			}
		}

		for(int i=0;i<sorted.size();i++)
			values.set(i, sorted.get(i));

		return num;
	}
}
