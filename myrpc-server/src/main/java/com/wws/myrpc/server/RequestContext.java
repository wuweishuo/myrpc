package com.wws.myrpc.server;

public class RequestContext {

    private static final ThreadLocal<RequestContext> REQUEST_CONTEXT = new ThreadLocal<>();

    private final int version;

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
