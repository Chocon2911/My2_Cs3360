package DataBase.Child;

import DataBase.Base.AbstractDb;
import DataBase.Base.DbData;
import DataBase.Base.DbType;
import Obj.Data.Manager;
import Obj.Data.Shop;
import java.util.ArrayList;
import java.util.List;

public class ManagerDb extends AbstractDb
{
    //==========================================Variable==========================================
    private static ManagerDb instance;

    //=========================================Singleton==========================================
    public static ManagerDb getInstance()
    {
        if (instance == null) instance = new ManagerDb();
        return instance;
    }

    //========================================Create Table========================================
    public boolean createManagerTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Managers"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "Name TEXT, "
                + "UserName TEXT UNIQUE, "
                + "Password TEXT, "
                + "IsLogin INTEGER, "
                + "ShopId TEXT, "
                + "FOREIGN KEY (Id) REFERENCES ids (GlobalId), "
                + "FOREIGN KEY (UserName) REFERENCES userNames (GlobalUserName)"
                + ");";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertManagerData(Manager manager)
    {
        String sql = "INSERT INTO Managers "
                + "(Id, Name, UserName, Password, IsLogin, ShopId) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromManager(manager);

        System.out.println("===insert Manager===");
        String result = this.insertData(url, sql, data);
        if (result == null) 
        {
            String idE = new IdDb().insertId(manager.getId());
            if (idE != null) return idE;

            String userNameE = new UserNameDb().insertUserName(manager.getUserName());
            if (userNameE != null) return userNameE;
        }

        return result;
    }
    
    //===========================================Query============================================
    public Manager queryManagerData(String id)
    {
        // Private Info
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryManagerRawDatas(queryData, queryValue);
        if (datas.isEmpty()) return null;
        Manager manager = this.getManagerData(datas.get(0));

        // Shop
        String shopId = datas.get(0).get(4).getValueStr();
        Shop shop = new ShopDb().queryShopPriData(shopId);
        manager.setShop(shop);

        manager.setShop(shop);
        return manager;
    }

    public Manager queryManagerByUserName(String userName)
    {
        DbData queryData = new DbData(userName);
        String queryValue = "UserName";
        List<List<DbData>> datas = this.queryManagerRawDatas(queryData, queryValue);
        if (datas.isEmpty()) 
        {
            System.out.println("queryManagerByUserName(): datas is null: " + userName);
            return null;
        }

        return this.queryManagerData(datas.get(0).get(0).getValueStr());
    }

    // Private Info
    public Manager queryManagerPriData(String id)
    {
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryManagerRawDatas(queryData, queryValue);
        
        return this.getManagerData(datas.get(0));
    }

    // Other
    public List<List<DbData>> queryManagerRawDatas(DbData queryData, String queryValue)
    {
        String sql = "SELECT * FROM Managers this WHERE " + queryValue + " = ?";
        List<String> rowNames = this.getManagerRowNames();
        List<DbType> rowTypes = this.getManagerRowTypes();

        System.out.println("===query Manager===");
        return this.queryDatas(url, sql, queryData, rowNames, rowTypes);
    }

    //===========================================Update===========================================
    public String updateManagerData(Manager manager)
    {
        String sql = """
            UPDATE Managers SET 
            Name = ?, UserName = ?, Password = ?, IsLogin = ?, ShopId = ?
            WHERE Id = ?
        """;

        List<DbData> data = this.getDataFromManager(manager);
        DbData id = data.get(0);
        data.remove(0);
        data.add(id);

        System.out.println("===update Manager===");
        return this.updateData(url, sql, data);
    }

    //===========================================Delete===========================================
    public boolean deleteManagerData(Manager manager)
    {
        String sql = "DELETE FROM Managers WHERE Id = ?";

        DbData idData = new DbData();
        idData.setValueStr(manager.getId());

        boolean result = this.deleteRow(url, sql, idData);
        if (result) 
        {
            new IdDb().deleteId(manager.getId());
            new UserNameDb().deleteUserName(manager.getUserName());
        }

        return result;
    }

    //===========================================Other============================================
    // ===Query===
    // Manager Pri
    public List<String> getManagerRowNames()
    {
        List<String> rowNames;
        rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("Name");
        rowNames.add("UserName");
        rowNames.add("Password");
        rowNames.add("IsLogin");
        rowNames.add("ShopId");

        return rowNames;
    }

    public List<DbType> getManagerRowTypes()
    {
        List<DbType> rowTypes = new ArrayList<>();
        rowTypes.add(DbType.TEXT);    // Id
        rowTypes.add(DbType.TEXT);    // Name
        rowTypes.add(DbType.TEXT);    // UserName
        rowTypes.add(DbType.TEXT);    // Password
        rowTypes.add(DbType.INTEGER); // IsLogin
        rowTypes.add(DbType.TEXT);    // ShopId

        return rowTypes;
    }

    public Manager getManagerData(List<DbData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String userName = data.get(2).getValueStr();
        String password = data.get(3).getValueStr();
        boolean isLogin = data.get(4).getValueInt() == 1;
        // String shopId = data.get(5).getValueStr();

        return new Manager(id, name, userName, password, isLogin);
    }

    // Update - Insert
    private List<DbData> getDataFromManager(Manager manager)
    {
        DbData id = new DbData(manager.getId());
        DbData name = new DbData(manager.getName());
        DbData userName = new DbData(manager.getUserName());
        DbData password = new DbData(manager.getPassword());
        DbData isLogin = new DbData(manager.getIsLogin() ? 1 : 0);
        DbData shopId = new DbData("NULL");
        if (manager.getShop() != null)
        {
            shopId = new DbData(manager.getShop().getId());
        }

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(userName);
        data.add(password);
        data.add(isLogin);
        data.add(shopId);

        return data;
    }
}