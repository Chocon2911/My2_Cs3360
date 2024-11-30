package DataBase.Child;

import DataBase.Base.*;
import Obj.Data.*;

import java.util.*;

public class ItemDb extends AbstractDb
{
    //========================================Create Table========================================
    public boolean createItemTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Items"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "Name TEXT, "
                + "ShopId TEXT, "
                + "Price FLOAT, "
                + "InitAmount INTEGER, "
                + "ItemType INTEGER, "
                + "Description TEXT, "
                + "FOREIGN KEY (Id) REFERENCES ids (GlobalId)"
                + ");";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertItemData(Item item)
    {
        String sql = "INSERT INTO Items "
                + "(Id, Name, ShopId, Price, InitAmount, ItemType, Description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromItem(item);
        String result = this.insertData(url, sql, data);
        if (result == null) 
        {
            String idE = new IdDb().insertId(item.getId());
            if (idE != null) return idE;
        }

        return result;
    }

    //===========================================Query============================================
    public Item queryItemData(String id)
    {
        DbData queryData = new DbData(id);
        String sql = "SELECT * FROM Items WHERE Id = ?";
        List<String> rowNames = this.getItemRowNames();
        List<DbType> rowTypes = this.getItemsRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        return this.getItem(datas.get(0));
    }

    public List<Item> queryItemsByShopId(String shopId)
    {
        DbData queryData = new DbData(shopId);
        String sql = "SELECT * FROM Items WHERE ShopId = ?";
        List<String> rowNames = this.getItemRowNames();
        List<DbType> rowTypes = this.getItemsRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            items.add(this.getItem(datas.get(i)));
        }

        return items;
    }

    //===========================================Update===========================================
    public String updateItemData(Item item)
    {
        String sql = "UPDATE Items SET * WHERE Id = ?";

        List<DbData> data = this.getDataFromItem(item);
        DbData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
    }

    //===========================================Delete===========================================
    public boolean deleteItemData(Item item)
    {
        String sql = "DELETE FROM Items WHERE Id = ?";
        DbData idDb = new DbData();
        idDb.setValueStr(item.getId());
        boolean result = this.deleteRow(url, sql, idDb);
        if (result) 
        {
            new IdDb().deleteId(item.getId());
        }

        return result;
    }

    //===========================================Other============================================
    // Query
    private List<String> getItemRowNames()
    {
        List<String> rowNames;
        rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("Name");
        rowNames.add("ShopId");
        rowNames.add("Price");
        rowNames.add("InitAmount");
        rowNames.add("ItemType");
        rowNames.add("Description");

        return rowNames;
    }
    
    private List<DbType> getItemsRowTypes()
    {
        List<DbType> rowTypes = new ArrayList<>();
        rowTypes.add(DbType.TEXT);    // Id
        rowTypes.add(DbType.TEXT);    // Name
        rowTypes.add(DbType.TEXT);    // ShopId
        rowTypes.add(DbType.FLOAT);   // Price
        rowTypes.add(DbType.INTEGER); // InitAmount
        rowTypes.add(DbType.INTEGER); // ItemType
        rowTypes.add(DbType.TEXT);    // Description

        return rowTypes;
    }

    private Item getItem(List<DbData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String shopId = data.get(2).getValueStr();
        float price = data.get(3).getValueFloat();
        int initAmount = data.get(4).getValueInt();
        int itemTypeInt = data.get(5).getValueInt();
        String description = data.get(6).getValueStr();

        Shop shop = new ShopDb().queryShopData(shopId);
        ItemType itemType = ItemType.values()[itemTypeInt];
        List<RequestedItem> requestedItems = new RequestedItemDb().queryRequestedItemsByItemId(id);
        return new Item(id, name, shop, price, itemType, initAmount, description, requestedItems);
    }

    // Update - Insert
    private List<DbData> getDataFromItem(Item item)
    {
        DbData id = new DbData(item.getId());
        DbData name = new DbData(item.getName());
        DbData shopId = new DbData(item.getShop().getId());
        DbData price = new DbData(item.getPrice());
        DbData initAmount = new DbData(item.getInitAmount());
        DbData itemType = new DbData(item.getItemType().ordinal());
        DbData description = new DbData(item.getDescription());

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(shopId);
        data.add(price);
        data.add(initAmount);
        data.add(itemType);
        data.add(description);

        return data;
    }
}
