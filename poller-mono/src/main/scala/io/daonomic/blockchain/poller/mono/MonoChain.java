package io.daonomic.blockchain.poller.mono;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.Iterator;
import java.util.function.Function;

public class MonoChain<T> {
    private final MonoSink<Void> sink;
    private final Iterator<T> values;
    private final Function<T, Mono<Void>> f;

    public MonoChain(MonoSink<Void> sink, Iterator<T> values, Function<T, Mono<Void>> f) {
        this.sink = sink;
        this.values = values;
        this.f = f;
    }


    private void onNext() {
        if (values.hasNext()) {
            f.apply(values.next()).subscribe(new Subscriber<Void>() {
                @Override
                public void onSubscribe(Subscription s) {

                }

                @Override
                public void onNext(Void aVoid) {

                }

                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }

                @Override
                public void onComplete() {
                    MonoChain.this.onNext();
                }
            });
        } else {
            sink.success();
        }
    }

    public static <T> Mono<Void> consequently(Iterable<T> chain, Function<T, Mono<Void>> f) {
        return Mono.create(sink -> new MonoChain<>(sink, chain.iterator(), f).onNext());
    }
}
