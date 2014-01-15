package ut.mpc.kdt;

import java.io.BufferedReader;
import java.io.FileReader;

import ut.mpc.setup.Init;
import KDTree.*;


public class KDTtester {
	public static KDTTree kdtree;
	
	public static void main(String[] args){
		kdtree = new KDTTree(2);
		
		try {
			fillPointsFromFile(); //added comment to tester
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		Temporal temp = new Temporal(40,2,3);
		insertPoint(temp.getXCoord(),temp.getYCoord(),kdtree,temp);
		temp = new Temporal(30,4,7);
		insertPoint(temp.getXCoord(),temp.getYCoord(),kdtree,temp);
		temp = new Temporal(100,5,1);
		insertPoint(temp.getXCoord(),temp.getYCoord(),kdtree,temp);
		temp = new Temporal(100,3,3);
		insertPoint(temp.getXCoord(),temp.getYCoord(),kdtree,temp);
		*/
		
		double[] lowRange = new double[2];
		lowRange[1] = 37.5;
		lowRange[0] = -123;
		
		double[] highRange = new double[2];
		highRange[1] = 38;	
		highRange[0] = -122;
		
		
		double[] lowRangeSample = new double[2];
		lowRangeSample[0] = 0;
		lowRangeSample[1] = 0;
		
		double[] highRangeSample = new double[2];
		highRangeSample[0] = 9;
		highRangeSample[1] = 9;

		kdtree.print();
		KDTTree balTree = kdtree.balanceTree(lowRange, highRange);
		//balTree.rangeSummary(lowRange,highRange);
		//kdtree.rangeSummary(lowRange, highRange);
		balTree.print();
		//kdtree.compareWindows(lowRangeSample, highRangeSample, lowRange, highRange);
		double[] key = new double[]{37.71134, -122.39488};
		double val = kdtree.getPointProbability(key,1);
		System.out.println(val);
		System.out.println("----------------------------------------");
		
		System.out.println("*******  Optimized Print Window *******");
        long start = System.currentTimeMillis();
		System.out.println("Window Prob: " + kdtree.windowQuery(true,1));
        System.out.println("Time: " + (System.currentTimeMillis() - start));
		
        System.out.println("*******  Un-Optimized Print Window *******");
        long start2 = System.currentTimeMillis();
		System.out.println("Window Prob: " + kdtree.windowQuery(false,0));
        System.out.println("Time: " + (System.currentTimeMillis() - start2));
	}
	
	//@pre: requires at least one point in file, otherwise seg. fault
	//@pre: requires first entry to be most recent and last entry to be least recent
	public static void fillPointsFromFile() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/../Crawdad/cabspottingdata/50_abboip.txt"));
		//BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/1_unbalanced.txt"));

		String line;
		Temporal temp;
		line = br.readLine();
		String[] split = new String[4];
		split = line.split(" ");
		Init.CURRENT_TIMESTAMP = Long.parseLong(split[3]);
		temp = new Temporal(Long.parseLong(split[3]),Double.parseDouble(split[1]),Double.parseDouble(split[0]));
		insertPoint(temp.getXCoord(),temp.getYCoord(),kdtree,temp);
		
		while ((line = br.readLine()) != null) {
		   split = new String[4];
		   split = line.split(" ");
		   temp = new Temporal(Long.parseLong(split[3]),Double.parseDouble(split[1]),Double.parseDouble(split[0]));
		   insertPoint(temp.getXCoord(),temp.getYCoord(),kdtree,temp);
		}
		Init.REFERENCE_TIMESTAMP = Long.parseLong(split[3]);
		br.close();
	}
	
	public static void insertPoint(double x, double y, KDTree kdtree, Temporal temp){
		double[] tempKey = new double[2];
		tempKey[0] = x;
		tempKey[1] = y;
		kdtree.insert(tempKey,temp);
	}
}
