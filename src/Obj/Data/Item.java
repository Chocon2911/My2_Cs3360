package Obj.Data;

import Obj.Base.AbstractMainObj;
import java.util.*;

public class Item extends AbstractMainObj
{
	//==========================================Variable==========================================
	private Shop shop;
	private float price;
	private ItemType itemType;
	private int initAmount;
	private List<RequestedItem> requestedItems;

	//========================================Constructor=========================================
	public Item()
	{
        super();
		this.shop = null;
		this.price = -1;
		this.itemType = null;
		this.initAmount = -1;
		this.requestedItems = null;
	}

	public Item(String id, String name, float price, ItemType itemType, 
	int initAmount)
	{
		super(id, name);
		this.price = price;
		this.itemType = itemType;
		this.initAmount = initAmount;
	}

	public Item(String id, String name, Shop shop, float price, ItemType itemType, 
    int initAmount, List<RequestedItem> requestedItems)
 	{
        super(id, name);
		this.shop = shop;
		this.price = price;
		this.itemType = itemType;
		this.initAmount = initAmount;
		this.requestedItems = requestedItems;
	}

	//============================================Get=============================================
	public Shop getShop() { return this.shop; }
	public float getPrice() { return this.price; }
	public ItemType getItemType() { return this.itemType; }
	public int getInitAmount() { return this.initAmount; }
	public List<RequestedItem> getRequestedItems() { return this.requestedItems; }

	public int getLeftAmount()
	{
		int leftAmount = this.initAmount;
		for (RequestedItem ri : this.requestedItems)
		{
			leftAmount -= ri.getAmount();
		}

		return leftAmount;
	}

	//============================================Set=============================================
	public void setShop(Shop shop) { this.shop = shop; }
	public void setPrice(float price) { this.price = price; }
	public void setItemType(ItemType itemType) { this.itemType = itemType; }
	public void setInitAmount(int initAmount) { this.initAmount = initAmount; }
	public void setRequestedItems(List<RequestedItem> requestedItems) { this.requestedItems = requestedItems; }
}