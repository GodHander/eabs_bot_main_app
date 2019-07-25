package com.fbhaedware.library.speech.asr;

import com.fbhaedware.library.EduInterface;

/**
 * zhangy
 * Created by zhangy on 2017/12/11.
 */

public interface AsrManager {

    void start();

    void stop();

    void rigsterAsrListener(EduInterface.AsrCallBack callBack);
}
