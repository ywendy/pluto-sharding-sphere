package org.pluto.common;

/**
 * @author yaojian
 * @date 2019/7/2
 */
public final class Constant {

    private Constant(){
        throw new RuntimeException("not allowed constructor!");
    }

    /**
     *
     * ----------------------------------------------------
     *  * | 1 |  34 | 5 | 12 | 11 |
     *  * --------------------------------------
     *
     */

    protected final static  long EPOCH = 1288834974657L;//时间戳基数（越大，生成的id越小）

    protected final  static long WORKER_ID_BIT = 5L;
    protected final static  long SEQQUENCE_BIT = 12L;



}
