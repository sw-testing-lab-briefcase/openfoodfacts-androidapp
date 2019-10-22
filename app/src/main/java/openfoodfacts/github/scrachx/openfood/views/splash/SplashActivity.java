package openfoodfacts.github.scrachx.openfood.views.splash;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import openfoodfacts.github.scrachx.openfood.R;
import openfoodfacts.github.scrachx.openfood.views.BaseActivity;
import openfoodfacts.github.scrachx.openfood.views.OFFApplication;
import openfoodfacts.github.scrachx.openfood.views.WelcomeActivity;
import pl.aprilapps.easyphotopicker.EasyImage;

public class SplashActivity extends BaseActivity implements ISplashPresenter.View {
    @BindView(R.id.tagline)
    TextView tagline;

    private ISplashPresenter.Actions presenter;
    private String[] taglines;
    /*
    To show different slogans below the logo while content is being downloaded.
     */
    private Runnable changeTagline = new Runnable() {

        int i = 0;

        @Override
        public void run() {
            i++;
            if (i > taglines.length - 1) {
                i = 0;
            }
            tagline.setText(taglines[i]);
            tagline.postDelayed(changeTagline, 1500);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_splash);
        taglines = getResources().getStringArray(R.array.taglines_array);
        tagline.post(changeTagline);

        presenter = new SplashPresenter(getSharedPreferences("prefs", 0), this, this);
        presenter.refreshData();
    }

    @Override
    public void navigateToMainActivity() {
        EasyImage.configuration(this)
            .setImagesFolderName("OFF_Images")
            .saveInAppExternalFilesDir()
            .setCopyExistingPicturesToPublicLocation(true);
        Intent mainIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading(boolean isError) {
        if (isError) {
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(OFFApplication.getInstance(), R.string.errorWeb, Toast.LENGTH_LONG).show());
        }
    }

    @Override
    public AssetManager getAssetManager() {
        return getAssets();
    }
}
