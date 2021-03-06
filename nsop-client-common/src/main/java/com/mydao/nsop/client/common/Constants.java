package com.mydao.nsop.client.common;

/**
 * @author zhuyanwei
 */
public final class Constants {
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
    public static final String ENTRY_EX_QUEUE = "entry_ex_queue";

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
    public static final String VEHICLE_DRIVE_OUT_TOPIC = "vehicle-drive-out-topic";
    public static final String VEHICLE_WHITE_TOPIC = "vehicle-white-topic";
    public static final String VEHICLE_BLACK_TOPIC = "vehicle-black-topic";

    public static final String VEHICLE_DRIVE_IN_SUB = "vehicle-drive-in-sub-";
    public static final String VEHICLE_DRIVE_OUT_SUB = "vehicle-drive-out-sub-";
    public static final String VEHICLE_WHITE_SUB = "vehicle-white-sub-";
    public static final String VEHICLE_BLACK_SUB = "vehicle-black-sub-";

    public static final String VEHICLE_DRIVE_IN_QUEUE = "vehicle-drive-in-queue-";
    public static final String VEHICLE_DRIVE_OUT_QUEUE = "vehicle-drive-out-queue-";
    public static final String VEHICLE_WHITE_QUEUE = "vehicle-white-queue-";
    public static final String VEHICLE_BLACK_QUEUE = "vehicle-black-queue-";

    /**
     * 车辆黑白名单全量请求队列
     */
    public static final String GET_BWLIST_QUEUE = "get_bwlist_queue";
    public static final String FULL_BLACK_LIST = "full_black_list";
    public static final String FULL_WHITE_LIST = "full_white_list";

    /**
     * 车辆驶入本地队列
     */
    public static final String VEHICLE_DRIVE_IN_LOCAL_QUEUE = "vehicle-drive-in-local-queue";

    /**
     * 车辆驶出本地队列
     */
    public static final String VEHICLE_DRIVE_OUT_LOCAL_QUEUE = "vehicle-drive-out-local-queue";


    /**
     * 接口参数名
     */
    public static final String INTER_PARAM = "entryInfo";
    public static final String EXIT_PARAM = "exitInfo";


    /**
     * 文件上传失败重试次数
     */
    public static final Integer FILEUPLOAD_RETRY = 5;

    /**
     * 重复读取驶入驶出记录频率(ms)
     */
    public static final int RETRY_TIMES = 30000;

    /**
     * 重复读取驶入驶出记录频率(ms)
     */
    public static final int EXCEPTION_CHECK_TIMES = 30000;
}
