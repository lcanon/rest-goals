package org.frontdev2ops.goals.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.ToString;

@Entity
@ToString
@Schema(description = "Goals")
@JsonIgnoreProperties(ignoreUnknown = false)
public class Goal extends PanacheEntity {

  @NotNull
  @Size(min = 3, max = 50)
  public String name;
  @NotNull
  @Min(1)
  public Double total;
  @JsonIgnore
  public Double actual;
  @JsonIgnore
  public Integer completed;
  @JsonIgnore
  public String urlImage;
  @JsonIgnore
  public Long userId;


}
