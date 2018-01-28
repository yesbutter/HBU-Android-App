**- 打电话测试：用一个按钮来实现跳转到拨打号码页面。**

```Java
ButtonCall=(Button)findViewById(R.id.iCall);//找到按钮
ButtonCall.setOnClickListener(new View.OnClickListener(){//设置监听事件
@Override
	public void onClick(View v){//点击事件
	Uri uri = Uri.parse("tel:10086");
	Intent intent = new Intent(Intent.ACTION_DIAL, uri);//获取拨打电话的Intent
	startActivity(intent);//实施打电话
     }
});

```
**- 发短信测试：用一个按钮来实现跳转到发短信页面。**

```Java
        ButtonMessage=(Button)findViewById(R.id.iMessage);
        ButtonMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("smsto:10086");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);//获取发短信的事件
                intent.putExtra("sms_body", "Hello");
                startActivity(intent);
```

**-跳转网页测试：用一个按钮来实现跳转到制定网页。**
    

```Java
        ButtonBrowser=(Button)findViewById(R.id.iBrowser);
        ButtonBrowser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("http://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
```

**-打开相机测试：用一个按钮来实现打开相机。**

```
        ButtonPhoto=(Button)findViewById(R.id.iPhoto);
        ButtonPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);             
		startActivityForResult(intent, 0);
            }
        });
```

**-打开设置测试：用一个按钮来实现打开设置。**

```
        ButtonSetting=(Button)findViewById(R.id.iSetting);
        ButtonSetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                 // 进入无线网络设置界面（其它可以举一反三）
                Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                startActivityForResult(intent, 0);
            }
        });
```

**-跳转到桌面测试：用一个按钮来实现跳转到桌面。**

```
        ButtonDeskTop=(Button)findViewById(R.id.iDesktop);
        ButtonDeskTop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);// 添加Action属性
                intent.addCategory(Intent.CATEGORY_HOME);// 添加Category属性
                startActivity(intent);// 启动Activity
            }
        });
```

## 同样也可以跳转到其他的页面

**-跳转到自己写的页面测试：用一个按钮来实现跳转到自己的页面。**
```
        ButtonOtherActivity=(Button)findViewById(R.id.iOtherActivity);
        ButtonOtherActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //注意一定要把Activity注册到manifest文件
                Intent intent = new Intent( MainActivity.this,SecondActivity.class);//SecondActivity.class是需要写出对应的Java文件来进行跳转
                startActivity(intent);
            }
        });
```

```
//测试的SecondActivity
public class SecondActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second);

    }
}

```
```xml

<?xml version="1.0" encoding="utf-8"?>
<!--对应的secondactivity的xml布局-->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:orientation="vertical" android:layout_width="match_parent"
android:layout_height="match_parent">
<Button
    android:text="我是第二个Activity ！"

    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    />
</LinearLayout>

```

**-使用Intent向下一个活动传递参数**
```
 Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("University","HebeiUniversity");//下一个Acticity是FourthActivity,University是一个名字，内容是后面
                bundle.putString("College","Cyberspace Security & Computer");//和上面一样
                intent.putExtras(bundle);//把bundle对象传递下一个活动，下一个活动接收到对象，通过对象获取传递的字符。
                intent.setClass(MainActivity.this,FourthActivity.class);
                startActivity(intent);//开启活动
		
		//下一个活动接收传递进来的数据
		Bundle bundle = getIntent().getExtras();//获得上一个的bundle对象
                String university = bundle.getString("University");//获取传递进来的参数，Universtity相当于键值对
                String college = bundle.getString("College");//同上
               	textView.setText(university + ":" +college);//当然是显示它咯！

```

**-使用Intent向上一个活动传递参数**
```
		
		Intent intent = new Intent();//上一个Activity
		Bundle bundle = new Bundle();
		bundle.putString("参数1-2","参数值1-2");//键值对
		intent.putExtras(bundle);//向下传递bundle对象
		intent.setClass(MainActivity.this,FifthActivity.class);
		startActivityForResult(intent,0);// 0 用于识别第二个页面返回值,
		
		
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {//上一个Activity获得下一个传递的参数
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
		//通过不同的request code来做不同的事情
		    case 0:
			if(resultCode == RESULT_OK )
			{
			    Bundle b = data.getExtras();//获得下一个获得的传递的bundle对象
			    String string = b.getString("参数2-1");//获取数据
			    textView.setText(string);//显示
			}
			break;
		    default:
			break;
		}
		
		
		Intent intent = new Intent();//下一个活动，先上一个活动传递数据
                Bundle bundle = new Bundle();
                bundle.putString("参数2-1","参数值2-1");
                intent.putExtras(bundle);//把bundle对象向上传递
                intent.setClass(FifthActivity.this,MainActivity.class);
                setResult(RESULT_OK,intent);//只有相应了这一个才会去执行onActivityResult的内容
                finish();//结束当前活动
```
