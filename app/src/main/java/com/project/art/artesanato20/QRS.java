package com.project.art.artesanato20;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.project.art.artesanato20.impl.ArtesaoFirebaseManager;
import com.project.art.artesanato20.models.Artesao;

public class QRS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrs);
        init();
    }

    public void init() {
        Activity activity = this;
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    public void retHome() {
        Intent intent = new Intent(QRS.this, MainActivity.class);
        intent.putExtra("goto", "qrScan");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "QR Scan cancelado", Toast.LENGTH_LONG).show();
                retHome();
            } else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                Artesao ARTESAO = ArtesaoFirebaseManager.getInstance().getArtesaoById(result.getContents());
                if (ARTESAO.getId() == null) {
                    Toast.makeText(this, "Este QR Code não pertence a um artesão", Toast.LENGTH_LONG).show();
                    retHome();
                } else {
                    Intent intent = new Intent(QRS.this, PerfilArtesao.class);
                    intent.putExtra("id", result.getContents().toString());
                    startActivity(intent);
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
