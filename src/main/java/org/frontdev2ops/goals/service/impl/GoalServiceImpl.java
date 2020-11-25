package org.frontdev2ops.goals.service.impl;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.frontdev2ops.goals.domain.Goal;
import org.frontdev2ops.goals.service.api.GoalService;

@ApplicationScoped
@Transactional(REQUIRED)
public class GoalServiceImpl implements GoalService {

  @Override
  @Transactional(SUPPORTS)
  public List<Goal> findAll() {
    return Goal.listAll();
  }


  @Override
  @Transactional(SUPPORTS)
  public Optional<Goal> findById(Long id) {
    return Goal.findByIdOptional(id);
  }

  @Override
  public Goal update(@Valid Goal goal) {
    Goal entity = Goal.findById(goal.id);
    entity.name = goal.name;
    return entity;
  }

  @Override
  public Goal save(@Valid Goal goal) {
    goal.persistAndFlush();
    return goal;
  }

  @Override
  public void delete(Long id) {
    Optional<PanacheEntityBase> goal = Goal.findByIdOptional(id);
    goal.ifPresent(PanacheEntityBase::delete);
  }
}
