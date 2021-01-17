package com.wws.myrpc.server;

/**
 * RequestContext
 * 请求上下文，封装flowId和version
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class RequestContext {

    private static final ThreadLocal<RequestContext> REQUEST_CONTEXT = new ThreadLocal<>();

    /**
     * 请求服务的版本号
     */
    private final int version;

    /**
     * 请求id，标示一次请求
     */
    private final long flowId;

    public RequestContext(int version, long flowId) {
        this.version = version;
        this.flowId = flowId;
    }

    public static RequestContext get() {
        return REQUEST_CONTEXT.get();
    }

    public static void set(RequestContext requestContext) {
        REQUEST_CONTEXT.set(requestContext);
    }

    public static void clear() {
        REQUEST_CONTEXT.remove();
    }

    public int getVersion() {
        return version;
    }

    public long getFlowId() {
        return flowId;
    }
}
