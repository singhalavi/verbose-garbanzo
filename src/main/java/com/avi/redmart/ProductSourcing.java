package com.avi.redmart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ProductSourcing {

	private int weights[][];
	private ArrayList<Integer> indices;
	private static  Long  toteLength = 30l, toteWidth = 35l, toteHeight = 45l;
	private List<Product> getMaxValueProducts(List<Product> products, int maxVw){
		
		weights = new int[products.size()+1][maxVw+1];
		for(int i=0; i < maxVw+1; i++){
			weights[0][i] = 0;
		}
		//Filling up the dp table
		for(int i = 1; i <= products.size(); i++){
			for(int j=0; j <= maxVw; j++){
				Product p  = products.get(i-1);
				if(p.getVw() <= j){
					weights[i][j] = Math.max(weights[i-1][j], 
							weights[i-1][j-p.getVw()] + p.getPrice());
				}else{
					weights[i][j] = weights[i-1][j];
				}
			}
		}
		
		//Getting the max possible value
		int maxValue = 0;
		for(int j=0;j<=maxVw; j++){
			if(weights[products.size()][j] > maxValue){
				maxValue = weights[products.size()][j] ;
			}
		}
		System.out.println("max value "+ maxValue);
		
		//Getting the products used for coming up with that value and comparing their weights, while keeping the products with minimum weights
		Long minWeight = null;
		List<Product> finalProducts = null;
		for(int i=0;i<=maxVw; i++){
			if(weights[products.size()][i] == maxValue){
				List<Product> userProducts = getProductsByBacktracking(products, weights, products.size(), i);
				Long totalWeight = getWeightSum(userProducts);
				if(minWeight == null){
					minWeight = totalWeight;
					finalProducts = userProducts;
				}else if(minWeight > totalWeight){
					minWeight = totalWeight;
					finalProducts = userProducts;
				}
			}
		}
		
		//printArray(weights);
		return finalProducts;
	}
	private Long getIdSum(List<Product> products){
		Long weight = 0l;
		for(Product p : products){
			weight += p.getPid();
		}
		return weight;
	}
	private Long getWeightSum(List<Product> products){
		Long weight = 0l;
		for(Product p : products){
			weight += p.getWeight();
		}
		return weight;
	}
	private List<Product> getProductsByBacktracking(List<Product> products, int[][] weights, int i, int j){
		List<Product> usedProducts = new ArrayList<>();
		
		while(i > 0){
			Product p = products.get(i-1);
			if(p.getVw() > j){//Did not consider this
				i--;
			}else{
				if(weights[i-1][j] > weights[i-1][j-p.getVw()] +p.getPrice()){//No need to consider this product
					i--;
				}else if(weights[i-1][j] == weights[i-1][j-p.getVw()] +p.getPrice()){
					System.out.println("Equal....");
					List<Product> products1 = getProductsByBacktracking(products, weights, i-1, j);
					List<Product> products2 = getProductsByBacktracking(products, weights, i-1, j-p.getVw());
					products2.add(p);
					Long weight1 = getWeightSum(products1);
					Long weight2 = getWeightSum(products2);
					if(weight1 < weight2){
						 usedProducts.addAll(products1);
						i--;
						return usedProducts;
					}else if(weight2 < weight1){
						i--;
						j = j - p.getVw();
						usedProducts.addAll(products2);
						return usedProducts;
						
					}else{
						System.out.println("Confusion... as in this case product ids sum may differ");
						i--;
						j = j - p.getVw();
					}
					
				}
				else{
					i--;
					j = j - p.getVw();
					usedProducts.add(p);
				}
			}
		}
		return usedProducts;
		
	}
	
	private void printArray(int weights[][]){
		for(int i = 0; i < weights.length; i++){
			for(int j=0; j < weights[0].length; j ++){
				System.out.print(weights[i][j]+", ");
			}
			System.out.println();
		}
	}
	private static Product getProduct(String s) {
		String[] strArr = s.split(",");
		Product product = new Product();
		product.setPid(Long.parseLong(strArr[0]));
		product.setPrice(Integer.parseInt(strArr[1]));
		product.setLength(Long.parseLong(strArr[2]));
		product.setHeight(Long.parseLong(strArr[3]));
		product.setWidth(Long.parseLong(strArr[4]));
		product.setWeight(Long.parseLong(strArr[5]));
		return product;
	}
	
	
	//L,W,H checking if product will fit into tote, by first sorting the sizes and than comparing the sizes
	private static boolean doesItFit(Product p){
		
		//Make length smaller
		if(p.getLength() > p.getWidth()){
			Long length = p.getLength();
			p.setLength(p.getWidth());
			p.setWidth(length);
		}
		
		if(p.getLength() > p.getHeight()){
			Long length = p.getLength();
			p.setLength(p.getHeight());
			p.setHeight(length);
		}
		
		//Make width smaller than height
		if(p.getWidth() > p.getHeight()){
			Long width = p.getWidth();
			p.setWidth(p.getHeight());
			p.setHeight(width);
		}
		if(p.getLength() <= toteLength && p.getWidth() <= toteWidth && p.getHeight() <= toteHeight){
			return true;
		}
		return false;
	}
	public static void main(String[] argv) {
		try {
			long startTime = System.currentTimeMillis();
			InputStream in = ProductSourcing.class.getResourceAsStream("/products.csv");
			Reader reader = new InputStreamReader(in, "utf-8");
			BufferedReader bufReader = new BufferedReader(reader);
			File file = new File("sample.out");
			FileWriter writer = new FileWriter(file);
			ArrayList<Product> productList = new ArrayList<>();
			do {
				String str = bufReader.readLine();
				if(str == null || str.isEmpty()){
					break;
				}
				Product product = getProduct(str);
				if(doesItFit(product))
				{
					productList.add(product);	
				}
			}while(true);
			writer.close();
			System.out.println("Product size "+ productList.size());
			ProductSourcing ps = new ProductSourcing();
			List<Product> usedProducts = ps.getMaxValueProducts(productList, 47250);
			System.out.println("Ids sum "+ ps.getIdSum(usedProducts));
			System.out.println("Total time taken = " + (System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
