package com.example.projecthairgate;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class BookingActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        mWebView = findViewById(R.id.webview);
        webSetting();
    }

    private void webSetting() {

        String siteUrl = "https://boka.itsperfect.se/27022812";
        mWebView.loadUrl(siteUrl);
        mWebView.getSettings().setAllowFileAccess(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mWebView.setWebViewClient(new mWebViewClient());
    }

    private class mWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!Uri.parse(url).getHost().equals("https://boka.itsperfect.se/27022812")) {
                return false;
            }
            view.loadUrl(url);
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            Toast.makeText(BookingActivity.this, "Loading page...", Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onPageFinished(WebView view, String url) {

            Toast.makeText(BookingActivity.this, "Loading finished", Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);

        }
    }
}
