package store.utils;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import store.config.StoreConfig;
import store.viewer.OutputViewer;

public class RecoveryUtils {

    private static final OutputViewer VIEWER = StoreConfig.getOutputViewer();

    private RecoveryUtils() {
    }

    public static <T, R> R executeWithRetry(Supplier<T> inputSupplier, Function<T, R> processFunction) {
        while (true) {
            try {
                return processFunction.apply(inputSupplier.get());
            } catch (IllegalArgumentException e) {
                VIEWER.printError(e);
            }
        }
    }

    public static <T> void executeWithRetry(Supplier<T> inputSupplier, Consumer<T> processFunction) {
        while (true) {
            try {
                T input = inputSupplier.get();
                processFunction.accept(input);
                return;
            } catch (IllegalArgumentException e) {
                VIEWER.printError(e);
            }
        }
    }

    public static <T> T executeWithRetry(Supplier<T> inputSupplier) {
        while (true) {
            try {
                return inputSupplier.get();  //i supplier.get()만 실행해서 결과 반환
            } catch (IllegalArgumentException e) {
                VIEWER.printError(e);
            }
        }
    }



}