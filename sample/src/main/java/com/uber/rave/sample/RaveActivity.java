package com.uber.rave.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import com.uber.rave.RaveException;
import com.uber.rave.sample.storage.DiskStorage;
import com.uber.rave.sample.network.GitHubModule;
import com.uber.rave.sample.network.GitHubService;
import com.uber.rave.sample.network.model.Owner;
import com.uber.rave.sample.network.model.Repo;
import com.uber.rave.sample.storage.StorageModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaveActivity extends Activity {

    @Inject GitHubService gitHubService;
    @Inject DiskStorage diskStorage;
    @BindView(R.id.use_valid_storage_object) CheckBox useValidStorageCheckBox;

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
                        makeToast("Call passed RAVE validation!");
                    }

                    @Override
                    public void onFailure(Call<Repo> call, Throwable t) {
                        Throwable cause = t.getCause();
                        if (cause instanceof RaveException) {
                            makeToast("Call did not pass RAVE validation: " + cause.getMessage());
                        } else if (cause != null) {
                            makeToast("Call failed for unknown reason: " + cause.getMessage());
                        }
                    }
                });
    }

    @OnClick(R.id.storage_request)
    void fetchFromStorage() {
        Owner original = new Owner();
        original.setId(1);

        // Not setting a login for an Owner will cause RAVE validation to fail because login is @NonNull.
        if (useValidStorageCheckBox.isChecked()) {
            original.setLogin("abc");
        }

        // Store to disk.
        diskStorage.storeOwner(original);

        // Retrieve from disk.
        try {
            diskStorage.getOwner();
            makeToast("Deserialized object passed RAVE validation");
        } catch (RuntimeException e) {
            if (e.getCause() instanceof RaveException) {
                makeToast("Deserializing object from storage failed RAVE validation: " + e.getCause().getMessage());
            } else {
                makeToast("Retrieving object from storage failed for unknown reason: " + e.getCause().getMessage());
            }
        }
    }

    private void makeToast(String text) {
        Toast.makeText(RaveActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @RaveActivityScope
    @dagger.Component(modules ={GitHubModule.class, StorageModule.class})
    interface Component {
        void inject(RaveActivity activity);
    }
}
