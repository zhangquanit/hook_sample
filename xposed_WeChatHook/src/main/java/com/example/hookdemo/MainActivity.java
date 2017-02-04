package com.example.hookdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.hookdemo.util.DaoHandler;

public class MainActivity extends Activity implements OnClickListener
{
	private TextView tvDiceNum;
	private TextView tvMorraNum;
	private Button btSetDice;
	private Button btSetMorra;
	private int diceNum = 6; // 默认点数6
	private int morraNum = 0; // 默认剪刀

	String[] morraStr = { "剪刀", "石头", "布" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setView();
		setListener();
		setValue();
	}

	private void setValue()
	{
		// TODO Auto-generated method stub
		Cursor cursor = DaoHandler.getInstance().query(null, null, null, null);
		if (cursor != null)
		{
			while (cursor.moveToNext())
			{
				diceNum = cursor.getInt(cursor.getColumnIndex("dice_num"));
				tvDiceNum.setText("点数" + (diceNum + 1));
			}
		}
	}

	private void setView()
	{
		tvDiceNum = (TextView) findViewById(R.id.tv_dice_num);
		btSetDice = (Button) findViewById(R.id.bt_set_dice);

		tvMorraNum = (TextView) findViewById(R.id.tv_morra_num);
		btSetMorra = (Button) findViewById(R.id.bt_set_morra);
	}

	private void setListener()
	{
		btSetDice.setOnClickListener(this);
		btSetMorra.setOnClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.bt_set_dice: // 设置骰子点数
			showSetDiceDialog();
			break;

		case R.id.bt_set_morra: // 设置猜拳
			showSetMorraDialog();
			break;

		}
	}

	private void showSetMorraDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("请设置猜拳数");

		/**
		 * 第一个参数指定我们要显示的一组下拉单选框的数据集合 第二个参数代表索引，指定默认哪一个单选框被勾选上
		 * 第三个参数给每一个单选项绑定一个监听器
		 */
		builder.setSingleChoiceItems(morraStr, morraNum, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				morraNum = which;
			}
		});

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				saveMorraNum();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				morraNum = 0;
			}
		});

		builder.show();
	}

	private void showSetDiceDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("请选择骰子点数");
		final String[] diceStr = { "点数1", "点数2", "点数3", "点数4", "点数5", "点数6" };

		/**
		 * 第一个参数指定我们要显示的一组下拉单选框的数据集合 第二个参数代表索引，指定默认哪一个单选框被勾选上
		 * 第三个参数给每一个单选项绑定一个监听器
		 */
		builder.setSingleChoiceItems(diceStr, diceNum, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				diceNum = which;
			}
		});

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				saveDiceNum();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				diceNum = 6;
			}
		});

		builder.show();
	}

	protected void saveDiceNum()
	{
		ContentValues values = new ContentValues();
		values.put("dice_num", diceNum);
		DaoHandler.getInstance().add(values);
		tvDiceNum.setText("点数" + (diceNum + 1));
	}

	protected void saveMorraNum()
	{
		ContentValues values = new ContentValues();
		values.put("morra_num", morraNum);
		DaoHandler.getInstance().add(values);
		tvMorraNum.setText(morraStr[morraNum]);
	}
}
