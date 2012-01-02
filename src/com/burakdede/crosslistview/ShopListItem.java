package com.burakdede.crosslistview;

public class ShopListItem {

	private String productTitle;
	
	private String productPrice;
	
	private String productSource;
	
	private String productImage;
	
	private int productCompl;
	
	public ShopListItem(String productTitle, String productPrice, String productSource,
			String productImage, int productCompl) {
		this.productTitle = productTitle;
		this.productPrice = productPrice;
		this.productSource = productSource;
		this.productImage = productImage;
		this.productCompl = productCompl;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductSource() {
		return productSource;
	}

	public void setProductSource(String productSource) {
		this.productSource = productSource;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public int getProductCompl() {
		return productCompl;
	}

	public void setProductCompl(int productCompl) {
		this.productCompl = productCompl;
	}
}
