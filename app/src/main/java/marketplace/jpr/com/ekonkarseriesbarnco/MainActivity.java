package marketplace.jpr.com.ekonkarseriesbarnco;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;



        public class MainActivity extends AppCompatActivity {

            WebView mWebView;
            ProgressBar progressBar;

            @SuppressLint("SetJavaScriptEnabled")
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                progressBar = (ProgressBar) findViewById(R.id.progressbar);
                mWebView = (WebView) findViewById(R.id.webView);

                progressBar.setVisibility(View.VISIBLE);
                mWebView.setWebViewClient(new Browser_home());
                mWebView.setWebChromeClient(new MyChrome());
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);


               /* mWebView.setVerticalScrollBarEnabled(true);
                mWebView.setHorizontalScrollBarEnabled(true);*/




                webSettings.setAllowFileAccess(true);
                webSettings.setAppCacheEnabled(true);
                loadWebsite();

            }

            private void loadWebsite() {
                ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    mWebView.loadUrl("http://ekonkarseries.org/");
                } else {
                    mWebView.setVisibility(View.GONE);
                }
            }

            class Browser_home extends WebViewClient {

                Browser_home() {
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);

                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    setTitle(view.getTitle());
                    progressBar.setVisibility(View.GONE);
                    super.onPageFinished(view, url);

                }
            }

            private class MyChrome extends WebChromeClient {

                private View mCustomView;
                private WebChromeClient.CustomViewCallback mCustomViewCallback;
                protected FrameLayout mFullscreenContainer;
                private int mOriginalOrientation;
                private int mOriginalSystemUiVisibility;

                MyChrome() {}

                public Bitmap getDefaultVideoPoster()
                {
                    if (mCustomView == null) {
                        return null;
                    }
                    return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
                }

                public void onHideCustomView()
                {
                    ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
                    this.mCustomView = null;
                    getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
                    setRequestedOrientation(this.mOriginalOrientation);
                    this.mCustomViewCallback.onCustomViewHidden();
                    this.mCustomViewCallback = null;
                }

                public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
                {
                    if (this.mCustomView != null)
                    {
                        onHideCustomView();
                        return;
                    }
                    this.mCustomView = paramView;
                    this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                    this.mOriginalOrientation = getRequestedOrientation();
                    this.mCustomViewCallback = paramCustomViewCallback;
                    ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
                    getWindow().getDecorView().setSystemUiVisibility(3846);
                }
            }
        }







