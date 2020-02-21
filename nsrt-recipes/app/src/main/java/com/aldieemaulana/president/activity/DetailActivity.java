package com.aldieemaulana.president.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.aldieemaulana.president.R;
import com.aldieemaulana.president.api.DataSourcePriceApi;
import com.aldieemaulana.president.model.Price;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.sp)
    TextView sp;
    @BindView(R.id.cp)
    TextView cp;

    private LinearLayoutManager layoutManager;
    private Intent data;
    private Price price;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        data = getIntent();
        price = new Price();

        price.setId(data.getIntExtra("id", -1));
        price.setName(data.getStringExtra("name"));
        price.setCp(data.getStringExtra("cp"));
        price.setSp(data.getStringExtra("sp"));

        title.setText(data.getStringExtra("title"));
        itemName.setText(price.getName());
        cp.setText("Rs. " + price.getCp());
        sp.setText("Rs. " + price.getSp());

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

    }

    @OnClick(R.id.buttonLeft)
    protected void backToMain(View view) {
        super.onBackPressed();

        Intent intent = new Intent(DetailActivity.this, GridActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.delete)
    protected void delete(View view) {
        new DataSourcePriceApi(this).delete(price);

        super.onBackPressed();

        Intent intent = new Intent(this, GridActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.edit)
    protected void edit(View view) {

        super.onBackPressed();

        Intent intent = new Intent(this, ListActivity.class);

        intent.putExtra("id", price.getId());
        intent.putExtra("name", price.getName());
        intent.putExtra("sp", price.getSp());
        intent.putExtra("cp", price.getCp());

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
