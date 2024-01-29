package com.unir.webdev.books.domain.response;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public sealed interface Result<S, E> {
    @Contract (value = "_ -> new", pure = true)
    static <S, E> @NotNull Result<S, E> success(S value) {
        return new Success<>(value);
    }

    @Contract (value = "_ -> new", pure = true)
    static <S, E> @NotNull Result<S, E> error(E error) {
        return new Error<>(error);
    }

    boolean isSuccess();

    S getSuccess();

    E getError();

    final class Success<S, E> implements Result<S, E> {
        private final S value;

        Success(S value) {
            this.value = value;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public S getSuccess() {
            return value;
        }

        @Contract (pure = true)
        @Override
        public @Nullable E getError() {
            return null;
        }
    }

    // Clase anidada para manejar errores
    final class Error<S, E> implements Result<S, E> {
        private final E error;

        Error(E error) {
            this.error = error;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Contract (pure = true)
        @Override
        public @Nullable S getSuccess() {
            return null;
        }

        @Override
        public E getError() {
            return error;
        }
    }
}
