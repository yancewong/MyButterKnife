package com.yw.injecttest;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.yw.library.BindView;
import com.yw.library.ContentView;
import com.yw.library.OnClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @BindView(R.id.tv)
    private TextView tv;
    @BindView(R.id.btn)
    private Button btn;

    @OnClick({R.id.tv,R.id.btn})
    public void onClick(View v){
        if(v.getId() == R.id.tv){
            Toast.makeText(this,"tv",Toast.LENGTH_LONG).show();
        }else if(v.getId() == R.id.btn){
            Toast.makeText(this,"btn",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show();
        tv.setText("henghenghengheng");
        btn.setText("i am btn");
    }
}
