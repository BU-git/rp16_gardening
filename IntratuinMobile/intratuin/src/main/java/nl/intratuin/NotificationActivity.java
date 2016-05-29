package nl.intratuin;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ivIntratuin;
    Button bOK;
    Button bToApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ivIntratuin=(ImageView)findViewById(R.id.ivIntratuin);
        bOK=(Button)findViewById(R.id.bOk);
        bToApp=(Button)findViewById(R.id.bToApp);

        ivIntratuin.setOnClickListener(this);
        bOK.setOnClickListener(this);
        bToApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ivIntratuin:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.getString(R.string.company_website)));
                startActivity(browserIntent);
                break;
            case R.id.bOk:
                removeNotification();
                finish();
                break;
            case R.id.bToApp:
                removeNotification();
                this.startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void removeNotification(){
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(RegisterActivity.NOTIFY_ID);
    }
}
