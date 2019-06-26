package dos0311.ara.ac.nz.eyeballmaze;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class Tutorial extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        TextView textTutorial = findViewById(R.id.tutorialText);
        textTutorial.setText("Welcome to the Eyeball Maze \n" +
                "Please follow those rules \n" +
                "Rule 1 : You can only move forward, left or right.\n" +
                "Rule 2 : You can only move to same shape or same colour.\n");

        VideoView videoView = findViewById(R.id.videoView);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tutorial);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }
}
