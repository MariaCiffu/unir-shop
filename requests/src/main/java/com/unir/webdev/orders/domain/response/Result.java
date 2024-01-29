package com.unir.webdev.orders.domain.response;

public sealed interface Result<S, E> {
    static <S, E> Result<S, E> success(S value) {
        return new Success<>(value);
    }

    static <S, E> Result<S, E> error(E value) {
        return new Error<>(value);
    }

    E getError();

    S getSuccess();

    ;

    boolean isSuccess();

    final class Success<S, E> implements Result<S, E> {
        S value;

        Success(S value) {
            this.value = value;
        }

        @Override
        public E getError() {
            return null;
        }

        @Override
        public S getSuccess() {
            return value;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }
    }

    final class Error<S, E> implements Result<S, E> {
        E value;

        Error(E value) {
            this.value = value;
        }

        @Override
        public E getError() {
            return value;
        }

        @Override
        public S getSuccess() {
            return null;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }
    }
}
