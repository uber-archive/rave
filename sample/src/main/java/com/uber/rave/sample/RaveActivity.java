package com.uber.rave.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.uber.rave.RaveException;
import com.uber.rave.sample.github.GitHubModule;
import com.uber.rave.sample.github.GitHubService;
import com.uber.rave.sample.github.model.Repo;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaveActivity extends Activity {

    @Inject GitHubService gitHubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rave);

        ButterKnife.bind(this);
        DaggerRaveActivity_Component.create()
                .inject(this);
    }

    @OnClick(R.id.make_request)
    void callGitHub() {
        gitHubService.listRepos("naturalwarren", "rave")
                .enqueue(new Callback<Repo>() {
                    @Override
                    public void onResponse(Call<Repo> call, Response<Repo> response) {
                        makeToast("Call passed Rave validation!");
                    }

                    @Override
                    public void onFailure(Call<Repo> call, Throwable t) {
                        Throwable cause = t.getCause();
                        if (cause instanceof RaveException) {
                            makeToast("Call did not pass Rave validation: " + cause.getMessage());
                        } else if (cause != null) {
                            makeToast("Call failed for unknown reason: " + cause.getMessage());
                        }
                    }
                });
    }

    private void makeToast(String text) {
        Toast.makeText(RaveActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @RaveActivityScope
    @dagger.Component(modules ={GitHubModule.class})
    interface Component {
        void inject(RaveActivity activity);
    }
}
