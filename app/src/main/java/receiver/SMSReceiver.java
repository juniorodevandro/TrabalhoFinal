package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.calculadora.trabalhofinalmobile.R;
import dao.AlunoDAO;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        Object[] messages = (Object[]) bundle.get("pdus");
        byte[] message = (byte[]) messages[0];

        SmsMessage sms = SmsMessage.createFromPdu( message );
        String telefone = sms.getDisplayOriginatingAddress();

        AlunoDAO dao = new AlunoDAO(context);

        if (dao.isAluno(telefone)) {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();

            Toast.makeText(context, "SMS recebido do aluno de telefone: "+
                    telefone, Toast.LENGTH_LONG).show();
        }

        dao.close();
    }

}
