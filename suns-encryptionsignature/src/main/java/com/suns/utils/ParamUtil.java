package com.suns.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 参数转换工具类
 * @author suns suntion@yeah.net
 * @since 2017年11月14日下午1:54:11
 */
public class ParamUtil {

    private static List<String> IGNORE = new ArrayList<String>(Arrays.asList("sign_type", "sign_data", "encrypt_type", "encrypt_data", "charset"));

    /**
     * 将map转换成String，除去不必要的key
     * <p>
     * 默认的 key有 "sign_type", "sign_data", "encrypt_type", "encrypt_data"
     * </p>
     * 
     * @param map
     * @param removeKeyList
     * @return
     */
    public static String mapToSortStr(Map<String, String> map, List<String> removeKeyList) {
        if (map == null) {
            return "";
        }
        if (removeKeyList == null) {
            removeKeyList = new ArrayList<String>();
        }
        removeKeyList.addAll(IGNORE);

        ArrayList<String> mapKeys = new ArrayList<String>(map.keySet());
        Collections.sort(mapKeys);

        StringBuilder link = new StringBuilder();
        boolean first = true;
        for_map_keys: for (String key : mapKeys) {
            String value = map.get(key);
            if (value == null || "".equals(value.trim())) {
                continue;
            }
            for (String removeKey : removeKeyList) {
                if (key.equalsIgnoreCase(removeKey)) {
                    continue for_map_keys;
                }
            }
            if (!first) {
                link.append("&");
            } else {
                first = false;
            }
            link.append(key).append("=").append(value);
        }
        return link.toString();
    }
    
}
