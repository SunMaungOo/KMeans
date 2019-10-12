package com.developer.sunmaungoo.kmean;

public final class ArithmeticUtils {

	private ArithmeticUtils() {
		
	}
	
	public static Double[] vectorSum(Double[] vector1,Double[] vector2) {
		
		Double[] sumVector = new Double[vector1.length];
		
		for(int i=0;i<vector1.length;i++) {
			
			sumVector[i] = vector1[i]+vector2[i];
		}
		
		return sumVector;
	}
	
	public static Double[] vectorMean(Double[] vector) {
		
		Double[] mean = new Double[vector.length]; 
		
		for(int i=0;i<vector.length;i++) {
			
			mean[i] = vector[i]/vector.length;
			
		}
		
		return mean;
	}
	
}
