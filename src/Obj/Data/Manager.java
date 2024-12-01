package Obj.Data;

import Obj.Base.AbstractAccount;

public class Manager extends AbstractAccount
{
	//==========================================Variable==========================================
	private Shop shop;

	//========================================Constructor=========================================
	public Manager()
	{
        super();
		this.shop = null;
	}

	public Manager(String id, String name, String userName, String password)
	{
		super(id, name, userName, password);
	}

	public Manager(String id, String name, String userName, String password, 
	Shop shop)
	{
        super(id, name, userName, password);
		this.shop = shop;
	}

	//============================================Get=============================================
	public Shop getShop()
	{ return this.shop; }

	//============================================Set=============================================
	public void setShop(Shop shop) { this.shop = shop; }
}