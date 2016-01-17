package com.fed.receipt.generator.main;

import java.math.BigDecimal;

import com.fed.receipt.generator.db.ProductDatabase;
import com.fed.receipt.generator.obj.Product;
import com.fed.receipt.generator.views.mainscreen.MainScreenFrame;

public class ReceiptGenerator {
	public static void main(String args[]) {
		ProductDatabase db = ProductDatabase.getInstance();
		
		for(int i=0; i<100; i++) {
			Product prod = new Product();
			prod.setDescription("Testing" + i);
			prod.setPrice(new BigDecimal(20.5));
			prod.setUnit("Bottle");
			
			System.out.println(db.addProduct(prod));
			
		}
		
//		MainScreenFrame mainScreen = new MainScreenFrame();
		
	}
}
