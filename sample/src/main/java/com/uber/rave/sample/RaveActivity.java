package com.uber.rave.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import com.uber.rave.RaveException;
import com.uber.rave.sample.github.OwnerStorage;
import com.uber.rave.sample.github.GitHubModule;
import com.uber.rave.sample.github.GitHubService;
import com.uber.rave.sample.github.model.Owner;
import com.uber.rave.sample.github.model.Repo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaveActivity extends Activity {

    @Inject GitHubService gitHubService;
    @Inject OwnerStorage ownerStorage;
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

        // Not setting a login for an Owner will cause this to fail RAVE validation. This emulates if you had an older
        // model serialized and stored in storage without the required "login" field, and then retrieved at a later
        // point after it has been added. Though the object exists, it's no longer valid with our Nullness Annotations.
        if (useValidStorageCheckBox.isChecked()) {
            original.setLogin("abc");
        }
        original.setId(1);

        ownerStorage.storeOwner(original);


        try {
            ownerStorage.getOwner();
            makeToast("Deserialized object passed RAVE validation");
        } catch (RuntimeException e) {
            if (e.getCause() instanceof RaveException) {
                makeToast("Deserialized object failed RAVE validation: " + e.getCause().getMessage());
            } else {
                makeToast("Failed for unknown reason: " + e.getCause().getMessage());
            }
        }
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
