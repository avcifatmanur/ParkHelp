package com.parkhelp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CameraSelectorDialogFragment extends DialogFragment {
    public interface CameraSelectorDialogListener {
        public void onCameraSelected(int cameraId);
    }

    private int mCameraId;
    private CameraSelectorDialogListener mListener;

    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
    }

    public static CameraSelectorDialogFragment newInstance(CameraSelectorDialogListener listener, int cameraId) {
        CameraSelectorDialogFragment fragment = new CameraSelectorDialogFragment();
        fragment.mCameraId = cameraId;
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(mListener == null) {
            dismiss();
            return null;
        }

        int numberOfCameras = Camera.getNumberOfCameras();
        String[] cameraNames = new String[numberOfCameras];
        int checkedIndex = 0;

        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraNames[i] = "Ön Katman";
            } else if(info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraNames[i] = "Arka Katman";
            } else {
                cameraNames[i] = "Kamera ID: " + i;
            }
            if(i == mCameraId) {
                checkedIndex = i;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("Kamera Seç")
                .setSingleChoiceItems(cameraNames, checkedIndex,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCameraId = which;
                            }
                        })
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // or return them to the component that opened the dialog
                        if (mListener != null) {
                            mListener.onCameraSelected(mCameraId);
                        }
                    }
                })
                .setNegativeButton("Çık", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }
}
