package com.blanke.wechatbotxposed.hook

import com.blanke.wechatbotxposed.hook.SendMsgHooker.wxMsgSplitStr
import com.gh0u1l5.wechatmagician.spellbook.WechatGlobal.wxProcessName
import com.gh0u1l5.wechatmagician.spellbook.interfaces.IMessageStorageHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

object WechatMessageHook : IMessageStorageHook {

    override fun onMessageStorageInserted(msgId: Long, msgObject: Any) {
        XposedBridge.log("onMessageStorageInserted msgId=$msgId,msgObject=$msgObject")
//        printMsgObj(msgObject)
        // 这些都是消息的属性，内容，发送人，类型等
        val field_content = XposedHelpers.getObjectField(msgObject, "field_content") as String?
        val field_talker = XposedHelpers.getObjectField(msgObject, "field_talker") as String?
        val field_type = (XposedHelpers.getObjectField(msgObject, "field_type") as Int).toInt()
        val field_isSend = (XposedHelpers.getObjectField(msgObject, "field_isSend") as Int).toInt()
        XposedBridge.log("field_content=$field_content,field_talker=$field_talker," +
                "field_type=$field_type,field_isSend=$field_isSend")
        if (field_isSend == 1) {// 代表自己发出的，不处理
            return
        }
        XposedBridge.log(wxProcessName + ">>>>>>" + "onMessageStorageInserted")
        if (field_type == 1 && "wxid_knfjmlhrupad21".equals(field_talker)) { //文本消息
            // field_content 就是消息内容，可以接入图灵机器人回复
            XposedBridge.log("reply2 = ${Objects.ChattingFooterEventImpl}")
            val replyContent = "reply: \n$field_content"
            Objects.ChattingFooterEventImpl?.apply {
                // 将 wx_id 和 回复的内容用分隔符分开
                val content = "$field_talker$wxMsgSplitStr$replyContent"
//                val content = "wxid_yzx0qd2ipigz21$wxMsgSplitStr$replyContent"
//                val content = "wxid_knfjmlhrupad21$wxMsgSplitStr$replyContent"
                val success = Methods.ChattingFooterEventImpl_SendMsg.invoke(this, content) as Boolean
                XposedBridge.log("reply msg success = $success")
            }
//            val clazz: Class<*>? = WechatGlobal.wxLoader?.loadClass("com.tencent.mm.ui.chatting.p")
//            val clazzA: Class<*>? = WechatGlobal.wxLoader?.loadClass("com.tencent.mm.ui.chatting.d.a")
//            val clazzChatFooter: Class<*>? = WechatGlobal.wxLoader?.loadClass("com.tencent.mm.pluginsdk.ui.chat.ChatFooter")
//            val clazzString: Class<*>? = WechatGlobal.wxLoader?.loadClass("java.lang.String")
//            val clazzBaseChattingUIFragment: Class<*>? = WechatGlobal.wxLoader?.loadClass("com.tencent.mm.ui.chatting.BaseChattingUIFragment")
//            val clazzAe: Class<*>? = WechatGlobal.wxLoader?.loadClass("com.tencent.mm.ui.chatting.ae")
//            val clazzAf: Class<*>? = WechatGlobal.wxLoader?.loadClass("com.tencent.mm.ui.chatting.af")
//            clazz?.let {
//                val consA = clazzA?.getConstructor(clazzBaseChattingUIFragment, clazzAe, clazzAf)
//                val anyA = consA?.newInstance(null, null, null)
//                val cons = clazz.getConstructor(clazzA, clazzChatFooter, clazzString)
//                val any = cons.newInstance(anyA, null, "")
//                val content = "$field_talker$wxMsgSplitStr$replyContent"
//                val method: Method = clazz.getMethod("Qu", clazzString)
//                method.invoke(any, content)
//            }
        }
    }

    private fun printMsgObj(msg: Any) {
        val fieldNames = msg::class.java.fields
        fieldNames.forEach {
            val field = it.get(msg)
            if (field is Array<*>) {
                val s = StringBuffer()
                field.forEach {
                    s.append(it.toString() + " , ")
                }
                XposedBridge.log("$it = $s")
            } else {
                XposedBridge.log("$it = $field")
            }
        }
    }
}