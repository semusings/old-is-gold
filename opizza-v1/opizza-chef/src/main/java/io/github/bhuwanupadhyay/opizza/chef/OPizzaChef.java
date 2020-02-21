package io.github.bhuwanupadhyay.opizza.chef;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;

@Command(

    description = "OPizza Tool For Chef",
    name = "opizza",
    mixinStandardHelpOptions = true,
    version = "opizza 1.0"

)
@Slf4j
public class OPizzaChef implements Callable<Void> {

    @Option(names = {"-a", "--action"}, description = "install, build, deploy, clean")
    private String action = "install";

    public static void main(String[] args) {
        CommandLine.call(new OPizzaChef(), args);
    }

    @Override
    public Void call() {


        return null;
    }
}
