package softuni.exam.instagraphlite.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

@Column(nullable = false)
private String caption;

@NotNull
@ManyToOne
private User user;

@NotNull
@ManyToOne
private Picture picture;
}
