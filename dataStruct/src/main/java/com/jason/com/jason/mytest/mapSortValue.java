package com.jason.com.jason.mytest;

import java.util.*;

/**
 * @Classname mapSortValue
 * @Description TODO
 * @Date 2020/8/20 3:26 下午
 * @Created by jason
 */
public class mapSortValue {
    public static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
        Map.Entry<String, Integer> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    public static class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {
            //按照从大到小的顺序排列，如果想正序 调换me1 me2的位置
            return me2.getValue().compareTo(me1.getValue());
        }
    }

}
