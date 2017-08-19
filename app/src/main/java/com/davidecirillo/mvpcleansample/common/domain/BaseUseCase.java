package com.davidecirillo.mvpcleansample.common.domain;


public abstract class BaseUseCase<Q extends BaseUseCase.RequestValues, P extends BaseUseCase.ResponseValues> {

    private Q mRequestValues;

    private UseCaseCallback<P> mUseCaseCallback;

    void setRequestValues(Q requestValues) {
        mRequestValues = requestValues;
    }

    public Q getRequestValues() {
        return mRequestValues;
    }

    protected UseCaseCallback<P> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        mUseCaseCallback = useCaseCallback;
    }

    void run() {
        executeUseCase(mRequestValues);
    }

    protected abstract void executeUseCase(Q requestValues);

    /**
     * Data passed to a request.
     */
    public interface RequestValues {
    }

    /**
     * Data received from a request.
     */
    public interface ResponseValues {
    }

    public interface UseCaseCallback<R> {
        void onSuccess(R response);
        void onError();
    }
}