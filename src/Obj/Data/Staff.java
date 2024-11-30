package Obj.Data;

import Obj.Base.AbstractAccount;

public class Staff extends AbstractAccount
{
	//==========================================Variable==========================================
	private Shop shop;

	//========================================Constructor=========================================
	public Staff()
	{
        super();
		this.shop = null;
	}

	public Staff(String id, String name, String userName, String password, Shop shop)
	{
        super(id, name, userName, password);
		this.shop = shop;
	}

	//============================================Get=============================================
	public Shop getShop() { return this.shop; }

	//============================================Set=============================================
	public void setShop(Shop shop) { this.shop = shop; }
}