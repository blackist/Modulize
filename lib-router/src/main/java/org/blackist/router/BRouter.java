package org.blackist.router;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

/**
 * @author LiangLiang.Dong<liangl.dong@qq.com>
 * @since 2018/9/28
 */

public class BRouter {

    private static volatile BRouter instance;
    private HashMap<String, BAction> actions;

    private BRouter() {
        actions = new HashMap<>();
    }

    /**
     * singleton for multithread.
     *
     * @return router instance
     */
    public static BRouter getInstance() {
        if (instance == null) {
            synchronized (BRouter.class) {
                if (instance == null) {
                    Log.d("BRouter", "BRouter initing... ");
                    instance = new BRouter();
                }
            }
        }
        return instance;
    }

    /**
     * register action in router pool.
     *
     * @param name   name of action
     * @param action action
     */
    public static void register(String name, BAction action) {
        if (getInstance().actions.containsKey(name)) {
            return;
        }
        getInstance().actions.put(name, action);
    }

    /**
     * invoke action by router request.
     *
     * @param context context
     * @param req     request param
     * @return router response
     */
    public static BRouterRes push(Context context, BRouterReq req) {
        BRouterRes res = new BRouterRes();
        BAction action = getAction(req);
        if (action != null) {
            Object object = action.startAction(context, req.getPath(), req.getParam(), null);
            res.set(object, BRouterRes.CODE.OK);
        } else {
            res.set(BRouterRes.CODE.NOT_FOUND);
        }
        return res;
    }

    /**
     * invoke action by router request.
     *
     * @param context context
     * @param req     request param
     * @param event   callback
     * @return router response
     */
    public static BRouterRes push(Context context, BRouterReq req, BEvent event) {
        BRouterRes res = new BRouterRes();
        BAction action = getAction(req);
        if (action != null) {
            Object object = action.startAction(context, req.getPath(), req.getParam(), event);
            res.set(object, BRouterRes.CODE.OK);
        } else {
            res.set(BRouterRes.CODE.NOT_FOUND);
        }
        return res;
    }

    /**
     * get action from router
     *
     * @param req request
     * @return action
     */
    private static BAction getAction(BRouterReq req) {
        if (getInstance().actions.containsKey(req.getAction())) {
            return getInstance().actions.get(req.getAction());
        }
        return null;
    }
}
