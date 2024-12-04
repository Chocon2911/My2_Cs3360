package Obj.Data;

import Obj.Base.AbstractObj;

public class RequestedItem extends AbstractObj
{
	//==========================================Variable==========================================
	private Shop shop;
	private CustomerRequest customerRequest;
	private Customer customer;
	private Item item;
	private int amount;

	//========================================Constructor=========================================
	public RequestedItem()
	{
        super();
		this.shop = null;
		this.customerRequest = null;
		this.customer = null;
		this.item = null;
		this.amount = -1;
	}

	public RequestedItem(String id, int amount)
	{
		super(id);
		this.amount = amount;
	}

	public RequestedItem(String id, Shop shop, CustomerRequest customerRequest, Customer customer, Item item, int amount)
	{
        super(id);
		this.shop = shop;
		this.customerRequest = customerRequest;
		this.customer = customer;
		this.item = item;
		this.amount = amount;
	}

	//============================================Get=============================================
	public Shop getShop()
	{
		return this.shop;
	}

	public CustomerRequest getCustomerRequest()
	{
		return this.customerRequest;
	}

	public Customer getCustomer()
	{
		return this.customer;
	}

	public Item getItem()
	{
		return this.item;
	}

	public int getAmount()
	{
		return this.amount;
	}

	public float getTotalMoney()
	{
		if (item == null) return 0.0f;
		return item.getPrice() * amount;
	}

	//============================================Set=============================================
	public void setShop(Shop shop)
	{
		this.shop = shop;
	}

	public void setCustomerRequest(CustomerRequest customerRequest)
	{
		this.customerRequest = customerRequest;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public void setItem(Item item)
	{
		this.item = item;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}
}