package hackeru.edu.moreintents;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final int REQEST_CODE_CALL = 2;
    private static final int REQEST_CODE_SMS = 1;
    EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPhone = (EditText) findViewById(R.id.etPhoneNumber);
    }

    public void setTimer(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, 12, 20, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "WakeUp");
        intent.putExtra(AlarmClock.EXTRA_LENGTH, 90);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, false);
        startActivity(intent);
    }

    public void call(@Nullable View view) {
        Uri phoneUri = Uri.parse("tel:" + etPhone.getText());
        Intent callintent = new Intent(Intent.ACTION_CALL, phoneUri);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQEST_CODE_CALL);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callintent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQEST_CODE_CALL:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    call(null);
                else
                    Toast.makeText(this, "No call for you...", Toast.LENGTH_SHORT).show();
                break;
            case REQEST_CODE_SMS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    call(null);
                else
                    Toast.makeText(this, "No call for you...", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void sendSMS(View view) {
        SmsManager sms = SmsManager.getDefault();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQEST_CODE_SMS);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        sms.sendTextMessage("0544887009", null, "Hi there", null, null);
    }
}
