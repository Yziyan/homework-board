package run.hxtia.workbd.common.util;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 字符串工具类
 */
public class Strings {

    /**
     * 获取UUID
     * @return ：去掉 "-" 后的 UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取指定长度的 UUID
     * @param len ：(0, 32]
     * @return ：获取 (0 , 32] 长度的UUID
     */
    public static String getUUID(int len) {
        if (len <= 0) return null;
        String uuid = getUUID();
        if (len > 32) return uuid;
        return uuid.substring(0, len);
    }

    /**
     * 将集合的元素用指定的分隔符连接起来
     * @param list ：集合
     * 例如：[1, 2, 3]
     * 分隔符：","
     * 返回："1,2,3"
     * @return ：连接后的字符串
     */
    public static String join(Iterable<?> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (Object o : list) {
            sb.append(o).append(separator);
        }
        return sb.substring(0, sb.length() - separator.length());
    }

    /**
     * 分割逗号分隔的字符串并转换为指定类型的列表
     * @param str 逗号分隔的字符串
     * @param type 转换的目标类型
     * @return 转换后的列表
     */
    public static <T> List<T> splitToList(String str, Class<T> type) {
        return Stream.of(str.split(","))
            .map(id -> {
                if (type == Short.class) {
                    return type.cast(Short.valueOf(id));
                } else if (type == Integer.class) {
                    return type.cast(Integer.valueOf(id));
                } else if (type == Long.class) {
                    return type.cast(Long.valueOf(id));
                } else {
                    return type.cast(id);
                }
            })
            .collect(Collectors.toList());
    }

    public static String join(String[] list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (Object o : list) {
            sb.append(o).append(separator);
        }
        return sb.substring(0, sb.length() - separator.length());
    }
}
