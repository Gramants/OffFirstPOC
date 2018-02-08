package com.example.offline.view;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.offline.R;
import com.example.offline.domain.services.jobobservers.SyncEventsByCatLifecycleObserver;
import com.example.offline.domain.services.jobobservers.SyncHomePageLifecycleObserver;
import com.example.offline.model.Category;
import com.example.offline.model.Event;
import com.example.offline.model.HomePage;
import com.example.offline.model.ModelConstants;
import com.example.offline.presentation.ExpoViewModel;
import com.example.offline.presentation.ExpoViewModelFactory;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.Orientation;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;


public class ExpoActivity extends AppCompatActivity implements DiscreteScrollView.ScrollStateChangeListener<CatAdapter.ViewHolder>,
        DiscreteScrollView.OnItemChangedListener<CatAdapter.ViewHolder>, View.OnClickListener {


    @Inject
    ExpoViewModelFactory viewModelFactory;


    @Inject
    SyncEventsByCatLifecycleObserver syncEventsByCatLifecycleObserver;

    @Inject
    SyncHomePageLifecycleObserver syncHomePageLifecycleObserver;


    @BindView(R.id.comments_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.item_picker)
    DiscreteScrollView itemPicker;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    private EventsListAdapter recyclerViewAdapter;

    private ExpoViewModel viewModel;
    private int mCategoryId;
    private LifecycleRegistry registry = new LifecycleRegistry(this);
    private ArrayList homeIndex;

    @Override
    public LifecycleRegistry getLifecycle() {
        return registry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity);

        ButterKnife.bind(this);

        initRecyclerView();

        getLifecycle().addObserver(syncEventsByCatLifecycleObserver);
        getLifecycle().addObserver(syncHomePageLifecycleObserver);


        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExpoViewModel.class);


        // launch remote job to load homepage in background
        viewModel.getRemoteHomePage();


        // sobserve livedata
        viewModel.homePage().observe(this, homePage -> setAvailableCat(homePage));

        // if to remote sync all the categories is driven by the flag true or false coming from the platform
        // passing the saved timestamp as parameter
        // observing the results of the category items
        //viewModel.filteredEventsByCat().observe(this, updateCommentList->updateItems(updateCommentList));

        itemPicker.addOnItemChangedListener(this);
        itemPicker.addScrollStateChangeListener(this);
        //viewModel.loadEventsByCat(6);
        itemPicker.setItemTransitionTimeMillis(ModelConstants.KEY_TRANSITION_TIME);
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
        itemPicker = (DiscreteScrollView) findViewById(R.id.item_picker);
        itemPicker.setOrientation(Orientation.HORIZONTAL);

        setupWindowAnimations();
        viewModel.loadHomePageFromDb();
    }

    private void updateItems(List<Event> updateEventList) {
        if (updateEventList != null) {
            if (updateEventList.size() > 0) {
                recyclerViewAdapter.updateCommentList(updateEventList);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                // if connection disappeared while loading category blocks, I will force the job to download now is it is empty, forcing loading of ALL elements
                viewModel.getRemoteEventsByCat(mCategoryId, true);
            }
        }


    }

    private void setupWindowAnimations() {
        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setAvailableCat(HomePage homePage) {

        if (homePage != null) {

            String[] catToUpdateInBackGround = homePage.catToShow.split(",");
            int[] catToUpdateInBackGroundInt = new int[catToUpdateInBackGround.length];

            homeIndex = new ArrayList<Category>();
            for (int i = 0; i < catToUpdateInBackGround.length; i++) {
                int catIndex = Integer.parseInt(catToUpdateInBackGround[i]);
                catToUpdateInBackGroundInt[i] = catIndex;
                homeIndex.add(new Category(catIndex, ModelConstants.getLabel().get(catIndex), ModelConstants.getLogo().get(catIndex)));
            }


            if (viewModel.needToSync()) {
                viewModel.setAllBackGroundJobs(catToUpdateInBackGroundInt);
                viewModel.setNeedToSync(false);
            }
        } else {
            // launch remote job to load homepage in background
            viewModel.getRemoteHomePage();

        }

        itemPicker.setAdapter((new CatAdapter(homeIndex)));

        // start with element 1
        viewModel.loadEventsByCatFromDb(((Category) homeIndex.get(0)).getCatId());
        viewModel.filteredEventsByCat().observe(this, updateCommentList -> updateItems(updateCommentList));

    }


    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        recyclerViewAdapter = new EventsListAdapter(new ArrayList<>(), new EventsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event item) {
                Intent intent = new Intent(ExpoActivity.this, ExpoDetailActivity.class);
                Bundle b = new Bundle();
                b.putInt("id", item.getId());
                intent.putExtras(b);
                transitionTo(intent);
            }
        });

        recyclerView.setAdapter(recyclerViewAdapter);


    }

    @SuppressWarnings("unchecked")
    void transitionTo(Intent i) {
        final android.support.v4.util.Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(i, transitionActivityOptions.toBundle());
    }

    private Visibility buildEnterTransition() {
        Fade enterTransition = new Fade();
        enterTransition.setDuration(1200);
        return enterTransition;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCurrentItemChanged(@Nullable CatAdapter.ViewHolder viewHolder, int adapterPosition) {
        viewHolder.showText();

    }

    @Override
    public void onScrollStart(@NonNull CatAdapter.ViewHolder currentItemHolder, int adapterPosition) {
        currentItemHolder.hideText();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerViewAdapter.clearItems();
    }

    @Override
    public void onScrollEnd(@NonNull CatAdapter.ViewHolder viewHolder, int adapterPosition) {
        if (viewHolder != null) {
            viewModel.loadEventsByCatFromDb(((Category) homeIndex.get(adapterPosition)).getCatId());
            this.mCategoryId = ((Category) homeIndex.get(adapterPosition)).getCatId();
        }
    }

    @Override
    public void onScroll(
            float position,
            int currentIndex, int newIndex,
            @Nullable CatAdapter.ViewHolder currentHolder,
            @Nullable CatAdapter.ViewHolder newHolder) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
