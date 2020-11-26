package org.frontdev2ops.goals.service.impl;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.text.NumberFormat;
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
  public Optional<Goal> findById(Long goalId) {
    return Goal.findByIdOptional(goalId);
  }

  @Override
  @Transactional(SUPPORTS)
  public List<Goal> findAllOfUser(Long userId) {
    return Goal.list("userId", userId);
  }


  @Override
  public Goal update(@Valid Goal goal) {
    Goal entity = Goal.findById(goal.id);
    entity.name = goal.name;
    return entity;
  }


  @Override
  public Goal save(@Valid Goal goal) {
    goal.persist();
    return goal;
  }

  @Override
  public void delete(Long id) {
    Optional<PanacheEntityBase> goal = Goal.findByIdOptional(id);
    goal.ifPresent(PanacheEntityBase::delete);
  }

  @Override
  public Optional<Goal> addTip(Long goalId, Double tipAmount) {
    Optional<Goal> entity = Goal.findByIdOptional(goalId);
    entity.ifPresent(goal -> {
      if(goal.actual+tipAmount <= goal.total) {
        goal.actual += tipAmount;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(3);
        goal.completed = (int) Math.floor((goal.actual / goal.total) * 100);
      }
    });
    return entity;
  }


}
