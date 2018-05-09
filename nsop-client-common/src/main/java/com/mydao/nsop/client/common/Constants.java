package com.mydao.nsop.client.common;

/**
 * @author zhuyanwei
 */
public class Constants {
    /**
     * 车辆驶入Topic
     */
    public static final String TOPIC_TSX_JOURNEY = "topic_tsx_journey";

    /**
     * 车辆驶入的匹配Key
     */
    public static final String ENTRY_KEY = "entry";

    /**
     * 车辆驶入的匹配队列名称
     */
    public static final String ENTRY_QUEUE = "entry_queue";

    /**
     * 车辆驶入异常的匹配Key
     */
    public static final String ENTRY_EX_KEY = "entry_ex";
    /**
     * 车辆驶入异常的匹配队列名称
     */
    public static final String ENTRY_EX_QUEUE = "entry_queue";

    /**
     * 车辆驶入否认的匹配Key
     */
    public static final String ENTRY_DENY_KEY = "entry_deny";

    /**
     * 车辆驶入否认的匹配队列名称
     */
    public static final String ENTRY_DENY_QUEUE = "entry_deny_queue";

    /**
     * 车辆驶入通行拒绝的匹配Key
     */
    public static final String PASS_REJECT_KEY = "pass_reject";

    /**
     * 车辆驶入通行拒绝的匹配队列名称
     */
    public static final String PASS_REJECT_QUEUE = "pass_reject_queue";

    /**
     * 车辆驶出的匹配Key
     */
    public static final String EXIT_KEY = "exit";
    /**
     * 车辆驶出的匹配队列名称
     */
    public static final String EXIT_QUEUE = "exit_queue";


    /**
     * 车辆驶出异常的匹配Key
     */
    public static final String EXIT_EX_KEY = "exit_ex";
    /**
     * 车辆驶出异常的匹配队列名称
     */
    public static final String EXIT_EX_QUEUE = "exit_ex_queue";




    /**
     *  车辆黑名单Topic
     */
    public static final String TOPIC_TSX_BLACKVEH = "topic_tsx_blackveh";

    public static final String ADD_BLACK_KEY = "add_black";
    public static final String ADD_BLACK_QUEUE = "add_black_queue";

    public static final String DEL_BLACK_KEY = "del_black";
    public static final String DEL_BLACK_QUEUE = "del_black_queue";

    /**
     * 车辆白名单Topic
     */
    public static final String TOPIC_TSX_WHITEVEH = "topic_tsx_whiteveh";

    public static final String ADD_WHITE_KEY = "add_white";
    public static final String ADD_WHITE_QUEUE = "add_white_queue";

    public static final String DEL_WHITE_KEY = "del_white";
    public static final String DEL_WHITE_QUEUE = "del_white_queue";

    /**
     * 暂时的测试MQ队列
     */
    public static final String CMQ_TOPIC_NSOP_CLOUD_MQ = "nsop-cloud-mq";
}
