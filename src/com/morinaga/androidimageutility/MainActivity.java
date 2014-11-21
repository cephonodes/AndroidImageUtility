package com.morinaga.androidimageutility;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
  private static final int REQUEST_GALLERY = 0;   // インテントが返って来た時の判別コード
  private static final int REQUEST_CAMERA = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // ギャラリーから写真を選択してアップロードする
    ((Button)findViewById(R.id.up_gallery)).setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
          intent1.setType("image/*");
          intent1.addCategory(Intent.CATEGORY_OPENABLE);
          startActivityForResult(intent1, REQUEST_GALLERY);   // ギャラリーへインテントを飛ばす
        }
      });

    // 写真を撮影してアップロードする
    ((Button)findViewById(R.id.up_camera)).setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          startActivityForResult(intent2, REQUEST_CAMERA);   // カメラへインテントを飛ばす
        }
      });
  }

  // インテント先から結果が返ってきたときの処理
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(resultCode == RESULT_OK) {
      ImageView imgView = (ImageView)findViewById(R.id.image_selected);
      if(requestCode == REQUEST_GALLERY) {  // ギャラリーで画像が選択された
        try {
          InputStream in = getContentResolver().openInputStream(data.getData());
          Bitmap img = BitmapFactory.decodeStream(in);
          in.close();
          imgView.setImageBitmap(img);
        } catch(Exception e) {

        }
      } else if(requestCode == REQUEST_CAMERA) {  // カメラで写真が撮影された
        Bitmap img = (Bitmap)data.getExtras().get("data");  // カメラで撮影された写真を取得
        imgView.setImageBitmap(img);
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
