package com.fbhaedware.library.speech.tts;

import com.fbhaedware.library.EduInterface;

/**
 * zhangy
 * Created by zhenglibin on 17/5/22.
 */

public interface TtsManager {



    void speaks(String message, EduInterface.TTsCallBack callBack);

    void stop();

    void cancel();

    void registerTtsListener(EduInterface.TTsCallBack callBack);
}
