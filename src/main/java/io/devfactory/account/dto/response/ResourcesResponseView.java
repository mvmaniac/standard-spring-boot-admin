package io.devfactory.account.dto.response;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class ResourcesResponseView {

  private int count;
  private List<ResourceResponseView> resources;

  private ResourcesResponseView(List<ResourceResponseView> views) {
    this.count = views.size();
    this.resources = views;
  }

  public static ResourcesResponseView of(List<ResourceResponseView> views) {
    return new ResourcesResponseView(views);
  }

}
