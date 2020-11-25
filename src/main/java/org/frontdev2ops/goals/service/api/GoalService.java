package org.frontdev2ops.goals.service.api;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.frontdev2ops.goals.domain.Goal;

public interface GoalService {

  List<Goal> findAll();

  Optional<Goal> findById(Long id);

  Goal update(@Valid Goal goal);

  Goal save(@Valid Goal goal);

  void delete(Long id);
}
