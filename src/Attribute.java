import java.util.*;

public class Attribute {
	public Map<Integer, Integer> dataSet;
	public boolean target = false;
	
	public Attribute(Map<Integer, Integer> dataSet, boolean target) {
		this.dataSet = dataSet;
		this.target = target;
	}
	
	public Attribute(Attribute attr) {
		this.dataSet = attr.getDataSet();
	}
	
	public Attribute getPartition(List<Integer> partitionList) {
		Map<Integer, Integer> data = new HashMap<Integer, Integer>();
		for(int i=0; i<partitionList.size(); i++) {
			data.put(partitionList.get(i), dataSet.get(partitionList.get(i)));
		}
		return new Attribute(data, false);
	}
	
	public double getEntropy() {
		Map<Integer, Integer> dataCount = new HashMap<Integer, Integer>();
		int totalexamples = 0;
		for(Map.Entry<Integer, Integer> entry: dataSet.entrySet()) {
			if(!dataCount.containsKey(entry.getValue())) {
				dataCount.put(entry.getValue(), 1);
			} 
			else {
				Integer count = dataCount.get(entry.getValue());
				count++;
				dataCount.put(entry.getValue(), count);
			}
			totalexamples++;
		}
		
		double entropy = 0.0;
		
		for(Map.Entry<Integer, Integer> entry: dataCount.entrySet()) {
			int val = entry.getValue();
			double pr = (double)val/(double)totalexamples;
			entropy += pr*((Math.log(1/pr))/(Math.log(2)));
		}
		return entropy;
	}
	
	public double getEntropy(Attribute conditionalAttribute) {
		Map<Integer, List<Integer>> dataPartition = new HashMap<Integer, List<Integer>>();
		int totalexamples = 0;
		for(Map.Entry<Integer, Integer> entry: conditionalAttribute.getDataSet().entrySet()) {
			if(dataSet.containsKey(entry.getKey())) {
				if(!dataPartition.containsKey(entry.getValue())) {
					List<Integer> partitionList = new ArrayList<Integer>();
					partitionList.add(entry.getKey());
					dataPartition.put(entry.getValue(), partitionList);
				} else {
					List<Integer> partitionList = dataPartition.get(entry.getValue());
					partitionList.add(entry.getKey());
					dataPartition.put(entry.getValue(), partitionList);
				}
				totalexamples++;
			}
		}
		
		double entropy = 0.0;
		for(Map.Entry<Integer, List<Integer>> entry: dataPartition.entrySet()) {
			Attribute a = this.getPartition(entry.getValue());
			double _entropy = a.getEntropy();
			double pr = (double)(entry.getValue().size())/totalexamples;
			_entropy = _entropy*pr;
			
			entropy += _entropy;
		}
		
		return entropy;
	}
	
	public Map<Integer, Integer> getDataSet() {
		return dataSet;
	}

	public void setDataSet(Map<Integer, Integer> dataSet) {
		this.dataSet = dataSet;
	}
}
