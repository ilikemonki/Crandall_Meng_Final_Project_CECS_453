package com.example.crandall_meng_final_project_cecs_453.Controller;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.example.crandall_meng_final_project_cecs_453.Model.TextToSpeechModel;

import java.util.Locale;

public class TextToSpeechController {
    TextToSpeechModel ttsModel;

    public TextToSpeechController(Context appContext) {
        ttsModel = new TextToSpeechModel(appContext);
    }

    public void setSpeakerLanguage (Locale lang) {
        ttsModel.getTextToSpeech().setLanguage(lang);
    }

    public TextToSpeechModel getTtsModel() {
        return ttsModel;
    }

    public TextToSpeech getTextToSpeech() {
        return ttsModel.getTextToSpeech();
    }

    public void speak(CharSequence text) {
        ttsModel.getTextToSpeech().speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
