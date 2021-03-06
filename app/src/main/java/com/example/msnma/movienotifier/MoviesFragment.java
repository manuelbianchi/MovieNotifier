package com.example.msnma.movienotifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msnma.movienotifier.adapter.MoviesAdapter;
import com.example.msnma.movienotifier.callback.MoviesCallback;
import com.example.msnma.movienotifier.model.Movie;
import com.example.msnma.movienotifier.util.MoviesUtil;
import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;

//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;

public class MoviesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        RecyclerItemClickSupport.OnItemClickListener {
    private static final String ARG_FRAG_TYPE = "fragType";

    public enum Type {
        NOTIFY,
        SUGGESTED,
        WATCHED
    }

    @State
    ArrayList<Movie> movies;
    @State
    Type fragType;
    TextView messageIfEmpty;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshView;
    @BindView(R.id.movies)
    RecyclerView moviesView;

    public static MoviesFragment newInstance(Type fragType) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FRAG_TYPE, fragType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        if (getArguments() != null) {
            fragType = (Type) getArguments().getSerializable(ARG_FRAG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_list, container, false);
        ButterKnife.bind(this, rootView);

        try {
            init();
//            updateView(rootView);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
//         EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
//         EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onRefresh() {
        movies = null;
        try {
            updateMovies();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        showMovieAtPosition(position);
    }

    //    @Subscribe(sticky = true)
    //    public void onEvent(UpdateFavoritesEvent event) {
    //        if (fragType == Type.FAVORITES) {
    //            EventBus.getDefault().removeStickyEvent(UpdateFavoritesEvent.class);
    //            onRefresh();
    //       }
    //    }

    //   @Subscribe(sticky = true)
    //   public void onEvent(TwoPaneEvent event) {
    //       twoPane = event.twoPane;
    //   }

    @Override
    protected void init() throws ParseException {
        RecyclerItemClickSupport.addTo(moviesView)
                .setOnItemClickListener(this);
        moviesView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        moviesView.setHasFixedSize(true);
        refreshView.setOnRefreshListener(this);
        updateMovies();
    }

    private void updateMovies() throws ParseException {
        if (movies == null) {
            MoviesCallback callback = new MoviesCallback() {
                @Override
                public void success(List<Movie> result) {
                    movies = new ArrayList<>(result);
                    if (moviesView != null) {
                        moviesView.setAdapter(new MoviesAdapter(getContext(), movies));
                    }
                    refreshView.setRefreshing(false);
                }

                @Override
                public void error(Exception error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                    refreshView.setRefreshing(false);
                }
            };
            switch (fragType) {
                case NOTIFY:
                    MoviesUtil.getNotifyMeMovies(getActivity(), callback, this);
                    break;
                case SUGGESTED:
                    MoviesUtil.getSuggestedMovies(getActivity(), callback, this);
                    break;
                case WATCHED:
                    MoviesUtil.getWatchedMovies(getActivity(), callback, this);
                    break;
            }
        } else if (moviesView != null) {
            moviesView.setAdapter(new MoviesAdapter(getContext(), movies));
            refreshView.setRefreshing(false);
        }
    }

    private void showMovieAtPosition(int position) {
//        if (movies != null && position <= movies.size() - 1) {
//            Movie movie = movies.get(position);
//            EventBus.getDefault().postSticky(new ShowMovieEvent(movie));
//            if (twoPane) {
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.movie_detail, new MovieFragment())
        //                       .commit();
        //           } else {
        //               startActivity(new Intent(getContext(), MovieActivity.class));
        //           }
        //       }
    }

//    public void updateView(View rootView){
//        if(movies == null || movies.isEmpty()){
//            switch (fragType) {
//                case NOTIFY:
//                    moviesView = (RecyclerView)rootView.findViewById(R.id.movies);
//                    messageIfEmpty = (TextView)rootView.findViewById(R.id.empty);
//                    messageIfEmpty.setText("Search a movie or go on the suggested list for add a movie you want to be notify about!");
//                    moviesView.setVisibility(View.GONE);
//                    messageIfEmpty.setVisibility(View.VISIBLE);
//                    break;
//                case SUGGESTED:
//                     moviesView = (RecyclerView)rootView.findViewById(R.id.movies);
//                     messageIfEmpty = (TextView)rootView.findViewById(R.id.empty);
//                     messageIfEmpty.setText("No connection, please try again");
    //                    moviesView.setVisibility(View.GONE);
//                     messageIfEmpty.setVisibility(View.VISIBLE);
//                     break;
//                case WATCHED:
//                    moviesView = (RecyclerView)rootView.findViewById(R.id.movies);
//                    messageIfEmpty = (TextView)rootView.findViewById(R.id.empty);
//                    messageIfEmpty.setText("Add here movies you have watched!");
//                    moviesView.setVisibility(View.GONE);
//                    messageIfEmpty.setVisibility(View.VISIBLE);
//                    break;
//            }
//        }
//    }
}
