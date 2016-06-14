import java.util.*;

public class Attribute {
	public String name;
	public Map<Integer, Integer> dataSet;
	public boolean target = false;
	
	public Attribute(Map<Integer, Integer> dataSet, boolean target) {
		this.dataSet = dataSet;
		this.target = target;
	}
	
	public Attribute(Attribute attr) {
		this.dataSet = attr.getDataSet();
	}
	
	public Attribute(Attribute attribute, Attribute conditionalSplitAttribute, Integer split, boolean target) {
		
		Map<Integer, Integer> conditionalSplitDataSet = conditionalSplitAttribute.getDataSet();
		Map<Integer, Integer> splitedDataSet = new HashMap<Integer, Integer>();
		
		for(Map.Entry<Integer, Integer> entry: conditionalSplitDataSet.entrySet()) {
			if(entry.getValue() == split) {
				splitedDataSet.put(entry.getKey(), split);
			}
		}
		
		this.dataSet = splitedDataSet;
		this.target = target;
	}

	public Attribute getPartition(List<Integer> partitionList) {
		Map<Integer, Integer> data = new HashMap<Integer, Integer>();
		for(int i=0; i<partitionList.size(); i++) {
			data.put(partitionList.get(i), dataSet.get(partitionList.get(i)));
		}
		return new Attribute(data, false);
	}
	
	public double getEntropy(Attribute conditionalAttribute) {
		
		Map<Integer, Integer> dataCount = new HashMap<Integer, Integer>();
		
		int totalexamples = 0;
		for(Map.Entry<Integer, Integer> entry: conditionalAttribute.getDataSet().entrySet()) {
			if(dataSet.containsKey(entry.getKey())) {
				if(!dataCount.containsKey(entry.getValue())) {
					dataCount.put(entry.getValue(), 1);
				} 
				else {
					Integer count = dataCount.get(entry.getValue());
					dataCount.put(entry.getValue(), count++);
				}
				totalexamples++;
			}
		}
		
		double entropy = 0.0;
		
		for(Map.Entry<Integer, Integer> entry: dataCount.entrySet()) {
			int val = entry.getValue();
			double pr = (double)val/(double)totalexamples;
			entropy += pr*((Math.log(1/pr))/(Math.log(2)));
		}
		
		return entropy;
	}
	
	public Map<Integer, Integer> getDataSet() {
		return dataSet;
	}

	public void setDataSet(Map<Integer, Integer> dataSet) {
		this.dataSet = dataSet;
	}

	public boolean isTarget() {
		return target;
	}

	public void setTarget(boolean target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
