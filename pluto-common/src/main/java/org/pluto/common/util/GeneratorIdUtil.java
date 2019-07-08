package org.pluto.common.util;

/**
 * ----------------------------------------------------
 * | 1 |  34 | 5 | 12 | 11 |
 * --------------------------------------
 *
 * 1:符号位 不适用
 * 34： 时间戳 到秒（1<< 34） -1 / 3600 * 24 *365 = （？） 年
 * 5：workerId
 * 12 ： 自增序列
 * 11 ：基因ID
 *
 *
 * @author yaojian
 * @date 2019/7/2
 */
public final class GeneratorIdUtil {

    private GeneratorIdUtil(){
        throw new RuntimeException("not allowed constructor!");
    }
}
