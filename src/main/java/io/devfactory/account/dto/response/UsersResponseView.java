package io.devfactory.account.dto.response;

import static lombok.AccessLevel.PRIVATE;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class UsersResponseView {

  private int count;
  private List<UserResponseView> users;

  @Builder(builderMethodName = "of")
  private UsersResponseView(List<UserResponseView> views) {
    this.count = views.size();
    this.users = views;
  }
  
}
