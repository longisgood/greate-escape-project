package net.koreate.greatescape.utils;

public class SearchCriteria extends Criteria {
	  private String searchType;
	  
	  private String keyword;
	  
	  public String getSearchType() {
	    return this.searchType;
	  }
	  
	  public String getKeyword() {
	    return this.keyword;
	  }
	  
	  public void setSearchType(String searchType) {
	    this.searchType = searchType;
	  }
	  
	  public void setKeyword(String keyword) {
	    this.keyword = keyword;
	  }
	  
	  public SearchCriteria() {}
	  
	  public String toString() {
	    return "SearchCriteria(super=" + super.toString() + ", searchType=" + getSearchType() + ", keyword=" + getKeyword() + ")";
	  }
	  
	  public SearchCriteria(int page, int perPageNum, String searchType, String keyword) {
	    super(page, perPageNum);
	    this.searchType = searchType;
	    this.keyword = keyword;
	  }
	}