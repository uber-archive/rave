package com.uber.rave.sample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.uber.rave.Rave;
import com.uber.rave.RaveException;
import com.uber.rave.sample.github.GitHubModule;
import com.uber.rave.sample.github.GitHubService;
import com.uber.rave.sample.github.model.Owner;
import com.uber.rave.sample.github.model.Repo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

        //Commenting out the line below will cause this to fail RAVE validation. This emulates if you had invalid
        //date serialized and stored in storage, and retrieved at a later point. Though the object exists, it is no
        //longer valid with our new Nullness Annotations.
        original.setLogin("abc");
        original.setId(1);

        Owner fromStorage = deserialize(serialize(original));
        if (fromStorage == null) {
            makeToast("Could not serialize/deserialize");
            return;
        }

        if (!fromStorage.equals(original)) {
            makeToast("Serialization/deserialization had problems, original and new object not equal.");
            return;
        }

        try {
            Rave.getInstance().validate(fromStorage);
            makeToast("Storage object passed RAVE validation");
        } catch (RaveException e) {
            makeToast("Storage object failed RAVE validation");
        }
    }

    private byte[] serialize(Owner owner) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(owner);
            out.flush();
            return bos.toByteArray();
        } catch (IOException ignored) {
            return new byte[0];
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

    private Owner deserialize(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(bis);
            return (Owner) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
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
