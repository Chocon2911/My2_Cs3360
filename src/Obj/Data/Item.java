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
	private String description;
	private List<RequestedItem> requestedItems;

	//========================================Constructor=========================================
	public Item()
	{
        super();
		this.shop = null;
		this.price = -1;
		this.itemType = null;
		this.initAmount = -1;
		this.description = null;
		this.requestedItems = null;
	}

	public Item(String id, String name, float price, ItemType itemType, 
	int initAmount, String description)
	{
		super(id, name);
		this.price = price;
		this.itemType = itemType;
		this.initAmount = initAmount;
		this.description = description;
	}

	public Item(String id, String name, Shop shop, float price, ItemType itemType, 
    int initAmount, String description, List<RequestedItem> requestedItems)
 	{
        super(id, name);
		this.shop = shop;
		this.price = price;
		this.itemType = itemType;
		this.initAmount = initAmount;
		this.description = description;
		this.requestedItems = requestedItems;
	}

	//============================================Get=============================================
	public Shop getShop() { return this.shop; }
	public float getPrice() { return this.price; }
	public ItemType getItemType() { return this.itemType; }
	public int getInitAmount() { return this.initAmount; }
	public String getDescription() { return this.description; }
	public List<RequestedItem> getRequestedItems() { return this.requestedItems; }

	//============================================Set=============================================
	public void setShop(Shop shop) { this.shop = shop; }
	public void setPrice(float price) { this.price = price; }
	public void setItemType(ItemType itemType) { this.itemType = itemType; }
	public void setInitAmount(int initAmount) { this.initAmount = initAmount; }
	public void setDescription(String description) { this.description = description; }
	public void setRequestedItems(List<RequestedItem> requestedItems) { this.requestedItems = requestedItems; }
}