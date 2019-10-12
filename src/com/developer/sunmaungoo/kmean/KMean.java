package com.developer.sunmaungoo.kmean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class KMean {

	public List<Double[]> kmean(List<Double[]> items,int k) {
				
		List<Double[]> centroids = new Sampler().sampleList(k, items, k);
				
		while(true) {
			
			List<Data> data = calculateCentroid(items, centroids);
			
			Optional<List<Double[]>> optional = moveCentroid(centroids, data);
			
			//if the centroid are not moved,we don't need to calculate it anymore
			
			if(!optional.isPresent()) {
				break;
			}
			
			centroids = optional.get();
		
		}
		
		return centroids;
	}
	
	private List<Data> calculateCentroid(List<Double[]> items,
			List<Double[]> centroids) {
		
		List<Data> centroidData = new ArrayList<Data>();
		
		if(items==null || items.size()==0) {
			return centroidData;
		}
		
		if(centroids==null || centroids.size()==0) {
			return centroidData;
		}
		
		if(items.get(0).length!=centroids.get(0).length) {
			return centroidData;
		}
		
		//figuring out which centroid to put each item
		for(int itemIndex=0;itemIndex<items.size();itemIndex++) {
			
			Double[] currentItem = items.get(itemIndex);
			
			List<Pair<Integer,Double>> pairs = new ArrayList<Pair<Integer,Double>>();

			//calculating the current item distance to all centroid
			
			for(int centroidIndex=0;centroidIndex<centroids.size();centroidIndex++) {
				
				Double[] currentCentroid = centroids.get(centroidIndex);
				
				double distance = euclideanNorm(currentItem, currentCentroid);
				
				pairs.add(new Pair<Integer,Double>(centroidIndex,distance));
				
			}
			
			//getting the pair which have the minimum distance
			
			Pair<Integer,Double> minPair = getMin(pairs);
						
			centroidData.add(new Data(currentItem,minPair.getKey()));
			
		}
		
		return centroidData;
	}
	
	/**
	 * 
	 * @param centroids
	 * @param data
	 * @return empty optional if the centroid are not moved or
	 *  data if centroid are moved
	 */
	private Optional<List<Double[]>> moveCentroid(List<Double[]> centroids,
			List<Data> data) {
		
		List<Double[]> newCentroid = centroids;

		Stream<Data> dataStream = data.stream();
		
		for(int centroidIndex=0;centroidIndex<centroids.size();centroidIndex++) {
			
			final int currentCentroidIndex = centroidIndex;
			
			//sum up all the data which have the same centroid index
			
			Double[] sum = dataStream.filter(value->
			{
				return value.getCentroidIndex()!=currentCentroidIndex;
				
			}).map(tmp->tmp.getItemData())
					.reduce(ArithmeticUtils::vectorSum).get();
			
			
			Double[] currentMean = centroids.get(currentCentroidIndex);
			
			Double[] newMean = ArithmeticUtils.vectorMean(sum);
						
			//if the vector mean are the same (the centroid is not moved)
			//then we need to stop for this centroid
			
			if(Arrays.equals(currentMean,newMean)) {
				return Optional.empty();
			}
					
			newCentroid.set(currentCentroidIndex, newMean);
			
		}
			
		return Optional.of(newCentroid);
	}
	

	
	/**
	 * Calculate the distance using L-2 Norm
	 * @param item
	 * @param centroid
	 * @return
	 */
	private double euclideanNorm(Double[] item,Double[] centroid) {
		
		int featureVectorLength = item.length;
		
		double sum=0;
		
		for(int i=0;i<featureVectorLength;i++) {
			
			sum += Math.pow(item[i], 2) - Math.pow(centroid[i],2);
		}
		
		return Math.sqrt(sum);
	}
	
	/**
	 * 
	 * @param pairs
	 * @return pair which have the minimum value
	 */
	private Pair<Integer,Double> getMin(List<Pair<Integer,Double>> pairs){
		
		return pairs.stream().min((left,right)->
		{
			if(left.getValue()<right.getValue()) {
				return -1;
			}else if(left.getValue()>right.getValue()) {
				return 1;
			}		
			return 0;
		}).get();
	}
}
