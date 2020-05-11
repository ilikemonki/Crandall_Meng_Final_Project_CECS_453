package com.example.crandall_meng_final_project_cecs_453.Controller;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.example.crandall_meng_final_project_cecs_453.Model.TextToSpeechModel;

import java.util.Locale;

// Text to speech controller. Converts text to speech.
public class TextToSpeechController {
    TextToSpeechModel ttsModel;

    // Constructor
    public TextToSpeechController(Context appContext) {
        ttsModel = new TextToSpeechModel(appContext);
    }

    // Set speaker's language.
    public void setSpeakerLanguage (Locale lang) {
        ttsModel.getTextToSpeech().setLanguage(lang);
    }

    public TextToSpeechModel getTtsModel() {
        return ttsModel;
    }

    public TextToSpeech getTextToSpeech() {
        return ttsModel.getTextToSpeech();
    }

    // Start speaking.
    public void speak(CharSequence text) {
        ttsModel.getTextToSpeech().speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
