import java.util.*;

public class MOD_ID3 extends ID3 {
	
	public List<List<String>> getNextPartition(List<List<String>> partitions, List<Attribute> attributes) {
		
		// F = pr*Entropy;
		double F_max = 0;
		int attr_index = 0;
		int partition_index = 0;
		for(int i=0; i<partitions.size(); i++) {
			List<String> partition = partitions.get(i);
			List<Integer> int_partition = new ArrayList<Integer>();
			for(int j=1; j<partition.size(); j++) {
				int_partition.add(Integer.parseInt(partition.get(j)));
			}
			Attribute a1 = new Attribute(attributes.get(0));
			Attribute a = a1.getPartition(int_partition);
			
			for(int j=0; j<attributes.size()-1; j++) {
				double entropy = a.getEntropy(attributes.get(j));
				double gain = 1-entropy;
				double f = ((double)int_partition.size()/(double)attributes.get(j).getDataSet().size())*gain;
				System.out.println("----partition "+i + " Attribute "+j+"----");
				System.out.println(entropy+" - "+gain+" - "+f);
				if(f>F_max) {
					F_max = f;
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
				List<List<String>> splits = splitPartition(partitions.get(i), attributes.get(attr_index));
				
				System.out.print("Partition "+partitions.get(partition_index).get(0)
						+ " replaced with ");
				
				for(int j=0; j<splits.size(); j++) {
					result.add(splits.get(j));
					System.out.print(splits.get(j).get(0) + " ");
				}
				
				System.out.println(" using feature "+(partition_index+1));
			}
		}
		
		return result;
	}
	
	public List<List<String>> splitPartition(List<String> partition, Attribute attr) {
		List<List<String>> result = new ArrayList<List<String>>();
		//Map<Integer>
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
		int count = 0;
		for(int i=1; i<partition.size(); i++) {
			if(attr.getDataSet().containsKey(i)) {
				int value = attr.getDataSet().get(i);
				if(!map.containsKey(value)) {
					List<String> arr = new ArrayList<String>();
					count++;
					arr.add(partition.get(0)+""+count);
					arr.add(i+"");
					map.put(value, arr);
				} else {
					List<String> arr = map.get(value);
					arr.add(i+"");
					map.put(value, arr);
				}
			}	
		}
		
		for(Map.Entry<Integer, List<String>> entry : map.entrySet()) {
			result.add(entry.getValue());
		}
		return result;
	}
	
}
