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


    public static final String VEHICLE_DRIVE_IN_TOPIC = "vehicle-drive-in-topic";
    public static final String VEHICLE_WHITE_TOPIC = "vehicle-white-topic";
    public static final String VEHICLE_BLACK_TOPIC = "vehicle-black-topic";

    public static final String VEHICLE_DRIVE_IN_SUB = "vehicle-drive-in-sub-";
    public static final String VEHICLE_WHITE_SUB = "vehicle-white-sub-";
    public static final String VEHICLE_BLACK_SUB = "vehicle-black-sub-";

    public static final String VEHICLE_DRIVE_IN_QUEUE = "vehicle-drive-in-queue-";
    public static final String VEHICLE_WHITE_QUEUE = "vehicle-white-queue-";
    public static final String VEHICLE_BLACK_QUEUE = "vehicle-black-queue-";


    /**
     * 车辆驶入本地队列
     */
    public static final String VEHICLE_DRIVE_IN_LOCAL_QUEUE = "vehicle-drive-in-local-queue";


    /**
     * 接口参数名
     */
    public static final String ENTRY = "entryInfo";
    public static final String ENTRY_EX = "entryInfo";
    public static final String ENTRY_DENY = "entryDenyParam";
    public static final String PASS_REJECT = "passRejectParam";
    public static final String EXIT = "exitParam";
    public static final String EXIT_EX = "exitExParam";


    /**
     * 文件上传失败重试次数
     */
    public static final Integer FILEUPLOAD_RETRY = 5;
}
