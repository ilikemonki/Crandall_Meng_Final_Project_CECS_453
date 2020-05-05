package com.example.crandall_meng_final_project_cecs_453.Model;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

import java.util.Locale;

public class TextToSpeechModel {
    private TextToSpeech textToSpeech;
    private Context appContext;

    public TextToSpeechModel(final Context ctx) {
        this.appContext = ctx;
        textToSpeech = new TextToSpeech(appContext, new OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
    }
    public TextToSpeech getTextToSpeech() {
        return textToSpeech;
    }

    public Context getAppContext() {
        return appContext;
    }
}
