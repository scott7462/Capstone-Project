package scott.com.workhard.app.ui.workout_create;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.entities.Workout;

/**
 * @author Pedro Scott. scott7462@gmail.com
 * @version 7/12/16.
 *          <p>
 *          Copyright (C) 2015 The Android Open Source Project
 *          <p/>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p/>
 * @see <a href = "http://www.aprenderaprogramar.com" /> http://www.apache.org/licenses/LICENSE-2.0 </a>
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class ActivityCreateWorkout extends BaseActivity {

    private static final String TYPE_VIEW = "TYPE_VIEW";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private static final String CONTENT_FRAGMENT = "content";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setToolbar(toolbar);
        savedFragmentState(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void savedFragmentState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            navigateMainContent(getSupportFragmentManager().getFragment(
                    savedInstanceState,CONTENT_FRAGMENT), getString(R.string.app_name));
        } else {
            navigateMainContent(FrgCreateOrUpdateWorkout.newInstance((Workout) getIntent().getParcelableExtra(Workout.WORKOUT_ARG)
            ,getIntent().getIntExtra(TYPE_VIEW,FrgCreateOrUpdateWorkout.NEW)), getString(R.string.frg_create_workout_title));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,CONTENT_FRAGMENT, getSupportFragmentManager().findFragmentById(R.id.container));
    }

    public static void newInstance(Activity activity, @Nullable Workout workout,int typeView) {
        Intent intent = new Intent(activity, ActivityCreateWorkout.class);
        if (workout != null) {
            intent.putExtra(Workout.WORKOUT_ARG, workout);
        }
        intent.putExtra(TYPE_VIEW,typeView);
        activity.startActivity(intent);
    }

}
