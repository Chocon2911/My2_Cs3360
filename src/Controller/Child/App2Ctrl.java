package Controller.Child;

import DataBase.Child.ShopDb;
import Obj.Data.Shop;
import Util.ObjUtil;

public class App2Ctrl
{
    //===========================================Login============================================
    public int login(String userName, String password)
    {
        Shop shop = ShopDb.getInstance().queryShopByUserName(userName);
        if (shop == null) return 1; // UserName Not Found
        else if (!shop.getPassword().equals(password)) return 2; // Password Wrong
        
        return 0;
    }

    public String getUserId(String userName, String password) 
    { 
        Shop shop = ShopDb.getInstance().queryShopByUserName(userName);
        if (shop == null) return null;
        else if (!shop.getPassword().equals(password)) return null;

        return shop.getId();
    }

    //==========================================Sign Up===========================================
    public int signUp(String name, String userName, String password, String checkInCode, String systemCode)
    {
        String shopId = ObjUtil.getInstance().getRandomStr(10);
        Shop shop = new Shop(shopId, name, userName, password, false, systemCode, checkInCode, null, null, null, null, null);

        String e = ShopDb.getInstance().insertShopData(shop);
        if (e == null) return 0;
        else if (e.contains("Shops.Id"))
        {
            System.out.println("Error: Id already exists");
            return this.signUp(name, userName, password, checkInCode, systemCode);
        }
        else if (e.contains("Shops.UserName")) return 1;
        else if (e.contains("Shops.CheckInCode")) return 2;

        return 0;
    }
}
