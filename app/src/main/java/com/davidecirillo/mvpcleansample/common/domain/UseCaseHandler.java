package com.davidecirillo.mvpcleansample.common.domain;


public class UseCaseHandler {

    private static UseCaseHandler INSTANCE;

    private final UseCaseScheduler mUseCaseScheduler;

    UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        mUseCaseScheduler = useCaseScheduler;
    }

    public <T extends BaseUseCase.RequestValues, R extends BaseUseCase.ResponseValues> void execute(
            final BaseUseCase<T, R> useCase,
            T values,
            BaseUseCase.UseCaseCallback<R> callback) {

        useCase.setRequestValues(values);
        useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));

        // The network request might be handled in a different thread so make sure
        // Espresso knows
        // that the app is busy until the response is handled.
//        EspressoIdlingResource.increment(); // App is busy until further notice

        mUseCaseScheduler.execute(new Runnable() {
            @Override
            public void run() {

                useCase.run();
                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
//                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
//                    EspressoIdlingResource.decrement(); // Set app as idle.
//                }
            }
        });
    }

    private <V extends BaseUseCase.ResponseValues> void notifyResponse(final V response,
                                                                       final BaseUseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.onSuccess(response, useCaseCallback);
    }

    private <V extends BaseUseCase.ResponseValues> void notifyError(final BaseUseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.onError(useCaseCallback);
    }

    static final class UiCallbackWrapper<V extends BaseUseCase.ResponseValues> implements
            BaseUseCase.UseCaseCallback<V> {
        private final BaseUseCase.UseCaseCallback<V> mCallback;
        private final UseCaseHandler mUseCaseHandler;

        UiCallbackWrapper(BaseUseCase.UseCaseCallback<V> callback,
                          UseCaseHandler useCaseHandler) {
            mCallback = callback;
            mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(V response) {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }

        @Override
        public void onError() {
            mUseCaseHandler.notifyError(mCallback);
        }
    }

    public static UseCaseHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }
        return INSTANCE;
    }
}
