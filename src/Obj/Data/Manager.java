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

	public Manager(String id, String name, String userName, String password, boolean isLogin)
	{
		super(id, name, userName, password, isLogin);
	}

	public Manager(String id, String name, String userName, String password, boolean isLogin, 
	Shop shop)
	{
        super(id, name, userName, password, isLogin);
		this.shop = shop;
	}

	//============================================Get=============================================
	public Shop getShop()
	{ return this.shop; }

	//============================================Set=============================================
	public void setShop(Shop shop) { this.shop = shop; }
}