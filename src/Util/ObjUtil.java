package Util;

import Obj.Data.ItemType;
import java.util.Random;

public final class ObjUtil
{
    private static ObjUtil instance;

    //=========================================Singleton==========================================
    public static ObjUtil getInstance()
    {
        if (instance == null) instance = new ObjUtil();
        return instance;
    }

    //===========================================Other============================================
    public final String getRandomStr(int length)
    {
        if (length <= 0)
        {
            System.out.println("ERROR: getRandomStr(): length is <= 0");
            return null;
        }

        Random rand = new Random();
        String randChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randStr = new StringBuilder();

        for (int i = 0; i < length; i++)
        {
            int index = rand.nextInt(randChar.length());
            randStr.append(randChar.charAt(index));
        }

        return randStr.toString();
    }

    //==========================================Convert===========================================
    public final ItemType getItemTypeFromInt(int value)
    {
        if (value == 1) return ItemType.Food;
        else if (value == 2) return ItemType.Drink;
        else if (value == 3) return ItemType.Souvenir;
        else return null;
    }

    public final int getIntFromItemType(ItemType itemType)
    {
        if (itemType == ItemType.Food) return 1;
        else if (itemType == ItemType.Drink) return 2;
        else if (itemType == ItemType.Souvenir) return 3;
        else return 0;
    }

    public final ItemType getItemTypeFromStr(String value)
    {
        if (value.equals("Food")) return ItemType.Food;
        else if (value.equals("Drink")) return ItemType.Drink;
        else if (value.equals("Souvenir")) return ItemType.Souvenir;
        return null;
    }

    public final String getStrFromItemType(ItemType itemType)
    {
        if (itemType == ItemType.Food) return "Food";
        else if (itemType == ItemType.Drink) return "Drink";
        else if (itemType == ItemType.Souvenir) return "Sourvenir";
        return "";
    }
}
