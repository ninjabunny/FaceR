package com.example.facer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;

import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private static Uri mImageCaptureUri1;
	private Bitmap imgBitmap;
	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// force landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		// fire camera
		iv = (ImageView) findViewById(R.id.imageView1);
	}

	// private void dispatchTakePictureIntent() {
	// Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	// if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	// startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	// }
	//
	// }

	public void getPix(View v) {
		if (imgBitmap != null) {
			final FaceGrabber fg = new FaceGrabber(imgBitmap);

			// get # faces
			TextView tv = (TextView) findViewById(R.id.faceNums);
			tv.setText("faces=" + fg.getFaceNum());

			// test
			iv.setImageBitmap(fg.getFaceBitmap()[0]);

			// face rec image

			// grab faces
			// scale
			// black and white
			// normalize

			// display images
			// iv.setImageBitmap(imgBitmap);
		} else {
			Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG)
					.show();

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			// Bundle extras = data.getExtras();
			// imgBitmap = (Bitmap) extras.get("data");

			try {
				imgBitmap = MediaStore.Images.Media.getBitmap(
						this.getContentResolver(), mImageCaptureUri1);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void takePic(View v) {
		// dispatchTakePictureIntent();
		openCamera();
	}

	public void openCamera() {

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		mImageCaptureUri1 = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), "tmp_avatar_"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg"));

		cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri1);
		cameraIntent.putExtra("return-data", true);
		startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
	}

}
