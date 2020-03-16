package javar;

import java.util.HashMap;
import java.util.Map;

public class ProvinceByDate {
    String province;
    Map<String, int[]> data;
    
    public ProvinceByDate(String province) {
        this.province=province;
        data = new HashMap<String, int[]>();
    }
    
    public void put(String date,int[] tmp) {
        data.put(date, tmp);
        return;
    }
    
    public int[] getData(String date) {
        return data.get(date);
    }
}
