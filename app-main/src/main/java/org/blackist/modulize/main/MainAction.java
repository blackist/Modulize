package org.blackist.modulize.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.blackist.modulize.main.view.MainActivity;
import org.blackist.router.BAction;
import org.blackist.router.BEvent;

/**
 * @author LiangLiang.Dong<liangl.dong@qq.com>
 * @since 2018/9/28
 */

public class MainAction extends BAction {

    public static final String NAME = "main";

    @Override
    public Object startAction(Context context, String path, Bundle param, BEvent event) {

        switch (path) {

            default: {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtras(param);
                if (context instanceof Activity) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
            }
        }

        return null;
    }
}
