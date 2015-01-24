package cn.androiddevelop.cycleviewpager.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import cn.androiddevelop.cycleviewpager.R;

/**
 * 主页面
 * 
 * @author YD
 *
 */
public class Main extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cycleviewpager_main);
		findViewById(R.id.noCycleBtn).setOnClickListener(this);
		findViewById(R.id.cycleBtn).setOnClickListener(this);
		findViewById(R.id.wheelCycleBtn).setOnClickListener(this);
		findViewById(R.id.eventCycleBtn).setOnClickListener(this);
		findViewById(R.id.fixedCycleBtn).setOnClickListener(this);
		findViewById(R.id.nestedCycleBtn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		int id = v.getId();
		if (id == R.id.noCycleBtn) {
			intent = new Intent(this, NoCycleTextView.class);
			startActivity(intent);
			return;
		}
		if (id == R.id.cycleBtn) {
			intent = new Intent(this, CycleTextView.class);
			startActivity(intent);
			return;
		}

		if (id == R.id.wheelCycleBtn) {
			intent = new Intent(this, WheelCycleTextView.class);
			startActivity(intent);
			return;
		}

		if (id == R.id.eventCycleBtn) {
			intent = new Intent(this, EventCycleTextView.class);
			startActivity(intent);
			return;
		}
		if (id == R.id.fixedCycleBtn) {
			intent = new Intent(this, FixedCycleTextView.class);
			startActivity(intent);
			return;
		}
		if (id == R.id.nestedCycleBtn) {
			intent = new Intent(this, NestedCycleTextView.class);
			startActivity(intent);
			return;
		}
	}
}