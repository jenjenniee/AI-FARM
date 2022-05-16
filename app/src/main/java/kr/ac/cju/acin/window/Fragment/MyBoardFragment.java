package kr.ac.cju.acin.window.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.ac.cju.acin.window.MainActivity;
import kr.ac.cju.acin.window.R;
import kr.ac.cju.acin.window.Request.RequestHttp;
import kr.ac.cju.acin.window.onBackPressedListener;

public class MyBoardFragment extends Fragment implements onBackPressedListener {

    private WebView webView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment, container, false);
        SharedPreferences spLogin = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String url = RequestHttp.getHost()+"myBoard/1/?token="+spLogin.getString("token","");
        //웹뷰 세팅
        webView    = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new AndroidBridge(), "android");
        return view;
    }

    @Override
    public void onBackPressed() {
        getActivity().finish();
    }

    class AndroidBridge{
        /*
            상세
         */
        @JavascriptInterface
        public void moveReadBoard(String id){
            Intent intent =new Intent(getContext(), MainActivity.class);
            intent.putExtra("fragment_name","ReadBoardFragment");
            intent.putExtra("id",id);
            startActivity(intent);
        }

    }
    @Override
    public void onStart() {
        super.onStart();
        webView.reload();
    }
}
