package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.avi.redmart.Product;
import com.avi.redmart.ProductSourcing;

public class ProductSourcingTest {

	@Test
	public void testOne(){
		ProductSourcing ps = new ProductSourcing();
		List<Product> usedProducts = ps.getMaxValueProducts(getSampleData(), 50);
		Assert.assertEquals(new Long(8), getIdSum(usedProducts));
	}
	
	private Long getIdSum(List<Product> products){
		Long weight = 0l;
		for(Product p : products){
			weight += p.getPid();
		}
		return weight;
	}
	
	private List<Product> getSampleData(){
		List<Product> products = new ArrayList<>();
		products.add(new Product(1l, 1l,1l,4l,32l,100));
		products.add(new Product(2l, 5l,1l,4l,3l,1000));
		products.add(new Product(3l, 1l,5l,4l,50l,100));
		products.add(new Product(4l, 1l,8l,4l,500l,100));
		products.add(new Product(5l, 2l,3l,4l,30l,100));
		return products;
	}
}
