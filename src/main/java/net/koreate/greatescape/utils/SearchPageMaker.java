package net.koreate.greatescape.utils;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class SearchPageMaker extends PageMaker {
  public String makeQuery(int page) {
    SearchCriteria sCriteria = (SearchCriteria)getCri();
    UriComponents uri = UriComponentsBuilder.newInstance()
      .queryParam("page", new Object[] { Integer.valueOf(page) }).queryParam("perPageNum", new Object[] { Integer.valueOf(sCriteria.getPerPageNum()) }).queryParam("searchType", new Object[] { sCriteria.getSearchType() }).queryParam("keyword", new Object[] { sCriteria.getKeyword() }).build();
    String query = uri.toUriString();
    System.out.println(query);
    return query;
  }
}
