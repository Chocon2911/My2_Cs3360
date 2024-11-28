package DataBase.Child;

import DataBase.Base.*;
import Obj.Main.*;
import java.util.*;

public class ItemDb extends AbstractDataBase
{
    //========================================Create Table========================================
    public boolean createItemTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Items"
                + "("
                + "Id TEXT PRIMARY KEY UNIQUE NOT NULL, "
                + "Name TEXT NOT NULL, "
                + "ShopId TEXT NOT NULL, "
                + "Price FLOAT NOT NULL, "
                + "InitAmount INTEGER NOT NULL, "
                + "ItemType INTEGER NOT NULL, "
                + "Description TEXT"
                + ");";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public boolean insertItemData(Item item)
    {
        String sql = "INSERT INTO Items "
                + "(Id, Name, ShopId, Price, InitAmount, ItemType, Description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        List<DataBaseData> data = this.getDataFromItem(item);
        return this.insertData(url, sql, data);
    }

    //===========================================Query============================================
    public Item queryItemData(String id)
    {
        DataBaseData queryData = new DataBaseData(id);
        String sql = "SELECT * FROM Items WHERE Id = ?";
        List<String> rowNames = this.getItemRowNames();
        List<DataBaseType> rowTypes = this.getItemsRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        return this.getItem(datas.get(0));
    }

    public List<Item> queryItemsByShopId(String shopId)
    {
        DataBaseData queryData = new DataBaseData(shopId);
        String sql = "SELECT * FROM Items WHERE ShopId = ?";
        List<String> rowNames = this.getItemRowNames();
        List<DataBaseType> rowTypes = this.getItemsRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            items.add(this.getItem(datas.get(i)));
        }

        return items;
    }

    //===========================================Update===========================================
    public boolean updateItemData(Item item)
    {
        String sql = "UPDATE Items SET * WHERE Id = ?";

        List<DataBaseData> data = this.getDataFromItem(item);
        DataBaseData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
    }

    //===========================================Delete===========================================
    public boolean deleteItemData(String id)
    {
        String sql = "DELETE FROM Items WHERE Id = ?";

        DataBaseData idDb = new DataBaseData();
        idDb.setValueStr(id);

        return this.deleteRow(url, sql, idDb);
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
    
    private List<DataBaseType> getItemsRowTypes()
    {
        List<DataBaseType> rowTypes = new ArrayList<>();
        rowTypes.add(DataBaseType.TEXT);    // Id
        rowTypes.add(DataBaseType.TEXT);    // Name
        rowTypes.add(DataBaseType.TEXT);    // ShopId
        rowTypes.add(DataBaseType.FLOAT);   // Price
        rowTypes.add(DataBaseType.INTEGER); // InitAmount
        rowTypes.add(DataBaseType.INTEGER); // ItemType
        rowTypes.add(DataBaseType.TEXT);    // Description

        return rowTypes;
    }

    private Item getItem(List<DataBaseData> data)
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
    private List<DataBaseData> getDataFromItem(Item item)
    {
        DataBaseData id = new DataBaseData(item.getId());
        DataBaseData name = new DataBaseData(item.getName());
        DataBaseData shopId = new DataBaseData(item.getShop().getId());
        DataBaseData price = new DataBaseData(item.getPrice());
        DataBaseData initAmount = new DataBaseData(item.getInitAmount());
        DataBaseData itemType = new DataBaseData(item.getItemType().ordinal());
        DataBaseData description = new DataBaseData(item.getDescription());

        List<DataBaseData> data = new ArrayList<>();
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
