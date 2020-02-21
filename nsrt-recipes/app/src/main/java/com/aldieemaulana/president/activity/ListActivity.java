package com.aldieemaulana.president.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aldieemaulana.president.R;
import com.aldieemaulana.president.api.AppOptional;
import com.aldieemaulana.president.api.DataSourcePriceApi;
import com.aldieemaulana.president.api.PriceApi;
import com.aldieemaulana.president.model.Price;
import com.aldieemaulana.president.response.PriceResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.itemName)
    EditText itemName;
    @BindView(R.id.sp)
    EditText sp;
    @BindView(R.id.cp)
    EditText cp;
    @BindView(R.id.addPrice)
    Button addPrice;

    private Intent data;
    private Price price;
    private LinearLayoutManager layoutManager;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        swipeContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

        data = getIntent();
        price = new Price();

        price.setId(data.getIntExtra("id", -1));
        price.setName(data.getStringExtra("name"));
        price.setCp(data.getStringExtra("cp"));
        price.setSp(data.getStringExtra("sp"));

        itemName.setText(price.getName());
        sp.setText(price.getSp());
        cp.setText(price.getCp());

        if (!isNew()) {
            addPrice.setText("Update");
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHorizontalScrollBarEnabled(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @OnClick(R.id.buttonLeft)
    protected void backToMain(View view) {
        super.onBackPressed();
    }


    @OnClick(R.id.addPrice)
    protected void addPrice(View view) {
        PriceApi priceApi = new DataSourcePriceApi(this);

        Price price = new Price();
        price.setName(itemName.getText().toString());
        price.setSp(sp.getText().toString());
        price.setCp(cp.getText().toString());

        if (isNew()) {

            AppOptional<PriceResponse> result = priceApi.create(price);
            if (result.isSuccessful()) {

                this.clearFields();

            }
        } else {
            price.setId(this.price.getId());
            priceApi.update(price);
        }

        Intent intent = new Intent(ListActivity.this, GridActivity.class);
        startActivity(intent);
    }

    private boolean isNew() {
        return this.price.getId() == -1;
    }

    private void clearFields() {
        itemName.setText(null);
        sp.setText(null);
        cp.setText(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
