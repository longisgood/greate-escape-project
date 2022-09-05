package net.koreate.greatescape.utils;

public class Criteria {
	  private int page;
	  
	  private int perPageNum;
	  
	  public Criteria() {
	    this(1, 10);
	  }
	  
	  public Criteria(int page, int perPageNum) {
	    this.page = page;
	    this.perPageNum = perPageNum;
	  }
	  
	  public int getPage() {
	    return this.page;
	  }
	  
	  public void setPage(int page) {
	    if (page <= 0) {
	      this.page = 1;
	      return;
	    } 
	    this.page = page;
	  }
	  
	  public int getPerPageNum() {
	    return this.perPageNum;
	  }
	  
	  public void setPerPageNum(int perPageNum) {
	    if (perPageNum <= 0 || perPageNum > 100) {
	      this.perPageNum = 10;
	      return;
	    } 
	    this.perPageNum = perPageNum;
	  }
	  
	  public int getStartRow() {
	    return (this.page - 1) * this.perPageNum;
	  }
	  
	  public String toString() {
	    return "Criteria [page=" + this.page + ", perPageNum=" + this.perPageNum + "]";
	  }
	}

