package jp.co.abs.onseininnsikitsuboi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    ListView listView;
    ImageView imageview;
    ArrayList<String> data;

    Intent intent;
    SpeechRecognizer recognizer;
    int code=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.list);
        imageview=findViewById(R.id.mic1);

        if(getPackageManager().queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0).size() == 0){
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

 /*   protected void stopListening(){
        if(recognizer != null){
            recognizer.destroy();
        }
        recognizer = null;
    }
 */
   /* public void restartListening(){
        recognizer.stopListening();
        recognizer.startListening(intent);
    }
*/
    public void onClick(View view) {
        switch (code) {
            case 0:
                code++;
                if (getPackageManager().queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0).size() == 0) {
                    return;
                }

                intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());

                recognizer = SpeechRecognizer.createSpeechRecognizer(this);
                recognizer.setRecognitionListener(new RecognitionListener() {

                    @Override
                    public void onReadyForSpeech(Bundle bundle) {
                        Toast.makeText(getApplicationContext(), "音声を入力してください", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBeginningOfSpeech() {
                        imageview.setImageResource(R.drawable.mic2);
                    }

                    @Override
                    public void onRmsChanged(float v) {
                    }

                    @Override
                    public void onBufferReceived(byte[] bytes) {
                    }

                    @Override
                    public void onEndOfSpeech() {
                        imageview.setImageResource(R.drawable.mic);
                    }

                    @Override
                    public void onResults(Bundle results) {

                        //ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, data);
                        data = results.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION);
                       // listView.setAdapter(adapter);

                        if (data.size() > 10) {
                            data.remove(0);
                        }
                      //  restartListening();
                    }

                    @Override
                    public void onPartialResults(Bundle bundle) {
                    }

                    @Override
                    public void onEvent(int i, Bundle bundle) {
                    }

                    public void onError(int error) {
                        String reason = "";
                        switch (error) {
                            // Audio recording error
                            case SpeechRecognizer.ERROR_AUDIO:
                                reason = "ERROR_AUDIO";
                                break;
                            // Other client side errors
                            case SpeechRecognizer.ERROR_CLIENT:
                                reason = "ERROR_CLIENT";
                                break;
                            // Insufficient permissions
                            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                                reason = "ERROR_INSUFFICIENT_PERMISSIONS";
                                break;
                            // 	Other network related errors
                            case SpeechRecognizer.ERROR_NETWORK:
                                reason = "ERROR_NETWORK";
                                /* ネットワーク接続をチェックする処理をここに入れる */
                                break;
                            // Network operation timed out
                            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                                reason = "ERROR_NETWORK_TIMEOUT";
                                break;
                            // No recognition result matched
                            case SpeechRecognizer.ERROR_NO_MATCH:
                                reason = "ERROR_NO_MATCH";
                                break;
                            // RecognitionService busy
                            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                                reason = "ERROR_RECOGNIZER_BUSY";
                                break;
                            // Server sends error status
                            case SpeechRecognizer.ERROR_SERVER:
                                reason = "ERROR_SERVER";
                                /* ネットワーク接続をチェックをする処理をここに入れる */
                                break;
                            // No speech input
                            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                                reason = "ERROR_SPEECH_TIMEOUT";
                                break;
                        }
                       // restartListening();
                        Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_SHORT).show();
                    }
                });
                recognizer.startListening(intent);
                break;
            case 1:
                code--;
                if(data != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list, data);
                    listView.setAdapter(adapter);
                }
                break;
        }
    }
}
