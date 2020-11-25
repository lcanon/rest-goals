package org.frontdev2ops.goals.app;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.event.Observes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoalApplicationLifeCycle {

  void onStart(@Observes StartupEvent ev) {
    log.info("Goals API");
  }

  void onStop(@Observes ShutdownEvent ev) {
    log.info("The application Goals is stopping...");
  }
}
