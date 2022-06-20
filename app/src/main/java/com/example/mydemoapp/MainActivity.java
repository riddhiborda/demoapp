package com.example.mydemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydemoapp.util.retrofit.APiService;
import com.example.mydemoapp.util.retrofit.RetrofitClient;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    MyViewModel myViewModel;
    TextView txtTitle;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setOnClickListener(v -> {
//            myViewModel.data.postValue("hello click button ");
//            payment();
            rxJavaApiCalling();
        });


        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        myViewModel.data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("TAG", "onChanged: message " + s);
            }
        });

    }

    private void rxJavaApiCalling() {
        APiService service=RetrofitClient.getClient().create(APiService.class);

        disposable.add(service.doGetListResources().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> {
                    Log.e("TAG", "rxJavaApiCalling: "+"do subscribee" );
                })
                .doOnTerminate(() -> {
                    Log.e("TAG", "rxJavaApiCalling: "+"do on  terminate" );
                })
                .subscribe(getNotes -> {
                    Log.e("TAG", "rxJavaApiCalling: "+"subscribee" );
                }, Throwable::printStackTrace));

    }

    private void payment() {
        String samount = "100";

        // rounding off the amount.
        int amount = Math.round(Float.parseFloat(samount) * 100);

        // initialize Razorpay account.
        Checkout checkout = new Checkout();

        // set your id as below
        checkout.setKeyID("rzp_test_iQsDrFf5zjUCEz");

        // set image
        checkout.setImage(R.drawable.common_full_open_on_phone);

        // initialize json object
        JSONObject object = new JSONObject();
        try {
            // to put name
            object.put("name", "riddhi");

            // put description
            object.put("description", "Test payment");

            // to set theme color
            object.put("theme.color", "");

            // put the currency
            object.put("currency", "INR");

            // put amount
            object.put("amount", amount);

            // put mobile number
            object.put("prefill.contact", "6354247752");

            // put email
            object.put("prefill.email", "chaitanyamunje@gmail.com");

            // open razorpay to checkout activity
            checkout.open(MainActivity.this, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment is failee : " + s, Toast.LENGTH_SHORT).show();
    }
}