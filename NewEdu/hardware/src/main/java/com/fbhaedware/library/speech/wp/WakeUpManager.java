package com.fbhaedware.library.speech.wp;

import com.fbhaedware.library.EduInterface;

/**
 * zhangy
 * Created by zhangy on 2017/12/12.
 */

public interface WakeUpManager {

    /**
     * 开启唤醒
     */
    void startWakeUp();

    /**
     * 停止唤醒
     */
    void stopWakeUp();


    void rigsterWakeUpListener(EduInterface.WakeUpCallBack callBack);
}
