import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		
		Scanner s = new Scanner(System.in);
		System.out.println("Enter names of the files dataset input-partition output-partition");
		String _args = s.nextLine();
		String in_args[] = _args.split(" ");
		try {
			if(in_args.length == 3) {
				File f_dataSet = new File(in_args[0]);
				File in_partition = new File(in_args[1]);
				File out_partition = new File(in_args[2]);
				if(!(f_dataSet.exists() && in_partition.exists() && out_partition.exists())) {
					s.close();
					throw new Exception("One or more files not found");
				}

				Scanner s_dataset = new Scanner(f_dataSet);
				int num_example = s_dataset.nextInt();
				int num_attr = s_dataset.nextInt();
				//List of attributes
				List<Attribute> inputAttr= new ArrayList<Attribute>();				
				
				//Generate attribute data structure for the input dataset
				for(int j=0; j< num_attr; j++) {
					Map<Integer, Integer> data = new HashMap<Integer, Integer>();
					Attribute attr;
					if(j == num_attr-1) {
						attr = new Attribute(data, true);
					} else {
						attr = new Attribute(data, false);
					}
					inputAttr.add(attr);
				}
				for(int i=1; i<= num_example; i++) {
					for(int j=0; j< num_attr; j++) {
						Attribute attr = inputAttr.get(j);
						Map<Integer, Integer> data = attr.getDataSet();
						int attr_value = s_dataset.nextInt();
						data.put(i,attr_value);
					}
				}
				
				Scanner s_partition = new Scanner(in_partition);
				List<List<String>> partitions = new ArrayList<List<String>>();
				//Generate partition from List
				while(s_partition.hasNextLine()) {
					String partition = s_partition.nextLine();
					String[] partition_vals = partition.split(" ");
					List<String> list = new ArrayList<String>();
					for(int j=0; j<partition_vals.length; j++) {
						list.add(partition_vals[j]);
					}
					partitions.add(list);
				}
				
				MOD_ID3 id3 = new MOD_ID3();
				
				List<List<String>> result = id3.getNextPartition(partitions, inputAttr);
				
				for(int i=0; i<result.size(); i++) {
					List<String> part = result.get(i);
					for(int j=0; j<part.size(); j++) {
						System.out.print(part.get(j) + " ");
					}
					System.out.println();
				}
				
				s_dataset.close();
				s_partition.close();
				s.close();
				
			} else {
				s.close();
				throw new Exception("Invalid Args: Program takes 3 inputs- <datasetfile> <input partition> <out partition>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
