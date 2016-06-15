import java.util.*;

public class MOD_ID3 {
	
	public List<List<String>> getNextPartition(List<List<String>> partitions, List<Attribute> attributes) {
		
		// F = pr*Entropy;
		double f_max = 0;
		int attr_index = 0;
		int partition_index = 0;
		
		for(int i=0; i<partitions.size(); i++) {
			List<String> partition = partitions.get(i);
			List<Integer> intval_partition = new ArrayList<Integer>();
			
			for(int j=1; j<partition.size(); j++) {
				intval_partition.add(Integer.parseInt(partition.get(j)));
			}
			
			Attribute a1 = new Attribute(attributes.get(attributes.size()-1));
			Attribute a = a1.getPartition(intval_partition);
						
			for(int j=0; j<attributes.size()-1; j++) {
				double entropy = a.getEntropy();
				double conditional_entropy = a.getEntropy(attributes.get(j));	
				double gain = entropy-conditional_entropy;
				double pr = (double)intval_partition.size()/(double)attributes.get(j).getDataSet().size();
				double f = pr*gain;
				
				if(f > f_max) {
					f_max = f;
					attr_index = j;
					partition_index = i;
				}
			}
		}
		
		List<List<String>> result = new ArrayList<List<String>>();
		for(int i=0; i<partitions.size(); i++) {
			if(partition_index!=i) {
				result.add(partitions.get(i));
			} else {				
				List<List<String>> splits = splitPartitionByAttribute(
												partitions.get(partition_index), 
												attributes.get(attr_index)
											);
				
				System.out.print("Partition "+partitions.get(partition_index).get(0)
						+ " replaced with ");
				for(int j=0; j<splits.size(); j++) {
					result.add(splits.get(j));
					System.out.print(splits.get(j).get(0) + " ");
				}
				System.out.println("using feature "+(attr_index+1));
			}
		}
		
		return result;
	}
	
	public List<List<String>> splitPartitionByAttribute(List<String> partition, Attribute attr) {
		List<List<String>> splittedPartition = new ArrayList<List<String>>();
		Map<Integer, List<String>> partitionMap = new HashMap<Integer, List<String>>();
		
		int count = 0;
		for(int i=1; i<partition.size(); i++) {

			int key = Integer.parseInt(partition.get(i));
			
			if(attr.getDataSet().containsKey(key)) {
				
				int value = attr.getDataSet().get(key);
				if(!partitionMap.containsKey(value)) {
					List<String> arr = new ArrayList<String>();
					count++;
					arr.add(partition.get(0)+""+count);
					arr.add(key+"");
					partitionMap.put(value, arr);
				} else {
					List<String> arr = partitionMap.get(value);
					arr.add(key+"");
					partitionMap.put(value, arr);
				}
			}	
		}
		
		for(Map.Entry<Integer, List<String>> entry : partitionMap.entrySet()) {
			splittedPartition.add(entry.getValue());
		}
	
		return splittedPartition;
	}
	
}
