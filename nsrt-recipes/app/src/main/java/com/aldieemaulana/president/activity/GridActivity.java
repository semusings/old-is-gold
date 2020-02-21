package com.aldieemaulana.president.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

import com.aldieemaulana.president.R;
import com.aldieemaulana.president.adapter.GridAdapter;
import com.aldieemaulana.president.api.AppOptional;
import com.aldieemaulana.president.api.DataSourcePriceApi;
import com.aldieemaulana.president.model.Price;
import com.aldieemaulana.president.response.PriceResponse;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@SuppressWarnings("FieldCanBeLocal")
public class GridActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.searchPrice)
    EditText searchPrice;
    private List<Price> priceList;
    private GridAdapter presidentGridAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ButterKnife.bind(this);
        layoutManager = new GridLayoutManager(this, 2);

        swipeContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPrice();
            }
        });

        priceList = new LinkedList<>();
        presidentGridAdapter = new GridAdapter(this, priceList);

        getPrice();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHorizontalScrollBarEnabled(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(16), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(presidentGridAdapter);
    }

    private void getPrice() {
        swipeContainer.setRefreshing(true);
        AppOptional<PriceResponse> call = new DataSourcePriceApi(this).getPrices();

        if (call.isSuccessful()) {
            presidentGridAdapter.clear();
            presidentGridAdapter.addAll(call.body().getPrice());

            if (!call.body().getTotal().equals(0)) {

                recyclerView.getViewTreeObserver().addOnPreDrawListener(
                        new ViewTreeObserver.OnPreDrawListener() {
                            @Override
                            public boolean onPreDraw() {
                                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                                    View v = recyclerView.getChildAt(i);
                                    v.setAlpha(0.0f);
                                    v.animate().alpha(1.0f)
                                            .setDuration(300)
                                            .setStartDelay(i * 50)
                                            .start();
                                }

                                return true;
                            }
                        });

            }
            Log.i("nsrt", "nsrt result: " + call.body().getTotal());
            swipeContainer.setRefreshing(false);
        } else {
            presidentGridAdapter.notifyDataSetChanged();

            Log.i("nsrt", "nsrt error: ");

            swipeContainer.setRefreshing(false);
        }

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @OnClick(R.id.buttonLeft)
    protected void backToMain(View view) {
        super.onBackPressed();
    }

    @OnTextChanged(R.id.searchPrice)
    protected void applySearch(CharSequence view) {

        List<Price> filterPrices = new LinkedList<>();
        AppOptional<PriceResponse> prices = new DataSourcePriceApi(getBaseContext()).getPrices();
        if (prices.isSuccessful()) {

            List<Price> priceList = prices.body().getPrice();

            for (int i = 0; i < priceList.size(); i++) {
                Price price = priceList.get(i);
                if (price.getName().toUpperCase().contains(view.toString().toUpperCase())) {
                    filterPrices.add(price);
                }
            }

        }
        presidentGridAdapter.clear();
        presidentGridAdapter.addAll(filterPrices);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = 0;
                }
                outRect.bottom = 0; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = 0; // item top
                }
            }
        }
    }

}
