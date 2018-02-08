package com.example.offline.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.offline.R;
import com.example.offline.model.Event;
import com.example.offline.model.ModelConstants;
import com.example.offline.presentation.ExpoDetailViewModel;
import com.example.offline.presentation.ExpoDetailViewModelFactory;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.example.offline.BuildConfig.API_BASE_URL_IMAGES;


public class ExpoDetailActivity extends AppCompatActivity {

    @Inject
    ExpoDetailViewModelFactory viewModelFactory;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.title)
    TextView eventTitle;

    @BindView(R.id.location)
    TextView eventLocation;

    @BindView(R.id.desc)
    WebView eventDesc;

    @BindView(R.id.immagine)
    ImageView immaginefiera;


    private ExpoDetailViewModel viewModel;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        Bundle b = getIntent().getExtras();


        int detailId = -1; // or other values
        if (b != null) {
            detailId = b.getInt("id");
        }


        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExpoDetailViewModel.class);
        viewModel.eventDetailPage().observe(this, detail -> drawUI(detail));
        viewModel.loadLocalEventById(detailId);


        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimaryDark));

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void drawUI(Event detail) {
        collapsingToolbarLayout.setTitle(((HashMap) ModelConstants.getLabel()).get(detail.getCat()).toString());
        eventTitle.setText(detail.getTitolo());
        eventLocation.setText(detail.getLocation());
        eventDesc.loadData(detail.getDescrizione(), "text/html; charset=utf-8", "UTF-8");


        Glide.with(this)
                .load(API_BASE_URL_IMAGES + detail.getFoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(immaginefiera);
    }


}
