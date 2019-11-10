package com.example.githubapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    ImageView avatar;
    CircleImageView circleImageView;
    TextView username, logIn, followers, following, email;
    Bundle extras;
    String newString;
    Bitmap myImage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //avatar = findViewById(R.id.avatar);
        circleImageView = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        logIn = findViewById(R.id.logIn);
        followers = findViewById(R.id.followers);
        following = findViewById(R.id.following);
        email = findViewById(R.id.email);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading the profile");

        extras = getIntent().getExtras();
        newString = extras.getString("STRING_I_NEED");

        loadData();
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void loadData() {

        progressDialog.show();

        final GitHubUserEndPoints apiService = APIClient.getClient()
                .create(GitHubUserEndPoints.class);
        Call<GitHubUserModel> call = apiService.getUser(newString);

        call.enqueue(new Callback<GitHubUserModel>() {
            @Override
            public void onResponse(Call<GitHubUserModel> call, Response<GitHubUserModel> response) {

                progressDialog.dismiss();

                ImageDownloader task = new ImageDownloader();

                try {
                    myImage = task.execute(response.body().getAvatar()).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*
                avatar.setImageBitmap(myImage);
                avatar.getLayoutParams().height=420;
                avatar.getLayoutParams().width=420;
                 */

                circleImageView.setImageBitmap(myImage);


                if(response.body().getName() == null) {
                    username.setText("No name provided!");
                } else
                {
                    username.setText(response.body().getName());
                }

                if(response.body().getEmail() == null) {
                    email.setText("No email provided!");
                } else
                {
                    email.setText("Email: "+ response.body().getEmail());
                }

               followers.setText(response.body().getFollowers());
               following.setText(response.body().getFollowing());
               logIn.setText(response.body().getLogin());

            }

            @Override
            public void onFailure(Call<GitHubUserModel> call, Throwable t) {

                progressDialog.dismiss();

                Toast.makeText(UserActivity.this,
                        t.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showRepos(View view){
        Intent intent = new Intent(UserActivity.this,Repositories.class);
        intent.putExtra("username", newString);
        startActivity(intent);
    }
}
