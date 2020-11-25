package org.frontdev2ops.goals.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.ToString;

@Entity
@ToString
@Schema(description = "Goals")
public class Goal extends PanacheEntity {

  @NotNull
  @Size(min = 3, max = 50)
  public String name;
  public Long price;



}
