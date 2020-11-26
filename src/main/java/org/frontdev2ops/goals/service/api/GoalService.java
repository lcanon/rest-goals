package org.frontdev2ops.goals.service.api;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.frontdev2ops.goals.domain.Goal;

public interface GoalService {

  Optional<Goal> findById(Long goalId);

  List<Goal> findAllOfUser(Long userId);

  Goal update(@Valid Goal goal);

  Goal save(@Valid Goal goal);

  void delete(Long goalId);

  Optional<Goal> addTip(Long goalId, Double tipAmount);
}
