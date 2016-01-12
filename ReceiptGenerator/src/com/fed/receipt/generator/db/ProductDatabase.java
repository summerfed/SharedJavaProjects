package com.fed.receipt.generator.db;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fed.receipt.generator.obj.Product;
import com.fed.receipt.generator.utils.Utilities;

public class ProductDatabase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8646453682699096247L;
	/**
	 * 
	 */
	private static final String lookupLoc = "dat/lkup.bin";
	private static final String productStoreLoc = "dat/prdStre.bin";
	private static ProductDatabase instance = new ProductDatabase();	
	private List<String> prodDescLookup;
	private Map<String, Product> productStorage;
	
	@SuppressWarnings("unchecked")
	private ProductDatabase (){
		/*try {
			instance = (ProductDatabase) Utilities.getInstance().getFileUtil().readObjectFromFilesystem(dbLocation);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		
		try {
			productStorage = (Map<String, Product>) Utilities.getInstance().getFileUtil().readObjectFromFilesystem(productStoreLoc);
			prodDescLookup = (List<String>) Utilities.getInstance().getFileUtil().readObjectFromFilesystem(lookupLoc);
		} catch (ClassNotFoundException
				| IOException e) {
			productStorage = new HashMap<>();
			prodDescLookup = new LinkedList<>();
			e.printStackTrace();
		}
			
	}
	
	public static ProductDatabase getInstance() {
		return instance;
	}
	
	public boolean addProduct(Product product) {
		String desc = product.getDescription();
		boolean isExist = prodDescLookup.contains(desc.trim().toUpperCase());
		if(isExist) {
			return false;
		} else {
			prodDescLookup.add(desc.trim().toUpperCase());
			productStorage.put(desc, product);
			commitDatabase();
			return true;
		}
	}
	
	public Product getProduct(String productDesc) {
		Product product = productStorage.get(productDesc);
		return product;
	}
	
	public List<String> viewProducts() {
		List<String> tmp = new LinkedList<>();
		tmp.addAll(productStorage.keySet());
		return tmp;
	}
	
	private boolean commitDatabase() {
		try {
			Utilities.getInstance().getFileUtil().writeObjectToFilesystem(prodDescLookup, lookupLoc);
			Utilities.getInstance().getFileUtil().writeObjectToFilesystem(productStorage, productStoreLoc);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
