package com.fbhaedware.library.face;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.Face3DAngle;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.VersionInfo;
import com.fbhaedware.library.EduInterface;
import com.fbhaedware.library.face.util.CameraHelper;
import com.fbhaedware.library.face.util.CameraListener;

import java.util.ArrayList;
import java.util.List;

public class FaceHelper{

    private static final String TAG = "FaceHelper";
    private View mPreviewView;
    private Context mContext;
    private FaceRectView mFaceRectView;
    private FaceEngine faceEngine;
    private Camera.Size previewSize;
    private DrawHelper drawHelper;
    private CameraHelper cameraHelper;
    private int afCode = -1;
    private int processMask = FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS;
    private Integer cameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private EduInterface.FaceCallBack mFaceCallBack;

    public FaceHelper(Context context, View previewView, FaceRectView faceRectView){
        mContext =  context;
        mPreviewView = previewView;
        mFaceRectView = faceRectView;

        initEngine();
        initCamera();
    }


    private void initEngine() {
        faceEngine = new FaceEngine();
        afCode = faceEngine.init(mContext, FaceEngine.ASF_DETECT_MODE_VIDEO, FaceEngine.ASF_OP_0_HIGHER_EXT,
                16, 20, FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS);
        VersionInfo versionInfo = new VersionInfo();
        faceEngine.getVersion(versionInfo);
        Log.i(TAG, "initEngine:  init: " + afCode + "  version:" + versionInfo);
        if (afCode != ErrorInfo.MOK) {
            Log.d(TAG, "initEngine: init failed,code is :"+afCode);
        }
    }

    private void initCamera(){

        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                Log.i(TAG, "onCameraOpened: " + cameraId + "  " + displayOrientation + " " + isMirror);
                previewSize = camera.getParameters().getPreviewSize();
                drawHelper = new DrawHelper(previewSize.width, previewSize.height, mPreviewView.getWidth(), mPreviewView.getHeight(), displayOrientation
                        , cameraId, isMirror);
            }


            @Override
            public void onPreview(byte[] nv21, Camera camera) {
                if (mFaceRectView != null) {
                    mFaceRectView.clearFaceInfo();
                }
                List<FaceInfo> faceInfoList = new ArrayList<>();
                int code = faceEngine.detectFaces(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList);
                if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
                    code = faceEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, processMask);
                    if (code != ErrorInfo.MOK) {
                        return;
                    }
                }else {
                    if(mFaceCallBack != null){
                        mFaceCallBack.PersonComeOff(null);
                    }
                    return;
                }
                List<AgeInfo> ageInfoList = new ArrayList<>();
                List<GenderInfo> genderInfoList = new ArrayList<>();
                List<Face3DAngle> face3DAngleList = new ArrayList<>();
                List<LivenessInfo> faceLivenessInfoList = new ArrayList<>();
                int ageCode = faceEngine.getAge(ageInfoList);
                int genderCode = faceEngine.getGender(genderInfoList);
                int face3DAngleCode = faceEngine.getFace3DAngle(face3DAngleList);
                int livenessCode = faceEngine.getLiveness(faceLivenessInfoList);

                //有其中一个的错误码不为0，return
                if ((ageCode | genderCode | face3DAngleCode | livenessCode) != ErrorInfo.MOK) {
                    return;
                }
                if (mFaceRectView != null && drawHelper != null) {
                    List<DrawInfo> drawInfoList = new ArrayList<>();
                    for (int i = 0; i < faceInfoList.size(); i++) {
                        drawInfoList.add(new DrawInfo(faceInfoList.get(i).getRect(), genderInfoList.get(i).getGender(), ageInfoList.get(i).getAge(), faceLivenessInfoList.get(i).getLiveness(), null));
                    }
                    drawHelper.draw(mFaceRectView, drawInfoList);
                    if(mFaceCallBack != null) {
                        mFaceCallBack.PersonComeOn(null);
                    }
                }
            }

            @Override
            public void onCameraClosed() {
                Log.i(TAG, "onCameraClosed: ");
            }

            @Override
            public void onCameraError(Exception e) {
                Log.i(TAG, "onCameraError: " + e.getMessage());
            }

            @Override
            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
                if (drawHelper != null) {
                    drawHelper.setCameraDisplayOrientation(displayOrientation);
                }
                Log.i(TAG, "onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
            }
        };
        cameraHelper = new CameraHelper.Builder()
                .previewViewSize(new Point(mPreviewView.getMeasuredWidth(),mPreviewView.getMeasuredHeight()))
//                .rotation(mContext.getWindowManager().getDefaultDisplay().getRotation())
                .specificCameraId(cameraID != null ? cameraID : Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(mPreviewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
    }

    private void unInitEngine(){
            if (afCode == 0) {
                afCode = faceEngine.unInit();
                Log.i(TAG, "unInitEngine: " + afCode);
        }
    }


    public void destory(){
        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }
        unInitEngine();
    }

    public void registerFaceListener(EduInterface.FaceCallBack callBack) {
        mFaceCallBack = callBack;
    }
}
