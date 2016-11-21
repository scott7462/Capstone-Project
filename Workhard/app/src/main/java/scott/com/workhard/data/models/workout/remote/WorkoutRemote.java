package scott.com.workhard.data.models.workout.remote;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import scott.com.workhard.R;
import scott.com.workhard.app.App;
import scott.com.workhard.data.models.workout.WorkoutRepository;
import scott.com.workhard.data.sourse.rest.api.RestClient;
import scott.com.workhard.data.sourse.rest.response.ResponseWorkout;
import scott.com.workhard.entities.Workout;

/**
 * Created by androiddev3 on 10/5/16.
 */

public class WorkoutRemote implements WorkoutRepository {

    private static WorkoutRemote instance;
    private RestClient restClient;

    public static WorkoutRemote newInstance() {
        if (instance == null) {
            instance = new WorkoutRemote();
        }
        return instance;
    }

    public WorkoutRemote() {
        restClient = new RestClient(App.getGlobalContext().getString(R.string.base_url));
    }

    public RestClient getRestClient() {
        return restClient;
    }

    @Override
    public Observable<Workout> add(Workout workout) {
        return getRestClient().getPrivateService().createWorkout(workout);
    }

    @Override
    public Observable<Boolean> delete(Workout workout) {
        return getRestClient().getPrivateService()
                .deleteWorkout(workout.getId())
                .flatMap(new Func1<Void, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Void aVoid) {
                        return Observable.just(true);
                    }
                });
    }

    @Override
    public Observable<Workout> update(Workout object) {
        return null;
    }


    @Override
    public Observable<List<Workout>> findAll() {
        return getRestClient().getPrivateService().getWorkouts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<ResponseWorkout, Observable<List<Workout>>>() {
                    @Override
                    public Observable<List<Workout>> call(ResponseWorkout responseWorkout) {
                        return Observable.just(responseWorkout.getWorkouts());
                    }
                });
    }

    @Override
    public Observable<List<Workout>> findMyWorkouts() {
        return getRestClient().getPrivateService().getMyWorkouts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<ResponseWorkout, Observable<List<Workout>>>() {
                    @Override
                    public Observable<List<Workout>> call(ResponseWorkout responseWorkout) {
                        return Observable.just(responseWorkout.getWorkouts());
                    }
                });
    }

    @Override
    public Observable<List<Workout>> findHistoriesWorkouts() {
        return getRestClient().getPrivateService().getHistoriesWorkouts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<ResponseWorkout, Observable<List<Workout>>>() {
                    @Override
                    public Observable<List<Workout>> call(ResponseWorkout responseWorkout) {
                        return Observable.just(responseWorkout.getWorkouts());
                    }
                });
    }
}
