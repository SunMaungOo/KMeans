package com.developer.sunmaungoo.kmean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Sampler {

	/**
	 * 
	 * @param k size of the sample
	 * @return k-size sample of the data
	 */
	public Double[] sample(int k,Double[] stream) {
		
		Double[] sample = new Double[k];

        int index = 0;

        for(;index<k;index++)
        {
            sample[index] = stream[index];
        }

        for(;index<stream.length;index++)
        {
            Random random = new Random();

            int j = random.nextInt(index);
            
            if(j<k)
            {
                sample[j] = stream[index];
            }

        }

        return sample;
	}
	
	/**
	 * 
	 * @param k size of the sample
	 * @param stream k-size sample of the data
	 * @param listSize how many list of stream
	 * @return
	 */
	public List<Double[]> sampleList(int k,
			List<Double[]> stream,
			int listSize){
				
		List<Double[]> sampleList = new ArrayList<Double[]>();
		
		for(int j=0;j<stream.size();j++) {
			
			if(sampleList.size()==listSize) {
				return sampleList;
			}
			
			Double[] currentStream = stream.get(j);
			
			sampleList.add(sample(k,currentStream));				
		}
		
		
		return stream;		
	}

}
