package cn.hiboot.framework.research.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2021/7/6 13:57
 */
public class ReactorDemo {

    @Test
    public void flux(){
        Flux.just("Hello", "World").subscribe(System.out::println);

        Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);

        Flux.empty().subscribe(System.out::println);

        Flux.range(1, 10).subscribe(System.out::println);

        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);

        Flux.range(1, 1000).take(10).subscribe(System.out::println);

        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);

        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);

        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);
    }

    @Test
    public void mono(){
        Mono.fromSupplier(() -> "Hello").subscribe(System.out::println);

        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);

        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);
    }

}
