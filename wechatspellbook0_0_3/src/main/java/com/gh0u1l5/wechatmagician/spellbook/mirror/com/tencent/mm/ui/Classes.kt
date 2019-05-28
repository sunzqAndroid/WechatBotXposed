package com.gh0u1l5.wechatmagician.spellbook.mirror.com.tencent.mm.ui

import android.widget.BaseAdapter
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxClasses
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLazy
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxLoader
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxPackageName
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxProcessName
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findClassIfExists
import com.gh0u1l5.wechatmagician.spellbook.util.ReflectionUtil.findClassesFromPackage
import de.robv.android.xposed.XposedBridge

object Classes {
    val LauncherUI: Class<*> by wxLazy("LauncherUI") {
        findClassIfExists("$wxPackageName.ui.LauncherUI", wxLoader!!)
    }

    val MMActivity: Class<*> by wxLazy("MMActivity") {
        findClassIfExists("$wxPackageName.ui.MMActivity", wxLoader!!)
    }

    val MMFragmentActivity: Class<*> by wxLazy("MMFragmentActivity") {
        findClassIfExists("$wxPackageName.ui.MMFragmentActivity", wxLoader!!)
    }

    val MMBaseAdapter: Class<*> by wxLazy("MMBaseAdapter") {
        XposedBridge.log("init MMBaseAdapter")
        if (wxClasses == null) {
            XposedBridge.log(wxProcessName + ">>>>>>" + "wxClasses==null")
        } else {
            XposedBridge.log(wxProcessName + ">>>>>>" + "wxClasses不为空")
        }
        findClassesFromPackage(wxLoader!!, wxClasses!!, "$wxPackageName.ui")
                .filterBySuper(BaseAdapter::class.java)
                .filterByField("TAG", "java.lang.String")
                .firstOrNull()
    }
}